package xyz.taotao.docbook.core.util;

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
