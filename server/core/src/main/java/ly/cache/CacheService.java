package ly.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import ly.LoggerDef;
import ly.game.MiniPlayer;
import org.slf4j.Logger;

/**
 * 进程内轻量缓存服务。
 * <p>
 * 每种数据类型共享一个 {@link CacheService} 实例，底层使用 Caffeine。缓存只保证本进程
 * 内命中，不承担跨服一致性；跨服或可恢复数据应放 Redis/MySQL。
 */
public class CacheService<T> {
  Logger logger = LoggerDef.SystemLogger;
  Class<T> classType;
  public final Cache<String, T> CACHE =
      Caffeine.newBuilder()
          .expireAfterAccess(10, TimeUnit.MINUTES) // 30分钟未访问则清除
          .maximumSize(10000) // 最大1万玩家信息
          .build();

  public CacheService(Class<T> classType) {
    this.classType = classType;
  }

  static Map<String, CacheService<?>> cacheServiceMap = new ConcurrentHashMap<>();

  @SuppressWarnings("unchecked")
  public static synchronized <T> CacheService<T> getCacheService(Class<T> clazz) {
    String className = clazz.getName();
    if (cacheServiceMap.containsKey(className)) {
      return (CacheService<T>) cacheServiceMap.get(className);
    }
    CacheService<T> service = new CacheService<>(clazz);
    cacheServiceMap.put(className, service);
    return service;
  }

  public static CacheService<String> getStringCacheService() {
    return getCacheService(String.class);
  }

  public static CacheService<Integer> getIntegerCacheService() {
    return getCacheService(Integer.class);
  }

  public static CacheService<MiniPlayer> getMiniPlayerCacheService() {
    return getCacheService(MiniPlayer.class);
  }

  public T get(String... keys) {
    String key = getKey(keys);
    return CACHE.getIfPresent(key);
  }

  /**
   * 先从本地缓存中取该对象，未取到 则执行 supplier 函数 从指定的地方 加载
   *
   * @param supplier 需要加载的地方
   * @param keys key
   * @return
   */
  /**
   * 先读本地缓存，未命中时执行 supplier 加载并写回缓存。
   * <p>
   * 适合“读多写少、允许短时间不一致”的对象，例如 MiniPlayer 摘要。
   */
  public T getWithSupplier(Supplier<T> supplier, String... keys) {
    String key = getKey(keys);
    T value = CACHE.getIfPresent(key);
    if (value != null) {
      return value;
    }
    if (supplier != null) {
      value = supplier.get();
      if (value != null) {
        CACHE.put(key, value);
      }
    }
    return value;
  }

  public void put(T data, String... keys) {
    String key = getKey(keys);
    CACHE.put(key, data);
  }

  /** 使用点号拼接多段 key，保持调用方 key 规则一致。 */
  public String getKey(String... params) {
    return String.join(".", params);
  }
}
