package ly.utils.rank;

/** 排行榜异步更新任务。 */
public record RankUpdateTask(AbstractRank rank, RankUpdateType type, long playerId, long value) {
  public static RankUpdateTask set(AbstractRank rank, long playerId, long score) {
    return new RankUpdateTask(rank, RankUpdateType.SET, playerId, score);
  }

  public static RankUpdateTask add(AbstractRank rank, long playerId, long delta) {
    return new RankUpdateTask(rank, RankUpdateType.ADD, playerId, delta);
  }
}
