package ly.redis;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import ly.LoggerDef;
import ly.ServerContext;
import org.apache.logging.log4j.core.Logger;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/*
 * Author: liuYang
 * Date: 2025/4/11
 * File: RedisUtils
 *
 * 说明：Redis 工具类，封装了 Redisson 的常用操作。
 */
public class RedisUtils {
  static Logger logger = LoggerDef.DbLogger;
  static RedissonClient redissonClient;

  /** 初始化 Redis 客户端连接 */
  public static void init() {
    try {
      Config config = new Config();
      config
          .useSingleServer()
          .setAddress(
              String.format(
                  "redis://%s:%d",
                  ServerContext.serverConfig.redis.host, ServerContext.serverConfig.redis.port))
          .setPassword(ServerContext.serverConfig.redis.password);

      redissonClient = Redisson.create(config);
      logger.info(
          "[Redis] 初始化成功，连接到 {}:{}",
          ServerContext.serverConfig.redis.host,
          ServerContext.serverConfig.redis.port);
    } catch (Exception e) {
      logger.error("[Redis] 初始化失败", e);
    }
  }

  /** 设置一个 key 的值（无过期时间） */
  public static void set(String key, Object value) {
    long start = System.nanoTime();
    try {
      redissonClient.getBucket(key).set(value);
      long cost = System.nanoTime() - start;
      if (logger.isDebugEnabled()) {
        logger.debug("[Redis] SET key='{}' 耗时={}μs", key, cost / 1000);
      }
    } catch (Exception e) {
      logger.error("[Redis] SET 操作失败 key='{}', error={}", key, e.getMessage(), e);
    }
  }

  /** 获取 key 对应的值 */
  @SuppressWarnings("unchecked")
  public static <T> T get(String key) {
    long start = System.nanoTime();
    try {
      RBucket<Object> bucket = redissonClient.getBucket(key);
      T value = (T) bucket.get();
      long cost = System.nanoTime() - start;
      if (logger.isDebugEnabled()) {
        logger.debug("[Redis] GET key='{}' 耗时={}μs", key, cost / 1000);
      }
      return value;
    } catch (Exception e) {
      logger.error("[Redis] GET 操作失败 key='{}', error={}", key, e.getMessage(), e);
      return null;
    }
  }

  /** 删除 key */
  public static boolean del(String key) {
    long start = System.nanoTime();
    try {
      RBucket<Object> bucket = redissonClient.getBucket(key);
      boolean result = bucket.delete();
      long cost = System.nanoTime() - start;
      if (logger.isDebugEnabled()) {
        logger.debug("[Redis] DEL key='{}' 耗时={}μs", key, cost / 1000);
      }
      return result;
    } catch (Exception e) {
      logger.error("[Redis] DEL 操作失败 key='{}', error={}", key, e.getMessage(), e);
      return false;
    }
  }

  /** 判断 key 是否存在 */
  public static boolean exists(String key) {
    long start = System.nanoTime();
    try {
      boolean result = get(key) != null;
      long cost = System.nanoTime() - start;
      if (logger.isDebugEnabled()) {
        logger.debug("[Redis] exists key='{}' 耗时={}μs", key, cost / 1000);
      }
      return result;
    } catch (Exception e) {
      logger.error("[Redis] exists 操作失败 key='{}', error={}", key, e.getMessage(), e);
      return false;
    }
  }

  /** 设置 key 的过期时间（单位：秒） */
  public static void setExpire(String key, int seconds) {
    long start = System.nanoTime();
    try {
      boolean result = redissonClient.getBucket(key).expire(java.time.Duration.ofSeconds(seconds));
      long cost = System.nanoTime() - start;
      if (logger.isDebugEnabled()) {
        logger.debug(
            "[Redis] EXPIRE key='{}', seconds={}, 成功={}, 耗时={}μs",
            key,
            seconds,
            result,
            cost / 1000);
      }
    } catch (Exception e) {
      logger.error("[Redis] EXPIRE 操作失败 key='{}', error={}", key, e.getMessage(), e);
    }
  }

  /** 获取 key 剩余的 TTL（毫秒） */
  public static long getExpire(String key) {
    long start = System.nanoTime();
    try {
      long remainMs = redissonClient.getBucket(key).remainTimeToLive();
      long cost = System.nanoTime() - start;
      if (logger.isDebugEnabled()) {
        logger.debug("[Redis] TTL key='{}', 剩余毫秒={}, 耗时={}μs", key, remainMs, cost / 1000);
      }
      return remainMs;
    } catch (Exception e) {
      logger.error("[Redis] TTL 查询失败 key='{}', error={}", key, e.getMessage(), e);
      return -1;
    }
  }

