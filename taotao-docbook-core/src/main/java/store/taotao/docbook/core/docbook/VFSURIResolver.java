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

import lombok.extern.slf4j.Slf4j;
import store.taotao.docbook.core.util.XmlUtils;
import store.taotao.docbook.core.TaotaoDocbookException;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;

/**
 * 基于 VFS 的 URIResolver
 */
@Slf4j
public class VFSURIResolver implements URIResolver {
    /**
     * Called by the processor when it encounters
     * an xsl:include, xsl:import, or document() function.
     *
     * @param href An href attribute, which may be relative or absolute.
     * @param base The base URI against which the first argument will be made
     *             absolute if the absolute URI is required.
     * @return A Source object, or null if the href cannot be resolved,
     * and the processor should try to resolve the URI itself.
     * @throws TransformerException if an error occurs when trying to
     *                              resolve the URI.
     */
    @Override
    public Source resolve(String href, String base) throws TransformerException {
        log.debug("------------------ uri resolve 开始 -----------------");
        log.debug("href=[{}]", href);
        log.debug("base=[{}]", base);

        try {
            return XmlUtils.getSource(href,base);
        } catch (TaotaoDocbookException e) {
            log.warn("获取 source 异常");
            throw new TransformerException("获取 source 异常",e);
        }finally {
            log.debug("------------------ uri resolve 结束 -----------------");
        }
    }
}
