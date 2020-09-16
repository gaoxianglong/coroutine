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
package com.github.coroutine.api.exception;

/**
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/9/16 2:56 上午
 */
public class CoroutineException extends RuntimeException{
    private static final long serialVersionUID = -3134040291970858742L;

    public CoroutineException() {
        super();
    }

    public CoroutineException(String str) {
        super(str);
    }

    public CoroutineException(Throwable throwable) {
        super(throwable);
    }

    public CoroutineException(String str, Throwable throwable) {
        super(str, throwable);
    }
}