  /** 原子自增 key 的值 */
  public static long incr(String key) {
    long start = System.nanoTime();
    try {
      long newValue = redissonClient.getAtomicLong(key).incrementAndGet();
      long cost = System.nanoTime() - start;
      if (logger.isDebugEnabled()) {
        logger.debug("[Redis] INCR key='{}', newValue={}, 耗时={}μs", key, newValue, cost / 1000);
      }
      return newValue;
    } catch (Exception e) {
      logger.error("[Redis] INCR 操作失败 key='{}', error={}", key, e.getMessage(), e);
      return -1;
    }
  }

  /** 原子自减 key 的值 */
  public static long decr(String key) {
    long start = System.nanoTime();
    try {
      long newValue = redissonClient.getAtomicLong(key).decrementAndGet();
      long cost = System.nanoTime() - start;
      if (logger.isDebugEnabled()) {
        logger.debug("[Redis] DECR key='{}', newValue={}, 耗时={}μs", key, newValue, cost / 1000);
      }
      return newValue;
    } catch (Exception e) {
      logger.error("[Redis] DECR 操作失败 key='{}', error={}", key, e.getMessage(), e);
      return -1;
    }
  }

  /** 原子减少指定值 */
  public static long decrBy(String key, long delta) {
    long start = System.nanoTime();
    try {
      long newValue = redissonClient.getAtomicLong(key).addAndGet(-delta);
      long cost = System.nanoTime() - start;
      if (logger.isDebugEnabled()) {
        logger.debug(
            "[Redis] DECRBY key='{}', delta={}, newValue={}, 耗时={}μs",
            key,
            delta,
            newValue,
            cost / 1000);
      }
      return newValue;
    } catch (Exception e) {
      logger.error(
          "[Redis] DECRBY 操作失败 key='{}', delta={}, error={}", key, delta, e.getMessage(), e);
      return -1;
    }
  }

  /** 获取分布式锁（指定超时时间） */
  public static boolean lock(String key, long timeout, TimeUnit unit) {
    long start = System.nanoTime();
    try {
      RLock lock = redissonClient.getLock(key);
      boolean isLocked = lock.tryLock(0, timeout, unit);
      long cost = System.nanoTime() - start;
      if (isLocked) {
        logger.info("[Redis] 获取锁成功 key='{}', 耗时={}μs", key, cost / 1000);
      } else {
        logger.warn("[Redis] 获取锁失败 key='{}', 耗时={}μs", key, cost / 1000);
      }
      return isLocked;
    } catch (Exception e) {
      logger.error("[Redis] 锁操作失败 key='{}', error={}", key, e.getMessage(), e);
      return false;
    }
  }

  /** 获取锁，默认 1 秒超时 */
  public static boolean lock(String key) {
    return lock(key, 1, TimeUnit.SECONDS);
  }

  /** 释放锁 */
  public static void unlock(String key) {
    long start = System.nanoTime();
    try {
      RLock lock = redissonClient.getLock(key);
      if (lock.isHeldByCurrentThread()) {
        lock.unlock();
        long cost = System.nanoTime() - start;
        logger.info("[Redis] 释放锁成功 key='{}', 耗时={}μs", key, cost / 1000);
      } else {
        logger.warn("[Redis] 当前线程未持有锁，无法释放锁 key='{}'", key);
      }
    } catch (Exception e) {
      logger.error("[Redis] 锁释放失败 key='{}', error={}", key, e.getMessage(), e);
    }
  }

  /** 使用分布式锁执行回调函数（带超时时间） */
  public static void lockWithCallBack(
      String key, long timeout, TimeUnit unit, Function<Boolean, Void> callback) {
    boolean locked = false;
    try {
      locked = lock(key, timeout, unit);
      if (locked) {
        callback.apply(true);
      } else {
        logger.warn("[Redis] 未获取到锁，key='{}'，callback 未执行", key);
        callback.apply(false);
      }
    } catch (Exception e) {
      logger.error("[Redis] 执行回调发生异常 key='{}'，error={}", key, e.getMessage(), e);
      callback.apply(false);
    } finally {
      if (locked) {
        unlock(key);
      }
    }
  }

  /** 使用分布式锁执行回调函数（默认超时时间 1 秒） */
  public static void lockWithCallBack(String key, Function<Boolean, Void> callback) {
    lockWithCallBack(key, 1, TimeUnit.SECONDS, callback);
  }

  // --- 列表操作 ---

  /** 向列表添加元素 */
  public static <T> void listAdd(String key, T value) {
    try {
      long start = System.currentTimeMillis();
      redissonClient.<T>getList(key).add(value);
      if (logger.isDebugEnabled()) {
        logger.debug("[Redis] listAdd key={} cost={}ms", key, System.currentTimeMillis() - start);
      }
    } catch (Exception e) {
      logger.error("[Redis] listAdd key={} error={}", key, e.getMessage(), e);
    }
  }

