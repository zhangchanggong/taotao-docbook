package xyz.taotao.docbook.core.postprocessor;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.fop.apps.*;
import org.apache.fop.configuration.DefaultConfiguration;
import xyz.taotao.docbook.core.*;
import xyz.taotao.docbook.core.fop.FopUtils;
import xyz.taotao.docbook.core.util.VFSUtils;
import xyz.taotao.docbook.core.util.XmlUtils;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;

/**
 * 将 fo 文件转换成指定格式
 */
@Slf4j
public class FopPostProcessor extends ClassNameConfigKeyProcesser<FopPostProcessor.FopPostConfig> implements PostProcessor<FopPostProcessor.FopPostConfig> {

    protected FopPostProcessor() {
        super(FopPostConfig.class);
    }

    @Override
    protected void doProcess(FopPostConfig config) throws TaotaoDocbookException {
        FopFactoryBuilder builder = FopUtils.getFactoryBuilder(config.stagingDir, config.resourceDir);
        DefaultConfiguration configuration = FopUtils.getConfig(config.fopConfigPath);
        builder.setConfiguration(configuration);
        FopFactory fopFactory = builder.build();
        try {
            FileObject output = VFSUtils.getResource(config.outFile, config.outDir);
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF,output.getContent().getOutputStream());
            TransformerFactory transformerFactory = XmlUtils.getTransformerFactory();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setParameter(TaotaoDocbookConstant.L10N_GENTEXT_LANGUAGE,config.language);
            Source source = XmlUtils.getSource(config.foFile, config.foDir);
            Result result=new SAXResult(fop.getDefaultHandler());
            transformer.transform(source,result);
        } catch (FileSystemException e) {
            log.warn("vfs 异常,config=[{}]",config,e);
            throw new TaotaoDocbookException("vfs 异常",e);
        } catch (FOPException e) {
            log.warn("fop 异常,config=[{}]",config,e);
            throw new TaotaoDocbookException("fop 异常",e);
        } catch (TransformerConfigurationException e) {
            log.warn("transformer 配置异常,config=[{}]",config,e);
            throw new TaotaoDocbookException("transformer 配置异常",e);
        } catch (TransformerException e) {
            log.warn("transformer 转换异常,config=[{}]",config,e);
            throw new TaotaoDocbookException("transformer 转换异常",e);
        }


    }

    @Setter
    @Getter
    public static class FopPostConfig extends ProcessorConfig{
        /**
         * xsl-fo 文件所在的文件夹
         */
        private String foDir;
        /**
         * xsl-fo 文件名
         */
        private String foFile;
        /**
         * fop 配置文件地址
         */
        private String fopConfigPath;
        /**
         * 临时活动路径
         */
        private String stagingDir;
        /**
         * 渲染资源子路经
         */
        private String resourceDir= TaotaoDocbookConstant.RESOURCE_DIR;
        /**
         * 输出文件路径
         */
        private String outDir;
        /**
         * 输出文件名
         */
        private String outFile;
        /**
         * 输出的参考语言
         */
        private String language;
        /**
         * 输出文件的 type 取值参考 org.apache.fop.apps.MimeConstants
         */
        private String mimeType;

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("foDir", foDir)
                    .append("foFile", foFile)
                    .append("fopConfigPath", fopConfigPath)
                    .append("stagingDir", stagingDir)
                    .append("resourceDir", resourceDir)
                    .append("outDir", outDir)
                    .append("outFile", outFile)
                    .append("language", language)
                    .append("mimeType", mimeType)
                    .toString();
        }
    }
}
