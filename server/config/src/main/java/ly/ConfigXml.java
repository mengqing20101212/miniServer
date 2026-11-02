package ly;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.BiConsumer;
import javax.xml.stream.*;

public final class ConfigXml {
    private ConfigXml() {}

    public static void read(Path file, List<ConfigColumnMeta> columns,
                            BiConsumer<Integer, Map<String, String>> consumer) {
        XMLInputFactory factory = XMLInputFactory.newFactory();
        // GM 上传的 XML 是外部输入，不允许解析外部实体或 DTD。
        factory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        Set<String> names = new HashSet<>();
        for (ConfigColumnMeta column : columns) names.add(column.name());
        try (java.io.InputStream input = Files.newInputStream(file)) {
            XMLStreamReader reader = factory.createXMLStreamReader(input, "UTF-8");
            try {
                reader.nextTag();
                reader.require(XMLStreamConstants.START_ELEMENT, null, "Root");
                while (reader.nextTag() == XMLStreamConstants.START_ELEMENT) {
                    reader.require(XMLStreamConstants.START_ELEMENT, null, "Data");
                    Map<String, String> values = new LinkedHashMap<>();
                    for (int index = 0; index < reader.getAttributeCount(); index++)
                        values.put(reader.getAttributeLocalName(index), reader.getAttributeValue(index));
                    if (!values.keySet().equals(names))
                        throw new ConfigLoadException(file + ":行" + reader.getLocation().getLineNumber()
                                + ":字段应为 " + names + "，实际为 " + values.keySet());
                    consumer.accept(reader.getLocation().getLineNumber(), values);
                    reader.nextTag();
                    reader.require(XMLStreamConstants.END_ELEMENT, null, "Data");
                }
                reader.require(XMLStreamConstants.END_ELEMENT, null, "Root");
                while (reader.hasNext()) reader.next();
            } finally {
                reader.close();
            }
        } catch (java.io.IOException | XMLStreamException error) {
            throw new ConfigLoadException(file + ": " + error.getMessage());
        }
    }
}
