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
package com.github.coroutine.client;


import com.github.coroutine.api.core.Coroutine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/9/16 1:57 上午
 */
public class UseCoroutine implements Coroutine {
    private Logger log = LoggerFactory.getLogger(Main.class);

    @Override
    public void run(int param) {
        log.info("tid:{},param:{}", Thread.currentThread().getId(), param);
        param <<= 2;
        suspend();//调用挂起函数挂起协程
        log.info("tid:{},param:{}", Thread.currentThread().getId(), param);
    }
}
