package xyz.taotao.docbook.core.preprocessor;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import xyz.taotao.docbook.core.ClassNameConfigKeyProcesser;
import xyz.taotao.docbook.core.PreProcessor;
import xyz.taotao.docbook.core.ProcessorConfig;
import xyz.taotao.docbook.core.util.VFSUtils;

import java.io.IOException;
import java.util.Arrays;

/**
 * 主要用于对目标路径的清理
 */
@Slf4j
public class CleanPreProcessor extends ClassNameConfigKeyProcesser<CleanPreProcessor.CleanConfig> implements PreProcessor<CleanPreProcessor.CleanConfig> {

    public CleanPreProcessor() {
        super(CleanConfig.class);
    }

    @Override
    protected void doProcess(CleanConfig config) {
        Arrays.stream(config.cleanDir).forEach(path -> {
            try {
                FileUtils.forceDelete(VFSUtils.getResource(path, null).getPath().toFile());
            } catch (IOException e) {
                log.warn("删除指定路径错误，path=[{}]", path, e);
            }
        });
    }

    @Setter
    @Getter
    public static class CleanConfig extends ProcessorConfig {
        /**
         * 需要清理的路径
         */
        public String[] cleanDir;
    }
}
