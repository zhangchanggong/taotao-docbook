package store.taotao.docbook.docx.processor;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.Selectors;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import store.taotao.docbook.core.ClassNameConfigKeyProcesser;
import store.taotao.docbook.core.DocbookProcessor;
import store.taotao.docbook.core.ProcessorConfig;
import store.taotao.docbook.core.TaotaoDocbookException;
import store.taotao.docbook.core.util.VFSUtils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.util.Arrays;


/**
 * docx 图片处理过程
 */
@Slf4j
public class DocxImageProcessor extends ClassNameConfigKeyProcesser<DocxImageProcessor.DocxImageProcessorConfig> implements DocbookProcessor<DocxImageProcessor.DocxImageProcessorConfig> {


    private DocumentBuilder documentBuilder;
    private XPathExpression xpathExpression;

    {
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            xpathExpression = XPathFactory.newInstance().newXPath().compile("/Relationships/Relationship[@Type='http://schemas.openxmlformats.org/package/2006/relationships/image']/@Target");
        } catch (ParserConfigurationException | XPathExpressionException e) {
            log.info("生成 xml 解析工具错误", e);
        }
    }


    public DocxImageProcessor() {
        super(DocxImageProcessorConfig.class);
    }

    /**
     * 处理 docx 文件相关图片
     *
     * @param config
     * @throws TaotaoDocbookException
     */
    @Override
    protected void doProcess(DocxImageProcessorConfig config) throws TaotaoDocbookException {
        try {
            FileObject wordDir = VFSUtils.getResource("word", config.docxDir);
            FileObject relDir = VFSUtils.getResource("word/_rels", config.docxDir);
            FileObject resourceDir = VFSUtils.getResource(config.resourceDir, null);
            FileObject[] children = relDir.getChildren();
            Arrays.stream(children).filter(child -> {
                try {
                    return "rels".equals(child.getName().getExtension()) && child.exists();
                } catch (FileSystemException e) {
                    log.info("分析 rel 文件发生异常,rel=[{}]", child, e);
                    return false;
                }
            }).forEach(rel -> processImage(rel, resourceDir, wordDir));
        } catch (FileSystemException e) {
            log.warn("路径解析错误", e);
            log.debug("config=[{}]", config);
            throw new TaotaoDocbookException("路径解析错误", e);
        }

    }

    /**
     * 将 rel 文件中记录的图片从  resourceDir 复制到 wordDir
     *
     * @param rel         word 的 rels 文件
     * @param resourceDir 资源文件夹
     * @param wordDir     word 文件目录，word 资源文件的根目录
     */
    private void processImage(FileObject rel, FileObject resourceDir, FileObject wordDir) {
        try {
            Document relDoc = documentBuilder.parse(rel.getPublicURIString());
            NodeList imagePaths = (NodeList)xpathExpression.evaluate(relDoc, XPathConstants.NODESET);
            for (int i=0;i<imagePaths.getLength();++i){
                String imagePath=imagePaths.item(i).getNodeValue();
                FileObject imageFile = resourceDir.resolveFile(imagePath);
                if (imageFile.exists()){
                    wordDir.resolveFile(imagePath).copyFrom(imageFile, Selectors.SELECT_SELF);
                }
            }
        } catch (SAXException | IOException | XPathExpressionException e) {
            log.info("xml 文件解析异常",e);
        }
    }

    /**
     * docx 图片处理过程的配置类
     */
    @Setter
    @Getter
    public static class DocxImageProcessorConfig extends ProcessorConfig {
        /**
         * 资源的根路径
         */
        private String resourceDir;
        /**
         * 这里指生成的 docx 散装文件的路径
         */
        private String docxDir;

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
        }
    }
}
