package ly.utils.rank;

/** 排行榜类型定义，统一维护 Redis key 的公共前缀和玩法编码。 */
public enum RankType {
  POWER("power", "战力榜"),
  ARENA("arena", "竞技场榜"),
  ACTIVITY_DAMAGE("activity_damage", "活动伤害榜");

  private static final String REDIS_KEY_PREFIX = "rank:zset";

  private final String code;
  private final String desc;

  RankType(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public String getKey(Object... parts) {
    StringBuilder key = new StringBuilder(REDIS_KEY_PREFIX).append(':').append(code);
    if (parts != null) {
      for (Object part : parts) {
        if (part == null) {
          continue;
        }
        key.append(':').append(part);
      }
    }
    return key.toString();
  }

  public String getCode() {
    return code;
  }

  public String getDesc() {
    return desc;
  }
}
