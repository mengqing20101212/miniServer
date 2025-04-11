package ly.db;

import io.netty.util.internal.StringUtil;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import ly.LoggerDef;
import ly.db.entry.ShareEnumConfigEntry;
import ly.db.entry.ShareEnumConfigEntryHelper;
import org.slf4j.Logger;

/*
 * Author: liuYang
 * Date: 2025/4/3
 * File: MysqlService
 */
public class MysqlService {
  Logger logger = LoggerDef.DbLogger;
  private MysqlConnector mysqlConnector;
  private static final MysqlService instance = new MysqlService();

  /** 所有待入库的entry集合 */
  private LinkedBlockingQueue<saveOrUpdateEntry> dataQueue = new LinkedBlockingQueue<>();

  public static MysqlService getInstance() {
    return instance;
  }

  public void init(
      String jdbcUrl,
      String username,
      String password,
      int maxPoolSize,
      int minIdle,
      int idleTimeout,
      int connectionTimeout) {
    mysqlConnector =
        new MysqlConnector(
            jdbcUrl, username, password, maxPoolSize, minIdle, idleTimeout, connectionTimeout);

    // 启动一个保存的协程
    startSaveThread();
  }

  private void startSaveThread() {
    Thread.ofVirtual()
        .name("MysqlService-dbSaveVirtual")
        .start(
            () -> {
              saveOrUpdateEntry entry = null;
              try {
                while ((entry = dataQueue.poll()) != null) {
                  try {
                    if (entry.type == SAVE_TYPE) save(entry.data);
                    else if (entry.type == UPDATE_TYPE) update(entry.data, entry.fileds);
                  } catch (Exception e) {
                    logger.error(
                        String.format(
                            "save  AbstractEntry:%s error", entry.getClass().getSimpleName()),
                        e);
                    e.printStackTrace();
                  }
                }

              } catch (Exception e) {
                logger.error("MysqlService-dbSaveThread error", e);
                e.printStackTrace();
              }
            });
  }

  /**
   * 立即保存entry，同步阻塞
   *
   * @param entry
   * @return true 保存成功， false 保存失败
   */
  public boolean save(AbstractEntry entry) {
    if (!entry.canSave()) { // 该对象不需要保存
      return true;
    }
    List<Object> params = new ArrayList<>();
    try {
      String saveSql = getInsertSql(entry, params);
      return mysqlConnector.execute(saveSql, params.toArray());
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      logger.error(" 保存数据[%s] 报错 ", entry.toString(), e);
    }
    return true;
  }

  public boolean update(AbstractEntry entry, String... updateFileds) {
    if (!entry.canSave()) {
      return true;
    }
    List<Object> params = new ArrayList<>();
    try {
      String saveSql = getUpdateSql(entry, params, updateFileds);
      return mysqlConnector.execute(saveSql, params.toArray());
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      logger.error(" 保存数据[%s] 报错 ", entry.toString(), e);
    }
    return true;
  }

  public MysqlConnector getMysqlConnector() {
    if (mysqlConnector == null) {
      throw new NullPointerException("mysqlConnector is null");
    }
    return mysqlConnector;
  }

  /*** 异步保存，添加到保存队列 */
  public void addSaveEntry(AbstractEntry entry) {
    if (entry.canSave()) {
      dataQueue.add(new saveOrUpdateEntry(SAVE_TYPE, entry));
    }
  }

  public void addUpdateEntry(AbstractEntry entry, String... fileds) {
    if (entry.canSave()) {
      dataQueue.add(new saveOrUpdateEntry(UPDATE_TYPE, entry, fileds));
    }
  }

  public <T extends AbstractEntry> T selectOnce(Class<T> clazz, String[] fileds, Object... params) {
    String sql = getSelectSql(clazz, fileds);
    List<Map<String, Object>> resultList = mysqlConnector.select(sql, params);
    if (resultList.isEmpty()) {
      return null;
    }
    return packetEntry(resultList.getFirst(), clazz);
  }

  public <T extends AbstractEntry> List<T> selectAll(
      Class<T> clazz, String[] fileds, Object... params) {
    List<T> list = new ArrayList<>();
    String sql = getSelectSql(clazz, fileds);
    List<Map<String, Object>> resultList = mysqlConnector.select(sql, params);
    if (resultList.isEmpty()) {
      return list;
    }
    for (Map<String, Object> map : resultList) {
      list.add(packetEntry(map, clazz));
    }
    return list;
  }

