/*
 * Copyright 2024 王金涛
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package store.taotao.docbook.core.docbook;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import store.taotao.docbook.core.*;
import store.taotao.docbook.core.util.XmlUtils;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.SAXSource;

/**
 * 单文件输出的 docbook 处理过程
 */
@Slf4j
public class SingleOutputProcessor extends ClassNameConfigKeyProcesser<SingleOutputProcessor.SingleOutputProcessorConfig> implements DocbookProcessor<SingleOutputProcessor.SingleOutputProcessorConfig> {
    public SingleOutputProcessor() {
        super(SingleOutputProcessorConfig.class);
    }

    /**
     * 处理过程
     *
     * @param config 处理器配置参数
     * @throws TaotaoDocbookException 可能的抛出的异常
     */
    @Override
    protected void doProcess(SingleOutputProcessorConfig config) throws TaotaoDocbookException {
        log.info("SingleOutputProcessor.process config={}", config);
        SingleOutputProcessorConfig theConfig= (SingleOutputProcessorConfig) config;
        Transformer transformer = XmlUtils.getTransformer(config.xsltFile, config.xsltDir);
        transformer.setParameter(TaotaoDocbookConstant.L10N_GENTEXT_LANGUAGE,config.language);
        transformer.setErrorListener(new ErrorListener() {
            @Override
            public void warning(TransformerException exception) throws TransformerException {
                log.warn("警告", exception);
            }

            @Override
            public void error(TransformerException exception) throws TransformerException {
                log.error("错误", exception);

            }

            @Override
            public void fatalError(TransformerException exception) throws TransformerException {
                log.error("失败", exception);
            }
        });
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
        /**
         * 语言 ll-CC 的格式
         */
        private String language;
    }
}
