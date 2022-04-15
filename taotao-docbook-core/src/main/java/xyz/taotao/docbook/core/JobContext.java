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
    private String docbookDir;
    /**
     * docbook 首文件文件名
     */
    private String docbookFile;
    /**
     * 工作目录
     */
    private String workDir;
    /**
     * 目标文件所在的路径（不包括文件本身）
     * vfs 格式
     */
    private String descDir;
    /**
     * 目标文件文件名
     */
    private String descFile;
    /**
     * 输出的参考语言
     */
    private String language;
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
    /**
     * 提供通用资源的路径
     * vfs 格式
     */
    private String[] resourcePaths;
    /**
     * 提供字体的路径
     * vfs 格式
     */
    private String[] fontPaths;
    /**
     * 提供docx 西苑的路径
     */
    private String[] docxPaths;

    /**
     * xslt 首文件所在的路径（不包括文件本身）
     * vfs 格式
     */
    private String xsltDir;
    /**
     * xslt 首文件所在的路径，如果是绝对路径，则 xsltDirPath 不生效
     * vfs 格式
     */
    private String xsltFile;
}
