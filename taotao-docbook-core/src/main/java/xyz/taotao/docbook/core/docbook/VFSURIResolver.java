/*
 * Copyright © 2022 王金涛。
 * This file is part of taotao-docbook.
 *
 * taotao-docbook is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * taotao-docbook is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with taotao-docbook. If
 * not, see <https://www.gnu.org/licenses/>.
 */

package xyz.taotao.docbook.core.docbook;

import lombok.extern.slf4j.Slf4j;
import xyz.taotao.docbook.core.TaotaoDocbookException;
import xyz.taotao.docbook.core.util.XmlUtils;

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
