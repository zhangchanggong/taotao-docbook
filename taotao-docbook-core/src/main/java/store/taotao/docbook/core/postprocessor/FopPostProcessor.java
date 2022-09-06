/*
 * Copyright © 2022 王金涛。
 * This file is part of taotao-docbook.
 *
 * taotao-docbook is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * taotao-docbook is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE. See the GNU Lesser Public License for more details.
 *
 * You should have received a copy of the GNU Lesser Public License along with taotao-docbook. If
 * not, see <https://www.gnu.org/licenses/>.
 */

package store.taotao.docbook.core.postprocessor;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FopFactoryBuilder;
import org.apache.fop.configuration.DefaultConfiguration;
import store.taotao.docbook.core.*;
import xyz.taotao.docbook.core.*;
import store.taotao.docbook.core.fop.FopUtils;
import store.taotao.docbook.core.util.VFSUtils;
import store.taotao.docbook.core.util.XmlUtils;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;

/**
 * 将 fo 文件转换成指定格式
 */
@Slf4j
public class FopPostProcessor extends ClassNameConfigKeyProcesser<FopPostProcessor.FopPostConfig> implements PostProcessor<FopPostProcessor.FopPostConfig> {

    public FopPostProcessor() {
        super(FopPostConfig.class);
    }

    @Override
    protected void doProcess(FopPostConfig config) throws TaotaoDocbookException {
        FopFactoryBuilder builder = FopUtils.getFactoryBuilder(config.stagingDir, config.resourceDir);
        DefaultConfiguration configuration = FopUtils.getConfig(config.fopConfigPath);
        builder.setConfiguration(configuration);
        builder.setSourceResolution(config.sourceResolution);
        builder.setTargetResolution(config.targetResolution);
        FopFactory fopFactory = builder.build();
        try {
            FileObject output = VFSUtils.getResource(config.outFile, config.outDir);
            Fop fop = fopFactory.newFop(config.mimeType,output.getContent().getOutputStream());
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
    public static class FopPostConfig extends ProcessorConfig {
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
                    .append("sourceResolution", sourceResolution)
                    .append("targetResolution", targetResolution)
                    .toString();
        }
    }
}
