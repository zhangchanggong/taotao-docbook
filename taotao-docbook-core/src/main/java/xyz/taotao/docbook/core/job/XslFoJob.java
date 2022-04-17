package xyz.taotao.docbook.core.job;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import xyz.taotao.docbook.core.*;
import xyz.taotao.docbook.core.docbook.SingleOutputProcessor;
import xyz.taotao.docbook.core.postprocessor.FopPostProcessor;
import xyz.taotao.docbook.core.preprocessor.CleanPreProcessor;
import xyz.taotao.docbook.core.preprocessor.ResourceProcessor;
import static xyz.taotao.docbook.core.TaotaoDocbookConstant.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 基于 xsl-fo 的
 */
@Slf4j
public class XslFoJob implements Job<XslFoJob.XslFoJobContext> {

    private PreProcessor<?>[] preProcessors=new PreProcessor[]{
            new CleanPreProcessor(),
            new ResourceProcessor()
    };

    private DocbookProcessor<?> docbookProcessor=new SingleOutputProcessor();

    private PostProcessor<?>[] postProcessors=new PostProcessor[]{
            new FopPostProcessor()
    };

    @Override
    public void process(XslFoJobContext jobContext) throws TaotaoDocbookException {
        log.info("-------------------- XslFoJob 处理开始 --------------------");
        initContext(jobContext);

        for (PreProcessor<?> processor:preProcessors){
            processor.process(jobContext.getPreProcessorConfigs().get(processor.getConfigKey()));
        }
        docbookProcessor.process(jobContext.getDocbookProcessorConfig());
        for (PostProcessor<?> processor:postProcessors){
            processor.process(jobContext.getPostProcessorConfigs().get(processor.getConfigKey()));
        }

        log.info("-------------------- XslFoJob 处理结束 --------------------");
    }

    private void initContext(XslFoJobContext jobContext) {
        jobContext.setPreProcessorConfigs(new HashMap<>());
        jobContext.setPostProcessorConfigs(new HashMap<>());

        initCleanConfig(jobContext);
        initResourceConfig(jobContext);

        initProcessorConfig(jobContext);

        initFopConfig(jobContext);
    }

    private void initFopConfig(XslFoJobContext jobContext) {
        FopPostProcessor.FopPostConfig config=new FopPostProcessor.FopPostConfig();

        config.setTargetResolution(jobContext.targetResolution);
        config.setStagingDir(StringUtils.joinWith("/",jobContext.getWorkDir(),STAGING_DIR));
        config.setOutFile(jobContext.getDescFile());
        config.setOutDir(jobContext.getDescDir());
        config.setMimeType(jobContext.mimeType);
        config.setFoFile(jobContext.getDocbookFile());
        config.setFopConfigPath(jobContext.fopConfigPath);
        config.setFoDir(StringUtils.joinWith("/",jobContext.getWorkDir(),STAGING_DIR,FO_DIR));
        config.setLanguage(jobContext.getLanguage());
        config.setResourceDir(StringUtils.joinWith("/",jobContext.getWorkDir(),STAGING_DIR,RESOURCE_DIR));
        config.setSourceResolution(jobContext.sourceResolution);

        jobContext.getPostProcessorConfigs().put(FopPostProcessor.class.getCanonicalName(),config);
    }

    private void initProcessorConfig(XslFoJobContext jobContext) {
        SingleOutputProcessor.SingleOutputProcessorConfig config=new SingleOutputProcessor.SingleOutputProcessorConfig();

        config.setLanguage(jobContext.getLanguage());
        config.setDocbookDir(jobContext.getDocbookDir());
        config.setDocbookFile(jobContext.getDocbookFile());
        config.setResultDir(StringUtils.joinWith("/",jobContext.getWorkDir(),STAGING_DIR,FO_DIR));
        config.setResultFile(jobContext.getDocbookFile());
        config.setXsltDir(jobContext.getXsltDir());
        config.setXsltFile(jobContext.getXsltFile());

        jobContext.setDocbookProcessorConfig(config);

    }

    private void initResourceConfig(XslFoJobContext jobContext) {
        ResourceProcessor.ResourceConfig config=new ResourceProcessor.ResourceConfig();
        config.setBaseDir(StringUtils.joinWith("/",jobContext.getWorkDir(),STAGING_DIR));
        Map<String,String[]> copyPair=new HashMap<>(3);
        copyPair.put(RESOURCE_DIR,jobContext.getResourcePaths());
        copyPair.put(FONTS_DIR,jobContext.getFontPaths());
        copyPair.put(DOCX_DIR,jobContext.getDocxPaths());
        config.setCopyPair(copyPair);
        jobContext.getPreProcessorConfigs().put(ResourceProcessor.class.getCanonicalName(),config);
    }

    private void initCleanConfig(XslFoJobContext jobContext) {
        CleanPreProcessor.CleanConfig config=new CleanPreProcessor.CleanConfig();
        config.setCleanDir(new String[]{jobContext.getWorkDir()});
        jobContext.getPreProcessorConfigs().put(CleanPreProcessor.class.getCanonicalName(),config);
    }

    /**
     * 扩展的上下文
     */
    @Setter
    @Getter
    public static class XslFoJobContext extends JobContext {
        /**
         * fop 的配置文件路径 vfs 格式
         */
        private String fopConfigPath;
        /**
         * fop 特有的输出格式类型
         */
        private String mimeType;
        /**
         * 源分辨率
         */
        private float sourceResolution=72f;
        /**
         * 目标分辨率
         */
        private float targetResolution=72f;

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
        }
    }
}
