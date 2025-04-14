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

/*
 * Author: liuYang
 * Date: 2025/4/14
 * File: CacheService
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

  static Map<String, CacheService> cacheServiceMap = new ConcurrentHashMap<>();

  public static synchronized <T> CacheService getCacheService(Class<T> clazz) {
    String className = clazz.getName();
    if (cacheServiceMap.containsKey(className)) {
      return cacheServiceMap.get(className);
    }
    CacheService<T> service = new CacheService<>(clazz);
    cacheServiceMap.put(className, service);
    return service;
  }

  public static CacheService getStringCacheService() {
    return getCacheService(String.class);
  }

  public static CacheService getIntegerCacheService() {
    return getCacheService(Integer.class);
  }

  public static CacheService getMiniPlayerCacheService() {
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

  public String getKey(String... params) {
    return String.join(".", params);
  }
}
