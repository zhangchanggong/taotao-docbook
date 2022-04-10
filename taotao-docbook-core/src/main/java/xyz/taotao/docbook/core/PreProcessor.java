package xyz.taotao.docbook.core;

/**
 * 前处理器接口
 * @param <T> 处理器配置类型
 */
public interface PreProcessor<T extends ProcessorConfig>  extends Processor<T> {
}
