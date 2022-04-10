package xyz.taotao.docbook.core;

import lombok.extern.slf4j.Slf4j;

/**
 * 基于类名定义的 config key 的处理器
 * @param <T> 配置类型
 */
@Slf4j
public abstract class ClassNameConfigKeyProcesser<T extends ProcessorConfig> implements Processor<T> {
    protected Class<T> configClass;

    protected ClassNameConfigKeyProcesser(Class<T> clazz){
        this.configClass=clazz;
    }

    /**
     * 获取配置参数
     *
     * @return 配置参数的key
     */
    @Override
    public String getConfigKey() {
        return this.getClass().getCanonicalName();
    }

    /**
     * 处理过程
     *
     * @param config 处理器配置参数
     * @throws TaotaoDocbookException 可能的抛出的异常
     */
    @Override
    public void process(ProcessorConfig config) throws TaotaoDocbookException {
        if (null!=config&&!configClass.isInstance(config)){
            log.warn("config 的数据类型错误");
            log.warn("config=[{}]",config);
            log.warn("configClass=[{}]",configClass);
            throw new TaotaoDocbookException("config 的数据类型错误");
        }
        doProcess((T)config);
    }

    protected abstract void doProcess(T config) throws TaotaoDocbookException;
}
