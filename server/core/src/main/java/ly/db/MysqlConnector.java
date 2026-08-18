package ly.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ly.LoggerDef;
import ly.utils.RandomUtils;
import org.slf4j.Logger;

/**
 * MySQL 底层连接器。
 * <p>
 * 负责创建 HikariCP 连接池、执行参数化 SQL，并把查询结果转换为列名到值的 Map。
 * 单个方法内部会从连接池获取独立 Connection；类字段本身不保存事务上下文。
 */
public class MysqlConnector {
  private static final String MYSQL_REQUIRED_PARAMS =
      "useSSL=false&allowPublicKeyRetrieval=true&connectTimeout=8000&socketTimeout=8000";
  private static final int MYSQL_INIT_MAX_RETRIES = 5;
  private static final long MYSQL_INIT_RETRY_SLEEP_MS = 2000;

  HikariDataSource dataSource;
  Logger logger = LoggerDef.DbLogger;

  // SQL 执行的最大超时时间
  private final int SQL_MAX_OPT_TIMEOUT = 300;

  public MysqlConnector(
      String jdbcUrl,
      String username,
      String password,
      int maxPoolSize,
      int minIdle,
      int idleTimeout,
      int connectionTimeout) {
    String effectiveJdbcUrl = normalizeJdbcUrl(jdbcUrl);
    dataSource =
        createDataSourceWithRetry(
            effectiveJdbcUrl, username, password, maxPoolSize, minIdle, idleTimeout, connectionTimeout);
    if (dataSource != null && testConnect()) {
      logger.info(String.format("database connection success, jdbcUrl:%s", effectiveJdbcUrl));
    } else {
      logger.error(
          String.format(
              "database connection failed, check jdbcUrl:%s, userName:%s, password:%s",
              effectiveJdbcUrl, username, password));
    }
  }

  /**
   * 带重试创建连接池。
   * <p>
   * HikariDataSource 构造成功不代表数据库可用，因此每次创建后都会立即取连接验证。
   */
  private HikariDataSource createDataSourceWithRetry(
      String jdbcUrl,
      String username,
      String password,
      int maxPoolSize,
      int minIdle,
      int idleTimeout,
      int connectionTimeout) {
    RuntimeException lastException = null;
    for (int attempt = 1; attempt <= MYSQL_INIT_MAX_RETRIES; attempt++) {
      HikariDataSource currentDataSource = null;
      try {
        currentDataSource =
            new HikariDataSource(
                buildConfig(jdbcUrl, username, password, maxPoolSize, minIdle, idleTimeout, connectionTimeout));
        try (Connection ignored = currentDataSource.getConnection()) {
          logger.info(
              "database connection initialized on attempt {}/{}",
              attempt,
              MYSQL_INIT_MAX_RETRIES);
          return currentDataSource;
        }
      } catch (Exception e) {
        if (currentDataSource != null) {
          currentDataSource.close();
        }
        lastException = new RuntimeException(e);
        logger.warn(
            "database init attempt {}/{} failed, jdbcUrl:{}, error:{}",
            attempt,
            MYSQL_INIT_MAX_RETRIES,
            jdbcUrl,
            e.getMessage());
        if (attempt < MYSQL_INIT_MAX_RETRIES) {
          sleepBeforeRetry();
        }
      }
    }
    if (lastException != null) {
      lastException.printStackTrace();
    }
    return null;
  }

  private HikariConfig buildConfig(
      String jdbcUrl,
      String username,
      String password,
      int maxPoolSize,
      int minIdle,
      int idleTimeout,
      int connectionTimeout) {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(jdbcUrl);
    config.setUsername(username);
    config.setPassword(password);
    config.setDriverClassName("com.mysql.cj.jdbc.Driver");
    config.setMaximumPoolSize(maxPoolSize == 0 ? 10 : maxPoolSize);
    config.setMinimumIdle(minIdle == 0 ? 2 : minIdle);
    config.setIdleTimeout(idleTimeout == 0 ? 30000 : idleTimeout);
    config.setConnectionTimeout(connectionTimeout == 0 ? 2000 : connectionTimeout);
    config.setMaxLifetime(1800000);
    return config;
  }

