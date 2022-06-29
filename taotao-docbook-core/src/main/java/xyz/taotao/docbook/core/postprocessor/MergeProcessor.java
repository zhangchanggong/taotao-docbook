package xyz.taotao.docbook.core.postprocessor;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.vfs2.AllFileSelector;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import xyz.taotao.docbook.core.ClassNameConfigKeyProcesser;
import xyz.taotao.docbook.core.PostProcessor;
import xyz.taotao.docbook.core.ProcessorConfig;
import xyz.taotao.docbook.core.TaotaoDocbookException;
import xyz.taotao.docbook.core.util.VFSUtils;

import java.io.IOException;

/**
 * 将无需处理的资源和xsl 处理后的产出物进一个路径下
 */
@Slf4j
public class MergeProcessor extends ClassNameConfigKeyProcesser<MergeProcessor.MergeConfig> implements PostProcessor<MergeProcessor.MergeConfig> {


    public MergeProcessor() {
        super(MergeConfig.class);
    }

    @Override
    public void doProcess(MergeConfig config) throws TaotaoDocbookException {
        FileObject descDir=null;
        try {
            descDir = VFSUtils.getResource(config.descPath,null);
            if (descDir.exists()){
                if (!descDir.isFolder()){
                    throw new TaotaoDocbookException("目标路径存在但不是文件夹");
                }
            }else{
                FileUtils.forceMkdir
                        (descDir.getPath().toFile());
            }
        } catch (FileSystemException e) {
            log.warn("获取目标文件夹失败,descPath=[{}]",config.descPath,e);
            throw new TaotaoDocbookException("获取目标文件夹失败",e);
        } catch (IOException e) {
            log.warn("创建目标文件夹失败,descPath=[{}]",config.descPath,e);
            throw new TaotaoDocbookException("创建目标文件夹失败",e);
        }

        for (String sourcePath:config.sourcePaths){
            try {
                FileObject sourceFile = VFSUtils.getResource(sourcePath, null);
                descDir.copyFrom(sourceFile,new AllFileSelector());
            } catch (FileSystemException e) {
                log.warn("sourcePath=[{}],处理失败",sourcePath);
            }
        }

    }

    @Getter
    @Setter
    public static class MergeConfig extends ProcessorConfig {
        /**
         * 需要聚合的源
         */
        private String[] sourcePaths;
        /**
         * 目标路径
         */
        private String descPath;
    }
}
