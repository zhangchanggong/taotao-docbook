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
将 docbook 处理成单一文档
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="2.0"
                xmlns:uuid="http://store.taotao.com/uuid"
                xmlns:docbook="http://docbook.org/ns/docbook"
                exclude-result-prefixes="xsl docbook uuid">
    <xsl:output method="xml" version="1.0"
                encoding="UTF-8" indent="yes"/>

    <xsl:template match="docbook:orderedlist[not(@id) or @id='']">
        <xsl:call-template name="genId">
            <xsl:with-param name="id_prefix" select="'ol'"/>
        </xsl:call-template>
    </xsl:template>
    <xsl:template match="docbook:imagedata[not(@id) or @id='']">
        <xsl:call-template name="genId">
            <xsl:with-param name="id_prefix" select="'image'"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="genId">
        <xsl:param name="id_prefix"/>
        <xsl:copy>
            <xsl:variable name="refId" select="uuid:randomUUID()"/>
            <xsl:attribute name="xml:id"><xsl:value-of select="$id_prefix"/>.<xsl:value-of select="$refId"/></xsl:attribute>
            <xsl:apply-templates select="node()|@*"/>
        </xsl:copy>
    </xsl:template>
    <xsl:template match="node()|@*">
        <xsl:copy>
            <xsl:apply-templates select="node()|@*"/>
        </xsl:copy>
    </xsl:template>
    <!--    <xsl:template match="@*">-->
    <!--        <xsl:message>-->
    <!--            <xsl:value-of select="."/>-->
    <!--        </xsl:message>-->
    <!--        <xsl:attribute name="{name(.)}" namespace="{namespace-uri(.)}" >-->
    <!--            <xsl:value-of select="."/>-->
    <!--        </xsl:attribute>-->
    <!--    </xsl:template>-->


</xsl:stylesheet>
