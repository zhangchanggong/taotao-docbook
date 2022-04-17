<?xml version='1.0' encoding='utf-8'?>
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
