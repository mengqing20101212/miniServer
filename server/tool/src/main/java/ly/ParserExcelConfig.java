package ly;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.lang.model.SourceVersion;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/** Excel 配置表生成器，负责生成 serverConfig 文本、不可变 Config、Manager 和 Checker。 */
public class ParserExcelConfig {
  final String excelFileDir;
  List<String> excelFileList = new ArrayList<>(256);

  public ParserExcelConfig(String excelFileDir) {
    this.excelFileDir = excelFileDir;
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
      System.out.println("处理表 " + fileName + ", 完成 耗时:" + (System.currentTimeMillis() - begin));
    }
    System.out.println("转表 耗时: " + (System.currentTimeMillis() - startTime) / 1000 + "s");
  }

  private boolean parserFile(String fileName) {
    String configName =
        fileName.substring(fileName.lastIndexOf(File.separator) + 1, fileName.lastIndexOf("."));
    ExcelConfig excelConfig = new ExcelConfig(configName, fileName);
    if (!excelConfig.parser()) {
      return false;
    }
    excelConfig.createJavaFile();
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
    List<String> tokenType = new ArrayList<>(128);
    List<String> descList = new ArrayList<>(128);

    ExcelConfig(String configName, String filepathName) {
      this.configName = configName;
      this.filepathName = filepathName;
    }

    boolean parser() {
      try (FileInputStream fis = new FileInputStream(new File(filepathName))) {
        Workbook workbook = filepathName.endsWith(".xlsx") ? new XSSFWorkbook(fis) : new HSSFWorkbook(fis);
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        Sheet sheet = workbook.getSheet("Sheet1");
        StringBuilder serverLines = new StringBuilder();
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
            makeTextData(serverLines, evaluator, row);
          }
        }
        writeServerLineConfig(serverLines);
        return true;
      } catch (NotOfficeXmlFileException e) {
        // 目录里历史上存在把制表符文本误命名成 .xlsx 的样例文件，不能让它阻断全量转表。
        System.err.println("[WARN] 跳过非法 Excel 文件: " + filepathName + ", reason=" + e.getMessage());
        return false;
      } catch (Exception e) {
        throw new RuntimeException("解析配置表失败:" + filepathName, e);
      }
    }

    private void addTokens(Row row) {
      int emptyNum = 0;
      for (Cell cell : row) {
        if (cell.getColumnIndex() == 0) continue;
        if (cell.getCellType() == CellType.STRING) {
          tokenType.add(cell.getStringCellValue().trim());
        } else {
          emptyNum++;
          if (emptyNum > 2) {
            return;
          }
          tokenType.add(null);
          System.err.printf("表 %s, 数据类型 第 %d 列 为空, 请检查%n", configName, cell.getColumnIndex());
        }
      }
    }

    private void makeTextData(StringBuilder serverLines, FormulaEvaluator evaluator, Row row) {
      List<String> serverDataList = new ArrayList<>(128);
      for (int i = 0; i < serverHeadTitleList.size(); i++) {
        serverDataList.add(defaultTextValue(typeAt(i)));
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
          serverDataList.set(index - 1, value.replace('\r', ' ').replace('\n', ' '));
        }
      }
      appendNotNullLine(serverLines, serverDataList);
    }

    private String defaultTextValue(String type) {
      return type != null && type.equalsIgnoreCase("INT") ? "0" : " ";
    }

    private void appendNotNullLine(StringBuilder out, List<String> values) {
      boolean first = true;
      for (int i = 0; i < values.size(); i++) {
        if (serverHeadTitleList.get(i) == null) {
          continue;
        }
        if (!first) {
          out.append("\t");
        }
        out.append(values.get(i));
        first = false;
      }
      out.append("\n");
    }

    private void addDescHeadTitle(Row row) {
      for (Cell cell : row) {
        if (cell.getColumnIndex() == 0) continue;
        if (cell.getCellType() == CellType.STRING) {
          descList.add(cell.getStringCellValue().replaceAll("\n", ", "));
        } else {
          descList.add("  null");
        }
      }
    }

    private void addClientHeadTitle(Row row) {
      for (Cell cell : row) {
        if (cell.getColumnIndex() == 0) continue;
        clientHeadTitleList.add(cell.getCellType() == CellType.STRING ? cell.getStringCellValue() : null);
      }
    }

    private void addServerHeadTitle(Row row) {
      for (Cell cell : row) {
        if (cell.getColumnIndex() == 0) continue;
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
        case NUMERIC -> (int) cell.getNumericCellValue() + "";
        case BOOLEAN -> cell.getBooleanCellValue() + "";
        case FORMULA -> getFormulaValue(evaluator, cell);
        default -> null;
      };
    }

    private String getFormulaValue(FormulaEvaluator evaluator, Cell cell) {
      try {
        CellValue cellValue = evaluator.evaluate(cell);
        return switch (cellValue.getCellType()) {
          case NUMERIC -> (int) cellValue.getNumberValue() + "";
          case BOOLEAN -> cellValue.getBooleanValue() + "";
          case STRING -> cellValue.getStringValue();
          default -> null;
        };
      } catch (Exception e) {
        return null;
      }
    }

    private void writeServerLineConfig(StringBuilder text) throws IOException {
      File dir = new File(excelFileDir + File.separator + "serverConfig");
      if (!dir.exists() && !dir.mkdirs()) {
        throw new IOException("创建 serverConfig 目录失败:" + dir.getAbsolutePath());
      }
      File file = new File(dir, configName + ".txt");
      try (FileWriter writer = new FileWriter(file)) {
        writer.write("#fields");
        forEachGeneratedColumn((title, type, index) -> writer.write("\t" + title));
        writer.write("\r\n#types");
        forEachGeneratedColumn((title, type, index) -> writer.write("\t" + normalizeType(type)));
        writer.write("\r\n");
        writer.write(text.toString());
      }
    }

    private void createJavaFile() {
      createSimpleJavaFile();
      createConfigManagerJavaFile();
      createCheckerBaseJavaFile();
      createCheckerJavaFileIfAbsent();
    }

    private File configJavaDir() {
      return Path.of(excelFileDir).toAbsolutePath().resolve("../server/config/src/main/java/ly/config").toFile();
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
                fields.append("  /**").append(desc.replace("\n", " ")).append("*/\n");
              }
              fields.append("  public final ").append(javaType).append(" ").append(title).append(";\n\n");
              if (!params.isEmpty()) {
                params.append(", ");
              }
              params.append(javaType).append(" ").append(title);
              assigns.append("    this.").append(title).append(" = ").append(title).append(";\n");
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
        String idType = idStr == null ? null : boxedJavaType(tokenType.get(serverHeadTitleList.indexOf(idStr)));
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
                    + "Config> getConfigMap() {\n      return configMap;\n    }\n\n";
        String localVars = buildLocalVars();
        String parseCode = buildParseCode();
        String ctorArgs = buildCtorArgs();
        String putMap =
            idStr == null ? "" : "          newMap.put(config." + idStr + ", config);\n";
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
                + "import java.util.concurrent.atomic.AtomicBoolean;\n"
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
                + "  private static final AtomicBoolean switched = new AtomicBoolean(false);\n"
                + "  private static final "
                + simple
                + "ConfigManager instance = new "
                + simple
                + "ConfigManager();\n"
                + "  private static final "
                + simple
                + "ConfigManagerImpl instanceImplA = new "
                + simple
                + "ConfigManagerImpl();\n"
                + "  private static final "
                + simple
                + "ConfigManagerImpl instanceImplB = new "
                + simple
                + "ConfigManagerImpl();\n\n"
                + "  public static "
                + simple
                + "ConfigManagerImpl getInstance() {\n"
                + "    return switched.get() ? instanceImplA : instanceImplB;\n"
                + "  }\n\n"
                + "  private static "
                + simple
                + "ConfigManagerImpl getStandby() {\n"
                + "    return switched.get() ? instanceImplB : instanceImplA;\n"
                + "  }\n\n"
                + "  @Override\n"
                + "  public void loadConfig(Logger logger, String configDir) throws ConfigLoadException {\n"
                + "    getInstance().reload(logger, configDir);\n"
                + "  }\n\n"
                + "  @Override\n"
                + "  public void loadStandbyConfig(Logger logger, String configDir) throws ConfigLoadException {\n"
                + "    getStandby().reload(logger, configDir);\n"
                + "  }\n\n"
                + "  @Override\n"
                + "  public AbstractConfigManger switchConfig() {\n"
                + "    "
                + simple
                + "ConfigManagerImpl oldActive = getInstance();\n"
                + "    switched.set(!switched.get());\n"
                + "    return oldActive;\n"
                + "  }\n\n"
                + "  @Override\n"
                + "  public String getConfigFileName() {\n"
                + "    return getInstance().getConfigFileName();\n"
                + "  }\n\n"
                + "  public static class "
                + simple
                + "ConfigManagerImpl extends AbstractConfigManger {\n"
                + "    private List<"
                + simple
                + "Config> configList = List.of();\n"
                + defMap
                + "    // @@@@@自定义属性开始区@@@@@\n"
                + fieldStr
                + "\n    // @@@@@自定义属性结束区@@@@@\n\n"
                + "    @Override\n"
                + "    public void reload(Logger logger, String configDir) throws ConfigLoadException {\n"
                + "      String fileName = configDir + File.separator + getConfigFileName();\n"
                + "      File file = new File(fileName);\n"
                + "      if (!file.exists()) {\n"
                + "        logger.error(fileName + \" does not exist\");\n"
                + "        throw new ConfigLoadException(\"Config file does not exist :\" + fileName);\n"
                + "      }\n"
                + "      "
                + simple
                + "ConfigChecker checker = new "
                + simple
                + "ConfigChecker();\n"
                + "      checker.checkHeader(logger, configDir);\n"
                + "      List<"
                + simple
                + "Config> newList = new ArrayList<>();\n"
                + newMap
                + "      try (BufferedReader br = new BufferedReader(new FileReader(file))) {\n"
                + "        String rowText;\n"
                + "        br.readLine();\n"
                + "        br.readLine();\n"
                + "        while ((rowText = br.readLine()) != null) {\n"
                + "          if (rowText.isBlank()) { continue; }\n"
                + "          String[] arr = rowText.split(\"\\\\t\", -1);\n"
                + "          if (arr.length < "
                + generatedColumnCount()
                + ") {\n"
                + "            throw new ConfigLoadException(\"Config column size mismatch :\" + fileName + \", line=\" + rowText);\n"
                + "          }\n"
                + localVars
                + "          try {\n"
                + parseCode
                + "          } catch (Exception e) {\n"
                + "            logger.error(String.format(\"解析配置 %s 表, 字符串:%s 报错，请检查:%s\", fileName, rowText, e.getMessage()));\n"
                + "            throw new ConfigLoadException(\"Error parsing config file :\" + fileName);\n"
                + "          }\n"
                + "          "
                + simple
                + "Config config = new "
                + simple
                + "Config("
                + ctorArgs
                + ");\n"
                + "          config.afterLoad();\n"
                + "          newList.add(config);\n"
                + putMap
                + "        }\n"
                + "        checker.checkAfterParse(logger, newList);\n"
                + "        configList = List.copyOf(newList);\n"
                + publishMap
                + "        afterLoad();\n"
                + "      } catch (IOException e) {\n"
                + "        throw new ConfigLoadException(\"Config file could not be read :\" + fileName);\n"
                + "      }\n"
                + "    }\n\n"
                + "    @Override\n"
                + "    public void clear() {\n"
                + "      configList = List.of();\n"
                + clearMap
                + "      // @@@@@自定义clear方法开始区@@@@@\n"
                + clearStr
                + "\n      // @@@@@自定义clear方法结束区@@@@@\n"
                + "    }\n\n"
                + parserHelpers()
                + "    public List<"
                + simple
                + "Config> getConfigList() {\n      return configList;\n    }\n\n"
                + mapGetter
                + "    @Override\n"
                + "    public String getConfigFileName() {\n      return \""
                + configName
                + ".txt\";\n    }\n\n"
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
            columns
                .append("        new ConfigColumnMeta(")
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
              + ".txt\";\n  }\n\n"
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
                  .append(title)
                  .append(" = ")
                  .append(defaultJavaValue(type))
                  .append(";\n"));
      return sb.toString();
    }

    private String buildParseCode() {
      StringBuilder sb = new StringBuilder();
      forEachGeneratedColumn(
          (title, type, index) -> {
            String desc = index < descList.size() ? descList.get(index) : "";
            if (desc != null && !desc.isBlank()) {
              sb.append("            // 解析 ").append(desc.replace("\n", " ")).append("\n");
            }
            sb.append("            if (!arr[")
                .append(index)
                .append("].trim().isEmpty()) {\n")
                .append("              ")
                .append(title)
                .append(" = ")
                .append(parseExpression(index, type))
                .append(";\n")
                .append("            }\n\n");
          });
      return sb.toString();
    }

    private String buildCtorArgs() {
      List<String> args = new ArrayList<>();
      forEachGeneratedColumn((title, type, index) -> args.add(title));
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
        String type = typeAt(i);
        if (type == null || type.isBlank()) {
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

    private String typeAt(int index) {
      return index >= 0 && index < tokenType.size() ? tokenType.get(index) : null;
    }

    private String parserHelpers() {
      return ""
          + "    private List<Integer> parseIntList(String value) {\n"
          + "      if (value == null || value.trim().isEmpty()) { return new ArrayList<>(); }\n"
          + "      String[] parts = value.split(\",\");\n"
          + "      List<Integer> result = new ArrayList<>();\n"
          + "      for (String part : parts) {\n"
          + "        if (!part.trim().isEmpty()) { result.add(Integer.parseInt(part.trim())); }\n"
          + "      }\n"
          + "      return result;\n"
          + "    }\n\n"
          + "    private List<KV<Integer, Integer>> parseIntKVList(String value) {\n"
          + "      if (value == null || value.trim().isEmpty()) { return new ArrayList<>(); }\n"
          + "      List<KV<Integer, Integer>> result = new ArrayList<>();\n"
          + "      for (String pair : value.split(\",\")) {\n"
          + "        int idx = pair.indexOf(\":\");\n"
          + "        if (idx > 0) {\n"
          + "          result.add(new KV<>(Integer.parseInt(pair.substring(0, idx).trim()), Integer.parseInt(pair.substring(idx + 1).trim())));\n"
          + "        }\n"
          + "      }\n"
          + "      return result;\n"
          + "    }\n\n"
          + "    private List<KV<String, String>> parseStringKVList(String value) {\n"
          + "      if (value == null || value.trim().isEmpty()) { return new ArrayList<>(); }\n"
          + "      List<KV<String, String>> result = new ArrayList<>();\n"
          + "      for (String pair : value.split(\",\")) {\n"
          + "        int idx = pair.indexOf(\":\");\n"
          + "        if (idx > 0) { result.add(new KV<>(pair.substring(0, idx).trim(), pair.substring(idx + 1).trim())); }\n"
          + "      }\n"
          + "      return result;\n"
          + "    }\n\n";
    }

    private String javaType(String type) {
      if (type.equalsIgnoreCase("INT")) return "int";
      if (type.equalsIgnoreCase("LONG")) return "long";
      if (type.equalsIgnoreCase("DOUBLE")) return "double";
      if (type.equalsIgnoreCase("BOOLEAN")) return "boolean";
      if (type.equalsIgnoreCase("FLOAT")) return "float";
      if (type.equalsIgnoreCase("INT1")) return "List<Integer>";
      if (type.equalsIgnoreCase("INT2")) return "List<KV<Integer, Integer>>";
      if (type.equalsIgnoreCase("STRING2")) return "List<KV<String, String>>";
      return "String";
    }

    private String boxedJavaType(String type) {
      if (type.equalsIgnoreCase("INT")) return "Integer";
      if (type.equalsIgnoreCase("LONG")) return "Long";
      if (type.equalsIgnoreCase("DOUBLE")) return "Double";
      if (type.equalsIgnoreCase("BOOLEAN")) return "Boolean";
      if (type.equalsIgnoreCase("FLOAT")) return "Float";
      return "String";
    }

    private String defaultJavaValue(String type) {
      if (type.equalsIgnoreCase("INT")) return "0";
      if (type.equalsIgnoreCase("LONG")) return "0L";
      if (type.equalsIgnoreCase("DOUBLE")) return "0D";
      if (type.equalsIgnoreCase("BOOLEAN")) return "false";
      if (type.equalsIgnoreCase("FLOAT")) return "0F";
      return "null";
    }

    private String parseExpression(int i, String type) {
      if (type.equalsIgnoreCase("INT")) return "Integer.parseInt(arr[" + i + "].trim())";
      if (type.equalsIgnoreCase("LONG")) return "Long.parseLong(arr[" + i + "].trim())";
      if (type.equalsIgnoreCase("DOUBLE")) return "Double.parseDouble(arr[" + i + "].trim())";
      if (type.equalsIgnoreCase("BOOLEAN")) return "Boolean.parseBoolean(arr[" + i + "].trim())";
      if (type.equalsIgnoreCase("FLOAT")) return "Float.parseFloat(arr[" + i + "].trim())";
      if (type.equalsIgnoreCase("INT1")) return "parseIntList(arr[" + i + "].trim())";
      if (type.equalsIgnoreCase("INT2")) return "parseIntKVList(arr[" + i + "].trim())";
      if (type.equalsIgnoreCase("STRING2")) return "parseStringKVList(arr[" + i + "].trim())";
      return "arr[" + i + "].trim()";
    }

    private String normalizeType(String type) {
      return type == null ? "" : type.trim().toUpperCase();
    }

    private String classSimpleName() {
      return configName.substring(0, 1).toUpperCase() + configName.substring(1);
    }

    private void writeFile(File file, String source) throws IOException {
      Files.createDirectories(file.toPath().getParent());
      try (FileWriter writer = new FileWriter(file)) {
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
          Pattern.compile(Pattern.quote(begin) + "(.+?)" + Pattern.quote(end), Pattern.DOTALL);
      Matcher matcher = pattern.matcher(source == null ? "" : source);
      return matcher.find() ? matcher.group(1).trim() : "";
    }
  }

  @FunctionalInterface
  interface ColumnConsumer {
    void accept(String title, String type, int index) throws IOException;
  }

  public static void main(String[] args) {
    String dir = args != null && args.length > 0 ? args[0] : "D:\\WORK\\me\\miniServer\\excel";
    new ParserExcelConfig(dir).startParser();
  }
}
