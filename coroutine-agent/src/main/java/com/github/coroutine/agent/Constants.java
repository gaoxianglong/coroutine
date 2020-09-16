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
package com.github.coroutine.agent;

/**
 * 缺省静态常量相关类
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/9/17 1:33 上午
 */
public class Constants {
    /**
     * 协程接口内部名
     */
    public static final String COROUTINE_INTERNAL_NAME = "com/github/coroutine/api/core/Coroutine";

    /**
     * 协程挂起函数名称
     */
    public static final String SUSPEND_METHOD_NAME = "suspend";
    /**
     * 构造函数内部名
     */
    public static final String CONSTRUCT_INTERNAL_NAME = "<init>";
    /**
     * 协程函数名称
     */
    public static final String DEFAULT_COROUTINE_METHOD = "run";
}
