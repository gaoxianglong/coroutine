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

import com.github.coroutine.api.exception.CoroutineException;

/**
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/9/16 1:58 上午
 */
public interface Coroutine {
    /**
     * 任务的运行时上下文状态标记
     */
    ThreadLocal<Integer> code = new ThreadLocal<>();
    /**
     * 函数调用堆栈信息
     */
    ThreadLocal<Object> param = new ThreadLocal<>();

    /**
     * 挂起函数,用于挂起协程
     */
    default void suspend() {
        throw new CoroutineException();
    }

    /**
     * 协程函数
     *
     * @param param
     */
    void run(int param);
}
