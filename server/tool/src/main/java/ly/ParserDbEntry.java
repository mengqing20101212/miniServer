package ly;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  HikariDataSource dataSource;
  List<TableInfo> tables = new ArrayList<TableInfo>();

  class FiledInfo {
    String name;
    Object type;
    String desc;
    boolean autoIncrement;
  }

  class TableInfo {
    String name;
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
    fetchDatabaseSchema(curDbName);
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
        tableInfo.name = getTableName(tableName);
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
      int columnSize = columns.getInt("COLUMN_SIZE");
      String isAutoIncrement = columns.getString("IS_AUTOINCREMENT");

      FiledInfo fieldInfo = new FiledInfo();
      fieldInfo.name = columnName;
      fieldInfo.type = columnType;
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
