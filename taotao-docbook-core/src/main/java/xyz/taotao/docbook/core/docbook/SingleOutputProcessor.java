package xyz.taotao.docbook.core.docbook;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import xyz.taotao.docbook.core.ClassNameConfigKeyProcesser;
import xyz.taotao.docbook.core.DocbookProcessor;
import xyz.taotao.docbook.core.ProcessorConfig;
import xyz.taotao.docbook.core.TaotaoDocbookException;
import xyz.taotao.docbook.core.util.XmlUtils;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.SAXSource;

/**
 * 单文件输出的 docbook 处理过程
 */
@Slf4j
public class SingleOutputProcessor extends ClassNameConfigKeyProcesser<SingleOutputProcessor.SingleOutputProcessorConfig> implements DocbookProcessor<SingleOutputProcessor.SingleOutputProcessorConfig> {
    /**
     * 处理过程
     *
     * @param config 处理器配置参数
     * @throws TaotaoDocbookException 可能的抛出的异常
     */
    @Override
    public void process(SingleOutputProcessorConfig config) throws TaotaoDocbookException {
        log.info("SingleOutputProcessor.process config={}", config);
        Transformer transformer = XmlUtils.getTransformer(config.xsltFile, config.xsltDir);
        SAXSource srcSource = XmlUtils.getSAXSource(config.docbookFile, config.docbookDir);
        Result descResult = XmlUtils.getResult(config.resultFile, config.resultDir);
        try {
            transformer.transform(srcSource, descResult);
        } catch (TransformerException e) {
            log.warn("xslt 装换错误 config={}", config, e);
            throw new TaotaoDocbookException("xslt 转换错误", e);
        }
    }

    @Setter
    @Getter
    public static class SingleOutputProcessorConfig extends ProcessorConfig {
        /**
         * docbook 首文件所在的路径（不包括文件本身）
         * vfs 格式
         */
        private String docbookDir;
        /**
         * docbook 首文件路径，如果是绝对路径，则 docbookDir 不生效
         */
        private String docbookFile;
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
        /**
         * 目标文件所在的路径（不包括文件本身）
         * vfs 格式
         */
        private String resultDir;
        /**
         * 目标文件所在的路径，如果是绝对路径，则 resultDir 不生效
         * vfs 格式
         */
        private String resultFile;
    }
}
