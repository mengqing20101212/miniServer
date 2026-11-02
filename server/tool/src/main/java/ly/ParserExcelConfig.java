package ly;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.lang.model.SourceVersion;

/** Excel 配置表生成器，负责生成 serverConfig 文本、不可变 Config、Manager 和 Checker。 */
public class ParserExcelConfig {
    final String excelFileDir;
    final File serverConfigDir;
    final boolean generateJavaSources;
    List<String> excelFileList = new ArrayList<>(256);

    public ParserExcelConfig(String excelFileDir) {
        this(excelFileDir, Path.of(excelFileDir, "serverConfig").toString(), true);
    }

    public ParserExcelConfig(
            String excelFileDir, String serverConfigDir, boolean generateJavaSources) {
        this.excelFileDir = excelFileDir;
        this.serverConfigDir = Path.of(serverConfigDir).toAbsolutePath().normalize().toFile();
        this.generateJavaSources = generateJavaSources;
    }

    public void startParser() {
        long startTime = System.currentTimeMillis();
        scanAllExcelFile();
        if (excelFileList.isEmpty()) {
            System.out.println("未发现可以解析的配置表，请检查目录:" + excelFileDir);
            return;
        }
        for (String fileName : excelFileList) {
            long begin = System.currentTimeMillis();
            if (!parserFile(fileName)) {
                continue;
            }
            System.out.println(
                    "处理表 " + fileName + ", 完成 耗时:" + (System.currentTimeMillis() - begin));
        }
        System.out.println("转表 耗时: " + (System.currentTimeMillis() - startTime) / 1000 + "s");
    }

    private boolean parserFile(String fileName) {
        String configName =
                fileName.substring(
                        fileName.lastIndexOf(File.separator) + 1, fileName.lastIndexOf("."));
        ExcelConfig excelConfig = new ExcelConfig(configName, fileName);
        if (!excelConfig.parser()) {
            return false;
        }
        if (generateJavaSources) {
            excelConfig.createJavaFile();
        }
        return true;
    }

