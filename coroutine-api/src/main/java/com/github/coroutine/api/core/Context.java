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
package com.github.coroutine.api.core;

/**
 * 保存函数调用堆栈
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/9/27 1:46 下午
 */
public class Context {
    /**
     * 指令标记,类似指令位置
     */
    public int flag;
    /**
     * 方法入参
     */
    public Object param;

    public Context(int flag) {
        this.flag = flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "Context{" +
                "flag=" + flag +
                ", param=" + param +
                '}';
    }
}
