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

package store.taotao.docbook.core.highlight;

import lombok.extern.slf4j.Slf4j;
import net.sf.saxon.event.Builder;
import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.expr.parser.Loc;
import net.sf.saxon.om.*;
import net.sf.saxon.pattern.AnyNodeTest;
import net.sf.saxon.s9api.Location;
import net.sf.saxon.str.StringTool;
import net.sf.saxon.tree.iter.AxisIterator;
import net.sf.saxon.tree.iter.ListIterator;
import net.sf.saxon.type.AnyType;
import net.sf.saxon.type.Type;
import net.sf.xslthl.Block;
import net.sf.xslthl.Config;
import net.sf.xslthl.MainHighlighter;
import net.sf.xslthl.StyledBlock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * xslthl 针对 高版本 saxon 代码高亮功能扩展
 * 该类虽然名为 HE，仅表示该类可以在 Saxon HE 下编译通过，由于 saxon HE 不包含 java 扩展类，所以至少需要 saxon PE 才能起作用。
 */
@Slf4j
public class SaxonHEUtils {
    /**
     * 将 Block （高亮代码块）渲染进 builder
     *
     * @param b       代码块（本质是一个高亮片段）
     * @param builder saxon 中定义的节点构造器
     * @param config  渲染时的配置信息
     * @throws Exception 发生渲染异常
     */
    private static void blockToSaxonNode(Block b, Builder builder,
                                         Config config) throws Exception {
        Location loc = new Loc(null, 0, 0);
        if (b.isStyled()) {
            FingerprintedQName fpQname = new FingerprintedQName(
                    config.getPrefix(), NamespaceUri.of(config.getUri()), ((StyledBlock) b).getStyle()
            );
            builder.startElement(fpQname, AnyType.getInstance(), new SmallAttributeMap(Collections.emptyList()), NamespaceMap.emptyMap(), loc, 0);
            builder.characters(StringTool.fromCharSequence(b.getText()), loc, b.getText().length());
            builder.endElement();
        } else {
            builder.characters(StringTool.fromCharSequence(b.getText()), loc, b.getText().length());
        }

    }

    /**
     * highlight the nodes using a specific interface
     *
     * @param context           上下文
     * @param hlCode            高亮类型标识，一般是语言
     * @param seq               标签序列迭代器
     * @param configFilename    高亮的配置文件
     * @return                  输出的标签序列迭代器
     */
    public static SequenceIterator highlight(XPathContext context,
                                             String hlCode, SequenceIterator seq, String configFilename) {

        try {
            Config c = VFSConfig.getInstance(configFilename);
            MainHighlighter hl = c.getMainHighlighter(hlCode);
            int childType = AxisInfo.CHILD;
            List<Item> resultNodes = new ArrayList<>();
            Item itm;
            while ((itm = seq.next()) != null) {
                if (itm instanceof NodeInfo) {
                    NodeInfo ni = (NodeInfo) itm;

                    SequenceIterator ae = ni.iterateAxis(childType, AnyNodeTest.getInstance());
                    Item itm2;
                    while ((itm2 = ae.next()) != null) {
                        if (itm2 instanceof NodeInfo) {
                            NodeInfo n2i = (NodeInfo) itm2;
                            if (n2i.getNodeKind() == Type.TEXT) {
                                if (hl != null) {
                                    Builder builder = context.getController()
                                            .makeBuilder();
                                    builder.open();
                                    builder.startDocument(0);
                                    List<Block> l = hl.highlight(n2i
                                            .getStringValue());
                                    for (Block b : l) {
                                        blockToSaxonNode(b, builder, c);
                                    }
                                    builder.endDocument();
                                    builder.close();
                                    NodeInfo doc = builder.getCurrentRoot();

                                    AxisIterator elms =doc.iterateAxis(childType,AnyNodeTest.getInstance());
                                    Item crt = null;
                                    while ((crt = elms.next()) != null) {
                                        resultNodes.add(crt);
                                    }
                                } else {
                                    resultNodes.add(n2i);
                                }
                            } else {
                                resultNodes.add(n2i);
                            }
                        } else {
                            resultNodes.add(itm2);
                        }
                    }
                } else {
                    resultNodes.add(itm);
                }
            }
            return new ListIterator.Of<Item>(resultNodes);
        } catch (Throwable e) {
            log.warn("代码高亮发生异常，hlCode=[{}],code=[{}]", hlCode, seq, e);
            return null;
        }
    }

    private SaxonHEUtils(){

    }
}
