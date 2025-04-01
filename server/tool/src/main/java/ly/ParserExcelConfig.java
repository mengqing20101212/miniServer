package ly;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.lang.model.SourceVersion;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 * Author: liuYang
 * Date: 2025/4/1
 * File: ParserExcelConfig
 */
public class ParserExcelConfig {
  final String excelFileDir;
  List<String> excelFileList = new ArrayList<String>(256);
  CountDownLatch countDownLatch;

  public ParserExcelConfig(String excelFileDir) {
    this.excelFileDir = excelFileDir;
  }

  public void startParser() {
    long startTime = System.currentTimeMillis();
    scanAllExcelFile();
    parserAllFiles();
    long endTime = System.currentTimeMillis();
    System.out.println("转表 耗时: " + (endTime - startTime) / 1000 + "s");
  }

  private void parserAllFiles() {
    if (excelFileList.isEmpty()) {
      System.out.println("未发现可以解析的配置表，请检查目录:" + excelFileDir);
      return;
    }
    for (String fileName : excelFileList) {
      Thread.ofVirtual()
          .start(
              () -> {
                try {
                  long begin = System.currentTimeMillis();
                  parserFile(fileName);

                  System.out.println(
                      "处理表 " + fileName + ", 完成 耗时:" + (System.currentTimeMillis() - begin));
                } finally {
                  countDownLatch.countDown();
                }
              });
    }
    try {
      countDownLatch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void parserFile(String fileName) {
    String configName =
        fileName.substring(fileName.lastIndexOf(File.separator) + 1, fileName.lastIndexOf("."));
    ExcelConfig excelConfig = new ExcelConfig(configName, fileName);
    excelConfig.parser();
    excelConfig.createJavaFile();
  }

  private void scanAllExcelFile() {
    excelFileList.clear();
    File dir = new File(excelFileDir);
    if (!dir.isDirectory()) {
      System.out.println("该路径不是: " + excelFileDir + " 目录, 请检查");
      return;
    }
    File[] files = dir.listFiles();
    for (File file : files) {
      if (file.isFile() && (file.getName().endsWith(".xls") || file.getName().endsWith(".xlsx"))) {
        excelFileList.add(file.getAbsolutePath());
      }
      countDownLatch = new CountDownLatch(excelFileList.size());
    }
  }

  class ExcelConfig {
    String configName;
    String filepathName;
    File writeServerFile;

    /** 服务器的表头，不存在 则为空 */
    List<String> serverHeadTitleList = new ArrayList<>(128);

    /** 服务器的表头，不存在 则为空 */
    List<String> clientHeadTitleList = new ArrayList<>(128);

    /** 字段类型 */
    List<String> tokenType = new ArrayList<>(128);

    /** 注释 */
    List<String> descList = new ArrayList<>(128);

    public ExcelConfig(String configName, String filepathName) {
      this.configName = configName;
      this.filepathName = filepathName;
    }

    public void parser() {
      try (FileInputStream fis = new FileInputStream(new File(filepathName))) {
        // 创建 XSSFWorkbook 实例（适用于 .xlsx 文件）
        Workbook workbook = null;
        if (filepathName.endsWith(".xlsx")) {
          workbook = new XSSFWorkbook(fis);
        } else if (filepathName.endsWith(".xls")) {
          workbook = new HSSFWorkbook(fis);
        }
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        Sheet sheet = workbook.getSheet("Sheet1");
        int i = 0;
        StringBuffer serverLines = new StringBuffer();
        StringBuffer clientLines = new StringBuffer();
        for (Row row : sheet) {
          // 遍历每一列
          int j = 0;
          for (Cell cell : row) {
            // 根据不同的单元格类型，获取对应的值
            String value = getCellValue(evaluator, cell);
            if (i == 0 && j != 0) {
              if (value == null) continue;
              if (serverHeadTitleList.contains(value)) {
                serverHeadTitleList.add(null);
              } else {
                addHeadTitle(value, serverHeadTitleList);
              }
            } else if (i == 1 && j != 0) {
              if (value == null) continue;
              if (clientHeadTitleList.contains(value)) {
                clientHeadTitleList.add(null);
              } else {
                addHeadTitle(value, clientHeadTitleList);
              }
            } else if (i == 2 && j != 0) {
              if (value == null) continue;
              addHeadTitle(value, tokenType);
            } else if (i == 3 && j != 0) {
              if (value == null) continue;
              addHeadTitle(value, descList);
            } else if (i > 3) {
              if (j == 0 && (value == null || !value.equals("#"))) {
                break;
              }
              if (j > 0) {
                if (j < serverHeadTitleList.size() && serverHeadTitleList.get(j - 1) != null) {
                  if (value == null) {
                    if (tokenType.get(j - 1).equalsIgnoreCase("STRING")) {
                      value = "";
                    } else if (tokenType.get(j - 1).equalsIgnoreCase("INT")) {
                      value = "0";
                    }
                  }
                  serverLines.append(value).append("\t");
                }
                if (j < clientHeadTitleList.size() && clientHeadTitleList.get(j) != null) {
                  clientLines.append(value).append("\t");
                }
              }
            }
            j++;
          }
          if (serverLines.length() > 0) {
            serverLines.deleteCharAt(serverLines.length() - 1);
            serverLines.append("\r\n");
          }
          i++;
        }
        if (serverLines.length() > 0) {
          writeServerLineConfig(serverLines);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    private void addHeadTitle(String value, List<String> serverHeadTitleList) {
      if (StringUtil.isBlank(value) || value.equals("#") || isJavaKeyValue(value)) {
        serverHeadTitleList.add(null);
      } else {
        serverHeadTitleList.add(value);
      }
    }

    private boolean isJavaKeyValue(String value) {
      if (StringUtil.isBlank(value)) {
        return false;
      }
      return SourceVersion.isKeyword(value);
    }

    private static String getCellValue(FormulaEvaluator evaluator, Cell cell) {
      String value = "";
      switch (cell.getCellType()) {
        case STRING:
          value = cell.getStringCellValue();
          break;
        case NUMERIC:
          value = (int) cell.getNumericCellValue() + "";
          break;
        case BOOLEAN:
          value = cell.getBooleanCellValue() + "";
          break;
        case FORMULA:
          value = getFormaulaValue(evaluator, cell);
          break;
        default:
          return null;
      }
      return value;
    }

    private static String getFormaulaValue(FormulaEvaluator evaluator, Cell cell) {
      String value;
      try {
        CellValue cellValue = evaluator.evaluate(cell);
        if (cellValue.getCellType() == CellType.NUMERIC) {
          value = (int) cellValue.getNumberValue() + "";
        } else if (cellValue.getCellType() == CellType.BOOLEAN) {
          value = cellValue.getBooleanValue() + "";
        } else {
          value = cellValue.getStringValue();
        }
      } catch (Exception e) {
        return null;
      }
      return value;
    }

    private void writeServerLineConfig(StringBuffer text) {
      String writeFileName =
          excelFileDir + File.separator + "serverConfig" + File.separator + configName + ".txt";
      boolean createFile = false;
      if (writeServerFile == null) {
        writeServerFile = new File(writeFileName);
        if (writeServerFile.exists()) {
          writeServerFile.delete();
        }
        try {
          createFile = writeServerFile.createNewFile();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      try {
        FileWriter fileWriter = new FileWriter(writeServerFile);
        if (createFile) {
          String titleStr = "";
          for (int i = 0; i < serverHeadTitleList.size(); i++) {
            String title = serverHeadTitleList.get(i);
            if (title != null) {
              titleStr += title + "\t";
            }
          }
          fileWriter.write(titleStr + "\r\n");
        }
        fileWriter.append(text.toString());
        fileWriter.flush();
        fileWriter.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    private void createJavaFile() {
      createSimlpeJavaFile();
      createConfigManagerJavaFile();
    }

    private void createConfigManagerJavaFile() {
      String dstDir =
          Path.of(excelFileDir)
              .toAbsolutePath()
              .resolve("../server/config/src/main/java/ly/config")
              .toString();
      String dstJavaFileName =
          configName.substring(0, 1).toUpperCase()
              + configName.substring(1)
              + "ConfigManager"
              + ".java";
      String dstJavaFileSimpleName = dstJavaFileName.substring(0, dstJavaFileName.length() - 18);
      String dstFileName = dstDir + File.separator + dstJavaFileName;
      String filedStr = ""; // 自定义属性区
      String methodStr = ""; // 自定义方法区
      String clearStr = ""; // clear  自定义代码区
      File dstFile = new File(dstFileName);
      try {
        if (dstFile.exists()) {
          String score = readFile(dstFile);
          if (score != null) {
            filedStr = extractFieldStr(score);
            methodStr = extractMethodStr(score);
            clearStr = extractClearStr(score);
          }
        } else {
          dstFile.createNewFile();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      String score =
          createConfigManagerJavaFile(dstJavaFileSimpleName, filedStr, methodStr, clearStr);
      FileWriter fileWriter = null;
      try {
        fileWriter = new FileWriter(dstFile);
        fileWriter.write(formatJavaCode(score));
        fileWriter.flush();
        fileWriter.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    private void createSimlpeJavaFile() {
      String dstDir =
          Path.of(excelFileDir)
              .toAbsolutePath()
              .resolve("../server/config/src/main/java/ly/config")
              .toString();
      String dstJavaFileName =
          configName.substring(0, 1).toUpperCase() + configName.substring(1) + "Config" + ".java";
      String dstFileName = dstDir + File.separator + dstJavaFileName;
      String filedStr = ""; // 自定义属性区
      String methodStr = ""; // 自定义方法区
      File dstFile = new File(dstFileName);
      try {
        if (dstFile.exists()) {
          String score = readFile(dstFile);
          if (score != null) {
            filedStr = extractFieldStr(score);
            methodStr = extractMethodStr(score);
          }
        } else {
          dstFile.createNewFile();
        }
        StringBuffer sb =
            new StringBuffer(
                "package ly.config;\n"
                    + "\n"
                    + "/***\n"
                    + " * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@\n"
                    + " */\n"
                    + "public class ");
        sb.append(dstJavaFileName.substring(0, dstJavaFileName.length() - 5)).append(" { \n");
        for (int i = 0; i < serverHeadTitleList.size(); i++) {
          String title = serverHeadTitleList.get(i);
          if (title == null) {
            continue;
          }
          String type = tokenType.get(i);
          if (type == null) {
            continue;
          }
          String descStr = "";
          if (!descList.isEmpty() && i < descList.size()) {
            try {
              descStr = descList.get(i);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
          if (type.equalsIgnoreCase("STRING")) {
            if (!StringUtil.isBlank(descStr)) {
              sb.append("  /**").append(descStr).append("*/ \n");
            }
            sb.append("   public String " + title + ";\n\n");
          } else if (type.equalsIgnoreCase("INT")) {
            if (!StringUtil.isBlank(descStr)) {
              sb.append("  /**").append(descStr).append("*/ \n");
            }
            sb.append("   public int " + title + ";\n\n");
          } else if (type.equalsIgnoreCase("double")) {
            if (!StringUtil.isBlank(descStr)) {
              sb.append("  /**").append(descStr).append("*/ \n");
            }
            sb.append("   public double " + title + ";\n\n");
          } else if (type.equalsIgnoreCase("float")) {
            if (!StringUtil.isBlank(descStr)) {
              sb.append("  /**").append(descStr).append("*/ \n");
            }
            sb.append("   public float " + title + ";\n\n");
          } else {
            if (!StringUtil.isBlank(descStr)) {
              sb.append("  /**").append(descStr).append("*/ \n");
            }
            sb.append("   public String " + title + ";\n\n");
          }
        }

        sb.append("// @@@@@自定义属性开始区@@@@@ \n")
            .append(filedStr)
            .append("\n // @@@@@自定义属性结束区@@@@@ \n\n");

        if (StringUtil.isBlank(methodStr)) {
          methodStr = "public void afterLoad() {}\n\n";
        }
        sb.append("// @@@@@自定义方法开始区@@@@@ \n")
            .append(methodStr)
            .append("\n // @@@@@自定义方法结束区@@@@@ \n\n");

        sb.append(" }\n");
        FileWriter fileWriter = new FileWriter(dstFile);
        fileWriter.write(formatJavaCode(sb.toString()));
        fileWriter.flush();
        fileWriter.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    private String readFile(File file) {
      try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
          content.append(line).append("\n"); // 每一行内容追加到 content
        }
        return content.toString();
      } catch (IOException e) {
        e.printStackTrace();
      }
      return null;
    }

    // 提取自定义属性区的内容
    private String extractFieldStr(String source) {
      StringBuilder fieldStr = new StringBuilder();
      Pattern pattern =
          Pattern.compile("// @@@@@自定义属性开始区@@@@@(.+?)// @@@@@自定义属性结束区@@@@@", Pattern.DOTALL);
      Matcher matcher = pattern.matcher(source);
      if (matcher.find()) {
        fieldStr.append(matcher.group(1).trim());
      }
      return fieldStr.toString();
    }

    // 提取自定义方法区的内容
    private String extractMethodStr(String source) {
      StringBuilder methodStr = new StringBuilder();
      Pattern pattern =
          Pattern.compile("// @@@@@自定义方法开始区@@@@@(.+?)// @@@@@自定义方法结束区@@@@@", Pattern.DOTALL);
      Matcher matcher = pattern.matcher(source);
      if (matcher.find()) {
        methodStr.append(matcher.group(1).trim());
      }
      return methodStr.toString();
    }

    String formatJavaCode(String source) {
      return source;
    }

    String createConfigManagerJavaFile(
        String dstJavaFileSimpleName, String filedStr, String methodStr, String clearStr) {
      if (StringUtil.isBlank(methodStr)) {
        methodStr = "    @Override\n" + "    protected void afterLoad() {\n" + "\n" + "    }";
      }
      StringBuilder parserStr = new StringBuilder();
      String defMapStr = "";
      String defMapMethodStr = "";
      String putMapStr = "";
      String mapClearStr = "";
      String idStr = "";
      if (serverHeadTitleList.contains("id")
          || serverHeadTitleList.contains("Id")
          || serverHeadTitleList.contains("ID")) {
        if (serverHeadTitleList.contains("id")) {
          idStr = "id";
        }
        if (serverHeadTitleList.contains("Id")) {
          idStr = "Id";
        }
        if (serverHeadTitleList.contains("ID")) {
          idStr = "ID";
        }
        int idIndex = serverHeadTitleList.indexOf(idStr);
        String idType = tokenType.get(idIndex);
        String IdKeyType = "Integer";
        if (idType.equalsIgnoreCase("String")) {
          IdKeyType = "String";
        }
        defMapStr =
            "    Map<"
                + IdKeyType
                + ", "
                + dstJavaFileSimpleName
                + "Config> configMap = new HashMap<"
                + IdKeyType
                + ", "
                + dstJavaFileSimpleName
                + "Config>();\n\n";

        defMapMethodStr =
            "    public Map<"
                + IdKeyType
                + ", "
                + dstJavaFileSimpleName
                + "Config> getConfigMap() {\n"
                + "      return configMap;\n"
                + "    }";

        putMapStr = "          configMap.put(config." + idStr + ", config);\n";
        mapClearStr = "      configMap.clear();\n";
      }

      for (int i = 0; i < serverHeadTitleList.size(); i++) {
        String title = serverHeadTitleList.get(i);
        String type = tokenType.get(i);
        String descStr = "";
        if (title == null) {
          continue;
        }
        if (type == null || StringUtil.isBlank(type)) {
          continue;
        }
        if (i < descList.size()) {
          descStr = descList.get(i);
        }
        if (StringUtil.isNotBlank(descStr)) {
          parserStr
              .append("            ")
              .append("//解析 ")
              .append(descStr.replaceAll("\n", " "))
              .append("\n");
        }
        parserStr
            .append("            ")
            .append("config.")
            .append(title)
            .append(" = ")
            .append(makeStr(i, type))
            .append(";\n\n");
      }

      String template =
          "package ly.config;\n"
              + "\n"
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
              + "import org.apache.logging.log4j.core.Logger;\n"
              + "\n"
              + "/*\n"
              + " * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@\n"
              + " * File: {javaFileSimpleName}ConfigManager\n"
              + " */\n"
              + "public class {javaFileSimpleName}ConfigManager implements InterfaceConfigManagerProxy {\n"
              + "  AtomicBoolean switched = new AtomicBoolean(false);\n"
              + "  private static final {javaFileSimpleName}ConfigManager instance = new {javaFileSimpleName}ConfigManager();\n"
              + "  private static final {javaFileSimpleName}ConfigManagerImpl instanceImplA =\n"
              + "      new {javaFileSimpleName}ConfigManagerImpl();\n"
              + "  private static final {javaFileSimpleName}ConfigManagerImpl instanceImplB =\n"
              + "      new {javaFileSimpleName}ConfigManagerImpl();\n"
              + "\n"
              + "  public boolean isSwitched() {\n"
              + "    return switched.getAndSet(!switched.get());\n"
              + "  }\n"
              + "\n"
              + "  public static {javaFileSimpleName}ConfigManagerImpl getInstance() {\n"
              + "    if (instance.isSwitched()) {\n"
              + "      return instanceImplA;\n"
              + "    } else {\n"
              + "      return instanceImplB;\n"
              + "    }\n"
              + "  }\n"
              + "\n"
              + "  @Override\n"
              + "  public void loadConfig(Logger logger, String configDir) throws ConfigLoadException {\n"
              + "    getInstance().reload(logger, configDir);\n"
              + "  }\n"
              + "\n"
              + "  public static class {javaFileSimpleName}ConfigManagerImpl extends AbstractConfigManger {\n"
              + "\n"
              + "    List<{javaFileSimpleName}Config> configList = new ArrayList<{javaFileSimpleName}Config>();\n"
              + defMapStr
              + "\n"
              + "    // @@@@@自定义属性开始区@@@@@\n"
              + filedStr
              + "\n"
              + "    // @@@@@自定义属性结束区@@@@@\n"
              + "\n"
              + "    @Override\n"
              + "    protected void reload(Logger logger, String configDir) throws ConfigLoadException {\n"
              + "      String fileName = configDir + File.separator + getConfigFileName();\n"
              + "      File file = new File(fileName);\n"
              + "      clear();\n"
              + "      if (!file.exists()) {\n"
              + "        logger.error(fileName + \" does not exist\");\n"
              + "        throw new ConfigLoadException(\"Config file does not exist :\" + fileName);\n"
              + "      }\n"
              + "      try (BufferedReader br = new BufferedReader(new FileReader(file))) {\n"
              + "        String line;\n"
              + "        br.readLine(); //先读取一行表头 \n"
              + "        while ((line = br.readLine()) != null) { // 按行读取\n"
              + "          String[] arr = line.split(\"\\t\");\n"
              + "          {javaFileSimpleName}Config config = new {javaFileSimpleName}Config();\n"
              + "          try {\n"
              + parserStr.toString()
              + "\n"
              + "          } catch (Exception e) {\n"
              + "            logger.error(\n"
              + "                String.format(\"解析配置 %s 表, 字符串:%s 报错，请检查:%s\", fileName, line, e.getMessage()));\n"
              + "            e.printStackTrace();\n"
              + "            throw new ConfigLoadException(\"Error parsing config file :\" + fileName);\n"
              + "          }\n"
              + "          config.afterLoad();\n"
              + "          configList.add(config);\n"
              + putMapStr
              + "        }\n"
              + "        afterLoad();\n"
              + "      } catch (IOException e) {\n"
              + "        e.printStackTrace();\n"
              + "        throw new ConfigLoadException(\"Config file could not be read :\" + fileName);\n"
              + "      }\n"
              + "    }\n"
              + "\n"
              + "    @Override\n"
              + "    protected void clear() {\n"
              + "\n"
              + "      configList.clear();\n"
              + mapClearStr
              + "\n"
              + "      // @@@@@自定义clear方法开始区@@@@@\n"
              + clearStr
              + "\n\n"
              + "      // @@@@@自定义clear方法结束区@@@@@\n"
              + "    }\n"
              + "\n"
              + "    public List<{javaFileSimpleName}Config> getConfigList() {\n"
              + "      return configList;\n"
              + "    }\n"
              + "\n"
              + defMapMethodStr
              + "\n"
              + "    @Override\n"
              + "    public String getConfigFileName() {\n"
              + "      return \"{javaFileName}.txt\";\n"
              + "    }\n"
              + "\n"
              + "    // @@@@@自定义方法开始区@@@@@\n"
              + methodStr
              + "\n\n"
              + "    // @@@@@自定义方法结束区@@@@@\n"
              + "  }\n"
              + "}\n";
      return template
          .replaceAll("\\{javaFileSimpleName}", dstJavaFileSimpleName)
          .replaceAll("\\{javaFileName}", configName);
    }

    private String makeStr(int i, String type) {
      if (type.equalsIgnoreCase("STRING")) {
        return "arr[" + i + "]";
      } else if (type.equalsIgnoreCase("INT")) {
        return " Integer.parseInt(arr[" + i + "])";
      } else if (type.equalsIgnoreCase("LONG")) {
        return " Long.parseLong(arr[" + i + "])";
      } else if (type.equalsIgnoreCase("DOUBLE")) {
        return " Double.parseDouble(arr[" + i + "])";
      } else if (type.equalsIgnoreCase("BOOLEAN")) {
        return " Boolean.parseBoolean(arr[" + i + "])";
      } else if (type.equalsIgnoreCase("FLOAT")) {
        return " Float.parseFloat(arr[" + i + "])";
      } else if (type.equalsIgnoreCase("LIST<INT>")) return "arr[" + i + "]";
      else {
        return null;
      }
    }
  }

  private String extractClearStr(String source) {
    StringBuilder methodStr = new StringBuilder();
    Pattern pattern =
        Pattern.compile(
            "// @@@@@自定义clear方法开始区@@@@@(.+?)// @@@@@自定义clear方法结束区@@@@@", Pattern.DOTALL);
    Matcher matcher = pattern.matcher(source);
    if (matcher.find()) {
      methodStr.append(matcher.group(1).trim());
    }
    return methodStr.toString();
  }

  public static void main(String[] args) {
    var parser = new ParserExcelConfig("D:\\WORK\\me\\miniServer\\excel");
    parser.startParser();
  }
}
