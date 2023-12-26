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

package store.taotao.docbook.core.util;

import lombok.extern.slf4j.Slf4j;
import net.sf.saxon.Configuration;
import net.sf.saxon.TransformerFactoryImpl;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import store.taotao.docbook.core.TaotaoDocbookException;
import store.taotao.docbook.core.docbook.VFSURIResolver;
import store.taotao.docbook.core.highlight.Highlight;
import store.taotao.docbook.core.saxon.UUIDExtension;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;

/**
 * 统一的 xml 处理工具
 */
@Slf4j
public class XmlUtils {
    private XmlUtils() {

    }

    /**
     * 基于 SPI 获取 TransformerFactory
     * @return TransformerFactory 的实现
     */
    public static TransformerFactory getTransformerFactory()  {
        log.debug("------------------ getTransformerFactory 开始 -----------------");
        TransformerFactory transformerFactory = ServiceUtils.getService(TransformerFactory.class);
        log.debug("transformerFactory=[{}]", transformerFactory);
        log.debug("------------------ getTransformerFactory 结束 -----------------");
        return transformerFactory;
    }

    /**
     * 根据指定资源获取转换器
     * @param href 资源的相对地址，如果是绝对地址则 base 不起作用
     * @param base 资源的定位基址，不包括资源本身
     * @return 转换器
     * @throws TaotaoDocbookException 转换后的外部异常
     */
    public static Transformer getTransformer(String href, String base) throws TaotaoDocbookException{
        log.debug("------------------ getTransformer 开始 -----------------");
        TransformerFactory transformerFactory = getTransformerFactory();
        transformerFactory.setURIResolver(new VFSURIResolver());
        configFactory(transformerFactory);

        try {
            Source source = getSource(href, base);
            Transformer transformer = transformerFactory.newTransformer(source);
            log.debug("transformer=[{}]", transformer);
            return transformer;
        } catch (TransformerConfigurationException e) {
            log.warn("transformer 配置错误",e);
            throw new TaotaoDocbookException("transformer 配置错误",e);
        } finally {
            log.debug("------------------ getTransformer 结束 -----------------");
        }
    }

    private static void configFactory(TransformerFactory transformerFactory) {
        if (transformerFactory instanceof TransformerFactoryImpl){
            TransformerFactoryImpl tfi=(TransformerFactoryImpl) transformerFactory;

            Configuration configuration=tfi.getConfiguration();
            configuration.registerExtensionFunction(new Highlight());
            configuration.registerExtensionFunction(new UUIDExtension());
        }
    }


    /**
     * 基于 SPI 获取 SAXParserFactory
     * @return SAXParserFactory 的实现
     */
    public static SAXParserFactory getSAXParserFactory(){
        log.debug("------------------ getSAXParserFactory 开始 -----------------");
        SAXParserFactory saxParserFactory = ServiceUtils.getService(SAXParserFactory.class);
        log.debug("saxParserFactory=[{}]", saxParserFactory);
        log.debug("------------------ getSAXParserFactory 结束 -----------------");
        return saxParserFactory;
    }
    /**
     * 根据 href 和 base 获得资源的 StreamSource 流。
     * @param href xml 中 include、import、document 的 href 属性
     * @param base xml 或 xsl 的资源位置 vfs url 的形式
     * @return href 对应的 StreamSource 。
     * @throws TaotaoDocbookException 转义后的外部异常
     */
    public static Source getSource(String href, String base) throws TaotaoDocbookException{
        log.debug("------------------ getStreamSource 开始 -----------------");
        try {
            FileObject resource=VFSUtils.getResource(href,base);
            if (null==resource){
                return null;
            }
            log.debug("------------------ getStreamSource 结束 -----------------");
            return new StreamSource(resource.getContent().getInputStream(),resource.getPublicURIString());
        } catch (FileSystemException e) {
            log.warn("getStreamSource 时 vfs 错误，href=[{}],base=[{}]",href,base,e);
            throw new TaotaoDocbookException("getStreamSource 时 vfs 错误",e);
        }
    }

