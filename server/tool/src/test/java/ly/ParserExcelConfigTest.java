package ly;

import static org.junit.Assert.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;
import java.util.*;
import javax.tools.ToolProvider;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParserExcelConfigTest {
    @Rule public TemporaryFolder temporary = new TemporaryFolder();

    @Test
    public void generatesCompilesAndLoadsActualExcelIncludingSimpleCollections() throws Exception {
        Path project = temporary.newFolder().toPath();
        Path excel = Files.createDirectory(project.resolve("excel"));
        Path data = project.resolve("data");
        String[] fields = {"id", "tiny", "small", "large", "ratio", "rate", "enabled", "drops",
                "tags", "costs", "rates", "flags", "empty", "formula", "ignored", "last"};
        String[] types = {"int", "byte", "short", "long", "float", "double", "bool", "string",
                "list<string>", "map<int,long>", "list<double>", "map<int,bool>",
                "list<int>", "double", "", "int"};
        Object[] values = {7, 12, 30000, "9223372036854775807", 1.25, 2.75, true, "1001;2|1002;3",
                "alpha;中文<&", "1:5000000000", "1.5;2.25",
                "1:true;2:false", "", "=1.5+2.25", "ignored", 9};
        writeExcel(excel.resolve("typed.xlsx"), fields, types, values);
        new ParserExcelConfig(excel.toString(), data.toString(), true).startParser();
        Path generated = project.resolve("server/config/src/main/java/ly/config");
        Path classes = Files.createDirectory(project.resolve("classes"));
        List<String> compilerArguments = new ArrayList<>(List.of("-encoding", "UTF-8", "-classpath",
                System.getProperty("java.class.path"), "-d", classes.toString()));
        try (java.util.stream.Stream<Path> sources = Files.list(generated)) {
            sources.filter(path -> path.toString().endsWith(".java")).forEach(path -> compilerArguments.add(path.toString()));
        }
        assertEquals("生成的 Java 必须可编译", 0,
                ToolProvider.getSystemJavaCompiler().run(null, null, null, compilerArguments.toArray(String[]::new)));
        try (URLClassLoader loader = new URLClassLoader(new URL[]{classes.toUri().toURL()}, getClass().getClassLoader())) {
            Class<?> managerClass = loader.loadClass("ly.config.TypedConfigManager$TypedConfigManagerImpl");
            Object manager = managerClass.getConstructor().newInstance();
            managerClass.getMethod("reload", Logger.class, String.class)
                    .invoke(manager, LoggerFactory.getLogger(getClass()), data.toString());
            List<?> rows = (List<?>) managerClass.getMethod("getConfigList").invoke(manager);
            assertEquals(1, rows.size());
            Object config = rows.getFirst();
            Map<String, Object> expected = new LinkedHashMap<>();
            expected.put("id", 7); expected.put("tiny", (byte) 12); expected.put("small", (short) 30000);
            expected.put("large", Long.MAX_VALUE); expected.put("ratio", 1.25F); expected.put("rate", 2.75D);
            expected.put("enabled", true); expected.put("drops", "1001;2|1002;3");
            expected.put("tags", List.of("alpha", "中文<&")); expected.put("costs", Map.of(1, 5000000000L));
            expected.put("rates", List.of(1.5D, 2.25D));
            expected.put("flags", Map.of(1, true, 2, false));
            expected.put("empty", List.of()); expected.put("formula", 3.75D); expected.put("last", 9);
            for (Map.Entry<String, Object> entry : expected.entrySet()) {
                Field field = config.getClass().getField(entry.getKey());
                assertEquals(entry.getKey(), entry.getValue(), field.get(config));
            }
            Field collection = config.getClass().getField("costs");
            assertEquals("java.util.Map<java.lang.Integer, java.lang.Long>", collection.getGenericType().getTypeName());
            assertEquals(config, ((Map<?, ?>) managerClass.getMethod("getConfigMap").invoke(manager)).get(7));
            Path xml = data.resolve("typed.xml");
            org.w3c.dom.Document document = javax.xml.parsers.DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().parse(xml.toFile());
            org.w3c.dom.Element invalid = (org.w3c.dom.Element) document.getElementsByTagName("Data").item(0).cloneNode(true);
            invalid.setAttribute("id", "bad");
            document.getDocumentElement().appendChild(invalid);
            javax.xml.transform.TransformerFactory.newInstance().newTransformer().transform(
                    new javax.xml.transform.dom.DOMSource(document), new javax.xml.transform.stream.StreamResult(xml.toFile()));
            managerClass.getMethod("reload", Logger.class, String.class)
                    .invoke(manager, LoggerFactory.getLogger(getClass()), data.toString());
            assertEquals(1, ((List<?>) managerClass.getMethod("getConfigList").invoke(manager)).size());
        }
    }

    @Test
    public void dataOnlyExportUsesTheSameTypeParserAndReportsTheExcelLocation() throws Exception {
        Path project = temporary.newFolder().toPath();
        Path excel = Files.createDirectory(project.resolve("excel"));
        Path output = project.resolve("data");
        writeExcel(excel.resolve("invalid.xlsx"), new String[]{"id", "costs"},
                new String[]{"int", "list<long>"}, new Object[]{1, "10;bad"});
        java.io.ByteArrayOutputStream errors = new java.io.ByteArrayOutputStream();
        java.io.PrintStream previous = System.err;
        try {
            System.setErr(new java.io.PrintStream(errors, true, java.nio.charset.StandardCharsets.UTF_8));
            new ParserExcelConfig(excel.toString(), output.toString(), false).startParser();
        } finally { System.setErr(previous); }
        assertTrue(errors.toString(java.nio.charset.StandardCharsets.UTF_8).contains("第6行"));
        assertTrue(errors.toString(java.nio.charset.StandardCharsets.UTF_8).contains("字段 costs"));
        assertTrue(Files.exists(output.resolve("invalid.xml")));
        assertFalse(Files.readString(output.resolve("invalid.xml")).contains("<Data"));
    }

    private void writeExcel(Path file, String[] fields, String[] types, Object[] values) throws Exception {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet1");
            for (int index = 0; index < 6; index++) sheet.createRow(index);
            for (int column = 0; column < fields.length; column++) {
                sheet.getRow(0).createCell(column + 1).setCellValue(fields[column]);
                sheet.getRow(2).createCell(column + 1).setCellValue(types[column]);
                Cell cell = sheet.getRow(5).createCell(column + 1);
                Object value = values[column];
                if (value instanceof Number number) cell.setCellValue(number.doubleValue());
                else if (value instanceof Boolean bool) cell.setCellValue(bool);
                else if (value.toString().startsWith("=")) cell.setCellFormula(value.toString().substring(1));
                else cell.setCellValue(value.toString());
            }
            sheet.getRow(5).createCell(0).setCellValue("#");
            try (java.io.OutputStream output = Files.newOutputStream(file)) { workbook.write(output); }
        }
    }
}
