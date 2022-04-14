package xyz.taotao.docbook.core.fop;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.fop.configuration.ConfigurationException;
import org.apache.fop.configuration.DefaultConfiguration;
import org.apache.fop.configuration.DefaultConfigurationBuilder;
import xyz.taotao.docbook.core.TaotaoDocbookException;
import xyz.taotao.docbook.core.util.VFSUtils;
@Slf4j
public class FopUtils {
  private FopUtils(){
  }

  public static DefaultConfiguration getConfig(String path) throws TaotaoDocbookException{
    if (StringUtils.isBlank(path)){
      path="classpath:META-INF/fop-config.xml";
    }
    DefaultConfigurationBuilder builder=new DefaultConfigurationBuilder();
    try {
      FileObject configFile = VFSUtils.getResource(path, null);
      return builder.build(configFile.getContent().getInputStream());
    } catch (FileSystemException e) {
      log.warn("生成 fop 默认配置错误",e);
      throw new TaotaoDocbookException("生成 fop 默认配置错误",e);
    } catch (ConfigurationException e) {
      log.warn("fop 默认配置解析错误",e);
      throw new TaotaoDocbookException("fop 默认配置解析错误",e);
    }
  }
}
