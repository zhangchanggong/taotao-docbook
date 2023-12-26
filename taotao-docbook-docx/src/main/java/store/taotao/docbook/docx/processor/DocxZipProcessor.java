package store.taotao.docbook.docx.processor;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import store.taotao.docbook.core.ClassNameConfigKeyProcesser;
import store.taotao.docbook.core.Processor;
import store.taotao.docbook.core.ProcessorConfig;
import store.taotao.docbook.core.TaotaoDocbookException;
import store.taotao.docbook.core.util.VFSUtils;

import java.io.File;

/**
 * docx 文件压缩处理过程
 */
@Slf4j
public class DocxZipProcessor extends ClassNameConfigKeyProcesser<DocxZipProcessor.DocxZipProcessorConfig> implements Processor<DocxZipProcessor.DocxZipProcessorConfig> {

    protected DocxZipProcessor() {
        super(DocxZipProcessorConfig.class);
    }

    @Override
    protected void doProcess(DocxZipProcessorConfig config) throws TaotaoDocbookException {
        try {
            FileObject descFile = VFSUtils.getResource(config.getDescFile(), config.getDescDir());
            if (!descFile.exists()){
                descFile.createFile();
            }
            new ZipFile(descFile.getPublicURIString()).addFolder(new File(config.getDocxDir()));
        } catch (FileSystemException e) {
            log.warn("解析或生成目标文件错误",e);
            log.debug("config=[{}]",config);
            throw new TaotaoDocbookException("解析或生成目标文件错误",e);
        } catch (ZipException e) {
            log.warn("目标文件压缩错误",e);
            log.debug("config=[{}]",config);
            throw new TaotaoDocbookException("目标文件压缩错误",e);
        }
    }

    /**
     * docx zip 压缩配置
     */
    @Setter
    @Getter
    public static class DocxZipProcessorConfig extends ProcessorConfig{
        /**
         * 目标文件夹
         */
        private String descDir;
        /**
         * 目标文件
         */
        private String descFile;
        /**
         * docx 文件夹
         */
        private String docxDir;
    }
}
