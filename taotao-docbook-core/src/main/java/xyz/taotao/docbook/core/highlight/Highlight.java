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

package xyz.taotao.docbook.core.highlight;

import lombok.extern.slf4j.Slf4j;
import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.lib.ExtensionFunctionCall;
import net.sf.saxon.lib.ExtensionFunctionDefinition;
import net.sf.saxon.om.Sequence;
import net.sf.saxon.om.SequenceIterator;
import net.sf.saxon.om.StructuredQName;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.value.SequenceExtent;
import net.sf.saxon.value.SequenceType;

/**
 * 高亮函数定义
 */
@Slf4j
public class Highlight extends ExtensionFunctionDefinition {
    @Override
    public StructuredQName getFunctionQName() {
        return new StructuredQName("hl", "http://docbook.taotao.xyz/saxon-extension", "highlight");
    }

    @Override
    public SequenceType[] getArgumentTypes() {
        return new SequenceType[]{
            SequenceType.SINGLE_STRING,
            SequenceType.NODE_SEQUENCE,
            SequenceType.SINGLE_STRING
        };
    }

    @Override
    public SequenceType getResultType(SequenceType[] suppliedArgumentTypes) {
        return SequenceType.NODE_SEQUENCE;
    }

    @Override
    public ExtensionFunctionCall makeCallExpression() {
        return new ExtensionFunctionCall() {
            @Override
            public Sequence call(XPathContext context, Sequence[] arguments) throws XPathException {
                String language=arguments[0].head().getStringValue();
                SequenceIterator seq=arguments[1].iterate();
                String configFilename=arguments[2].head().getStringValue();
                log.debug("language=[{}]",language);
                log.debug("seq=[{}]",seq);
                log.debug("configFilename=[{}]",configFilename);
                SequenceIterator sequenceIterator = SaxonHEUtils.highlight(context, language, seq, configFilename);
                return SequenceExtent.from(sequenceIterator);
            }
        };
    }
}
