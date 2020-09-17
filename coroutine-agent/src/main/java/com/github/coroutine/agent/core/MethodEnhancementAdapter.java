/*
 * Copyright 2019-2119 gao_xianglong@sina.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.coroutine.agent.core;

import com.github.coroutine.agent.Constants;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 方法增强类
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/9/17 2:19 上午
 */
public class MethodEnhancementAdapter extends MethodVisitor {
    private MethodVisitor mv;
    private String className;
    private Label l0;
    private boolean isEnhancement;

    public MethodEnhancementAdapter(MethodVisitor methodVisitor, String className, Label l0) {
        super(Opcodes.ASM5, methodVisitor);
        this.mv = methodVisitor;
        this.className = className;
        this.l0 = l0;
    }

    @Override
    public void visitCode() {
        mv.visitCode();
        mv.visitFieldInsn(Opcodes.GETSTATIC, className, "code", "Ljava/lang/ThreadLocal;");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/ThreadLocal", "get", "()Ljava/lang/Object;", false);
        mv.visitJumpInsn(Opcodes.IFNONNULL, l0);
    }

    @Override
    public void visitEnd() {
        if (!isEnhancement) {
            mv.visitLabel(l0);
            mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            mv.visitInsn(Opcodes.RETURN);
        }
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        mv.visitMaxs(maxStack + 4, maxLocals + 4);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        //INVOKEVIRTUAL suspend时才继续增强
        if (opcode == Opcodes.INVOKEVIRTUAL && name.equals(Constants.SUSPEND_METHOD_NAME)) {
            mv.visitInsn(Opcodes.POP);
            mv.visitFieldInsn(Opcodes.GETSTATIC, className, "code", "Ljava/lang/ThreadLocal;");
            mv.visitInsn(Opcodes.ICONST_1);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/ThreadLocal", "set", "(Ljava/lang/Object;)V", false);
            mv.visitFieldInsn(Opcodes.GETSTATIC, className, "param", "Ljava/lang/ThreadLocal;");
            mv.visitVarInsn(Opcodes.ILOAD, 1);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/ThreadLocal", "set", "(Ljava/lang/Object;)V", false);
            mv.visitInsn(Opcodes.RETURN);
            mv.visitLabel(l0);
            mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            mv.visitFieldInsn(Opcodes.GETSTATIC, className, "param", "Ljava/lang/ThreadLocal;");
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/ThreadLocal", "get", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Integer");
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
            mv.visitVarInsn(Opcodes.ISTORE, 1);
            mv.visitFieldInsn(Opcodes.GETSTATIC, className, "code", "Ljava/lang/ThreadLocal;");
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/ThreadLocal", "remove", "()V", false);
            mv.visitFieldInsn(Opcodes.GETSTATIC, className, "param", "Ljava/lang/ThreadLocal;");
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/ThreadLocal", "remove", "()V", false);
            isEnhancement = true;
            return;
        }
        mv.visitMethodInsn(opcode, owner, name, desc, itf);
    }
}