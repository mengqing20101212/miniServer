package ly.db;

import java.util.concurrent.LinkedBlockingQueue;
import ly.LoggerDef;
import ly.db.entry.ShareEnumConfigEntry;
import ly.db.entry.ShareEnumConfigHelper;
import org.apache.logging.log4j.Logger;

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
  private LinkedBlockingQueue<AbstractEntry> dataQueue = new LinkedBlockingQueue<>();

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
              AbstractEntry entry = null;
              try {
                while ((entry = dataQueue.poll()) != null) {
                  try {
                    save(entry);
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
    dataQueue.add(entry);
  }

  public <T extends AbstractEntry> T selectOnce(Class<T> clazz, String[] fileds, Object... params) {
    return (T) dataQueue.peek();
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

  public static void main(String[] args) {
    ShareEnumConfigEntry entry =
        getInstance().selectOnce(ShareEnumConfigEntry.class, new String[] {"name"}, "1231");
    ShareEnumConfigHelper.getShareEnumConfigEntryById(1231);
  }
}
