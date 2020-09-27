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
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Objects;

/**
 * 增强类
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/9/17 2:24 上午
 */
public class ClassEnhancementAdapter extends ClassVisitor {
    private String className;
    private boolean isInterface;

    public ClassEnhancementAdapter(int api, ClassVisitor cv, String className) {
        super(api, cv);
        this.className = className;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
    }

    @Override
    public org.objectweb.asm.MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
        /**
         * 方法增强需要满足几个条件：
         * 1、非接口类型
         * 2、MethodVisitor非空
         * 3、非<init>函数
         * 4、匹配run()函数
         */
        if (!isInterface && Objects.nonNull(methodVisitor)
                && !name.equals(Constants.CONSTRUCT_INTERNAL_NAME)
                && name.equals(Constants.DEFAULT_COROUTINE_METHOD)) {
            methodVisitor = new MethodEnhancementAdapter(methodVisitor, className);
        }
        return methodVisitor;
    }
}
