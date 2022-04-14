package xyz.taotao.docbook.core.highlight;

import lombok.extern.slf4j.Slf4j;
import net.sf.xslthl.Config;
import net.sf.xslthl.FilteredElementIterator;
import net.sf.xslthl.MainHighlighter;
import net.sf.xslthl.highlighters.HTMLHighlighter;
import net.sf.xslthl.highlighters.XMLHighlighter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import xyz.taotao.docbook.core.util.VFSUtils;
import xyz.taotao.docbook.core.util.XmlUtils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public class VFSConfig extends Config {
    private static final Map<String, Config> instances = new HashMap<String, Config>();

    public VFSConfig(String filename) {
        super(filename);
    }

    /**
     * Get the default config
     *
     * @return
     */
    public static Config getInstance() {
        return getInstance(null);
    }

    /**
     * Get the config from a given file
     *
     * @param filename
     * @return
     */
    public static Config getInstance(String filename) {
        String key = (filename == null) ? "" : filename;
        if (!instances.containsKey(key)) {
            Config conf = new VFSConfig(filename);
            instances.put(key, conf);
        }
        return instances.get(key);
    }

    @Override
    protected void loadConfiguration(String configFilename) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();

            // Find the configuration filename
            if (configFilename == null || "".equals(configFilename)) {
                log.warn("No config file specified, falling back to default behavior");
                if (System.getProperty(CONFIG_PROPERTY) != null) {
                    configFilename = System.getProperty(CONFIG_PROPERTY);
                } else {
                    configFilename = "xslthl-config.xml";
                }
            }

            log.info(String.format("Loading Xslthl configuration from %s",
                    configFilename));
            Document doc = builder.parse(XmlUtils.getInputSource(configFilename,null));
            NodeList hls = doc.getDocumentElement().getElementsByTagName(
                    "highlighter");
            Map<String, MainHighlighter> fileMapping = new HashMap<String, MainHighlighter>();
            for (int i = 0; i < hls.getLength(); i++) {
                // Process the highlighters
                Element hl = (Element) hls.item(i);
                String id = hl.getAttribute("id");

                if (highlighters.containsKey(id)) {
                    log.warn("Highlighter with id '{}' already exists!", id);
                }

                String filename = hl.getAttribute("file");
                String absFilename = VFSUtils.getResource(filename,configFilename).getPublicURIString();
                if (fileMapping.containsKey(absFilename)) {
                    // no need to load the same file twice.
                    log.warn("Reusing loaded highlighter for {} from {}", id,
                            absFilename);
                    highlighters.put(id, fileMapping.get(absFilename));
                    continue;
                }
                log.info(String.format("Loading %s highligter from %s", id,
                        absFilename));
                try {
                    MainHighlighter mhl = loadHl(id, absFilename);
                    highlighters.put(id, mhl);
                    fileMapping.put(absFilename, mhl);
                } catch (Exception e) {
                    log.error(
                            "Failed to load highlighter from {}: {}",
                            absFilename, e.getMessage(), e);
                }
            }

            // Process the additional settings
            NodeList prefixNode = doc.getDocumentElement()
                    .getElementsByTagName("namespace");
            if (prefixNode.getLength() == 1) {
                Element e = (Element) prefixNode.item(0);
                prefix = e.getAttribute("prefix");
                uri = e.getAttribute("uri");
            }
        } catch (Exception e) {
            log.error(
                    "Cannot read configuration {}: {}", configFilename,
                    e.getMessage(), e);
        }

        if (!highlighters.containsKey("xml")) {
            // add the built-in XML highlighting if it wasn't overloaded
            MainHighlighter xml = new MainHighlighter("xml", null);
            xml.add(new XMLHighlighter());
            highlighters.put("xml", xml);
        }
        if (!highlighters.containsKey("html")) {
            // add the built-in HTML highlighting if it wasn't overloaded
            MainHighlighter html = new MainHighlighter("html", null);
            html.add(new HTMLHighlighter());
            highlighters.put("html", html);
        }
    }

    protected MainHighlighter loadHl(String id, String filename)
            throws Exception {
        MainHighlighter main = new MainHighlighter(id, filename);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbf.newDocumentBuilder();
        Document doc = builder.parse(XmlUtils.getInputSource(filename,null));
        Set<String> tagNames = new HashSet<String>();
        tagNames.add("highlighter");
        tagNames.add("wholehighlighter");
        createHighlighters(main,
                new FilteredElementIterator(doc.getDocumentElement(), tagNames));
        return main;
    }
}
