package ly.utils.rank;

/** 排行榜查询结果。 */
public class RankEntry {
  private final long playerId;
  private final int rank;
  private final long score;
  private final long scoreTimeMillis;

  public RankEntry(long playerId, int rank, long score, long scoreTimeMillis) {
    this.playerId = playerId;
    this.rank = rank;
    this.score = score;
    this.scoreTimeMillis = scoreTimeMillis;
  }

  public long getPlayerId() {
    return playerId;
  }

  public int getRank() {
    return rank;
  }

  public long getScore() {
    return score;
  }

  public long getScoreTimeMillis() {
    return scoreTimeMillis;
  }
}
