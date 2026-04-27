package ly;

import static ly.ParserExcelConfig.ExcelConfig.formatJavaCode;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.util.StringUtil;

/**
 * 数据库实体生成工具，读取表结构并生成 Entry 与 Helper 访问代码。
 */
public class ParserDbEntry {
  String jdbcUrl = "jdbc:mysql://118.25.76.117:3306/pick_money";
  String username = "root";
  String password = "Ly@2026Root!8899";
  String targetDir = "./core/src/main/java/ly/db/entry";
  String tableNamePrefix = ""; // 表名前缀过滤，空=不限制
  String targetModule = "";
  String baseDir = "";

  public ParserDbEntry() {
    this.baseDir = resolveBaseDir();
  }

  public ParserDbEntry(String targetModule) {
    this();
    this.targetModule = targetModule;
    if ("core".equalsIgnoreCase(targetModule)) {
      this.targetDir = "./core/src/main/java/ly/db/entry";
    } else if ("GMServer".equalsIgnoreCase(targetModule)) {
      this.targetDir = "./GMServer/src/main/java/ly/db/entry";
      this.tableNamePrefix = "gm_";
    }
  }

  private static String resolveBaseDir() {
    // 根据 user.dir 自动定位 server 目录
    String userDir = System.getProperty("user.dir").replace("\\", "/");
    // 如果在 tool 目录下运行，往上一级到 server 目录
    if (userDir.endsWith("/tool") || userDir.endsWith("/tool/target")) {
      return userDir.substring(0, userDir.lastIndexOf("/tool"));
    }
    // 如果在 server 目录下运行（pom.xml 所在目录），直接返回
    if (userDir.endsWith("/server")) {
      return userDir;
    }
    // 尝试从代码位置推断：tool jar 的路径中包含 server/tool/target
    try {
      var cs = ParserDbEntry.class.getProtectionDomain().getCodeSource();
      if (cs != null) {
        String jarPath = java.net.URLDecoder.decode(cs.getLocation().getPath(), "UTF-8");
        if (jarPath.contains("/server/tool/")) {
          return jarPath.substring(0, jarPath.indexOf("/server/tool/")) + "/server";
        }
      }
    } catch (Exception e) {
      // fallback to user.dir
    }
    return userDir;
  }

  private String absPath(String relativePath) {
    // 拼接 baseDir + relativePath，然后规范化
    java.io.File f = new java.io.File(baseDir, relativePath);
    try {
      return f.getCanonicalPath();
    } catch (Exception e) {
      return f.getAbsolutePath();
    }
  }
  HikariDataSource dataSource;
  List<TableInfo> tables = new ArrayList<TableInfo>();

  class FiledInfo {
    String name;
    Object type;
    Object javaType;
    String desc;
    boolean autoIncrement;

    @Override
    public String toString() {
      return "FiledInfo{"
          + "name='"
          + name
          + '\''
          + ", type="
          + type
          + ", javaType="
          + javaType
          + ", desc='"
          + desc
          + '\''
          + ", autoIncrement="
          + autoIncrement
          + '}';
    }
  }

  class TableInfo {
    String javaName;
    String tableName;
    FiledInfo key;

    /** 所有的字段 */
    List<FiledInfo> fields = new ArrayList<>();

    /** key 索引 ，value 索引关联的字段 */
    Map<String, List<FiledInfo>> indexFiledMap = new HashMap<>();

    public void addField(FiledInfo fieldInfo) {
      for (FiledInfo field : fields) {
        if (field.name.equals(fieldInfo.name)) {
          return;
        }
      }
      fields.add(fieldInfo);
    }
  }

  public void parser() {
    connectDb();
  }

