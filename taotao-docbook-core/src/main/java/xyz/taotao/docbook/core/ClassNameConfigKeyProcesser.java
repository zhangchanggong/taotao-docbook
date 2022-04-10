package xyz.taotao.docbook.core;

/**
 * 基于类名定义的 config key 的处理器
 * @param <T> 配置类型
 */
public abstract class ClassNameConfigKeyProcesser<T extends ProcessorConfig> implements Processor<T>{
    /**
     * 获取配置参数
     *
     * @return 配置参数的key
     */
    @Override
    public String getConfigKey() {
        return this.getClass().getCanonicalName();
    }
}
