/*
 * Copyright 2024 王金涛
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package store.taotao.docbook.core;

/**
 * 基本 job 接口
 * @param <T> Job 配置类型
 */
public interface Job<T extends JobContext> {
    /**
     * job 的处理过程
     * @param jobContext 处理中的上下文
     * @throws TaotaoDocbookException 发生不可修复的异常
     */
    void process(T jobContext) throws TaotaoDocbookException;
}
