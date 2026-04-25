package ly.db;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;

import io.netty.util.internal.StringUtil;
import ly.LoggerDef;
import ly.db.entry.ShareEnumConfigEntry;
import ly.db.entry.ShareEnumConfigEntryHelper;

/**
 * 数据库访问组件，封装连接、元数据、实体脏标记和增删改查操作。
 * <p>
 * 业务层通常通过生成的 EntryHelper 调用这里。同步接口会立即执行 SQL；异步接口先进入
 * {@code dataQueue}，由后台虚拟线程批量消费。
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

  /**
   * 初始化 MySQL 连接池并启动异步保存任务。
   *
   * <p>连接池大小、空闲连接、空闲超时和连接超时参数传 0 时使用默认值。
   */
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

  /**
   * 启动异步保存线程。
   * <p>
   * 队列中的任务会按保存/更新类型调用同步接口；同步接口仍负责生成 SQL、执行和标记持久化。
   */
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
    if (entry == null) {
      logger.error("保存数据失败: entry 不能为 null");
      return false;
    }
    if (!entry.canSave()) { // 该对象不需要保存
      return true;
    }
    List<Object> params = new ArrayList<>();
    try {
      String saveSql = getInsertSql(entry, params);
      if (mysqlConnector == null) {
        logger.error("MysqlConnector 未初始化，无法保存数据");
        return false;
      }
      Number generatedKey = mysqlConnector.executeInsertReturnKey(saveSql, params.toArray());
      boolean success = generatedKey != null || hasNonAutoIncrementPrimaryKey(entry);
      if (success) {
        applyGeneratedKey(entry, generatedKey);
        entry.markPersisted();
      }
      return success;
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      logger.error(" 保存数据[%s] 报错 ", entry.toString(), e);
    } catch (Exception e) {
      logger.error(" 保存数据时发生未知错误 [%s]", entry.toString(), e);
    }
    return false;
  }

  /**
   * 同步更新实体。
   * <p>
   * {@code updateFileds} 为空时优先使用实体脏字段；仍为空则认为没有需要更新的字段，并把对象
   * 标记为已持久化。
   */
  public boolean update(AbstractEntry entry, String... updateFileds) {
    if (entry == null) {
      logger.error("更新数据失败: entry 不能为 null");
      return false;
    }
    if (!entry.canSave()) {
      return true;
    }
    List<Object> params = new ArrayList<>();
    try {
      String saveSql = getUpdateSql(entry, params, updateFileds);
      if (mysqlConnector == null) {
        logger.error("MysqlConnector 未初始化，无法更新数据");
        return false;
      }
      if (saveSql == null || saveSql.isEmpty()) {
        entry.markPersisted();
        return true;
      }
      boolean success = mysqlConnector.execute(saveSql, params.toArray());
      if (success) {
        entry.markPersisted();
      }
      return success;
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      logger.error(" 更新数据[%s] 报错 ", entry.toString(), e);
    } catch (Exception e) {
      logger.error(" 更新数据时发生未知错误 [%s]", entry.toString(), e);
    }
    return false;
  }

  public MysqlConnector getMysqlConnector() {
    if (mysqlConnector == null) {
      throw new NullPointerException("mysqlConnector is null");
    }
    return mysqlConnector;
  }

  /** 异步保存，添加到保存队列；真正执行由保存线程完成。 */
  public void addSaveEntry(AbstractEntry entry) {
    if (entry.canSave()) {
      dataQueue.add(new saveOrUpdateEntry(SAVE_TYPE, entry));
    }
  }

  /** 异步更新，字段参数为空时由实体脏字段决定 UPDATE 列。 */
  public void addUpdateEntry(AbstractEntry entry, String... fileds) {
    if (entry.canSave()) {
      dataQueue.add(new saveOrUpdateEntry(UPDATE_TYPE, entry, fileds));
    }
  }

  /**
   * 查询单条实体。
   *
   * @param fileds WHERE 条件列名数组，按顺序与 {@code params} 一一对应
   */
  public <T extends AbstractEntry> T selectOnce(Class<T> clazz, String[] fileds, Object... params) {
    if (clazz == null) {
      logger.error("查询单条记录失败: clazz 不能为 null");
      return null;
    }
    if (mysqlConnector == null) {
      logger.error("MysqlConnector 未初始化，无法查询数据");
      return null;
    }
    try {
      String sql = getSelectSql(clazz, fileds);
      List<Map<String, Object>> resultList = mysqlConnector.select(sql, params);
      if (resultList == null || resultList.isEmpty()) {
        return null;
      }
      return packetEntry(resultList.getFirst(), clazz);
    } catch (Exception e) {
      logger.error("查询单条记录时发生错误", e);
      return null;
    }
  }

  public <T extends AbstractEntry> List<T> selectAll(
      Class<T> clazz, String[] fileds, Object... params) {
    if (clazz == null) {
      logger.error("查询多条记录失败: clazz 不能为 null");
      return new ArrayList<>();
    }
    if (mysqlConnector == null) {
      logger.error("MysqlConnector 未初始化，无法查询数据");
      return new ArrayList<>();
    }
    List<T> list = new ArrayList<>();
    try {
      String sql = getSelectSql(clazz, fileds);
      List<Map<String, Object>> resultList = mysqlConnector.select(sql, params);
      if (resultList == null || resultList.isEmpty()) {
        return list;
      }
      for (Map<String, Object> map : resultList) {
        if (map != null) {
          T entry = packetEntry(map, clazz);
          if (entry != null) {
            list.add(entry);
          }
        }
      }
    } catch (Exception e) {
      logger.error("查询多条记录时发生错误", e);
    }
    return list;
  }

  /**
   * 把 JDBC 查询结果封装为 Entry。
   * <p>
   * 只处理带 {@link DbMeta.DbField} 注解的字段；构造完成后会调用
   * {@link AbstractEntry#markPersisted()}，避免刚从数据库读出的对象被误判为脏数据。
   */
  public static <T extends AbstractEntry> T packetEntry(
      Map<String, Object> resultMap, Class<T> clazz) {
    if (resultMap == null) {
      LoggerDef.DbLogger.error("packetEntry: resultMap 不能为 null");
      return null;
    }
    if (clazz == null) {
      LoggerDef.DbLogger.error("packetEntry: clazz 不能为 null");
      return null;
    }
    
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
            field.set(instance, convertFieldValue(field.getType(), value));
          }
        }
      }

      instance.markPersisted();
      return instance;
    } catch (Exception e) {
      LoggerDef.DbLogger.error("packetEntry: 封装对象时发生错误，resultMap={}, clazz={}", resultMap, clazz.getSimpleName(), e);
    }
    return null;
  }

  private <T extends AbstractEntry> String getSelectSql(Class<T> clazz, String[] fields) {
    if (clazz == null) {
      throw new IllegalArgumentException("Class 不能为 null");
    }
    
    // 获取 @DbTable 注解
    if (clazz.isAnnotationPresent(DbMeta.DbTable.class)) {
      DbMeta.DbTable tableAnnotation = clazz.getAnnotation(DbMeta.DbTable.class);
      if (tableAnnotation == null) {
        throw new IllegalArgumentException(
            "Class " + clazz.getSimpleName() + " 的 @DbTable 注解信息无效。");
      }
      
      String tableName = tableAnnotation.name();

      // 如果注解未提供表名，则返回空字符串
      if (tableName == null || tableName.isEmpty()) {
        throw new IllegalArgumentException(
            "Class " + clazz.getSimpleName() + " does not have a valid @DbTable name.");
      }
      StringBuilder sql = new StringBuilder();
      sql.append("SELECT * ");
      sql.append(" FROM ").append(tableName);
      if (fields != null && fields.length > 0) {
        sql.append(" WHERE 1=1 ");
        for (String field : fields) {
          if (field != null) {
            sql.append(" AND ").append("`" + field + "`=?");
          }
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
  /**
   * 生成 UPDATE SQL 和参数。
   * <p>
   * 主键只用于 WHERE 条件，不会进入 SET。指定字段优先；未指定时使用实体脏字段；脏字段为空时
   * 回落到全部普通字段。
   */
  private <T extends AbstractEntry> String getUpdateSql(
      T data, List<Object> paramsList, String[] fileds) throws IllegalAccessException {
    if (data == null) {
      throw new IllegalArgumentException("data 不能为 null");
    }
    if (paramsList == null) {
      throw new IllegalArgumentException("paramsList 不能为 null");
    }
    
    Class<?> clazz = data.getClass();
    StringBuilder sql = new StringBuilder();
    // 获取 @DbTable 注解
    if (clazz.isAnnotationPresent(DbMeta.DbTable.class)) {
      DbMeta.DbTable tableAnnotation = clazz.getAnnotation(DbMeta.DbTable.class);
      if (tableAnnotation == null) {
        throw new IllegalArgumentException(
            "Class " + clazz.getSimpleName() + " 的 @DbTable 注解信息无效。");
      }
      
      String tableName = tableAnnotation.name();

      String keyName = "";
      // 未指定更新的字段 则更新所有的字段
      List<String> allFields = new ArrayList<>();
      Object keyValue = null;
      for (Field field : clazz.getDeclaredFields()) {
        field.setAccessible(true);
        if (field.isAnnotationPresent(DbMeta.DbMasterKey.class)) {
          DbMeta.DbMasterKey masterKey = field.getAnnotation(DbMeta.DbMasterKey.class);
          if (masterKey != null) {
            keyName = masterKey.name();
          }
          keyValue = field.get(data);
        } else if (field.isAnnotationPresent(DbMeta.DbField.class)) {
          DbMeta.DbField dbField = field.getAnnotation(DbMeta.DbField.class);
          if (dbField != null) {
            allFields.add(dbField.name());
          }
        }
      }
      List<String> targetFields = resolveUpdateFields(data, fileds, allFields, keyName);
      if (targetFields.isEmpty()) {
        return "";
      }
      for (String fieldName : targetFields) {
        Field field = getFieldByDbName(clazz, fieldName);
        if (field == null) {
          throw new IllegalArgumentException("field not found for db column: " + fieldName);
        }
        field.setAccessible(true);
        paramsList.add(field.get(data));
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
      for (String field : targetFields) {
        if (field != null) {
          sql.append(" " + field + "=?,");
        }
      }
      if (sql.charAt(sql.length() - 1) == ',') {
        sql.deleteCharAt(sql.length() - 1);
      }
      sql.append(" WHERE  " + keyName + "=?");
      return sql.toString();
    } else {
      throw new IllegalArgumentException(
          "Class " + clazz.getSimpleName() + " is missing @DbTable annotation.");
    }
  }

  /**
   * 生成 INSERT SQL 和参数。
   * <p>
   * 自增主键不会插入，非自增主键会作为普通列写入；普通字段为 null 时跳过，交给数据库默认值。
   */
  private <T extends AbstractEntry> String getInsertSql(T data, List<Object> paramList)
      throws IllegalAccessException {
    if (data == null) {
      throw new IllegalArgumentException("data 不能为 null");
    }
    if (paramList == null) {
      throw new IllegalArgumentException("paramList 不能为 null");
    }
    
    StringBuilder sql = new StringBuilder();
    // 获取 @DbTable 注解
    Class<?> clazz = data.getClass();
    if (clazz.isAnnotationPresent(DbMeta.DbTable.class)) {
      DbMeta.DbTable tableAnnotation = clazz.getAnnotation(DbMeta.DbTable.class);
      if (tableAnnotation == null) {
        throw new IllegalArgumentException(
            "Class " + clazz.getSimpleName() + " 的 @DbTable 注解信息无效。");
      }
      
      String tableName = tableAnnotation.name();
      // 未指定更新的字段 则更新所有的字段
      List<String> allFields = new ArrayList<>();

      for (Field field : clazz.getDeclaredFields()) {
        field.setAccessible(true); // 允许访问私有字段
        if (field.isAnnotationPresent(DbMeta.DbMasterKey.class)) {
          DbMeta.DbMasterKey masterKey = field.getAnnotation(DbMeta.DbMasterKey.class);
          if (masterKey != null && !masterKey.autoIncrement()) {
            allFields.add(masterKey.name());
            paramList.add(field.get(data));
          }
        } else if (field.isAnnotationPresent(DbMeta.DbField.class)) {
          DbMeta.DbField dbField = field.getAnnotation(DbMeta.DbField.class);
          if (dbField != null) {
            Object value = field.get(data);
            if (value != null) {
              allFields.add(dbField.name());
              paramList.add(field.get(data));
            }
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
      if (allFields.isEmpty()) {
        logger.warn("尝试插入数据时没有找到有效的字段: {}", clazz.getSimpleName());
        return ""; // 返回空SQL，表示无有效字段可插入
      }
      String fieldStr = "(";
      String valStr = "(";
      for (String field : allFields) {
        if (field != null) {
          fieldStr += field + ",";
          valStr += " ?,";
        }
      }
      if (fieldStr.length() > 1) {
        fieldStr = fieldStr.substring(0, fieldStr.length() - 1);
        valStr = valStr.substring(0, valStr.length() - 1);
      }
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
    if (data == null) {
      logger.error("删除数据失败: data 不能为 null");
      return false;
    }
    
    if (mysqlConnector == null) {
      logger.error("MysqlConnector 未初始化，无法删除数据");
      return false;
    }
    
    // 获取 @DbTable 注解
    Class<?> clazz = data.getClass();
    String tableName = "";
    String keyName = "";
    Object keyValue = null;
    if (clazz.isAnnotationPresent(DbMeta.DbTable.class)) {
      DbMeta.DbTable tableAnnotation = clazz.getAnnotation(DbMeta.DbTable.class);
      if (tableAnnotation != null) {
        tableName = tableAnnotation.name();
        for (Field field : clazz.getDeclaredFields()) {
          field.setAccessible(true); // 允许访问私有字段
          if (field.isAnnotationPresent(DbMeta.DbMasterKey.class)) {
            try {
              keyValue = field.get(data);
              DbMeta.DbMasterKey masterKey = field.getAnnotation(DbMeta.DbMasterKey.class);
              if (masterKey != null) {
                keyName = masterKey.name();
              }
            } catch (IllegalAccessException e) {
              logger.error("删除数据时获取主键值失败: {}", data.getClass().getSimpleName(), e);
              return false;
            }
          }
        }
      }
    }
    if (StringUtil.isNullOrEmpty(tableName)
        || StringUtil.isNullOrEmpty(keyName)
        || keyValue == null) {
      logger.error("删除数据失败: 表名、主键名或主键值为空，tableName={}, keyName={}, keyValue={}", tableName, keyName, keyValue);
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

  private List<String> resolveUpdateFields(
      AbstractEntry data, String[] fileds, List<String> allFields, String keyName) {
    List<String> targetFields = new ArrayList<>();
    if (fileds != null && fileds.length > 0) {
      for (String field : fileds) {
        if (field != null && !field.isEmpty() && !field.equals(keyName)) {
          targetFields.add(field);
        }
      }
      return targetFields;
    }

    String[] dirtyFields = data.getDirtyFieldNames();
    if (dirtyFields.length > 0) {
      for (String field : dirtyFields) {
        if (field != null && !field.isEmpty() && !field.equals(keyName)) {
          targetFields.add(field);
        }
      }
      return targetFields;
    }

    for (String field : allFields) {
      if (field != null && !field.equals(keyName)) {
        targetFields.add(field);
      }
    }
    return targetFields;
  }

  private Field getFieldByDbName(Class<?> clazz, String dbFieldName) {
    for (Field field : clazz.getDeclaredFields()) {
      if (field.isAnnotationPresent(DbMeta.DbField.class)) {
        DbMeta.DbField dbField = field.getAnnotation(DbMeta.DbField.class);
        if (dbField != null && dbFieldName.equals(dbField.name())) {
          return field;
        }
      }
      if (field.isAnnotationPresent(DbMeta.DbMasterKey.class)) {
        DbMeta.DbMasterKey dbMasterKey = field.getAnnotation(DbMeta.DbMasterKey.class);
        if (dbMasterKey != null && dbFieldName.equals(dbMasterKey.name())) {
          return field;
        }
      }
    }
    return null;
  }

  private static Object convertFieldValue(Class<?> fieldType, Object value) throws Exception {
    if (value == null) {
      return null;
    }
    if (fieldType.isInstance(value)) {
      return value;
    }

    if (fieldType == String.class) {
      if (value instanceof Clob clob) {
        return clob.getSubString(1, (int) clob.length());
      }
      return String.valueOf(value);
    }

    if (fieldType == Boolean.class || fieldType == boolean.class) {
      if (value instanceof Boolean bool) {
        return bool;
      }
      if (value instanceof Number number) {
        return number.intValue() != 0;
      }
      String str = String.valueOf(value);
      return "1".equals(str) || "true".equalsIgnoreCase(str) || "y".equalsIgnoreCase(str);
    }

    if (fieldType == Byte.class || fieldType == byte.class) {
      return toNumber(value).byteValue();
    }
    if (fieldType == Short.class || fieldType == short.class) {
      return toNumber(value).shortValue();
    }
    if (fieldType == Integer.class || fieldType == int.class) {
      return toNumber(value).intValue();
    }
    if (fieldType == Long.class || fieldType == long.class) {
      return toNumber(value).longValue();
    }
    if (fieldType == Float.class || fieldType == float.class) {
      return toNumber(value).floatValue();
    }
    if (fieldType == Double.class || fieldType == double.class) {
      return toNumber(value).doubleValue();
    }
    if (fieldType == BigDecimal.class) {
      if (value instanceof BigDecimal decimal) {
        return decimal;
      }
      if (value instanceof BigInteger integer) {
        return new BigDecimal(integer);
      }
      return new BigDecimal(String.valueOf(value));
    }
    if (fieldType == BigInteger.class) {
      if (value instanceof BigInteger integer) {
        return integer;
      }
      if (value instanceof BigDecimal decimal) {
        return decimal.toBigInteger();
      }
      return new BigInteger(String.valueOf(value));
    }

    if (fieldType == LocalDate.class) {
      if (value instanceof LocalDate localDate) {
        return localDate;
      }
      if (value instanceof Date date) {
        return date.toLocalDate();
      }
      if (value instanceof Timestamp timestamp) {
        return timestamp.toLocalDateTime().toLocalDate();
      }
    }
    if (fieldType == LocalTime.class) {
      if (value instanceof LocalTime localTime) {
        return localTime;
      }
      if (value instanceof Time time) {
        return time.toLocalTime();
      }
      if (value instanceof Timestamp timestamp) {
        return timestamp.toLocalDateTime().toLocalTime();
      }
    }
    if (fieldType == LocalDateTime.class) {
      if (value instanceof LocalDateTime localDateTime) {
        return localDateTime;
      }
      if (value instanceof Timestamp timestamp) {
        return timestamp.toLocalDateTime();
      }
      if (value instanceof Date date) {
        return date.toLocalDate().atStartOfDay();
      }
    }

    if (fieldType == byte[].class) {
      if (value instanceof byte[] bytes) {
        return bytes;
      }
      if (value instanceof Blob blob) {
        return blob.getBytes(1, (int) blob.length());
      }
    }

    return value;
  }

  private static Number toNumber(Object value) {
    if (value instanceof Number number) {
      return number;
    }
    if (value instanceof Boolean bool) {
      return bool ? 1 : 0;
    }
    return new BigDecimal(String.valueOf(value));
  }

  private boolean hasNonAutoIncrementPrimaryKey(AbstractEntry entry) {
    for (Field field : entry.getClass().getDeclaredFields()) {
      if (!field.isAnnotationPresent(DbMeta.DbMasterKey.class)) {
        continue;
      }
      DbMeta.DbMasterKey dbMasterKey = field.getAnnotation(DbMeta.DbMasterKey.class);
      return dbMasterKey != null && !dbMasterKey.autoIncrement();
    }
    return false;
  }

  private void applyGeneratedKey(AbstractEntry entry, Number generatedKey)
      throws IllegalAccessException {
    if (generatedKey == null) {
      return;
    }
    for (Field field : entry.getClass().getDeclaredFields()) {
      if (!field.isAnnotationPresent(DbMeta.DbMasterKey.class)) {
        continue;
      }
      DbMeta.DbMasterKey dbMasterKey = field.getAnnotation(DbMeta.DbMasterKey.class);
      if (dbMasterKey == null || !dbMasterKey.autoIncrement()) {
        continue;
      }
      field.setAccessible(true);
      Class<?> fieldType = field.getType();
      if (fieldType == Integer.class || fieldType == int.class) {
        field.set(entry, generatedKey.intValue());
      } else if (fieldType == Long.class || fieldType == long.class) {
        field.set(entry, generatedKey.longValue());
      } else if (fieldType == Short.class || fieldType == short.class) {
        field.set(entry, generatedKey.shortValue());
      } else if (fieldType == Byte.class || fieldType == byte.class) {
        field.set(entry, generatedKey.byteValue());
      } else {
        field.set(entry, generatedKey);
      }
      return;
    }
  }

  public static void main(String[] args) {
    String jdbcUrl = "jdbc:mysql://118.25.76.117:3306/pick_money";
    String username = "root";
    String password = "Ly@2026Root!8899";
    getInstance().init(jdbcUrl, username, password, 0, 0, 0, 0);
    ShareEnumConfigEntry entry =
        getInstance().selectOnce(ShareEnumConfigEntry.class, new String[] {"name"}, "1231");
    ShareEnumConfigEntryHelper.getShareEnumConfigEntryById(1231);

    ShareEnumConfigEntry data = new ShareEnumConfigEntry();
    data.setCode("qqqqq");
    data.setName("wwwwwwww");
    data.setConfigDesc("ssssssss");
    //    getInstance().save(data);
    // entry = getInstance().selectOnce(ShareEnumConfigEntry.class, new String[] {"code"}, "qqqqq");
    data.setConfigDesc("dadaw");
    getInstance().save(data);
    data.setConfigDesc("43432");
    getInstance().update(data);
    getInstance().delete(data);
  }
}
