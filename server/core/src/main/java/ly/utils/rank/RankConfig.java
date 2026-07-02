package ly.utils.rank;

/** 排行榜通用配置。 */
public class RankConfig {
  private final int maxSize;
  private final int settleSnapshotSize;
  private final boolean allowScoreDown;
  private final int maxFlushPerTick;
  private final long deleteAfterSettleMillis;

  public RankConfig(
      int maxSize,
      int settleSnapshotSize,
      boolean allowScoreDown,
      int maxFlushPerTick,
      long deleteAfterSettleMillis) {
    this.maxSize = Math.max(1, maxSize);
    this.settleSnapshotSize = Math.max(1, settleSnapshotSize);
    this.allowScoreDown = allowScoreDown;
    this.maxFlushPerTick = Math.max(1, maxFlushPerTick);
    this.deleteAfterSettleMillis = Math.max(0L, deleteAfterSettleMillis);
  }

  public static RankConfig permanent(int maxSize) {
    return new RankConfig(maxSize, maxSize, true, 1000, 0L);
  }

  public int getMaxSize() {
    return maxSize;
  }

  public int getSettleSnapshotSize() {
    return settleSnapshotSize;
  }

  public boolean isAllowScoreDown() {
    return allowScoreDown;
  }

  public int getMaxFlushPerTick() {
    return maxFlushPerTick;
  }

  public long getDeleteAfterSettleMillis() {
    return deleteAfterSettleMillis;
  }
}