  public static <T extends AbstractEntry> T packetEntry(
      Map<String, Object> resultMap, Class<T> clazz) {
    try {
      // 1. 反射创建对象实例
      T instance = clazz.getDeclaredConstructor().newInstance();

      // 2. 遍历类的字段
      for (Field field : clazz.getDeclaredFields()) {
        field.setAccessible(true); // 允许访问私有字段

        String columnName = null; // 数据库字段名
        if (field.isAnnotationPresent(DbMeta.DbField.class)) {
          columnName = field.getAnnotation(DbMeta.DbField.class).name();
        }

        // 如果字段有 @DbField 注解
        if (columnName != null && !columnName.isEmpty()) {
          Object value = resultMap.get(columnName); // 从查询结果获取值
          if (value != null) {
            field.set(instance, value); // 赋值给对象字段
          }
        }
      }

      return instance;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private <T extends AbstractEntry> String getSelectSql(Class<T> clazz, String[] fields) {
    StringBuilder sql = new StringBuilder();
    // 获取 @DbTable 注解
    if (clazz.isAnnotationPresent(DbMeta.DbTable.class)) {
      DbMeta.DbTable tableAnnotation = clazz.getAnnotation(DbMeta.DbTable.class);
      String tableName = tableAnnotation.name();

      // 如果注解未提供表名，则返回空字符串
      if (tableName == null || tableName.isEmpty()) {
        throw new IllegalArgumentException(
            "Class " + clazz.getSimpleName() + " does not have a valid @DbTable name.");
      }
      sql.append("SELECT * ");
      sql.append(" FROM ").append(tableName);
      if (fields != null && fields.length > 0) {
        sql.append(" WHERE 1=1 ");
        for (String field : fields) {
          sql.append(" AND ").append("`" + field + "`=?");
        }
      }
      return sql.toString();
    } else {
      throw new IllegalArgumentException(
          "Class " + clazz.getSimpleName() + " is missing @DbTable annotation.");
    }
  }

  /**
   * 生成 update SQL
   *
   * @param data 需要更新的对象
   * @param paramsList 参数列表
   * @param fileds 需要更新的字段列表，为空 则更新所有字段
   * @return 拼接的字段
   * @param <T> 实例的类型
   */
  private <T extends AbstractEntry> String getUpdateSql(
      T data, List<Object> paramsList, String[] fileds) throws IllegalAccessException {
    Class<?> clazz = data.getClass();
    StringBuilder sql = new StringBuilder();
    // 获取 @DbTable 注解
    if (clazz.isAnnotationPresent(DbMeta.DbTable.class)) {
      DbMeta.DbTable tableAnnotation = clazz.getAnnotation(DbMeta.DbTable.class);
      String tableName = tableAnnotation.name();

      String keyName = "";
      // 未指定更新的字段 则更新所有的字段
      List<String> allFields = new ArrayList<>();
      Object keyValue = null;
      for (Field field : clazz.getDeclaredFields()) {
        field.setAccessible(true);
        if (field.isAnnotationPresent(DbMeta.DbMasterKey.class)) {
          keyName = field.getAnnotation(DbMeta.DbMasterKey.class).name();
          keyValue = field.get(data);
        } else if (field.isAnnotationPresent(DbMeta.DbField.class)) {
          allFields.add(field.getAnnotation(DbMeta.DbField.class).name());
          paramsList.add(field.get(data));
        }
      }
      paramsList.add(keyValue);

      // 如果注解未提供表名，则返回空字符串
      if (tableName == null || tableName.isEmpty() || keyName == null || keyName.isEmpty()) {
        throw new IllegalArgumentException(
            "Class "
                + clazz.getSimpleName()
                + " does not have a valid @DbTable @DbMasterKey name.");
      }
      sql.append("UPDATE ").append(tableName).append(" SET ");
      if (fileds != null && fileds.length > 0) {
        for (String field : fileds) {
          sql.append(" " + field + "=?,");
        }
      } else {
        allFields.forEach(field -> sql.append(" " + field + "=?,"));
      }
      sql.deleteCharAt(sql.length() - 1);
      sql.append(" WHERE  " + keyName + "=?");
      return sql.toString();
    } else {
      throw new IllegalArgumentException(
          "Class " + clazz.getSimpleName() + " is missing @DbTable annotation.");
    }
  }

  private <T extends AbstractEntry> String getInsertSql(T data, List<Object> paramList)
      throws IllegalAccessException {
    StringBuilder sql = new StringBuilder();
    // 获取 @DbTable 注解
    Class<?> clazz = data.getClass();
    if (clazz.isAnnotationPresent(DbMeta.DbTable.class)) {
      DbMeta.DbTable tableAnnotation = clazz.getAnnotation(DbMeta.DbTable.class);
      String tableName = tableAnnotation.name();
      // 未指定更新的字段 则更新所有的字段
      List<String> allFields = new ArrayList<>();

      for (Field field : clazz.getDeclaredFields()) {
        field.setAccessible(true); // 允许访问私有字段
        if (field.isAnnotationPresent(DbMeta.DbMasterKey.class)) {
          if (!field.getAnnotation(DbMeta.DbMasterKey.class).autoIncrement()) {
            allFields.add(field.getAnnotation(DbMeta.DbMasterKey.class).name());
            paramList.add(field.get(data));
          }
        } else if (field.isAnnotationPresent(DbMeta.DbField.class)) {
          Object value = field.get(data);
          if (value != null) {
            allFields.add(field.getAnnotation(DbMeta.DbField.class).name());
            paramList.add(field.get(data));
          }
        }
      }

      // 如果注解未提供表名，则返回空字符串
      if (tableName == null || tableName.isEmpty()) {
        throw new IllegalArgumentException(
            "Class "
                + clazz.getSimpleName()
                + " does not have a valid @DbTable @DbMasterKey name.");
      }
      sql.append("INSERT INTO ").append(tableName);
      String fieldStr = "(";
      String valStr = "(";
      for (String field : allFields) {
        fieldStr += field + ",";
        valStr += " ?,";
      }
      fieldStr = fieldStr.substring(0, fieldStr.length() - 1);
      valStr = valStr.substring(0, valStr.length() - 1);
      fieldStr += ")";
      valStr += ");";
      sql.append(fieldStr);
      sql.append(" VALUES ").append(valStr);
      return sql.toString();
    } else {
      throw new IllegalArgumentException(
          "Class " + clazz.getSimpleName() + " is missing @DbTable annotation.");
    }
  }

  public void shutdown() {
    long maxSleepTime = 1000;
    // 数据未全部入库，最多阻塞 1秒
    while (!dataQueue.isEmpty() && maxSleepTime > 0) {
      try {
        Thread.sleep(10);
        maxSleepTime -= 10;
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    if (mysqlConnector != null) {
      mysqlConnector.shutdown();
    }
    logger.info("MysqlService shutdown , 待入库的数据:" + dataQueue.size());
  }

  private static final int SAVE_TYPE = 1;
  private static final int UPDATE_TYPE = 2;

  public boolean delete(AbstractEntry data) {
    // 获取 @DbTable 注解
    Class<?> clazz = data.getClass();
    String tableName = "";
    String keyName = "";
    Object keyValue = null;
    if (clazz.isAnnotationPresent(DbMeta.DbTable.class)) {
      DbMeta.DbTable tableAnnotation = clazz.getAnnotation(DbMeta.DbTable.class);
      tableName = tableAnnotation.name();
      for (Field field : clazz.getDeclaredFields()) {
        field.setAccessible(true); // 允许访问私有字段
        if (field.isAnnotationPresent(DbMeta.DbMasterKey.class)) {
          try {
            keyValue = field.get(data);
            keyName = field.getAnnotation(DbMeta.DbMasterKey.class).name();
          } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
          }
        }
      }
    }
    if (StringUtil.isNullOrEmpty(tableName)
        || StringUtil.isNullOrEmpty(keyName)
        || keyValue == null) {
      return false;
    }
    String sql = String.format("DELETE  FROM %s WHERE %s=?", tableName, keyName);
    return mysqlConnector.execute(sql, keyValue);
  }

  class saveOrUpdateEntry {
    int type;
    AbstractEntry data;
    String[] fileds;

    public saveOrUpdateEntry(int type, AbstractEntry data) {
      this.type = type;
      this.data = data;
    }

    public saveOrUpdateEntry(int type, AbstractEntry data, String[] fileds) {
      this(type, data);
      this.fileds = fileds;
    }
  }

  public static void main(String[] args) {
    String jdbcUrl = "jdbc:mysql://139.224.80.204:3306/pick_money";
    String username = "root";
    String password = "ly.1006897725";
    getInstance().init(jdbcUrl, username, password, 0, 0, 0, 0);
    ShareEnumConfigEntry entry =
        getInstance().selectOnce(ShareEnumConfigEntry.class, new String[] {"name"}, "1231");
    ShareEnumConfigEntryHelper.getShareEnumConfigEntryById(1231);

    ShareEnumConfigEntry data = new ShareEnumConfigEntry();
    data.setCode("qqqqq");
    data.setName("wwwwwwww");
    data.setConfigDesc("ssssssss");
    //    getInstance().save(data);
    entry = getInstance().selectOnce(ShareEnumConfigEntry.class, new String[] {"code"}, "qqqqq");
    entry.setConfigDesc("dadaw");
    getInstance().update(entry);
    getInstance().delete(entry);
  }
}
