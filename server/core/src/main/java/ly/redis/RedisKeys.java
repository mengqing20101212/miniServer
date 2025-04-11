package ly.redis;

/*
 * Author: liuYang
 * Date: 2025/4/11
 * File: RedisKeys
 */
public enum RedisKeys {
  LOCK_LOGIN_KEY("LOGIN_LOCK_KEY_%s", "登录时候分布式锁");

  RedisKeys(String key, String desc) {
    this.key = key;
    this.desc = desc;
  }

  private String key;
  private String desc;

  public String getKey(Object... args) {
    String key = this.key;
    for (Object arg : args) {
      key += "_" + arg;
    }
    return key;
  }
}