  private void executeSchemaSql() {
    if (!"GMServer".equalsIgnoreCase(this.targetModule)) return;
    String absTargetDir = absPath(this.targetDir);
    String schemaPath = absTargetDir.substring(0, absTargetDir.indexOf("/src/main/java"))
        + "/src/main/resources/schema.sql";
    System.out.println("Executing schema.sql: " + schemaPath);
    try {
      java.nio.file.Path path = java.nio.file.Paths.get(schemaPath);
      String sql = java.nio.file.Files.readString(path);
      // Split by semicolons and execute each statement
      String[] statements = sql.split(";");
      try (var conn = dataSource.getConnection(); var stmt = conn.createStatement()) {
        for (String s : statements) {
          String trimmed = s.trim();
          if (!trimmed.isEmpty()) {
            try {
              stmt.execute(trimmed);
            } catch (Exception e) {
              System.out.println("  SQL warn: " + e.getMessage());
            }
          }
        }
      }
      System.out.println("schema.sql executed successfully");
    } catch (Exception e) {
      System.out.println("schema.sql execution skipped: " + e.getMessage());
    }
  }

  /** 补齐 MySQL 连接必需参数（同步 core 模块 MysqlConnector 的做法） */
  private static String normalizeJdbcUrl(String jdbcUrl) {
    if (jdbcUrl == null || jdbcUrl.isBlank() || !jdbcUrl.startsWith("jdbc:mysql://")) return jdbcUrl;
    String requiredParams = "useSSL=false&allowPublicKeyRetrieval=true&connectTimeout=8000&socketTimeout=8000";
    String result = jdbcUrl;
    for (String param : requiredParams.split("&")) {
      if (!result.contains(param.split("=")[0])) {
        result += (result.contains("?") ? "&" : "?") + param;
      }
    }
    return result;
  }

  private void connectDb() {
    // 创建 Hikari 配置对象
    HikariConfig config = new HikariConfig();
    String effectiveJdbcUrl = normalizeJdbcUrl(jdbcUrl);
    config.setJdbcUrl(effectiveJdbcUrl); // 数据库 URL
    config.setUsername(username); // 数据库用户名
    config.setPassword(password); // 数据库密码
    config.setDriverClassName("com.mysql.cj.jdbc.Driver"); // MySQL 驱动
    config.setMaximumPoolSize(3);
    config.setMinimumIdle(1);
    config.setConnectionTimeout(5000);
    config.setMaxLifetime(60000);
    String curDbName = effectiveJdbcUrl.substring(effectiveJdbcUrl.lastIndexOf('/') + 1);
    // 去掉 URL 参数部分得到纯数据库名
    if (curDbName.contains("?")) curDbName = curDbName.substring(0, curDbName.indexOf("?"));
    dataSource = new HikariDataSource(config);

    // 执行建表 SQL（如果模块有对应 schema.sql）
    executeSchemaSql();

    // 获取数据库表元数据
    fetchDatabaseSchema(curDbName);
    // 生成 entry
    createDbEntryJava();
    // 生成 entryHelper
    createDbEntryHelperJava();
  }

  public static String toCamelCase(String snake) {
    if (snake == null || snake.isEmpty()) return snake;

    StringBuilder result = new StringBuilder();
    String[] parts = snake.split("_");
    for (String part : parts) {
      if (!part.isEmpty()) {
        result.append(part.substring(0, 1).toUpperCase());
        if (part.length() > 1) {
          result.append(part.substring(1).toLowerCase());
        }
      }
    }
    return result.toString();
  }

