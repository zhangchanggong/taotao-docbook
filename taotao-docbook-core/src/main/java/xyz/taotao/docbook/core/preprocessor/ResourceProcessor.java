package xyz.taotao.docbook.core.preprocessor;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.vfs2.AllFileSelector;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import xyz.taotao.docbook.core.ClassNameConfigKeyProcesser;
import xyz.taotao.docbook.core.PreProcessor;
import xyz.taotao.docbook.core.ProcessorConfig;
import xyz.taotao.docbook.core.TaotaoDocbookException;
import xyz.taotao.docbook.core.util.VFSUtils;

import java.util.Arrays;
import java.util.Map;

@Slf4j
public class ResourceProcessor extends ClassNameConfigKeyProcesser<ResourceProcessor.ResourceConfig> implements PreProcessor<ResourceProcessor.ResourceConfig> {

    public ResourceProcessor() {
        super(ResourceConfig.class);
    }

    @Setter
    @Getter
    public static class ResourceConfig extends ProcessorConfig {
        /**
         * 目的路径与原路径的对应关系
         */
        private Map<String, String[]> copyPair;
        /**
         * 基本路径
         */
        private String baseDir;
    }

    @Override
    protected void doProcess(ResourceConfig config) throws TaotaoDocbookException {
        for (Map.Entry<String, String[]> entry : config.getCopyPair().entrySet()) {
            doCopy(entry.getKey(), entry.getValue(), config.baseDir);
        }
    }

    /**
     * 将 sources 中的内容复制到 desc 中
     *
     * @param desc    复制的目标地址
     * @param sources 复制的源地址
     * @param baseDir 复制的源地址
     * @throws TaotaoDocbookException 发生不和继续进行的异常
     */
    private void doCopy(String desc, String[] sources, String baseDir) throws TaotaoDocbookException {
        if (ObjectUtils.isEmpty(sources)){
            return;
        }
        try {
            FileObject descDir = VFSUtils.getResource(desc, baseDir);
            Arrays.stream(sources).forEach(source -> {
                try {
                    FileObject sourceFile = VFSUtils.getResource(source, null);
                    descDir.copyFrom(sourceFile, new AllFileSelector());
                } catch (FileSystemException e) {
                    log.warn("复制文件错误,source=[{}]", source, e);
                }
            });
        } catch (FileSystemException e) {
            log.warn("获取目标文件夹错误,desc=[{}]", desc, e);
            throw new TaotaoDocbookException("获取目标文件夹错误", e);
        }

    }
}
