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
import org.objectweb.asm.*;
import org.objectweb.asm.util.TraceClassVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;
import java.util.Objects;

/**
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/9/15 11:38 下午
 */
public class AgentMain {
    private static Logger log = LoggerFactory.getLogger(AgentMain.class);

    public static void premain(String agentArgs, Instrumentation instrumentation)
            throws ClassNotFoundException, UnmodifiableClassException, InterruptedException {
        log.info("Agent start...");
        instrumentation.addTransformer(new Transformer(), true);
    }

    static class Transformer implements ClassFileTransformer {
        /**
         * 方法增强相关实现
         *
         * @param classfileBuffer
         * @return
         */
        private byte[] enhancement(byte[] classfileBuffer) {
            ClassReader reader = new ClassReader(classfileBuffer);
            String className = reader.getClassName();
            String[] interfaces = reader.getInterfaces();//获取目标类接口，检查是否继承了协程接口Coroutine
            if (Objects.nonNull(interfaces) && interfaces.length > 0) {
                boolean isEnhancement = false;
                for (String interfaceName : interfaces) {//只有标记有Coroutine接口的类型才需要增强
                    if (interfaceName.equals(Constants.COROUTINE_INTERNAL_NAME)) {
                        isEnhancement = true;
                        break;
                    }
                }
                try {
                    //相关增强逻辑
                    if (isEnhancement) {
                        ClassWriter writer = new ClassWriter(0);//声明生成类
                        reader.accept(new ClassEnhancementAdapter(Opcodes.ASM5,
                                new TraceClassVisitor(writer, new PrintWriter(System.out)), className), 0);//分析入口
                        return writer.toByteArray();//返回增强后的字节码
                    }
                } catch (Throwable e) {
                    log.error("method:{},Enhancement failed!!!", className, e);
                }
            }
            return classfileBuffer;
        }

        @Override
        public byte[] transform(ClassLoader loader, String className,
                                Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                                byte[] classfileBuffer) throws IllegalClassFormatException {
            return enhancement(classfileBuffer);
        }
    }
}