  private void sleepBeforeRetry() {
    try {
      Thread.sleep(MYSQL_INIT_RETRY_SLEEP_MS);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  /**
   * 补齐 MySQL 连接必需参数。
   * <p>
   * 避免本地 MySQL 8 公钥获取、SSL 和网络超时参数缺失导致启动卡死或握手失败。
   */
  private static String normalizeJdbcUrl(String jdbcUrl) {
    if (jdbcUrl == null || jdbcUrl.isBlank() || !jdbcUrl.startsWith("jdbc:mysql://")) {
      return jdbcUrl;
    }
    String normalizedJdbcUrl = jdbcUrl;
    for (String param : MYSQL_REQUIRED_PARAMS.split("&")) {
      String key = param.substring(0, param.indexOf('='));
      if (!containsJdbcParam(normalizedJdbcUrl, key)) {
        normalizedJdbcUrl += normalizedJdbcUrl.contains("?") ? "&" + param : "?" + param;
      }
    }
    return normalizedJdbcUrl;
  }

  private static boolean containsJdbcParam(String jdbcUrl, String key) {
    int queryStart = jdbcUrl.indexOf('?');
    if (queryStart < 0 || queryStart == jdbcUrl.length() - 1) {
      return false;
    }
    String[] params = jdbcUrl.substring(queryStart + 1).split("&");
    for (String param : params) {
      if (param.equals(key) || param.startsWith(key + "=")) {
        return true;
      }
    }
    return false;
  }

  private boolean testConnect() {
    if (dataSource == null) {
      return false;
    }
    try (Connection connection = dataSource.getConnection()) {
      connection.prepareStatement("select 1;").executeQuery().close();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 执行查询 SQL。
   *
   * @return 每行以数据库列名为 key 的 Map；异常时返回已收集结果或空列表
   */
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
                  "select sql:%s, params:%s, cost:%d ms",
                  sql, getParamStr(params), System.currentTimeMillis() - startTime));
        }
      }
    } catch (Exception e) {
      logger.error(
          String.format("execute SQL(%s) error, params:%s, error:%s", sql, getParamStr(params), e.getMessage()));
    }
    if (System.currentTimeMillis() - startTime >= SQL_MAX_OPT_TIMEOUT) {
      logger.warn(String.format("select SQL cost too long, check %s, %s", sql, getParamStr(params)));
    }
    return resultList;
  }

  /**
   * 执行不允许降级为空结果的查询。
   *
   * <p>用于持久化主数据读取；查询失败时抛出异常，避免调用方把数据库故障误判为没有数据。
   */
  public List<Map<String, Object>> selectStrict(String sql, Object... params) {
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
      }
      return resultList;
    } catch (Exception e) {
      throw new IllegalStateException(
          String.format("execute strict SQL(%s) failed, params:%s", sql, getParamStr(params)), e);
    } finally {
      long cost = System.currentTimeMillis() - startTime;
      if (cost >= SQL_MAX_OPT_TIMEOUT) {
        logger.warn("select strict SQL cost too long, sql:{}, params:{}, cost:{} ms", sql, getParamStr(params), cost);
      }
    }
  }

  private static void addSqlParams(Object[] params, PreparedStatement st) throws Exception {
    if (params != null && params.length > 0) {
      for (int i = 0; i < params.length; i++) {
        Object param = params[i];
        if (param == null) {
          throw new RuntimeException("SQL param can not be null, please provide a default value");
        }
        st.setObject(i + 1, param);
      }
    }
  }

  public void batchExecute(List<String> sqls, List<Object[]> paramList) {
    long startTime = System.currentTimeMillis();
    if (sqls == null || sqls.isEmpty()) {
      logger.error("invalid args: sqls == null || sqls.isEmpty()");
      return;
    }
    if (paramList == null || paramList.isEmpty()) {
      logger.error("invalid args: paramList == null || paramList.isEmpty()");
      return;
    }
    if (sqls.size() != paramList.size()) {
      logger.error("invalid args: sqls.size() != paramList.size()");
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
                    "batchExecute item %d/%d sql:%s, params:%s %s, cost:%d ms",
                    i,
                    sqls.size(),
                    sql,
                    getParamStr(params),
                    result ? "success" : "fail",
                    System.currentTimeMillis() - beginTime));
          }
        } catch (Exception e) {
          logger.error(
              String.format("execute SQL(%s) error, params:%s, error:%s", sql, getParamStr(params), e.getMessage()));
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
              "batchExecute cost(%d ms) too long, successCount:%d size:%d",
              System.currentTimeMillis() - startTime, successCount, sqls.size()));
    }
  }

  /**
   * 在同一个事务中批量执行同一条参数化 SQL。
   *
   * <p>任意一条执行失败都会回滚整个批次，适合保存同一次玩家操作涉及的多个模块。
   */
  public boolean executeBatchTransaction(String sql, List<Object[]> paramList) {
    if (sql == null || sql.isBlank() || paramList == null || paramList.isEmpty()) {
      return false;
    }
    long startTime = System.currentTimeMillis();
    Connection connection = null;
    try {
      connection = dataSource.getConnection();
      connection.setAutoCommit(false);
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        for (Object[] params : paramList) {
          addSqlParams(params, statement);
          statement.addBatch();
        }
        statement.executeBatch();
      }
      connection.commit();
      return true;
    } catch (Exception e) {
      rollbackQuietly(connection);
      logger.error(
          "execute batch transaction error, sql:{}, size:{}, error:{}",
          sql,
          paramList.size(),
          e.getMessage(),
          e);
      return false;
    } finally {
      closeTransactionConnection(connection);
      long cost = System.currentTimeMillis() - startTime;
      if (cost >= SQL_MAX_OPT_TIMEOUT) {
        logger.warn("execute batch transaction cost too long, sql:{}, size:{}, cost:{} ms", sql, paramList.size(), cost);
      }
    }
  }

  private void rollbackQuietly(Connection connection) {
    if (connection == null) {
      return;
    }
    try {
      connection.rollback();
    } catch (Exception rollbackError) {
      logger.error("rollback batch transaction failed", rollbackError);
    }
  }

  private void closeTransactionConnection(Connection connection) {
    if (connection == null) {
      return;
    }
    try {
      connection.setAutoCommit(true);
    } catch (Exception resetError) {
      logger.warn("reset transaction autoCommit failed: {}", resetError.getMessage());
    }
    try {
      connection.close();
    } catch (Exception closeError) {
      logger.warn("close transaction connection failed: {}", closeError.getMessage());
    }
  }

  public boolean execute(String sql, Object... params) {
    long startTime = System.currentTimeMillis();
    boolean result = false;
    try (Connection connection = dataSource.getConnection();
        PreparedStatement st = connection.prepareStatement(sql)) {
      addSqlParams(params, st);
      if (isDdlSql(sql)) {
        // DDL 语句执行成功时通常返回 0 行影响，不能用影响行数判断成败。
        st.execute();
        result = true;
      } else {
        result = st.executeUpdate() > 0;
      }
      if (logger.isDebugEnabled()) {
        logger.debug(
            String.format(
                "execute sql:%s, params:%s %s, cost:%d ms",
                sql,
                getParamStr(params),
                result ? "success" : "fail",
                System.currentTimeMillis() - startTime));
      }
    } catch (Exception e) {
      logger.error(
          String.format("execute SQL(%s) error, params:%s, error:%s", sql, getParamStr(params), e.getMessage()));
      e.printStackTrace();
    }
    long endTime = System.currentTimeMillis();
    if (endTime - startTime >= SQL_MAX_OPT_TIMEOUT) {
      logger.warn(String.format("execute SQL cost too long, check %s, %s", sql, getParamStr(params)));
    }
    return result;
  }

  /**
   * 执行必须成功的 DML 并返回影响行数。
   *
   * <p>与 {@link #execute(String, Object...)} 不同，0 行影响也是一次成功执行，并且 SQL 异常会
   * 抛给调用方。版本化 UPSERT 需要据此区分“旧版本被忽略”和“数据库执行失败”。
   */
  public int executeUpdateStrict(String sql, Object... params) {
    long startTime = System.currentTimeMillis();
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      addSqlParams(params, statement);
      return statement.executeUpdate();
    } catch (Exception e) {
      throw new IllegalStateException(
          String.format("execute strict update SQL(%s) failed, params:%s", sql, getParamStr(params)), e);
    } finally {
      long cost = System.currentTimeMillis() - startTime;
      if (cost >= SQL_MAX_OPT_TIMEOUT) {
        logger.warn("strict update SQL cost too long, sql:{}, params:{}, cost:{} ms", sql, getParamStr(params), cost);
      }
    }
  }

  private boolean isDdlSql(String sql) {
    if (sql == null) {
      return false;
    }
    String normalized = sql.stripLeading().toLowerCase();
    return normalized.startsWith("create ")
        || normalized.startsWith("alter ")
        || normalized.startsWith("drop ")
        || normalized.startsWith("truncate ");
  }

  public Number executeInsertReturnKey(String sql, Object... params) {
    long startTime = System.currentTimeMillis();
    Number generatedKey = null;
    boolean result = false;
    try (Connection connection = dataSource.getConnection();
        PreparedStatement st =
            connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      addSqlParams(params, st);
      result = st.executeUpdate() > 0;
      if (result) {
        try (ResultSet rs = st.getGeneratedKeys()) {
          if (rs.next()) {
            Object key = rs.getObject(1);
            if (key instanceof Number number) {
              generatedKey = number;
            }
          }
        }
      }
      if (logger.isDebugEnabled()) {
        logger.debug(
            String.format(
                "executeInsertReturnKey sql:%s, params:%s %s, key:%s, cost:%d ms",
                sql,
                getParamStr(params),
                result ? "success" : "fail",
                generatedKey,
                System.currentTimeMillis() - startTime));
      }
    } catch (Exception e) {
      logger.error(
          String.format("execute SQL(%s) error, params:%s, error:%s", sql, getParamStr(params), e.getMessage()));
      e.printStackTrace();
    }
    long endTime = System.currentTimeMillis();
    if (endTime - startTime >= SQL_MAX_OPT_TIMEOUT) {
      logger.warn(String.format("execute SQL cost too long, check %s, %s", sql, getParamStr(params)));
    }
    return result ? generatedKey : null;
  }

  private String getParamStr(Object[] params) {
    if (params == null) {
      return "[]";
    }
    StringBuffer sb = new StringBuffer("[");
    for (int i = 0; i < params.length; i++) {
      if (params[i] != null) {
        String value = String.valueOf(params[i]);
        sb.append(String.format("'%s', ", value));
      } else {
        sb.append("'null', ");
      }
    }
    if (params.length > 0) {
      sb.delete(sb.length() - 2, sb.length());
    }
    sb.append("]");
    return sb.toString();
  }

  public void shutdown() {
    if (dataSource != null) {
      dataSource.close();
    }
    logger.info("database connection closed");
  }

  public static void main(String[] args) {
    String jdbcUrl = "jdbc:mysql://118.25.76.117:3306/pick_money";
    String username = "root";
    String password = "Ly@2026Root!8899";
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
    mysqlConnector.execute(insterSql, "321231", "test", "4543543543");
  }

  private static void testSelect(MysqlConnector mysqlConnector) {
    List<Map<String, Object>> resultList = mysqlConnector.select("select * from share_enum_config");
    System.out.println(resultList.get(0));
  }
}