    /**
     * 根据 href 和 base 获得资源的 SAXSource 流。
     * @param href xml 中 include、import、document 的 href 属性
     * @param base xml 或 xsl 的资源位置 vfs url 的形式
     * @return href 对应的 SAXSource 。
     * @throws TaotaoDocbookException 转义后的外部异常
     */
    public static SAXSource getSAXSource(String href, String base) throws TaotaoDocbookException {
        log.debug("------------------ getSAXSource 开始 -----------------");
        SAXParserFactory saxParserFactory = XmlUtils.getSAXParserFactory();
        saxParserFactory.setXIncludeAware(true);
        saxParserFactory.setNamespaceAware(true);
        XMLReader xmlReader = null;
        try {
            xmlReader = saxParserFactory.newSAXParser().getXMLReader();
            InputSource inputSource = XmlUtils.getInputSource(href,base);
            SAXSource saxSource = new SAXSource(xmlReader, inputSource);
            log.debug("saxSource=[{}]",saxSource);
            return saxSource;
        } catch (SAXException e) {
            log.warn("SAX 解析器错误",e);
            throw new TaotaoDocbookException("SAX 解析器错误",e);
        } catch (ParserConfigurationException e) {
            log.warn("解析器配置错误",e);
            throw new TaotaoDocbookException("解析器配置错误",e);
        } catch (IOException e) {
            log.warn("获取输入流错误",e);
            throw new TaotaoDocbookException("获取输入流错误",e);
        }finally {
            log.debug("------------------ getSAXSource 结束 -----------------");
        }
    }
    /**
     * 根据 href 和 base 获得资源的 InputSource 流。
     * @param href xml 中 include、import、document 的 href 属性
     * @param base xml 或 xsl 的资源位置 vfs url 的形式
     * @return href 对应的 InputSource 。
     * @throws IOException 发生原始流创建错误
     */
    public static InputSource getInputSource(String href, String base) throws IOException {
        log.debug("------------------ getInputSource 开始 -----------------");
        try {
            FileObject resource=VFSUtils.getResource(href,base);
            if (null==resource){
                return null;
            }
            InputSource inputSource = new InputSource(resource.getContent().getInputStream());
            inputSource.setSystemId(resource.getPublicURIString());
            log.debug("inputSource=[{}]",inputSource);
            log.debug("------------------ getInputSource 结束 -----------------");
            return inputSource;
        } catch (FileSystemException e) {
            log.warn("getInputSource 时 vfs 错误，href=[{}],base=[{}]",href,base,e);
            throw new IOException("getInputSource 时 vfs 错误",e);
        }
    }

    /**
     * 根据 href 和 base 获得资源的 Result 流。
     * @param href xml 中 include、import、document 的 href 属性
     * @param base xml 或 xsl 的资源位置 vfs url 的形式
     * @return href 对应的 Result 。
     * @throws TaotaoDocbookException 转义后的外部异常
     */
    public static Result getResult(String href, String base) throws TaotaoDocbookException{
        log.debug("------------------ getResult 开始 -----------------");
        try {
            FileObject resource=VFSUtils.getResource(href,base);
            if (null==resource){
                return null;
            }

            if (!resource.getParent().exists()){
                log.debug("创建父路径 parent=[{}]",resource.getParent());
                resource.getParent().createFolder();
            }
            if (resource.exists()){
                log.debug("删除已经存在的文件 resource=[{}]",resource);
                resource.delete();
            }
            resource.createFile();
            StreamResult result = new StreamResult(resource.getContent().getOutputStream());
            result.setSystemId(resource.getPublicURIString());
            log.debug("result=[{}]",result);
            return result;
        } catch (FileSystemException e) {
            log.warn("getResult 时 vfs 错误，href=[{}],base=[{}]",href,base,e);
            throw new TaotaoDocbookException("getResult 时 vfs 错误",e);
        }finally {
            log.debug("------------------ getResult 结束 -----------------");
        }
    }
}
