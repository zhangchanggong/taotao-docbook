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
