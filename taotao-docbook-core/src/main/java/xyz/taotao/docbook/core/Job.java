/*
 * Copyright © 2022 王金涛。
 * This file is part of taotao-docbook.
 *
 * taotao-docbook is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * taotao-docbook is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE. See the GNU Lesser Public License for more details.
 *
 * You should have received a copy of the GNU Lesser Public License along with taotao-docbook. If
 * not, see <https://www.gnu.org/licenses/>.
 */

package xyz.taotao.docbook.core;

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
