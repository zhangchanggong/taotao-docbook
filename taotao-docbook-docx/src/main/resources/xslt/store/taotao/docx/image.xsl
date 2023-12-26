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
生成 word 的 numbering xml
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0"
                xmlns:r="http://schemas.openxmlformats.org/package/2006/relationships"
                xmlns:docbook="http://docbook.org/ns/docbook"
                exclude-result-prefixes="xsl docbook r">
    <xsl:output method="xml" version="1.0"
                encoding="UTF-8" indent="yes" standalone="yes"/>
    <xsl:template match="/">
        <Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
            <xsl:apply-templates select="//docbook:imagedata"/>
        </Relationships>
    </xsl:template>
    <xsl:template match="docbook:imagedata">
        <xsl:element name="Relationship" namespace="http://schemas.openxmlformats.org/package/2006/relationships">
            <xsl:attribute name="Id">
                <xsl:value-of select="@xml:id"/>
            </xsl:attribute>
            <xsl:attribute name="Target">
                <xsl:value-of select="@fileref"/>
            </xsl:attribute>
            <xsl:attribute name="Type">
                <xsl:value-of select="'http://schemas.openxmlformats.org/package/2006/relationships/image'"/>
            </xsl:attribute>
        </xsl:element>
    </xsl:template>


</xsl:stylesheet>
