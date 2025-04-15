package ly.redis;

/*
 * Author: liuYang
 * Date: 2025/4/11
 * File: RedisKeys
 */
public enum RedisKeys {
  // login 相关
  LOCK_LOGIN_SELECT_GATE_KEY("LOCK_LOGIN_SELECT_GATE_KEY_%s", "登录的时候选择gateServer的分布式锁"),
  LOGIN_ACCOUNT_ID_KEY("LOGIN_ACCOUNT_ID", "玩家账号 id 映射key"),
  LOGIN_ACCOUNT_TOKEN_KEY("login_account_token", "玩家账号 token映射"),

  // player 相关
  MINI_PLAYER_KEY("mini_player", "玩家mini数据相关"),
  ;

  RedisKeys(String key, String desc) {
    this.key = key;
    this.desc = desc;
  }

  private String key;
  private String desc;

  public String getKey() {
    return key;
  }

  public String getDesc() {
    return desc;
  }

  public String getKey(Object... args) {
    String key = this.key;
    for (Object arg : args) {
      key += "_" + arg;
    }
    return key;
  }
}
