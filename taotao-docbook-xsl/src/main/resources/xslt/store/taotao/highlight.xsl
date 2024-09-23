<?xml version='1.0'?>
<!--
  ~    Copyright 2024 王金涛
  ~
  ~    Licensed under the Apache License, Version 2.0 (the "License");
  ~    you may not use this file except in compliance with the License.
  ~    You may obtain a copy of the License at
  ~
  ~        http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~    Unless required by applicable law or agreed to in writing, software
  ~    distributed under the License is distributed on an "AS IS" BASIS,
  ~    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~    See the License for the specific language governing permissions and
  ~    limitations under the License.
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
