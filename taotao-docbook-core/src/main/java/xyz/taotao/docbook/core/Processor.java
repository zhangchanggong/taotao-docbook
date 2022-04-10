package xyz.taotao.docbook.core;

/**
 * 通用的处理器
 * @param <T> 处理器配置类型
 */
public interface Processor<T extends ProcessorConfig> {
    /**
     * 获取配置参数
     * @return 配置参数的key
     */
    String getConfigKey();
    /**
     * 处理过程
     * @param config 处理器配置参数
     * @throws TaotaoDocbookException 可能的抛出的异常
     */
    void process(T config) throws TaotaoDocbookException;
}
