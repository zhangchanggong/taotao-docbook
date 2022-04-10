package xyz.taotao.docbook.core;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 过程配置参量和运行上下文
 */
@Setter
@Getter
public class JobContext {

    /**
     * docbook 首文件所在的路径（不包括文件本身）
     * vfs 格式
     */
    private String docbookPath;
    /**
     * docbook 首文件文件名
     */
    private String docbookFileName;
    /**
     * 目标文件所在的路径（不包括文件本身）
     * vfs 格式
     */
    private String descPath;
    /**
     * 目标文件文件名
     */
    private String descFileName;
    /**
     * 预处理器配置
     */
    private Map<String, ProcessorConfig> preProcessorConfigs;
    /**
     * 后处理器配置
     */
    private Map<String, ProcessorConfig> postProcessorConfigs;
    /**
     * docbook 处理过程的配置
     */
    private ProcessorConfig docbookProcessorConfig;

}
