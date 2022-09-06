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

package store.taotao.docbook.core.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 配合 SPI 获取service
 */
@Slf4j
public class ServiceUtils {
    /**
     * 获取指定 class 的 service 实现
     * @param clazz 指定的 class
     * @param <T> Service 定义
     * @return SPI 指定的第一个实现
     */
    public static <T> T getService(Class<T> clazz){
        log.debug("------------------ getService 开始 -----------------");
        Iterator<T> serviceIterator = ServiceLoader.load(clazz).iterator();
        if (!serviceIterator.hasNext()){
            log.debug("未找到 service");
            log.debug("------------------ getService 结束 -----------------");
            return null;
        }
        T service = serviceIterator.next();
        log.debug("service=[{}]",service);
        log.debug("------------------ getService 结束 -----------------");
        return service;
    }

    private ServiceUtils(){}
}
