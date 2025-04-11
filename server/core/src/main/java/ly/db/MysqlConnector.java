package ly.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ly.LoggerDef;
import ly.utils.RandomUtils;
import org.slf4j.Logger;

/** 负责操作MySQL,不是线程安全 Author: liuYang Date: 2025/4/3 File: MysqlConnector */
public class MysqlConnector {
  HikariDataSource dataSource;
  Logger logger = LoggerDef.DbLogger;

  // SQL执行的最大超时时间
  private final int SQL_MAX_OPT_TIMEOUT = 300;

  /**
   * @param jdbcUrl 数据库 URL
   * @param username 数据库用户名
   * @param password 数据库密码
   * @param maxPoolSize 连接池最大连接数
   * @param minIdle 最小空闲连接数
   * @param idleTimeout 连接空闲时间
   * @param connectionTimeout 连接超时时间
   */
  public MysqlConnector(
      String jdbcUrl,
      String username,
      String password,
      int maxPoolSize,
      int minIdle,
      int idleTimeout,
      int connectionTimeout) {
    // 创建 Hikari 配置对象
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(jdbcUrl); // 数据库 URL
    config.setUsername(username); // 数据库用户名
    config.setPassword(password); // 数据库密码
    config.setDriverClassName("com.mysql.cj.jdbc.Driver"); // MySQL 驱动
    //
    //    // 连接池参数
    config.setMaximumPoolSize(maxPoolSize == 0 ? 10 : maxPoolSize); // 连接池最大连接数
    config.setMinimumIdle(minIdle == 0 ? 2 : minIdle); // 最小空闲连接数
    config.setIdleTimeout(idleTimeout == 0 ? 30000 : idleTimeout); // 连接空闲时间
    config.setConnectionTimeout(connectionTimeout == 0 ? 2000 : connectionTimeout); // 连接超时时间
    config.setMaxLifetime(1800000); // 连接最大生命周期（30分钟）
    try {
      dataSource = new HikariDataSource(config);
    } catch (Exception e) {
      logger.error(
          String.format(
              "数据库连接失败,请检查 jdbcUrl:%s, userName:%s, password:%s", jdbcUrl, username, password));
      e.printStackTrace();
    }
    if (testConnect()) {
      logger.info(String.format("数据库连接成功, jdbcUrl:%s", jdbcUrl));
    } else {
      logger.error(
          String.format(
              "数据库连接失败,请检查 jdbcUrl:%s, userName:%s, password:%s", jdbcUrl, username, password));
    }
  }