    private void scanAllExcelFile() {
        excelFileList.clear();
        File dir = new File(excelFileDir);
        if (!dir.isDirectory()) {
            System.out.println("该路径不是: " + excelFileDir + " 目录, 请检查");
            return;
        }
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isFile()
                    && !file.getName().startsWith("@")
                    && (file.getName().endsWith(".xls") || file.getName().endsWith(".xlsx"))) {
                excelFileList.add(file.getAbsolutePath());
            }
        }
    }

    class ExcelConfig {
        String configName;
        String filepathName;
        List<String> serverHeadTitleList = new ArrayList<>(128);
        List<String> clientHeadTitleList = new ArrayList<>(128);
        List<ConfigValueType> tokenType = new ArrayList<>(128);
        List<String> descList = new ArrayList<>(128);

        ExcelConfig(String configName, String filepathName) {
            this.configName = configName;
            this.filepathName = filepathName;
        }

        boolean parser() {
            try (FileInputStream fis = new FileInputStream(new File(filepathName))) {
                Workbook workbook =
                        filepathName.endsWith(".xlsx")
                                ? new XSSFWorkbook(fis)
                                : new HSSFWorkbook(fis);
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                Sheet sheet = workbook.getSheet("Sheet1");
                org.w3c.dom.Document document = javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
                document.appendChild(document.createElement("Root"));
                for (Row row : sheet) {
                    if (row.getRowNum() == 0) {
                        addServerHeadTitle(row);
                    } else if (row.getRowNum() == 1) {
                        addClientHeadTitle(row);
                    } else if (row.getRowNum() == 2) {
                        addTokens(row);
                    } else if (row.getRowNum() == 3) {
                        addDescHeadTitle(row);
                    } else if (row.getRowNum() >= 5) {
                        makeXmlData(document, evaluator, row);
                    }
                }
                writeServerXmlConfig(document);
                return true;
            } catch (NotOfficeXmlFileException e) {
                // 目录里历史上存在把制表符文本误命名成 .xlsx 的样例文件，不能让它阻断全量转表。
                System.err.println(
                        "[WARN] 跳过非法 Excel 文件: " + filepathName + ", reason=" + e.getMessage());
                return false;
            } catch (Exception e) {
                throw new RuntimeException("解析配置表失败:" + filepathName, e);
            }
        }

        private void addTokens(Row row) {
            for (int column = 1; column <= serverHeadTitleList.size(); column++) {
                Cell cell = row.getCell(column, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String value = cell.getCellType() == CellType.STRING ? cell.getStringCellValue().trim() : "";
                if (serverHeadTitleList.get(column - 1) == null || value.isBlank()) {
                    tokenType.add(null);
                } else {
                    try {
                        tokenType.add(ConfigValueType.parse(value));
                    } catch (IllegalArgumentException error) {
                        throw new IllegalArgumentException(filepathName + " / Sheet1 第3行 第" + (column + 1)
                                + "列，字段 " + serverHeadTitleList.get(column - 1) + ": " + error.getMessage(), error);
                    }
                }
            }
        }

        private void makeXmlData(org.w3c.dom.Document document, FormulaEvaluator evaluator, Row row) {
            List<String> serverDataList = new ArrayList<>(128);
            for (int i = 0; i < serverHeadTitleList.size(); i++) {
                serverDataList.add("");
            }
            Cell flagCell = row.getCell(0);
            if (flagCell == null || !"#".equals(getCellValue(evaluator, flagCell))) {
                return;
            }
            for (Cell cell : row) {
                int index = cell.getColumnIndex();
                if (index == 0 || index > serverDataList.size()) {
                    continue;
                }
                if (serverHeadTitleList.get(index - 1) == null) {
                    continue;
                }
                String value = getCellValue(evaluator, cell);
                if (value != null) {
                    serverDataList.set(index - 1, value);
                }
            }
            if (!validateRow(serverDataList, row.getRowNum() + 1)) return;
            org.w3c.dom.Element data = document.createElement("Data");
            for (int index = 0; index < serverDataList.size(); index++) {
                if (serverHeadTitleList.get(index) != null && typeAt(index) != null)
                    data.setAttribute(serverHeadTitleList.get(index), serverDataList.get(index));
            }
            document.getDocumentElement().appendChild(data);
        }

        private boolean validateRow(List<String> values, int rowNumber) {
            for (int index = 0; index < values.size(); index++) {
                ConfigValueType type = typeAt(index);
                if (serverHeadTitleList.get(index) == null || type == null) continue;
                try {
                    ConfigValues.read(type, values.get(index),
                        filepathName + " / Sheet1 第" + rowNumber + "行 第" + (index + 2)
                                + "列，字段 " + serverHeadTitleList.get(index));
                } catch (ConfigLoadException error) {
                    System.err.println(error.getMessage());
                    return false;
                }
            }
            return true;
        }

        private void addDescHeadTitle(Row row) {
            for (int column = 1; column < row.getLastCellNum(); column++) {
                Cell cell = row.getCell(column, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                if (cell.getCellType() == CellType.STRING) {
                    descList.add(cell.getStringCellValue().replaceAll("\n", ", "));
                } else {
                    descList.add("  null");
                }
            }
        }

        private void addClientHeadTitle(Row row) {
            for (int column = 1; column < row.getLastCellNum(); column++) {
                Cell cell = row.getCell(column, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                clientHeadTitleList.add(
                        cell.getCellType() == CellType.STRING ? cell.getStringCellValue() : null);
            }
        }

        private void addServerHeadTitle(Row row) {
            for (int column = 1; column < row.getLastCellNum(); column++) {
                Cell cell = row.getCell(column, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                if (cell.getCellType() == CellType.STRING) {
                    String value = cell.getStringCellValue().trim();
                    if (isJavaKeyValue(value)) {
                        System.err.printf("%s 表头字段(%s)是Java关键字，请换一个%n", configName, value);
                        value = value.toUpperCase();
                    }
                    serverHeadTitleList.add(value.isBlank() ? null : value);
                } else {
                    serverHeadTitleList.add(null);
                }
            }
        }

        private boolean isJavaKeyValue(String value) {
            return value != null && !value.isBlank() && SourceVersion.isKeyword(value);
        }

        private String getCellValue(FormulaEvaluator evaluator, Cell cell) {
            return switch (cell.getCellType()) {
                case STRING -> cell.getStringCellValue();
                case NUMERIC -> numericText(cell.getNumericCellValue());
                case BOOLEAN -> cell.getBooleanCellValue() + "";
                case FORMULA -> getFormulaValue(evaluator, cell);
                case ERROR -> throw new IllegalArgumentException("Excel 错误单元格: " + cell.getAddress());
                default -> null;
            };
        }

        private String getFormulaValue(FormulaEvaluator evaluator, Cell cell) {
            try {
                CellValue cellValue = evaluator.evaluate(cell);
                return switch (cellValue.getCellType()) {
                    case NUMERIC -> numericText(cellValue.getNumberValue());
                    case BOOLEAN -> cellValue.getBooleanValue() + "";
                    case STRING -> cellValue.getStringValue();
                    case ERROR -> throw new IllegalArgumentException("Excel 公式错误: " + cell.getAddress());
                    default -> null;
                };
            } catch (Exception e) {
                throw new IllegalArgumentException(filepathName + " 单元格 " + cell.getAddress() + " 公式求值失败", e);
            }
        }

        private String numericText(double value) {
            if (!Double.isFinite(value)) throw new IllegalArgumentException("非有限数值");
            return BigDecimal.valueOf(value).stripTrailingZeros().toPlainString();
        }

        private void writeServerXmlConfig(org.w3c.dom.Document document) throws Exception {
            Files.createDirectories(serverConfigDir.toPath());
            javax.xml.transform.Transformer transformer = javax.xml.transform.TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            transformer.transform(new javax.xml.transform.dom.DOMSource(document),
                    new javax.xml.transform.stream.StreamResult(new File(serverConfigDir, configName + ".xml")));
        }

        private void createJavaFile() {
            createSimpleJavaFile();
            createConfigManagerJavaFile();
            createCheckerBaseJavaFile();
            createCheckerJavaFileIfAbsent();
        }

        private File configJavaDir() {
            return Path.of(excelFileDir)
                    .toAbsolutePath()
                    .resolve("../server/config/src/main/java/ly/config")
                    .toFile();
        }

        private void createSimpleJavaFile() {
            String className = classSimpleName() + "Config";
            File dstFile = new File(configJavaDir(), className + ".java");
            String fieldStr = "";
            String methodStr = "";
            try {
                if (dstFile.exists()) {
                    String old = readFile(dstFile);
                    fieldStr = extractFieldStr(old);
                    methodStr = extractMethodStr(old);
                }
                if (methodStr.isBlank()) {
                    methodStr = "public void afterLoad() {}\n";
                }
                StringBuilder fields = new StringBuilder();
                StringBuilder params = new StringBuilder();
                StringBuilder assigns = new StringBuilder();
                forEachGeneratedColumn(
                        (title, type, index) -> {
                            String javaType = javaType(type);
                            String desc = index < descList.size() ? descList.get(index) : "";
                            if (desc != null && !desc.isBlank()) {
                                fields.append("  /**")
                                        .append(desc.replace("\n", " "))
                                        .append("*/\n");
                            }
                            fields.append("  public final ")
                                    .append(javaType)
                                    .append(" ")
                                    .append(title)
                                    .append(";\n\n");
                            if (!params.isEmpty()) {
                                params.append(", ");
                            }
                            params.append(javaType).append(" ").append(title);
                            assigns.append("    this.")
                                    .append(title)
                                    .append(" = ")
                                    .append(title)
                                    .append(";\n");
                        });
                String source =
                        "package ly.config;\n\n"
                                + "import java.util.List;\n"
                                + "import ly.utils.KV;\n\n"
                                + "/***\n"
                                + " * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@\n"
                                + " */\n"
                                + "public class "
                                + className
                                + " {\n"
                                + fields
                                + "  // @@@@@自定义属性开始区@@@@@\n"
                                + fieldStr
                                + "\n  // @@@@@自定义属性结束区@@@@@\n\n"
                                + "  public "
                                + className
                                + "("
                                + params
                                + ") {\n"
                                + assigns
                                + "  }\n\n"
                                + "  // @@@@@自定义方法开始区@@@@@\n"
                                + methodStr
                                + "\n  // @@@@@自定义方法结束区@@@@@\n"
                                + "}\n";
                writeFile(dstFile, source);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void createConfigManagerJavaFile() {
            String simple = classSimpleName();
            File dstFile = new File(configJavaDir(), simple + "ConfigManager.java");
            String fieldStr = "";
            String methodStr = "";
            String clearStr = "";
            try {
                if (dstFile.exists()) {
                    String old = readFile(dstFile);
                    fieldStr = extractFieldStr(old);
                    methodStr = extractMethodStr(old);
                    clearStr = extractClearStr(old);
                }
                if (methodStr.isBlank()) {
                    methodStr = "    @Override\n    protected void afterLoad() {\n    }\n";
                }
                String idStr = idColumnName();
                String idType =
                        idStr == null
                                ? null
                                : boxedJavaType(tokenType.get(serverHeadTitleList.indexOf(idStr)));
                String defMap =
                        idStr == null
                                ? ""
                                : "    private Map<"
                                        + idType
                                        + ", "
                                        + simple
                                        + "Config> configMap = Map.of();\n\n";
                String mapGetter =
                        idStr == null
                                ? ""
                                : "    public Map<"
                                        + idType
                                        + ", "
                                        + simple
                                        + "Config> getConfigMap() {\n"
                                        + "      return configMap;\n"
                                        + "    }\n\n";
                String localVars = buildLocalVars();
                String parseCode = buildParseCode();
                String ctorArgs = buildCtorArgs();
                String putMap =
                        idStr == null
                                ? ""
                                : "          newMap.put(config." + idStr + ", config);\n";
                String clearMap = idStr == null ? "" : "      configMap = Map.of();\n";
                String newMap =
                        idStr == null
                                ? ""
                                : "      Map<"
                                        + idType
                                        + ", "
                                        + simple
                                        + "Config> newMap = new HashMap<>();\n";
                String publishMap =
                        idStr == null ? "" : "        configMap = Map.copyOf(newMap);\n";
                String source =
                        "package ly.config;\n\n"
                                + "import java.io.BufferedReader;\n"
                                + "import java.io.File;\n"
                                + "import java.io.FileReader;\n"
                                + "import java.io.IOException;\n"
                                + "import java.util.ArrayList;\n"
                                + "import java.util.HashMap;\n"
                                + "import java.util.List;\n"
                                + "import java.util.Map;\n"
                                + "import ly.AbstractConfigManger;\n"
                                + "import ly.ConfigLoadException;\n"
                                + "import ly.InterfaceConfigManagerProxy;\n"
                                + "import ly.utils.KV;\n"
                                + "import org.slf4j.Logger;\n\n"
                                + "/*\n"
                                + " * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@\n"
                                + " * File: "
                                + simple
                                + "ConfigManager\n"
                                + " */\n"
                                + "public class "
                                + simple
                                + "ConfigManager implements InterfaceConfigManagerProxy {\n"
                                + "  public static "
                                + simple
                                + "ConfigManagerImpl getInstance() {\n"
                                + "    return ly.ConfigService.getInstance().table("
                                + simple
                                + "ConfigManagerImpl.class);\n"
                                + "  }\n\n"
                                + "  @Override\n"
                                + "  public AbstractConfigManger createTable() { return new "
                                + simple
                                + "ConfigManagerImpl(); }\n\n"
                                + "  @Override\n"
                                + "  public String getConfigFileName() { return \""
                                + configName
                                + ".xml"
                                + "\"; }\n\n"
                                + "  public static class "
                                + simple
                                + "ConfigManagerImpl extends AbstractConfigManger {\n"
                                + "    private List<"
                                + simple
                                + "Config> configList = List.of();\n"
                                + defMap
                                + "    // @@@@@自定义属性开始区@@@@@\n"
                                + fieldStr
                                + "\n"
                                + "    // @@@@@自定义属性结束区@@@@@\n\n"
                                + "    @Override\n"
                                + "    public void reload(Logger logger, String configDir) throws"
                                + " ConfigLoadException {\n"
                                + typeConstants()
                                + "      String fileName = configDir + File.separator +"
                                + " getConfigFileName();\n"
                                + "      " + simple + "ConfigChecker checker = new " + simple + "ConfigChecker();\n"
                                + "      List<"
                                + simple
                                + "Config> newList = new ArrayList<>();\n"
                                + newMap
                                + "      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {\n"
                                + localVars
                                + "          try {\n"
                                + parseCode
                                + "          } catch (ConfigLoadException error) {\n"
                                + "            logger.error(error.getMessage());\n"
                                + "            return;\n"
                                + "          }\n"
                                + "          "
                                + simple
                                + "Config config = new "
                                + simple
                                + "Config("
                                + ctorArgs
                                + ");\n"
                                + "          newList.add(config);\n"
                                + putMap
                                + "      });\n"
                                + "      configList = List.copyOf(newList);\n"
                                + publishMap
                                + "    }\n\n"
                                + "    @Override\n"
                                + "    protected void finishLoad(Logger logger) {\n"
                                + "      for (" + simple + "Config config : configList) config.afterLoad();\n"
                                + "      new " + simple + "ConfigChecker().checkAfterParse(logger, configList);\n"
                                + "    }\n\n"
                                + "    @Override\n"
                                + "    public void clear() {\n"
                                + "      configList = List.of();\n"
                                + clearMap
                                + "      // @@@@@自定义clear方法开始区@@@@@\n"
                                + clearStr
                                + "\n      // @@@@@自定义clear方法结束区@@@@@\n"
                                + "    }\n\n"
                                + "    public List<"
                                + simple
                                + "Config> getConfigList() {\n      return configList;\n    }\n\n"
                                + mapGetter
                                + "    @Override\n"
                                + "    public String getConfigFileName() {\n      return \""
                                + configName
                                + ".xml\";\n    }\n\n"
                                + "    // @@@@@自定义方法开始区@@@@@\n"
                                + methodStr
                                + "\n    // @@@@@自定义方法结束区@@@@@\n"
                                + "  }\n"
                                + "}\n";
                writeFile(dstFile, source);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void createCheckerBaseJavaFile() {
            String simple = classSimpleName();
            File dstFile = new File(configJavaDir(), simple + "ConfigCheckerBase.java");
            StringBuilder columns = new StringBuilder();
            forEachGeneratedColumn(
                    (title, type, index) -> {
                        if (!columns.isEmpty()) {
                            columns.append(",\n");
                        }
                        columns.append("        new ConfigColumnMeta(")
                                .append(index)
                                .append(", \"")
                                .append(title)
                                .append("\", \"")
                                .append(normalizeType(type))
                                .append("\")");
                    });
            String source =
                    "package ly.config;\n\n"
                            + "import java.util.List;\n"
                            + "import ly.AbstractConfigChecker;\n"
                            + "import ly.ConfigColumnMeta;\n\n"
                            + "/** 自动生成的配置表检测基类，请不要手动修改。 */\n"
                            + "public abstract class "
                            + simple
                            + "ConfigCheckerBase extends AbstractConfigChecker<"
                            + simple
                            + "Config> {\n"
                            + "  @Override\n"
                            + "  public String getConfigFileName() {\n    return \""
                            + configName
                            + ".xml\";\n  }\n\n"
                            + "  @Override\n"
                            + "  public List<ConfigColumnMeta> getExpectedColumns() {\n"
                            + "    return List.of(\n"
                            + columns
                            + ");\n"
                            + "  }\n"
                            + "}\n";
            try {
                writeFile(dstFile, source);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void createCheckerJavaFileIfAbsent() {
            String simple = classSimpleName();
            File dstFile = new File(configJavaDir(), simple + "ConfigChecker.java");
            if (dstFile.exists()) {
                return;
            }
            String source =
                    "package ly.config;\n\n"
                            + "/** 配置表自定义检测扩展类，可在这里补充跨字段或跨表校验。 */\n"
                            + "public class "
                            + simple
                            + "ConfigChecker extends "
                            + simple
                            + "ConfigCheckerBase {\n"
                            + "}\n";
            try {
                writeFile(dstFile, source);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private String buildLocalVars() {
            StringBuilder sb = new StringBuilder();
            forEachGeneratedColumn(
                    (title, type, index) ->
                            sb.append("          ")
                                    .append(javaType(type))
                                    .append(" ")
                                    .append("parsedField").append(index)
                                    .append(" = ")
                                    .append(defaultJavaValue(type))
                                    .append(";\n"));
            return sb.toString();
        }

        private String typeConstants() {
            StringBuilder result = new StringBuilder();
            forEachGeneratedColumn((title, type, index) -> result.append("      ly.ConfigValueType type")
                    .append(index).append(" = ly.ConfigValueType.parse(\"").append(normalizeType(type)).append("\");\n"));
            return result.toString();
        }

        private String buildParseCode() {
            StringBuilder result = new StringBuilder();
            forEachGeneratedColumn((title, type, index) -> result.append("            ")
                    .append("parsedField").append(index).append(" = ly.ConfigValues.read(type").append(index)
                    .append(", values.get(\"").append(title)
                    .append("\"), fileName + \":行\" + lineNumber + \":字段 ")
                    .append(title).append("\");\n"));
            return result.toString();
        }

        private String buildCtorArgs() {
            List<String> args = new ArrayList<>();
            forEachGeneratedColumn((title, type, index) -> args.add("parsedField" + index));
            return String.join(", ", args);
        }

        private int generatedColumnCount() {
            final int[] count = {0};
            forEachGeneratedColumn((title, type, index) -> count[0]++);
            return count[0];
        }

        private void forEachGeneratedColumn(ColumnConsumer consumer) {
            int outIndex = 0;
            for (int i = 0; i < serverHeadTitleList.size(); i++) {
                String title = serverHeadTitleList.get(i);
                if (title == null) {
                    continue;
                }
                ConfigValueType type = typeAt(i);
                if (type == null) {
                    continue;
                }
                try {
                    consumer.accept(title, type, outIndex);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                outIndex++;
            }
        }

        private String idColumnName() {
            if (serverHeadTitleList.contains("id")) return "id";
            if (serverHeadTitleList.contains("Id")) return "Id";
            if (serverHeadTitleList.contains("ID")) return "ID";
            return null;
        }

        private ConfigValueType typeAt(int index) {
            return index >= 0 && index < tokenType.size() ? tokenType.get(index) : null;
        }

        private String javaType(ConfigValueType type) {
            return ConfigTypeJava.javaType(type, false);
        }

        private String boxedJavaType(ConfigValueType type) {
            return ConfigTypeJava.javaType(type, true);
        }

        private String defaultJavaValue(ConfigValueType type) {
            return switch (type.kind()) {
                case "byte", "short", "int" -> "0";
                case "long" -> "0L";
                case "float" -> "0F";
                case "double" -> "0D";
                case "bool" -> "false";
                default -> "null";
            };
        }

        private String normalizeType(ConfigValueType type) {
            return type.declaration();
        }

        private String classSimpleName() {
            return configName.substring(0, 1).toUpperCase() + configName.substring(1);
        }

        private void writeFile(File file, String source) throws IOException {
            Files.createDirectories(file.toPath().getParent());
            try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)) {
                writer.write(source);
            }
        }

        public static String readFile(File file) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                return content.toString();
            } catch (IOException e) {
                return "";
            }
        }

        private String extractFieldStr(String source) {
            return extract(source, "// @@@@@自定义属性开始区@@@@@", "// @@@@@自定义属性结束区@@@@@");
        }

        public static String extractMethodStr(String source) {
            return extract(source, "// @@@@@自定义方法开始区@@@@@", "// @@@@@自定义方法结束区@@@@@");
        }

        public static String formatJavaCode(String source) {
            return source;
        }

        private String extractClearStr(String source) {
            return extract(source, "// @@@@@自定义clear方法开始区@@@@@", "// @@@@@自定义clear方法结束区@@@@@");
        }

        private static String extract(String source, String begin, String end) {
            Pattern pattern =
                    Pattern.compile(
                            Pattern.quote(begin) + "(.+?)" + Pattern.quote(end), Pattern.DOTALL);
            Matcher matcher = pattern.matcher(source == null ? "" : source);
            return matcher.find() ? matcher.group(1).trim() : "";
        }
    }

    @FunctionalInterface
    interface ColumnConsumer {
        void accept(String title, ConfigValueType type, int index) throws IOException;
    }

    public static void main(String[] args) {
        if (args != null && args.length == 3 && "--data-only".equals(args[0])) {
            new ParserExcelConfig(args[1], args[2], false).startParser();
            return;
        }
        String dir = args != null && args.length > 0 ? args[0] : "D:\\WORK\\me\\miniServer\\excel";
        new ParserExcelConfig(dir).startParser();
    }
}
