<?xml version='1.0' encoding='utf-8'?>
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

<!--
生成 word 的顶级处理逻辑部分
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0"

                xmlns:docbook="http://docbook.org/ns/docbook"
                exclude-result-prefixes="xsl docbook">

    <!-- word 文档的顶级处理方法 -->
    <xsl:template match="/">
        <w:document xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main"
                    xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006"
                    xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"
                    xmlns:m="http://schemas.openxmlformats.org/officeDocument/2006/math"
                    xmlns:v="urn:schemas-microsoft-com:vml"
                    xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing"
                    xmlns:w10="urn:schemas-microsoft-com:office:word"
                    xmlns:wne="http://schemas.microsoft.com/office/word/2006/wordml"
        >
            <w:body>
                <xsl:apply-templates mode="word.body"/>
            </w:body>
        </w:document>
    </xsl:template>

    <xsl:template match="docbook:article" mode="word.body">
        <!--
            文章的匹配
        -->
        <xsl:call-template name="make-article-body">
            <xsl:with-param name="article" select="."/>
        </xsl:call-template>

    </xsl:template>
    <xsl:template match="docbook:book" mode="word.body">
        <!--
            书的匹配
        -->
        <xsl:call-template name="make-book-body">
            <xsl:with-param name="book" select="."/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="make-article-body">
        <!--
            生成 article 的 body 部分，以便于覆盖
        -->
        <xsl:param name="article"/>
        <!-- TODO: 标题 -->
        <xsl:call-template name="make-article-title">
            <xsl:with-param name="article_info" select="$article/docbook:info|$article/docbook:articleinfo"/>
        </xsl:call-template>
        <!-- TODO: 版本 -->
        <!-- TODO: 索引 -->
        <!-- TODO: 正文 -->
    </xsl:template>
    <xsl:template name="make-book-body">
        <!--
            生成 book 的 body 部分，以便于覆盖
        -->
        <xsl:param name="book"/>
        <xsl:message>
            处理book
        </xsl:message>
    </xsl:template>

</xsl:stylesheet>