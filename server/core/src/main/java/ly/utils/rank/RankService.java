package ly.utils.rank;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import ly.LoggerDef;
import org.slf4j.Logger;

/** 排行榜实例管理和异步更新调度服务。 */
public final class RankService {
  private static final Logger logger = LoggerDef.SystemLogger;
  // 队列必须有上限；Redis 异常或业务刷榜过快时，不能让无界队列把服务器内存打满。
  private static final int UPDATE_QUEUE_CAPACITY = 100_000;
  private static final Map<String, AbstractRank> ranks = new ConcurrentHashMap<>();
  private static final ArrayBlockingQueue<RankUpdateTask> updateQueue =
      new ArrayBlockingQueue<>(UPDATE_QUEUE_CAPACITY);

  private RankService() {}

  public static void register(AbstractRank rank) {
    if (rank == null) {
      throw new IllegalArgumentException("rank is null");
    }
    ranks.put(rank.getName(), rank);
  }

  @SuppressWarnings("unchecked")
  public static <T extends AbstractRank> T get(String name, Class<T> type) {
    AbstractRank rank = ranks.get(name);
    if (rank == null) {
      return null;
    }
    if (!type.isInstance(rank)) {
      throw new IllegalArgumentException(
          "rank type mismatch, name=" + name + ", expected=" + type.getName());
    }
    return (T) rank;
  }

  public static boolean submit(RankUpdateTask task) {
    boolean offered = updateQueue.offer(task);
    if (!offered) {
      // 队列满说明线上容量或调用频率需要调整，这里只打 error，不做无限重试兜底。
      logger.error(
          "rank update queue full, rank={}, playerId={}, type={}",
          task.rank().getName(),
          task.playerId(),
          task.type());
    }
    return offered;
  }

  /**
   * 由服务器主循环或独立轻量线程定期调用。
   *
   * <p>本方法只处理有限数量的队列任务，并触发结算/删除协程，不在当前线程执行重逻辑。
   */
  public static void tick() {
    flushUpdateQueue();
    long nowMillis = System.currentTimeMillis();
    for (AbstractRank rank : ranks.values()) {
      rank.tryStartSettle(nowMillis);
      rank.tryStartDelete(nowMillis);
    }
  }

  private static void flushUpdateQueue() {
    int maxFlush = maxFlushPerTick();
    for (int i = 0; i < maxFlush; i++) {
      RankUpdateTask task = updateQueue.poll();
      if (task == null) {
        return;
      }
      try {
        task.rank().executeTask(task);
      } catch (Exception e) {
        logger.error(
            "rank async update failed, rank={}, playerId={}, type={}",
            task.rank().getName(),
            task.playerId(),
            task.type(),
            e);
      }
    }
  }

  private static int maxFlushPerTick() {
    int max = 1;
    for (AbstractRank rank : ranks.values()) {
      // 多个榜共用一个队列时，取所有榜里最大的单 tick 刷新额度。
      max = Math.max(max, rank.getConfig().getMaxFlushPerTick());
    }
    return max;
  }
}