  private void createDbEntryHelperJava() {
    tables.forEach(
        table -> {
          String targetFileName = absPath(targetDir) + File.separator + table.javaName + "EntryHelper.java";
          File file = new File(targetFileName);
          String extractStr = "";
          String key = toCamelCase(table.key.name);

          if (file.exists()) {
            String score = ParserExcelConfig.ExcelConfig.readFile(file);
            extractStr = ParserExcelConfig.ExcelConfig.extractMethodStr(score);
            file.delete();
          }

          StringBuffer sb =
              new StringBuffer(
                  "package ly.db.entry;\n"
                      + "\n"
                      + "import java.util.ArrayList;\n"
                      + "import java.util.List;\n"
                      + "import ly.db.MysqlService;\n"
                      + "\n"
                      + "/*\n"
                      + " * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@\n"
                      + " */\n"
                      + "public class {javaName}EntryHelper {\n"
                      + "  public static {javaName}Entry get{javaName}EntryBy"
                      + key
                      + "("
                      + table.key.javaType
                      + " id) {\n"
                      + "    return MysqlService.getInstance()\n"
                      + "        .selectOnce({javaName}Entry.class, new String[] {\"id\"}, id);\n"
                      + "  }\n"
                      + "\n"
                      + "  public static boolean save({javaName}Entry {javaName}Entry) {\n"
                      + "    return MysqlService.getInstance().save({javaName}Entry);\n"
                      + "  }\n"
                      + "\n"
                      + "  public static boolean update({javaName}Entry {javaName}Entry, String... fileds) {\n"
                      + "    return MysqlService.getInstance().update({javaName}Entry, fileds);\n"
                      + "  }\n"
                      + "\n"
                      + "  public static boolean delete({javaName}Entry {javaName}Entry) {\n"
                      + "    return MysqlService.getInstance().delete({javaName}Entry);\n"
                      + "  }\n"
                      + "\n"
                      + "  public static void asyncSave({javaName}Entry {javaName}Entry) {\n"
                      + "    MysqlService.getInstance().addSaveEntry({javaName}Entry);\n"
                      + "  }\n"
                      + "\n"
                      + "  public static void asyncUpdate({javaName}Entry {javaName}Entry, String... fileds) {\n"
                      + "    MysqlService.getInstance().addUpdateEntry({javaName}Entry, fileds);\n"
                      + "  }\n"
                      + "\n"
                      + "  public static List<{javaName}Entry> select(String[] fields, Object... params) {\n"
                      + "\n"
                      + "    if (fields != null && params != null && fields.length == params.length) {\n"
                      + "      return MysqlService.getInstance().selectAll({javaName}Entry.class, fields, params);\n"
                      + "    }\n"
                      + "    return new ArrayList<>();\n"
                      + "  }\n"
                      + "\n"
                      + "  // @@@@@自定义方法开始区@@@@@\n"
                      + "\n"
                      + extractStr
                      + "  // @@@@@自定义方法结束区@@@@@\n"
                      + "}\n");

          FileWriter fileWriter = null;
          try {
            String str = sb.toString().replaceAll("\\{javaName}", table.javaName);
            fileWriter = new FileWriter(targetFileName);
            fileWriter.write(formatJavaCode(str));
            fileWriter.flush();
            fileWriter.close();
            System.out.println("生成文件:" + targetFileName);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
  }

  private void createDbEntryJava() {
    tables.forEach(
        table -> {
          String targetFileName = absPath(targetDir) + File.separator + table.javaName + "Entry.java";
          File file = new File(targetFileName);
          String methodStr = "";
          String filedStr = "";
          String toStringStr = "";
          String dirtyFieldInit = "";
          String extractStr = "";
          if (file.exists()) {
            String score = ParserExcelConfig.ExcelConfig.readFile(file);
            extractStr = ParserExcelConfig.ExcelConfig.extractMethodStr(score);
            file.delete();
          }
          for (int fieldIndex = 0; fieldIndex < table.fields.size(); fieldIndex++) {
            FiledInfo field = table.fields.get(fieldIndex);
            if (StringUtil.isNotBlank(field.desc)) {
              filedStr += "\n\n  /**" + field.desc + "*/";
            }
            if (field == table.key) {
              filedStr += "\n  @DbMeta.DbMasterKey(name=\"" + field.name + "\"";
              if (field.autoIncrement) {
                filedStr += ", autoIncrement=true";
              }
              filedStr += ")";
            }
            filedStr += "\n  @DbMeta.DbField(name=\"" + field.name + "\")";
            filedStr += "\n  private " + field.javaType + " " + field.name + ";";
            dirtyFieldInit += "\n        \"" + field.name + "\",";

            String filedName = toCamelCase(field.name);
            methodStr +=
                " public void set"
                    + filedName
                    + "("
                    + field.javaType
                    + " "
                    + filedName
                    + ") {\n"
                    + "    this."
                    + field.name
                    + " = "
                    + filedName
                    + ";\n"
                    + "    autoAddCurVersion();\n"
                    + "    markFieldDirty("
                    + fieldIndex
                    + ");\n"
                    + "  }\n";
            methodStr +=
                "  public "
                    + field.javaType
                    + " get"
                    + filedName
                    + "() {\n"
                    + "    return "
                    + field.name
                    + ";\n"
                    + "  }\n";

            toStringStr += String.format("+\n        \", %s=\"+%s", field.name, field.name);
          }
          StringBuffer sb =
              new StringBuffer(
                  "package ly.db.entry;\n"
                      + "\n"
                      + "import ly.db.AbstractEntry;\n"
                      + "import ly.db.DbMeta;\n"
                      + "\n"
                      + "/*\n"
                      + " * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@\n"
                      + " */\n"
                      + "@DbMeta.DbTable(name = \""
                      + table.tableName
                      + "\")\n"
                      + "public class {javaName}Entry extends AbstractEntry {\n"
                      + "  private static final String[] DIRTY_FIELDS = {"
                      + (dirtyFieldInit.isEmpty()
                          ? "};\n"
                          : dirtyFieldInit + "\n  };\n")
                      + filedStr
                      + "\n"
                      + "  public {javaName}Entry() {\n"
                      + "    initDirtyState(DIRTY_FIELDS.length);\n"
                      + "  }\n"
                      + "\n"
                      + "  @Override\n"
                      + "  protected String[] allDirtyFieldNames() {\n"
                      + "    return DIRTY_FIELDS;\n"
                      + "  }\n"
                      + "\n"
                      + "  public void save() {\n"
                      + "    {javaName}EntryHelper.save(this);\n"
                      + "  }\n"
                      + "\n"
                      + "  public void update() {\n"
                      + "    {javaName}EntryHelper.update(this);\n"
                      + "  }\n"
                      + "\n"
                      + "  public void delete() {\n"
                      + "    {javaName}EntryHelper.delete(this);\n"
                      + "  }\n"
                      + "\n"
                      + "  public void asyncSave() {\n"
                      + "    {javaName}EntryHelper.asyncSave(this);\n"
                      + "  }\n"
                      + "\n"
                      + "  public void asyncUpdate() {\n"
                      + "    {javaName}EntryHelper.asyncUpdate(this);\n"
                      + "  }\n"
                      + "\n"
                      + methodStr
                      + "\n  // @@@@@自定义方法开始区@@@@@\n"
                      + extractStr
                      + "\n"
                      + "  // @@@@@自定义方法结束区@@@@@\n"
                      + "\n"
                      + "  @Override\n"
                      + "  public String toString() {\n"
                      + "    return \"{javaName}Entry{\"\n"
                      + toStringStr
                      + "\n        + '}';\n"
                      + "  }\n"
                      + "}\n");

          FileWriter fileWriter = null;
          try {
            String str = sb.toString().replaceAll("\\{javaName}", table.javaName);
            fileWriter = new FileWriter(targetFileName);
            fileWriter.write(formatJavaCode(str));
            fileWriter.flush();
            fileWriter.close();
            System.out.println("生成文件:" + targetFileName);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
  }

  public void fetchDatabaseSchema(String databaseName) {
    try (Connection connection = dataSource.getConnection()) {
      DatabaseMetaData metaData = connection.getMetaData();

      // 确保连接的是目标数据库
      if (!databaseName.equals(connection.getCatalog())) {
        System.out.println("当前连接数据库与目标数据库不匹配: " + connection.getCatalog());
        return;
      }

      System.out.println("=== 数据库: " + databaseName + " ===");

      // 获取所有表
      ResultSet tables = metaData.getTables(databaseName, null, "%", new String[] {"TABLE"});
      while (tables.next()) {
        String tableName = tables.getString("TABLE_NAME");
        // 如果有前缀过滤，跳过不匹配的表
        if (!tableNamePrefix.isEmpty() && !tableName.startsWith(tableNamePrefix)) {
          continue;
        }
        System.out.println("\n表: " + tableName);
        TableInfo tableInfo = new TableInfo();
        tableInfo.javaName = getTableName(tableName);
        tableInfo.tableName = tableName;
        // 获取字段信息
        fetchColumns(metaData, databaseName, tableName, tableInfo);

        // 获取主键信息
        fetchPrimaryKeys(metaData, databaseName, tableName, tableInfo);

        // 获取索引信息
        fetchIndexes(metaData, databaseName, tableName, tableInfo);
        this.tables.add(tableInfo);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // 获取字段信息
  private void fetchColumns(
      DatabaseMetaData metaData, String databaseName, String tableName, TableInfo tableInfo)
      throws SQLException {
    ResultSet columns = metaData.getColumns(databaseName, null, tableName, "%");
    while (columns.next()) {
      String columnName = columns.getString("COLUMN_NAME");
      String columnType = columns.getString("TYPE_NAME");
      int sqlType = columns.getInt("DATA_TYPE"); // java.sql.Types
      int columnSize = columns.getInt("COLUMN_SIZE");
      String isAutoIncrement = columns.getString("IS_AUTOINCREMENT");
      String typeName = columns.getString("TYPE_NAME");

      FiledInfo fieldInfo = new FiledInfo();
      fieldInfo.name = columnName;
      fieldInfo.type = columnType;
      fieldInfo.javaType = mapSqlTypeToJavaType(sqlType, typeName, columnSize);
      fieldInfo.desc = columns.getString("REMARKS");
      fieldInfo.autoIncrement = isAutoIncrement != null && isAutoIncrement.equals("YES");
      tableInfo.addField(fieldInfo);
      System.out.println(
          "  字段: "
              + columnName
              + " | 类型: "
              + columnType
              + "("
              + columnSize
              + ") | 自增: "
              + isAutoIncrement);
    }
  }

  private Object mapSqlTypeToJavaType(int sqlType, String typeName, int columnSize) {
    String normalizedTypeName = typeName == null ? "" : typeName.toUpperCase();
    boolean unsigned = normalizedTypeName.contains("UNSIGNED");
    if (normalizedTypeName.contains("YEAR")) {
      return "Integer";
    }
    switch (sqlType) {
      case Types.BOOLEAN:
        return "Boolean";
      case Types.BIT:
        return columnSize <= 1 ? "Boolean" : "byte[]";
      case Types.TINYINT:
        if (normalizedTypeName.contains("BOOL")) {
          return "Boolean";
        }
        return unsigned ? "Short" : "Byte";
      case Types.SMALLINT:
        return unsigned ? "Integer" : "Short";
      case Types.INTEGER:
        if (normalizedTypeName.contains("MEDIUMINT")) {
          return "Integer";
        }
        return unsigned ? "Long" : "Integer";
      case Types.BIGINT:
        return unsigned ? BigInteger.class.getName() : "Long";
      case Types.REAL:
      case Types.FLOAT:
        return "Float";
      case Types.DOUBLE:
        return "Double";
      case Types.DECIMAL:
      case Types.NUMERIC:
        return "java.math.BigDecimal";
      case Types.VARCHAR:
      case Types.CHAR:
      case Types.LONGVARCHAR:
      case Types.NVARCHAR:
      case Types.NCHAR:
      case Types.LONGNVARCHAR:
      case Types.CLOB:
      case Types.NCLOB:
      case Types.SQLXML:
        return "String";
      case Types.DATE:
        return "java.time.LocalDate";
      case Types.TIME:
      case Types.TIME_WITH_TIMEZONE:
        return "java.time.LocalTime";
      case Types.TIMESTAMP:
      case Types.TIMESTAMP_WITH_TIMEZONE:
        return "java.time.LocalDateTime";
      case Types.BLOB:
      case Types.BINARY:
      case Types.VARBINARY:
      case Types.LONGVARBINARY:
        return "byte[]";
      case Types.NULL:
        return "Object";
      default:
        if (normalizedTypeName.contains("JSON")
            || normalizedTypeName.contains("ENUM")
            || normalizedTypeName.contains("SET")
            || normalizedTypeName.contains("TEXT")) {
          return "String";
        }
        return "Object";
    }
  }

  // 获取主键信息
  private void fetchPrimaryKeys(
      DatabaseMetaData metaData, String databaseName, String tableName, TableInfo tableInfo)
      throws SQLException {
    ResultSet primaryKeys = metaData.getPrimaryKeys(databaseName, null, tableName);
    System.out.print("  主键: ");
    List<String> keys = new ArrayList<>();
    while (primaryKeys.next()) {
      keys.add(primaryKeys.getString("COLUMN_NAME"));
    }
    keys.forEach(
        key -> {
          tableInfo.fields.forEach(
              fieldInfo -> {
                if (fieldInfo.name.equals(key)) {
                  tableInfo.key = fieldInfo;
                  return;
                }
              });
        });
    System.out.println(keys.isEmpty() ? "无" : String.join(", ", keys));
  }

  // 获取索引信息及索引包含的字段
  private void fetchIndexes(
      DatabaseMetaData metaData, String databaseName, String tableName, TableInfo tableInfo)
      throws SQLException {
    ResultSet indexes = metaData.getIndexInfo(databaseName, null, tableName, false, false);
    System.out.println("  索引:");
    List<String> indexNames = new ArrayList<>();
    while (indexes.next()) {
      String indexName = indexes.getString("INDEX_NAME");
      String columnName = indexes.getString("COLUMN_NAME");
      boolean nonUnique = indexes.getBoolean("NON_UNIQUE");
      String indexType = indexes.getString("TYPE");

      // 只输出索引名称和包含的字段
      if (!indexNames.contains(indexName)) {
        System.out.println(
            "    索引: " + indexName + " | 是否唯一: " + !nonUnique + " | 索引类型: " + indexType);
      }
      System.out.println("      包含字段: " + columnName);

      List<FiledInfo> indexFields = new ArrayList<>();
      if (!tableInfo.indexFiledMap.containsKey(indexName)) {
        tableInfo.indexFiledMap.put(indexName, new ArrayList<>());
      }
      indexFields = tableInfo.indexFiledMap.get(indexName);
      FiledInfo info = null;
      for (FiledInfo fieldInfo : tableInfo.fields) {
        if (fieldInfo.name.equals(columnName)) {
          info = fieldInfo;
          break;
        }
      }
      indexFields.add(info);
    }
  }

  private String getTableName(String tableName) {
    return toUpperCamelCase(tableName);
  }

  public static String toUpperCamelCase(String input) {
    StringBuilder result = new StringBuilder();
    boolean capitalizeNext = true; // 标记是否需要大写

    for (char c : input.toCharArray()) {
      if (c == '_') {
        capitalizeNext = true; // 下划线后，下个字母需要大写
      } else {
        result.append(capitalizeNext ? Character.toUpperCase(c) : c);
        capitalizeNext = false; // 只有首字母需要大写，后续的保持小写
      }
    }

    return result.toString();
  }

  public static void main(String[] args) {
    String targetModule = (args.length > 0) ? args[0] : "core";
    System.out.println("Generating entries for module: " + targetModule);
    ParserDbEntry dbParser = new ParserDbEntry(targetModule);
    dbParser.parser();
    System.out.println(dbParser.tables);
  }
}
