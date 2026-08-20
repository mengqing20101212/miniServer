package ly.db;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;

import com.alibaba.fastjson2.JSON;

import io.netty.util.internal.StringUtil;
import ly.LoggerDef;
import ly.ServerContext;
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
  // 异步入库队列必须限制容量；如果 DB 变慢或不可用，无界内存队列会把 DB 故障放大成 OOM。
  private static final int DATA_QUEUE_CAPACITY = 10_000;
  private static final int RETRY_QUEUE_CAPACITY = 50_000;
  private static final int MAX_RETRY_COUNT = 20;
  private static final long INITIAL_RETRY_DELAY_MILLIS = 5_000L;
  private static final long MAX_RETRY_DELAY_MILLIS = 5 * 60_000L;
  private static final long RETRY_WRITE_INTERVAL_MILLIS = 100L;
  private static final Path DEAD_LETTER_ROOT = Path.of("runlogs", "db-dead-letter");
  private static final String DEAD_LETTER_FILE_NAME = "db-write-failed.log";

  private final LinkedBlockingQueue<DbWriteTask> dataQueue = new LinkedBlockingQueue<>(DATA_QUEUE_CAPACITY);
  // DelayQueue 用于避免失败任务立刻空转重试；由于 DelayQueue 本身无界，retryQueueSize 负责提供逻辑容量上限。
  private final DelayQueue<DbWriteTask> retryQueue = new DelayQueue<>();
  private final AtomicInteger retryQueueSize = new AtomicInteger();
  private final AtomicBoolean asyncWorkersStarted = new AtomicBoolean();

  public static MysqlService getInstance() {
    return instance;
  }

  /**
   * 初始化 MySQL 连接池并启动异步保存任务。
   *
   * <p>
   * 连接池大小、空闲连接、空闲超时和连接超时参数传 0 时使用默认值。
   */
  public void init(
      String jdbcUrl,
      String username,
      String password,
      int maxPoolSize,
      int minIdle,
      int idleTimeout,
      int connectionTimeout) {
    mysqlConnector = new MysqlConnector(
        jdbcUrl, username, password, maxPoolSize, minIdle, idleTimeout, connectionTimeout);

    replayDeadLettersBeforeStartup();

    // 启动一个保存的协程
    startAsyncWriteWorkers();
  }

  private void startAsyncWriteWorkers() {
    if (!asyncWorkersStarted.compareAndSet(false, true)) {
      return;
    }
    startSaveThread();
    startRetryThread();
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
              while (!Thread.currentThread().isInterrupted()) {
                DbWriteTask entry = null;
                try {
                  entry = dataQueue.take();
                  if (!processWriteTask(entry)) {
                    scheduleRetry(entry, null);
                  }
                } catch (InterruptedException e) {
                  Thread.currentThread().interrupt();
                } catch (Exception e) {
                  scheduleRetry(entry, e);
                }
              }
            });
  }

  private void startRetryThread() {
    Thread.ofVirtual()
        .name("MysqlService-dbRetryVirtual")
        .start(
            () -> {
              long lastRetryWriteAt = 0L;
              while (!Thread.currentThread().isInterrupted()) {
                DbWriteTask task = null;
                try {
                  task = retryQueue.take();
                  retryQueueSize.decrementAndGet();
                  // 重试写入需要限频，避免 DB 恢复后大量失败任务同时冲击数据库。
                  long waitMillis = RETRY_WRITE_INTERVAL_MILLIS
                      - (System.currentTimeMillis() - lastRetryWriteAt);
                  if (waitMillis > 0) {
                    Thread.sleep(waitMillis);
                  }
                  lastRetryWriteAt = System.currentTimeMillis();
                  if (!processWriteTask(task)) {
                    scheduleRetry(task, null);
                  }
                } catch (InterruptedException e) {
                  Thread.currentThread().interrupt();
                } catch (Exception e) {
                  scheduleRetry(task, e);
                }
              }
            });
  }

  boolean processWriteTask(DbWriteTask task) {
    if (task == null || task.data == null) {
      return true;
    }
    if (task.type == SAVE_TYPE) {
      return save(task.data);
    }
    if (task.type == UPDATE_TYPE) {
      return update(task.data, task.fileds);
    }
    logger.error("unknown async db write type: {}", task.type);
    return true;
  }

  void scheduleRetry(DbWriteTask task, Exception error) {
    if (task == null) {
      return;
    }
    task.retryCount++;
    task.lastError = error != null ? error.toString() : "write returned false";

    // 失败任务不能一直留在内存里；达到最大重试次数后落到死信文件，便于后续排查或回放。
    if (task.retryCount > MAX_RETRY_COUNT) {
      writeDeadLetter(task, "max retry exceeded");
      return;
    }
    int queued = retryQueueSize.incrementAndGet();
    if (queued > RETRY_QUEUE_CAPACITY) {
      retryQueueSize.decrementAndGet();
      writeDeadLetter(task, "retry queue full");
      return;
    }
    task.nextRetryAt = System.currentTimeMillis() + calculateRetryDelayMillis(task.retryCount);
    retryQueue.offer(task);
  }

  long calculateRetryDelayMillis(int retryCount) {
    long delay = INITIAL_RETRY_DELAY_MILLIS;
    for (int i = 1; i < retryCount; i++) {
      if (delay >= MAX_RETRY_DELAY_MILLIS / 2) {
        return MAX_RETRY_DELAY_MILLIS;
      }
      delay *= 2;
    }
    return Math.min(delay, MAX_RETRY_DELAY_MILLIS);
  }

  void enqueueAsyncTask(DbWriteTask task) {
    if (task == null || task.data == null || !task.data.canSave()) {
      return;
    }
    try {
      // 短暂等待瞬时峰值消退；如果队列仍然满，则写入死信，避免业务线程被异步队列长期阻塞。
      if (!dataQueue.offer(task, 100, TimeUnit.MILLISECONDS)) {
        writeDeadLetter(task, "main queue full");
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      writeDeadLetter(task, "interrupted while enqueue");
    }
  }

  int asyncQueueSize() {
    return dataQueue.size();
  }

  int retryQueueSize() {
    return retryQueueSize.get();
  }

  int replayDeadLettersBeforeStartup() {
    return replayDeadLettersBeforeStartup(currentDeadLetterRoot());
  }

  /** 启动时同步重放当前服务器的历史死信；只要有一条失败，就直接中断启动。 */
  int replayDeadLettersBeforeStartup(Path root) {
    if (!Files.isDirectory(root)) {
      return 0;
    }
    int replayedCount = 0;
    try (var files = Files.walk(root, 2)) {
      for (Path file : files.filter(Files::isRegularFile).filter(this::canLoadDeadLetterFile).toList()) {
        replayedCount += replayDeadLetterFile(file);
      }
    } catch (Exception e) {
      LoggerDef.DeadLetterLogger.error("replay dead letter files failed, root={}", root, e);
      throw new IllegalStateException("启动失败：数据库死信任务未全部执行完成，root=" + root, e);
    }
    if (replayedCount > 0) {
      LoggerDef.DeadLetterLogger.warn("replayed async db dead letters before startup, count={}", replayedCount);
    }
    return replayedCount;
  }

  private boolean canLoadDeadLetterFile(Path file) {
    String fileName = file.getFileName().toString();
    return fileName.startsWith("db-write-failed") && fileName.endsWith(".log");
  }

  private int replayDeadLetterFile(Path file) throws Exception {
    // 先改名为 loading 文件，避免启动重放过程中又被当作新的死信文件写入或重复扫描。
    Path loadingFile = file.resolveSibling(file.getFileName() + ".loading." + System.currentTimeMillis() + ".log");
    Files.move(file, loadingFile, StandardCopyOption.REPLACE_EXISTING);

    int replayedCount = 0;
    boolean completed = false;
    try {
      List<String> lines = Files.readAllLines(loadingFile, StandardCharsets.UTF_8);
      for (int i = 0; i < lines.size(); i++) {
        String line = lines.get(i);
        if (line == null || line.isBlank()) {
          continue;
        }
        DeadLetterRecord record;
        try {
          record = JSON.parseObject(line, DeadLetterRecord.class);
        } catch (Exception e) {
          preserveUnfinishedDeadLetterLines(loadingFile, lines.subList(i, lines.size()));
          completed = true;
          throw new IllegalStateException("死信任务解析失败，file=" + loadingFile, e);
        }
        DbWriteTask task = DbWriteTask.from(record);
        if (task == null || task.data == null) {
          preserveUnfinishedDeadLetterLines(loadingFile, lines.subList(i, lines.size()));
          completed = true;
          throw new IllegalStateException("死信任务反序列化失败，file=" + loadingFile);
        }
        if (!processWriteTask(task)) {
          preserveUnfinishedDeadLetterLines(loadingFile, lines.subList(i, lines.size()));
          completed = true;
          throw new IllegalStateException("死信任务执行失败，file=" + loadingFile);
        }
        replayedCount++;
      }
      completed = true;
      return replayedCount;
    } finally {
      if (completed) {
        archiveLoadedDeadLetterFile(loadingFile);
      }
    }
  }

  private void preserveUnfinishedDeadLetterLines(Path loadingFile, List<String> lines) throws Exception {
    if (lines == null || lines.isEmpty()) {
      return;
    }
    Path currentFile = loadingFile.getParent().resolve(DEAD_LETTER_FILE_NAME);
    Files.write(
        currentFile,
        lines,
        StandardCharsets.UTF_8,
        StandardOpenOption.CREATE,
        StandardOpenOption.APPEND);
  }

  private void archiveLoadedDeadLetterFile(Path loadingFile) {
    try {
      // 全部执行完成后归档到 loaded 目录，避免服务下次启动重复回放同一批死信。
      Path loadedDir = loadingFile.getParent().resolve("loaded");
      Files.createDirectories(loadedDir);
      Files.move(
          loadingFile,
          loadedDir.resolve(loadingFile.getFileName() + ".done"),
          StandardCopyOption.REPLACE_EXISTING);
    } catch (Exception e) {
      LoggerDef.DeadLetterLogger.error("archive loaded dead letter file error, file={}", loadingFile, e);
    }
  }

  void writeDeadLetter(DbWriteTask task, String reason) {
    try {
      // 死信文件使用追加写 JSONL；同时保存序列化后的 Entry，避免回放工具依赖有损的 toString 输出。
      Path dir = currentDeadLetterRoot().resolve(
          LocalDate.now().format(java.time.format.DateTimeFormatter.BASIC_ISO_DATE));
      Files.createDirectories(dir);
      Path file = dir.resolve(DEAD_LETTER_FILE_NAME);
      String line = JSON.toJSONString(DeadLetterRecord.from(task, reason)) + System.lineSeparator();
      Files.writeString(
          file,
          line,
          StandardCharsets.UTF_8,
          StandardOpenOption.CREATE,
          StandardOpenOption.APPEND);
      LoggerDef.DeadLetterLogger.error(
          "async db write moved to dead letter, reason={}, entry={}, retryCount={}",
          reason,
          task.data != null ? task.data.getClass().getSimpleName() : "unknown",
          task.retryCount);
    } catch (Exception e) {
      String entryName = task != null && task.data != null ? task.data.getClass().getSimpleName() : "unknown";
      LoggerDef.DeadLetterLogger.error(
          "failed to write async db dead letter, entry={}, reason={}", entryName, reason, e);
    }
  }

  Path currentDeadLetterRoot() {
    String env = safePathSegment(ServerContext.ENV, "unknown-env");
    String serverType = ServerContext.serverType != null
        ? safePathSegment(ServerContext.serverType.getType(), "unknown-type")
        : "unknown-type";
    String serverId = safePathSegment(ServerContext.getServerId(), "unknown-server");
    return DEAD_LETTER_ROOT.resolve(env).resolve(serverType).resolve(serverId);
  }

  private static String safePathSegment(String value, String defaultValue) {
    if (value == null || value.isBlank()) {
      return defaultValue;
    }
    return value.replaceAll("[^a-zA-Z0-9._-]", "_");
  }

  private static String serializeEntry(AbstractEntry entry) {
    if (entry == null) {
      return "";
    }
    try (ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bytes)) {
      out.writeObject(entry);
      out.flush();
      return Base64.getEncoder().encodeToString(bytes.toByteArray());
    } catch (Exception e) {
      return "";
    }
  }

  private static AbstractEntry deserializeEntry(String serializedEntry) {
    if (serializedEntry == null || serializedEntry.isBlank()) {
      return null;
    }
    try (ByteArrayInputStream bytes = new ByteArrayInputStream(Base64.getDecoder().decode(serializedEntry));
        ObjectInputStream in = new ObjectInputStream(bytes)) {
      Object data = in.readObject();
      return data instanceof AbstractEntry entry ? entry : null;
    } catch (Exception e) {
      return null;
    }
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
    enqueueAsyncTask(new DbWriteTask(SAVE_TYPE, entry));
  }

  /** 异步更新，字段参数为空时由实体脏字段决定 UPDATE 列。 */
  public void addUpdateEntry(AbstractEntry entry, String... fileds) {
    enqueueAsyncTask(new DbWriteTask(UPDATE_TYPE, entry, fileds));
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
   * 严格查询实体列表。
   *
   * <p>查询条件仍然只接受实体注解中声明的数据库列名。与 {@link #selectAll(Class, String[], Object...)}
   * 不同，本方法不会把数据库异常降级为空集合，适合玩家主数据、场景恢复数据等不能混淆“无数据”和
   * “数据库不可用”的读取链路。
   *
   * @param clazz 实体类型，必须带 {@link DbMeta.DbTable}
   * @param fields 等值查询列名，按顺序与 {@code params} 一一对应
   * @param params 等值查询参数
   * @return 已完成 JDBC 类型转换并标记为已持久化的实体列表
   * @throws IllegalStateException 数据库查询失败或结果无法转换为实体
   */
  public <T extends AbstractEntry> List<T> selectAllStrict(
      Class<T> clazz, String[] fields, Object... params) {
    validateQueryArguments(clazz, fields, params);
    MysqlConnector connector = getMysqlConnector();
    List<Map<String, Object>> rows = connector.selectStrict(getSelectSql(clazz, fields), params);
    return packetEntriesStrict(rows, clazz);
  }

  /**
   * 使用游标字段执行严格的升序分页实体查询。
   *
   * <p>该接口用于启动恢复等大表扫描：先应用等值条件，再查询 {@code cursorField > afterCursor}，
   * 最后按游标升序并限制返回数量。它不会使用 OFFSET，因此翻页成本不会随着页码增长。
   *
   * @param clazz 实体类型
   * @param equalityFields 等值查询列，例如 {@code scene_id}、{@code deleted}
   * @param equalityParams 与等值查询列一一对应的参数
   * @param cursorField 单调递增的游标列，例如 {@code player_id}
   * @param afterCursor 本页不包含的上一页末尾游标
   * @param limit 本页最大实体数量
   * @return 按游标升序排列的实体列表
   */
  public <T extends AbstractEntry> List<T> selectPageAfterStrict(
      Class<T> clazz,
      String[] equalityFields,
      Object[] equalityParams,
      String cursorField,
      Object afterCursor,
      int limit) {
    validateQueryArguments(clazz, equalityFields, equalityParams);
    requireEntityColumn(clazz, cursorField);
    if (afterCursor == null) {
      throw new IllegalArgumentException("afterCursor 不能为 null");
    }
    if (limit <= 0) {
      throw new IllegalArgumentException("limit 必须大于 0");
    }

    Object[] safeEqualityParams = equalityParams == null ? new Object[0] : equalityParams;
    Object[] params = new Object[safeEqualityParams.length + 2];
    System.arraycopy(safeEqualityParams, 0, params, 0, safeEqualityParams.length);
    params[safeEqualityParams.length] = afterCursor;
    params[safeEqualityParams.length + 1] = limit;

    String sql = getSelectPageAfterSql(clazz, equalityFields, cursorField);
    List<Map<String, Object>> rows = getMysqlConnector().selectStrict(sql, params);
    return packetEntriesStrict(rows, clazz);
  }

  /**
   * 按实体唯一键执行版本化 UPSERT。
   *
   * <p>插入列、唯一键和版本列全部取自实体注解，业务层不需要也不允许拼接 SQL。唯一键冲突时，
   * 只有传入实体的 {@code revisionField} 大于数据库当前值才更新其他字段；旧版本或重复版本影响
   * 0 行也属于幂等成功。数据库异常会直接抛出，供上层异步落库队列重试或写入死信。
   *
   * @param entry 要保存的完整实体快照
   * @param revisionField 用于判断新旧的数据库列名
   * @return SQL 成功执行时始终返回 true，包括旧版本被数据库忽略的情况
   */
  public boolean upsertIfNewer(AbstractEntry entry, String revisionField) {
    if (entry == null) {
      throw new IllegalArgumentException("entry 不能为 null");
    }
    List<Object> params = new ArrayList<>();
    String sql;
    try {
      sql = getRevisionUpsertSql(entry, revisionField, params);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("读取实体字段失败: " + entry.getClass().getSimpleName(), e);
    }

    getMysqlConnector().executeUpdateStrict(sql, params.toArray());
    entry.markPersisted();
    return true;
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

  /** 生成实体游标分页 SQL；保持包可见，便于不连接数据库的 SQL 结构测试。 */
  <T extends AbstractEntry> String getSelectPageAfterSql(
      Class<T> clazz, String[] equalityFields, String cursorField) {
    String tableName = requireTableName(clazz);
    requireEntityColumn(clazz, cursorField);
    StringBuilder sql = new StringBuilder("SELECT * FROM ")
        .append(quoteIdentifier(tableName))
        .append(" WHERE ");
    if (equalityFields != null) {
      for (String field : equalityFields) {
        requireEntityColumn(clazz, field);
        sql.append(quoteIdentifier(field)).append("=? AND ");
      }
    }
    sql.append(quoteIdentifier(cursorField)).append(">?")
        .append(" ORDER BY ").append(quoteIdentifier(cursorField)).append(" ASC")
        .append(" LIMIT ?");
    return sql.toString();
  }

  /** 生成基于唯一键和版本列的实体 UPSERT SQL。 */
  String getRevisionUpsertSql(
      AbstractEntry entry, String revisionField, List<Object> paramList)
      throws IllegalAccessException {
    if (entry == null) {
      throw new IllegalArgumentException("entry 不能为 null");
    }
    if (paramList == null) {
      throw new IllegalArgumentException("paramList 不能为 null");
    }

    Class<?> clazz = entry.getClass();
    DbMeta.DbTable table = clazz.getAnnotation(DbMeta.DbTable.class);
    String tableName = requireTableName(clazz);
    Field revisionJavaField = requireEntityColumn(clazz, revisionField);
    revisionJavaField.setAccessible(true);
    Object revisionValue = revisionJavaField.get(entry);
    if (!(revisionValue instanceof Number)) {
      throw new IllegalArgumentException("revision 字段必须是非空数字: " + revisionField);
    }

    Set<String> conflictKeyColumns = resolveUniqueKeyColumns(clazz, table);
    List<String> insertColumns = new ArrayList<>();
    String masterKeyColumn = "";
    for (Field field : clazz.getDeclaredFields()) {
      field.setAccessible(true);
      if (field.isAnnotationPresent(DbMeta.DbMasterKey.class)) {
        DbMeta.DbMasterKey masterKey = field.getAnnotation(DbMeta.DbMasterKey.class);
        masterKeyColumn = masterKey.name();
        if (masterKey.autoIncrement()) {
          continue;
        }
        insertColumns.add(masterKey.name());
        paramList.add(field.get(entry));
      } else if (field.isAnnotationPresent(DbMeta.DbField.class)) {
        String columnName = field.getAnnotation(DbMeta.DbField.class).name();
        insertColumns.add(columnName);
        paramList.add(field.get(entry));
      }
    }
    if (!insertColumns.contains(revisionField)) {
      throw new IllegalArgumentException("revision 字段未参与实体持久化: " + revisionField);
    }

    List<String> updateColumns = new ArrayList<>();
    for (String column : insertColumns) {
      if (!column.equals(masterKeyColumn)
          && !column.equals(revisionField)
          && !conflictKeyColumns.contains(column)) {
        updateColumns.add(column);
      }
    }

    StringBuilder sql = new StringBuilder("INSERT INTO ")
        .append(quoteIdentifier(tableName)).append(" (");
    appendQuotedColumns(sql, insertColumns);
    sql.append(") VALUES (");
    for (int i = 0; i < insertColumns.size(); i++) {
      if (i > 0) {
        sql.append(", ");
      }
      sql.append('?');
    }
    sql.append(") ON DUPLICATE KEY UPDATE ");

    for (String column : updateColumns) {
      sql.append(quoteIdentifier(column)).append("=IF(VALUES(")
          .append(quoteIdentifier(revisionField)).append(")>")
          .append(quoteIdentifier(revisionField)).append(",VALUES(")
          .append(quoteIdentifier(column)).append("),")
          .append(quoteIdentifier(column)).append("),");
    }
    sql.append(quoteIdentifier(revisionField)).append("=GREATEST(")
        .append(quoteIdentifier(revisionField)).append(",VALUES(")
        .append(quoteIdentifier(revisionField)).append("))");
    return sql.toString();
  }

  /**
   * 生成 update SQL
   *
   * @param data       需要更新的对象
   * @param paramsList 参数列表
   * @param fileds     需要更新的字段列表，为空 则更新所有字段
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
    while ((!dataQueue.isEmpty() || retryQueueSize.get() > 0) && maxSleepTime > 0) {
      try {
        Thread.sleep(10);
        maxSleepTime -= 10;
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new RuntimeException(e);
      }
    }
    if (mysqlConnector != null) {
      mysqlConnector.shutdown();
    }
    logger.info(
        "MysqlService shutdown , pending={}, retryPending={}",
        dataQueue.size(),
        retryQueueSize.get());
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

  static class DbWriteTask implements Delayed {
    int type;
    AbstractEntry data;
    String[] fileds;
    int retryCount;
    long createdAt;
    long nextRetryAt;
    String lastError;

    // nextRetryAt 仅供 DelayQueue 判断可重试时间；普通写入队列复用同一个任务对象但不会读取它。
    public DbWriteTask(int type, AbstractEntry data) {
      this.type = type;
      this.data = data;
      this.createdAt = System.currentTimeMillis();
      this.nextRetryAt = this.createdAt;
    }

    public DbWriteTask(int type, AbstractEntry data, String[] fileds) {
      this(type, data);
      this.fileds = fileds;
    }

    static DbWriteTask from(DeadLetterRecord record) {
      if (record == null) {
        return null;
      }
      AbstractEntry entry = deserializeEntry(record.serializedEntry);
      if (entry == null) {
        return null;
      }
      DbWriteTask task = new DbWriteTask(record.type, entry, record.fields);
      task.lastError = record.lastError;
      return task;
    }

    @Override
    public long getDelay(TimeUnit unit) {
      return unit.convert(nextRetryAt - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed other) {
      if (other == this) {
        return 0;
      }
      long diff = getDelay(TimeUnit.MILLISECONDS) - other.getDelay(TimeUnit.MILLISECONDS);
      return diff == 0 ? 0 : (diff < 0 ? -1 : 1);
    }
  }

  static class DeadLetterRecord {
    public int type;
    public String typeName;
    public String className;
    public String[] fields;
    public int retryCount;
    public long createdAt;
    public long failedAt;
    public String reason;
    public String lastError;
    public String serializedEntry;

    static DeadLetterRecord from(DbWriteTask task, String reason) {
      DeadLetterRecord record = new DeadLetterRecord();
      record.type = task.type;
      record.typeName = task.type == SAVE_TYPE ? "SAVE" : task.type == UPDATE_TYPE ? "UPDATE" : "UNKNOWN";
      record.className = task.data != null ? task.data.getClass().getName() : "";
      record.fields = task.fileds;
      record.retryCount = task.retryCount;
      record.createdAt = task.createdAt;
      record.failedAt = System.currentTimeMillis();
      record.reason = reason;
      record.lastError = task.lastError;
      record.serializedEntry = serializeEntry(task.data);
      return record;
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

  /** 校验等值查询参数和实体列，阻止业务传入任意 SQL 片段充当列名。 */
  private <T extends AbstractEntry> void validateQueryArguments(
      Class<T> clazz, String[] fields, Object[] params) {
    if (clazz == null) {
      throw new IllegalArgumentException("clazz 不能为 null");
    }
    int fieldCount = fields == null ? 0 : fields.length;
    int paramCount = params == null ? 0 : params.length;
    if (fieldCount != paramCount) {
      throw new IllegalArgumentException(
          "查询列数量和参数数量不一致: fields=" + fieldCount + ", params=" + paramCount);
    }
    requireTableName(clazz);
    if (fields != null) {
      for (String field : fields) {
        requireEntityColumn(clazz, field);
      }
    }
  }

  /** 把严格查询结果转换为实体；任意一行转换失败都会中止整次恢复。 */
  private <T extends AbstractEntry> List<T> packetEntriesStrict(
      List<Map<String, Object>> rows, Class<T> clazz) {
    if (rows == null) {
      throw new IllegalStateException("严格实体查询返回 null: " + clazz.getSimpleName());
    }
    List<T> entries = new ArrayList<>(rows.size());
    for (Map<String, Object> row : rows) {
      T entry = packetEntry(row, clazz);
      if (entry == null) {
        throw new IllegalStateException(
            "数据库行无法转换为实体: clazz=" + clazz.getSimpleName() + ", row=" + row);
      }
      entries.add(entry);
    }
    return List.copyOf(entries);
  }

  /** 读取实体表名并校验为单一数据库标识符。 */
  private String requireTableName(Class<?> clazz) {
    if (clazz == null) {
      throw new IllegalArgumentException("clazz 不能为 null");
    }
    DbMeta.DbTable table = clazz.getAnnotation(DbMeta.DbTable.class);
    if (table == null || table.name() == null || table.name().isBlank()) {
      throw new IllegalArgumentException(
          "Class " + clazz.getSimpleName() + " is missing a valid @DbTable name.");
    }
    quoteIdentifier(table.name());
    return table.name();
  }

  /** 查找实体中声明的数据库列，不允许调用方注入表达式、排序语句或其他 SQL 片段。 */
  private Field requireEntityColumn(Class<?> clazz, String columnName) {
    if (columnName == null || columnName.isBlank()) {
      throw new IllegalArgumentException("数据库列名不能为空");
    }
    quoteIdentifier(columnName);
    Field field = getFieldByDbName(clazz, columnName);
    if (field == null) {
      throw new IllegalArgumentException(
          "实体 " + clazz.getSimpleName() + " 未声明数据库列: " + columnName);
    }
    return field;
  }

  /**
   * 展开实体的复合唯一键列。
   *
   * <p>版本化 UPSERT 必须依赖明确的业务唯一键；没有唯一键时退化为 INSERT 会破坏幂等语义，
   * 因此这里直接拒绝执行。
   */
  private Set<String> resolveUniqueKeyColumns(Class<?> clazz, DbMeta.DbTable table) {
    Set<String> columns = new HashSet<>();
    if (table != null && table.uniqueKeys() != null) {
      for (String uniqueKey : table.uniqueKeys()) {
        if (uniqueKey == null || uniqueKey.isBlank()) {
          continue;
        }
        for (String rawColumn : uniqueKey.split(",")) {
          String column = rawColumn.trim();
          requireEntityColumn(clazz, column);
          columns.add(column);
        }
      }
    }
    if (columns.isEmpty()) {
      throw new IllegalArgumentException(
          "版本化 UPSERT 要求实体声明 @DbTable.uniqueKeys: " + clazz.getSimpleName());
    }
    return columns;
  }

  /** 追加由实体元数据产生的带反引号列名列表。 */
  private void appendQuotedColumns(StringBuilder sql, List<String> columns) {
    for (int i = 0; i < columns.size(); i++) {
      if (i > 0) {
        sql.append(", ");
      }
      sql.append(quoteIdentifier(columns.get(i)));
    }
  }

  /** 只接受简单标识符，再增加 MySQL 反引号，避免元数据错误形成可执行 SQL 片段。 */
  private String quoteIdentifier(String identifier) {
    if (identifier == null || !identifier.matches("[A-Za-z_][A-Za-z0-9_]*")) {
      throw new IllegalArgumentException("非法数据库标识符: " + identifier);
    }
    return '`' + identifier + '`';
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
    ShareEnumConfigEntry entry = getInstance().selectOnce(ShareEnumConfigEntry.class, new String[] { "name" }, "1231");
    ShareEnumConfigEntryHelper.getShareEnumConfigEntryById(1231);

    ShareEnumConfigEntry data = new ShareEnumConfigEntry();
    data.setCode("qqqqq");
    data.setName("wwwwwwww");
    data.setConfigDesc("ssssssss");
    // getInstance().save(data);
    // entry = getInstance().selectOnce(ShareEnumConfigEntry.class, new String[]
    // {"code"}, "qqqqq");
    data.setConfigDesc("dadaw");
    getInstance().save(data);
    data.setConfigDesc("43432");
    getInstance().update(data);
    getInstance().delete(data);
  }
}
