package store.taotao.docbook.docx.job;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import store.taotao.docbook.core.Job;
import store.taotao.docbook.core.JobContext;
import store.taotao.docbook.core.TaotaoDocbookException;
import store.taotao.docbook.core.docbook.SingleOutputProcessor;
import store.taotao.docbook.docx.processor.DocxImageProcessor;

import java.util.HashMap;
import java.util.Map;

import static store.taotao.docbook.core.TaotaoDocbookConstant.*;

/**
 * 处理为 DocxJob 的任务,其过程如下
 * <ol>
 *     <li>单文件化，将由多个文件构成的 docbook 汇聚为单一的 docbook 文件，并补充相关属性</li>
 *     <li>根据单一文件，生成 document.xml(wordml) ,主文件</li>
 *     <li>根据单一文件，生成 numbering.xml(wordml),计数文件</li>
 *     <li>根据单一文件，生成 footnotes.xml(wordml),脚注文件</li>
 *     <li>根据单一文件，生成 images.xml.rels(wordml),资源索引文件</li>
 *     <li>将图片资源汇聚至 word 文件夹下</li>
 *     <li>将 docx 文件夹下的所有文件压缩为 zip，保存为 docx 文件</li>
 * </ol>
 */
@Slf4j
public class DocxJob implements Job<DocxJob.DocxJobContext> {

    /**
     * 单文件化处理器
     */
    private SingleOutputProcessor singleOutputProcessor=new SingleOutputProcessor();

    private DocxImageProcessor imageProcessor=new DocxImageProcessor();

    private String[] processes=new String[]{
            "single",
            "document",
            "numbering",
            "footnotes",
            "images"
    };



    @Override
    public void process(DocxJobContext jobContext) throws TaotaoDocbookException {
        log.info("-------------------- docx Job 处理开始 --------------------");
        initContext(jobContext);
        for (String config:processes) {
            log.info("[{}] 处理开始",config);
            singleOutputProcessor.process(jobContext.singleOutputProcessorConfigs.get(config));
            log.info("[{}] 处理结束",config);
        }

        DocxImageProcessor.DocxImageProcessorConfig imageProcessorConfig=getDocxImageProcessorConfig(jobContext);
        imageProcessor.process(imageProcessorConfig);
        log.info("-------------------- docx Job 处理结束 --------------------");
    }

    private DocxImageProcessor.DocxImageProcessorConfig getDocxImageProcessorConfig(DocxJobContext jobContext) {
        DocxImageProcessor.DocxImageProcessorConfig config=new DocxImageProcessor.DocxImageProcessorConfig();
        config.setDocxDir(StringUtils.joinWith("/",jobContext.getWorkDir(),STAGING_DIR,DOCX_DIR));
        config.setResourceDir(StringUtils.joinWith("/",jobContext.getWorkDir(),STAGING_DIR,RESOURCE_DIR));
        return config;
    }

    private void initContext(DocxJobContext jobContext) {
        initSingleProcessor(jobContext);
        initDocxProcessor(jobContext,"document",jobContext.getXsltFile(),"document.xml");
        initDocxProcessor(jobContext,"numbering",jobContext.getNumberingXslt(),"numbering.xml");
        initDocxProcessor(jobContext,"footnotes",jobContext.getFootnotesXslt(),"footnotes.xml");
        initDocxProcessor(jobContext,"images",jobContext.getImageRelXslt(),"_rels/image.xml.rels");
    }

    private void initDocxProcessor(DocxJobContext jobContext, String type, String xsltFile, String descFile) {
        SingleOutputProcessor.SingleOutputProcessorConfig config=new SingleOutputProcessor.SingleOutputProcessorConfig();
        config.setLanguage(jobContext.getLanguage());
        config.setDocbookDir(StringUtils.joinWith("/",jobContext.getWorkDir(),STAGING_DIR,SINGLE));
        config.setDocbookFile(jobContext.singleFile);
        config.setResultDir(StringUtils.joinWith("/",jobContext.getWorkDir(),STAGING_DIR,DOCX_DIR,"word"));
        config.setResultFile(descFile);
        config.setXsltDir(jobContext.getXsltDir());
        config.setXsltFile(xsltFile);
        jobContext.singleOutputProcessorConfigs.put(type,config);
    }

    /**
     * 生成文件单一化处理过程配置
     * @param jobContext job 上下文
     */
    private void initSingleProcessor(DocxJobContext jobContext) {
        SingleOutputProcessor.SingleOutputProcessorConfig config=new SingleOutputProcessor.SingleOutputProcessorConfig();

        config.setLanguage(jobContext.getLanguage());
        config.setDocbookDir(jobContext.getDocbookDir());
        config.setDocbookFile(jobContext.getDocbookFile());
        config.setResultDir(StringUtils.joinWith("/",jobContext.getWorkDir(),STAGING_DIR,SINGLE));
        config.setResultFile(jobContext.singleFile);
        config.setXsltDir(jobContext.getXsltDir());
        config.setXsltFile(jobContext.singleXslt);

        jobContext.singleOutputProcessorConfigs.put("single",config);
    }

    /**
     * 扩展的上下文
     */
    @Setter
    @Getter
    public static class DocxJobContext extends JobContext{
        /**
         * 多分配置变量
         */
        private Map<String,SingleOutputProcessor.SingleOutputProcessorConfig> singleOutputProcessorConfigs=new HashMap<>();
        /**
         * 临时单一文件
         */
        private String singleFile;
        /**
         * 临时单一文件转换规则
         */
        private String singleXslt;
        /**
         * 计数器转换
         */
        private String numberingXslt;
        /**
         * 脚注的转换
         */
        private String footnotesXslt;
        /**
         * 针对图片引用的转换规则
         */
        private String imageRelXslt;

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
        }



    }
}