  /** 获取列表中所有元素 */
  public static <T> List<T> listGetAll(String key) {
    try {
      long start = System.currentTimeMillis();
      List<T> list = redissonClient.<T>getList(key).readAll();
      if (logger.isDebugEnabled()) {
        logger.debug(
            "[Redis] listGetAll key={} size={} cost={}ms",
            key,
            list.size(),
            System.currentTimeMillis() - start);
      }
      return list;
    } catch (Exception e) {
      logger.error("[Redis] listGetAll key={} error={}", key, e.getMessage(), e);
      return Collections.emptyList();
    }
  }

  /** 移除列表中的某个元素 */
  public static void listRemove(String key, Object value) {
    try {
      long start = System.currentTimeMillis();
      redissonClient.getList(key).remove(value);
      if (logger.isDebugEnabled()) {
        logger.debug(
            "[Redis] listRemove key={} cost={}ms", key, System.currentTimeMillis() - start);
      }
    } catch (Exception e) {
      logger.error("[Redis] listRemove key={} error={}", key, e.getMessage(), e);
    }
  }

  // --- Set 操作 ---

  /** 向 Set 添加元素 */
  public static <T> void setAdd(String key, T value) {
    try {
      long start = System.currentTimeMillis();
      redissonClient.<T>getSet(key).add(value);
      logger.info("[Redis] setAdd key={} cost={}ms", key, System.currentTimeMillis() - start);
    } catch (Exception e) {
      logger.error("[Redis] setAdd key={} error={}", key, e.getMessage(), e);
    }
  }

  /** 获取 Set 中所有元素 */
  public static <T> Set<T> setGetAll(String key) {
    try {
      long start = System.currentTimeMillis();
      Set<T> set = redissonClient.<T>getSet(key).readAll();
      if (logger.isDebugEnabled()) {
        logger.debug(
            "[Redis] setGetAll key={} size={} cost={}ms",
            key,
            set.size(),
            System.currentTimeMillis() - start);
      }
      return set;
    } catch (Exception e) {
      logger.error("[Redis] setGetAll key={} error={}", key, e.getMessage(), e);
      return Collections.emptySet();
    }
  }

  /** 从 Set 中移除元素 */
  public static void setRemove(String key, Object value) {
    try {
      long start = System.currentTimeMillis();
      redissonClient.getSet(key).remove(value);
      if (logger.isDebugEnabled()) {
        logger.debug("[Redis] setRemove key={} cost={}ms", key, System.currentTimeMillis() - start);
      }
    } catch (Exception e) {
      logger.error("[Redis] setRemove key={} error={}", key, e.getMessage(), e);
    }
  }

  // --- Map 操作 ---

  /** 向 Map 中放入一个 key-value */
  public static <K, V> void mapPut(String mapKey, K fieldKey, V value) {
    try {
      long start = System.currentTimeMillis();
      redissonClient.<K, V>getMap(mapKey).put(fieldKey, value);
      logger.info(
          "[Redis] mapPut {}[{}] cost={}ms", mapKey, fieldKey, System.currentTimeMillis() - start);
    } catch (Exception e) {
      logger.error("[Redis] mapPut {}[{}] error={}", mapKey, fieldKey, e.getMessage(), e);
    }
  }

  /** 从 Map 中获取一个 key 的值 */
  public static <K, V> V mapGet(String mapKey, K fieldKey) {
    try {
      long start = System.currentTimeMillis();
      V value = redissonClient.<K, V>getMap(mapKey).get(fieldKey);
      if (logger.isDebugEnabled()) {
        logger.debug(
            "[Redis] mapGet {}[{}] cost={}ms",
            mapKey,
            fieldKey,
            System.currentTimeMillis() - start);
      }
      return value;
    } catch (Exception e) {
      logger.error("[Redis] mapGet {}[{}] error={}", mapKey, fieldKey, e.getMessage(), e);
      return null;
    }
  }

  /** 从 Map 中移除一个 key */
  public static <K> void mapRemove(String mapKey, K fieldKey) {
    try {
      long start = System.currentTimeMillis();
      redissonClient.getMap(mapKey).remove(fieldKey);
      if (logger.isDebugEnabled()) {
        logger.debug(
            "[Redis] mapRemove {}[{}] cost={}ms",
            mapKey,
            fieldKey,
            System.currentTimeMillis() - start);
      }
    } catch (Exception e) {
      logger.error("[Redis] mapRemove {}[{}] error={}", mapKey, fieldKey, e.getMessage(), e);
    }
  }

  /** 获取整个 Map 的所有数据 */
  public static <K, V> Map<K, V> mapGetAll(String mapKey) {
    try {
      long start = System.currentTimeMillis();
      Map<K, V> result = redissonClient.<K, V>getMap(mapKey).readAllMap();
      if (logger.isDebugEnabled()) {
        logger.debug(
            "[Redis] mapGetAll {} size={} cost={}ms",
            mapKey,
            result.size(),
            System.currentTimeMillis() - start);
      }
      return result;
    } catch (Exception e) {
      logger.error("[Redis] mapGetAll {} error={}", mapKey, e.getMessage(), e);
      return Collections.emptyMap();
    }
  }
}
