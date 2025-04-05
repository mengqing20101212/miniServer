package ly;

import static ly.ParserExcelConfig.ExcelConfig.formatJavaCode;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.util.StringUtil;

/*
 * Author: liuYang
 * Date: 2025/4/1
 * File: ParserDbEntry
 * 根据数据库的MySQL表结构 生成 指定的Java entry文件
 */
public class ParserDbEntry {
  String jdbcUrl = "jdbc:mysql://139.224.80.204:3306/pick_money";
  String username = "root";
  String password = "ly.1006897725";
  String targetDir = "D:\\WORK\\me\\miniServer\\server\\core\\src\\main\\java\\ly\\db\\entry";
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

  private void connectDb() {
    // 创建 Hikari 配置对象
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(jdbcUrl); // 数据库 URL
    config.setUsername(username); // 数据库用户名
    config.setPassword(password); // 数据库密码
    config.setDriverClassName("com.mysql.cj.jdbc.Driver"); // MySQL 驱动
    String curDbName = jdbcUrl.substring(jdbcUrl.lastIndexOf('/') + 1);
    dataSource = new HikariDataSource(config);

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
          String targetFileName = targetDir + File.separator + table.javaName + "EntryHelper.java";
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
                      + "  public static void save({javaName}Entry {javaName}Entry) {\n"
                      + "    MysqlService.getInstance().save({javaName}Entry);\n"
                      + "  }\n"
                      + "\n"
                      + "  public static void update({javaName}Entry {javaName}Entry, String... fileds) {\n"
                      + "    MysqlService.getInstance().update({javaName}Entry, fileds);\n"
                      + "  }\n"
                      + "\n"
                      + "  public static void delete({javaName}Entry {javaName}Entry) {\n"
                      + "    MysqlService.getInstance().delete({javaName}Entry);\n"
                      + "  }\n"
                      + "\n"
                      + "  public static void asyncSave({javaName}Entry {javaName}Entry) {\n"
                      + "    MysqlService.getInstance().addSaveEntry({javaName}Entry);\n"
                      + "  }\n"
                      + "\n"
                      + "  public static void asyncUpdate({javaName}Entry {javaName}Entry, String... fileds) {\n"
                      + "    MysqlService.getInstance().addUpdateEntry({javaName}Entry);\n"
                      + "  }\n"
                      + "\n"
                      + "  public static List<{javaName}Entry> select(String[] fields, Object... params) {\n"
                      + "\n"
                      + "    if (fields != null && params != null && fields.length != params.length) {\n"
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
          String targetFileName = targetDir + File.separator + table.javaName + "Entry.java";
          File file = new File(targetFileName);
          String methodStr = "";
          String filedStr = "";
          String toStringStr = "";
          String extractStr = "";
          if (file.exists()) {
            String score = ParserExcelConfig.ExcelConfig.readFile(file);
            extractStr = ParserExcelConfig.ExcelConfig.extractMethodStr(score);
            file.delete();
          }
          for (FiledInfo field : table.fields) {
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
                      + filedStr
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

      FiledInfo fieldInfo = new FiledInfo();
      fieldInfo.name = columnName;
      fieldInfo.type = columnType;
      fieldInfo.javaType = mapSqlTypeToJavaType(sqlType, columnType);
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

  private Object mapSqlTypeToJavaType(int sqlType, String typeName) {
    switch (sqlType) {
      case Types.INTEGER:
        return "Integer";
      case Types.BIGINT:
        return "Long";
      case Types.FLOAT:
        return "Float";
      case Types.DOUBLE:
        return "Double";
      case Types.DECIMAL:
      case Types.NUMERIC:
        return "BigDecimal";
      case Types.VARCHAR:
      case Types.CHAR:
      case Types.LONGVARCHAR:
      case Types.NVARCHAR:
      case Types.NCHAR:
        return "String";
      case Types.DATE:
        return "java.sql.Date"; // or java.time.LocalDate
      case Types.TIME:
        return "java.sql.Time"; // or java.time.LocalTime
      case Types.TIMESTAMP:
        return "java.sql.Timestamp"; // or java.time.LocalDateTime
      case Types.BOOLEAN:
      case Types.BIT:
      case Types.TINYINT:
        if ("TINYINT".equalsIgnoreCase(typeName) || "BIT".equalsIgnoreCase(typeName)) {
          return "Boolean";
        }
        return "Byte";
      case Types.BLOB:
      case Types.VARBINARY:
      case Types.LONGVARBINARY:
        return "byte[]";
      default:
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
    if (tableName.contains("_")) {
      return toUpperCamelCase(tableName);
    }
    return tableName;
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
    String str = "test_test_tt";
    System.out.println(toUpperCamelCase(str));
    ParserDbEntry dbParser = new ParserDbEntry();
    dbParser.parser();
    System.out.println(dbParser.tables);
  }
}
