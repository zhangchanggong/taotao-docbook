<?xml version='1.0'?>
<!--
  ~ Copyright © 2022 王金涛。
  ~ This file is part of taotao-docbook.
  ~
  ~ taotao-docbook is free software: you can redistribute it and/or modify it under the terms of the
  ~ GNU Lesser General Public License as published by the Free Software Foundation, either
  ~ version 3 of the License, or (at your option) any later version.
  ~
  ~ taotao-docbook is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
  ~ without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  ~ PURPOSE. See the GNU Lesser General Public License for more details.
  ~
  ~ You should have received a copy of the GNU Lesser General Public License along with taotao-docbook. If
  ~ not, see <https://www.gnu.org/licenses/>.
  -->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:exsl="http://exslt.org/common"
                xmlns:hl="http://docbook.taotao.store/saxon-extension"
                exclude-result-prefixes="exsl"
                version='1.0'>


    <!-- for saxonHE 11.2  -->
<!--    <saxon:script implements-prefix="s9hl" language="java" src="java:store.taotao.docbook.highlight.ConnectorSaxonHE" />-->


    <xsl:template name="apply-highlighting">
        <xsl:choose>
            <!-- Do we want syntax highlighting -->
            <xsl:when test="$highlight.source != 0">
                <xsl:variable name="language">
                    <xsl:call-template name="language.to.xslthl">
                        <xsl:with-param name="context" select="."/>
                    </xsl:call-template>
                </xsl:variable>
                <xsl:choose>
                    <xsl:when test="$language != ''">
                        <xsl:variable name="content">
                            <xsl:apply-templates/>
                        </xsl:variable>
<!--                        <xsl:choose>-->
<!--                            <xsl:when test="function-available('s9hl:highlight')">-->
<!--                                <xsl:apply-templates select="s9hl:highlight($language, exsl:node-set($content), $highlight.xslthl.config)"-->
<!--                                                     mode="xslthl"/>-->
<!--                            </xsl:when>-->
<!--                            <xsl:otherwise>-->
<!--                                <xsl:copy-of select="$content"/>-->
<!--                            </xsl:otherwise>-->
<!--                        </xsl:choose>-->
<!--                        <xsl:apply-templates select="xslthl:highlight($language, exsl:node-set($content), $highlight.xslthl.config)"-->
                        <!--                            xmlns:xslthl="java:store.taotao.docbook.highlight.ConnectorSaxonHE"/>-->

                        <xsl:apply-templates
                                select="hl:highlight($language, exsl:node-set($content), $highlight.xslthl.config)" mode="xslthl"/>

                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:apply-templates/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <!-- No syntax highlighting -->
            <xsl:otherwise>
                <xsl:apply-templates/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>
