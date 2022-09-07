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
= 生成 html 5 用的xsl模版
-->
<xsl:stylesheet  version="1.0"
                 xmlns:d="http://docbook.org/ns/docbook"
                 xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                 xmlns:fo="http://www.w3.org/1999/XSL/Format"
                 exclude-result-prefixes="fo xsl">

    <xsl:import href="classpath:docbook/xhtml/docbook.xsl" />
    <xsl:import href="classpath:docbook/xhtml/highlight.xsl" />
    <xsl:import href="common.xsl" />
    <!-- 扩展的高亮配置 -->
    <xsl:import href="highlight.xsl" />
    <xsl:param name="html.stylesheet" select="'css/docbook.css'"/>
    <xsl:param name="html.stylesheet.type" select="'text/css'"/>

</xsl:stylesheet>