  private boolean testConnect() {
    try (Connection connection = dataSource.getConnection()) {
      connection.prepareStatement("select 1;").executeQuery().close();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public List<Map<String, Object>> select(String sql, Object... params) {
    long startTime = System.currentTimeMillis();
    List<Map<String, Object>> resultList = new ArrayList<>();
    try (Connection connection = dataSource.getConnection();
        PreparedStatement st = connection.prepareStatement(sql)) {
      addSqlParams(params, st);
      try (ResultSet rs = st.executeQuery()) {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        while (rs.next()) {
          Map<String, Object> row = new HashMap<>();
          for (int i = 1; i <= columnCount; i++) {
            row.put(metaData.getColumnName(i), rs.getObject(i));
          }
          resultList.add(row);
        }
        if (logger.isDebugEnabled()) {
          logger.debug(
              String.format(
                  "select 执行sql: %s , params: %s ,  耗时: %d 毫秒",
                  sql, getParamStr(params), System.currentTimeMillis() - startTime));
        }
      }
    } catch (Exception e) {
      logger.error(
          String.format(
              "执行SQL(%s) 报错, params:%s, error:%s", sql, getParamStr(params), e.getMessage()));
    }
    if (System.currentTimeMillis() - startTime >= SQL_MAX_OPT_TIMEOUT) {
      logger.warn(String.format("执行SQL 耗时过长，请检查: %s , %s", sql, getParamStr(params)));
    }
    return resultList;
  }

  private static void addSqlParams(Object[] params, PreparedStatement st) throws Exception {
    if (params != null && params.length > 0) {
      for (int i = 0; i < params.length; i++) {
        Object param = params[i];
        if (param == null) {
          throw new RuntimeException("SQL 参数不可以为 null, 请设置一个默认值");
        }
        st.setObject(i + 1, param);
      }
    }
  }

  public void batchExecute(List<String> sqls, List<Object[]> paramList) {
    long startTime = System.currentTimeMillis();
    if (sqls == null || sqls.isEmpty()) {
      logger.error("参数错误，sqls == null || sqls.isEmpty() ");
      return;
    }
    if (paramList == null || paramList.isEmpty()) {
      logger.error("参数错误，paramList == null || paramList.isEmpty() ");
      return;
    }
    if (sqls.size() != paramList.size()) {
      logger.error("参数错误，sqls.size() != paramList.size() ");
      return;
    }
    int successCount = 0;
    try (Connection connection = dataSource.getConnection()) {
      for (int i = 0; i < sqls.size(); i++) {
        String sql = sqls.get(i);
        Object[] params = paramList.get(i);
        long beginTime = System.currentTimeMillis();
        try (PreparedStatement st = connection.prepareStatement(sql)) {
          addSqlParams(params, st);
          boolean result = st.executeUpdate() > 0;
          successCount += result ? 1 : 0;
          if (logger.isDebugEnabled()) {
            logger.debug(
                String.format(
                    "batchExecute 执行第 %d : %d 条sql:%s, params:%s %s,  耗时:%d  毫秒",
                    i,
                    sqls.size(),
                    sql,
                    getParamStr(params),
                    result ? "成功" : "失败",
                    System.currentTimeMillis() - beginTime));
          }
        } catch (Exception e) {
          logger.error(
              String.format(
                  "执行SQL(%s) 报错, params:%s, error:%s", sql, getParamStr(params), e.getMessage()));
          e.printStackTrace();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    long endTime = System.currentTimeMillis();
    if (endTime - startTime >= SQL_MAX_OPT_TIMEOUT) {
      logger.warn(
          String.format(
              "batchExecute 执行SQL, 耗时(%d 毫秒)过长, 执行成功数量:%d 请检查 size:%d",
              System.currentTimeMillis() - startTime, successCount, sqls.size()));
    }
  }

  public boolean execute(String sql, Object... params) {
    long startTime = System.currentTimeMillis();
    boolean result = false;
    try (Connection connection = dataSource.getConnection();
        PreparedStatement st = connection.prepareStatement(sql)) {
      addSqlParams(params, st);
      result = st.executeUpdate() > 0;
      if (logger.isDebugEnabled()) {
        logger.debug(
            String.format(
                "execute 执行sql: %s , params: %s %s, 耗时: %d 毫秒",
                sql,
                getParamStr(params),
                result ? "成功" : "失败",
                System.currentTimeMillis() - startTime));
      }
    } catch (Exception e) {
      logger.error(
          String.format(
              "执行SQL(%s) 报错, params:%s, error:%s", sql, getParamStr(params), e.getMessage()));
      e.printStackTrace();
    }
    long endTime = System.currentTimeMillis();
    if (endTime - startTime >= SQL_MAX_OPT_TIMEOUT) {
      logger.warn(String.format("执行SQL, 耗时过长, 请检查 %s, %s", sql, getParamStr(params)));
    }
    return result;
  }

  private String getParamStr(Object[] params) {
    StringBuffer sb = new StringBuffer("[");
    for (int i = 0; i < params.length; i++) {
      String value = String.valueOf(params[i]);
      sb.append(String.format("'%s, '", value));
    }
    sb.append("]");
    return sb.toString();
  }

  public void shutdown() {
    dataSource.close();
    logger.info("数据库连接关闭");
  }

  public static void main(String[] args) {
    String jdbcUrl = "jdbc:mysql://139.224.80.204:3306/pick_money";
    String username = "root";
    String password = "ly.1006897725";
    MysqlConnector mysqlConnector = new MysqlConnector(jdbcUrl, username, password, 0, 0, 0, 0);
    testSelect(mysqlConnector);
    testInster(mysqlConnector);
    testBatchInster(mysqlConnector);
    mysqlConnector.shutdown();
  }

  private static void testBatchInster(MysqlConnector mysqlConnector) {
    String insterSql = "insert into share_enum_config (code,name,config_desc) values(?,?,?)";
    List<String> sqls = new ArrayList<>();
    List<Object[]> params = new ArrayList<>();

    for (int i = 0; i < 1000; i++) {
      sqls.add(insterSql);
      Object[] param = new Object[3];
      param[0] = RandomUtils.generateRandomString(5);
      param[1] = RandomUtils.generateRandomString(5);
      param[2] = RandomUtils.generateRandomString(5);
      params.add(param);
    }
    mysqlConnector.batchExecute(sqls, params);
  }

  private static void testInster(MysqlConnector mysqlConnector) {
    String insterSql = "insert into share_enum_config (code,name,config_desc) values(?,?,?)";
    mysqlConnector.execute(insterSql, "321231", "测试测试测测试", "4543543543");
  }

  private static void testSelect(MysqlConnector mysqlConnector) {
    List<Map<String, Object>> resultList = mysqlConnector.select("select * from share_enum_config");
    System.out.println(resultList.get(0));
  }
}
