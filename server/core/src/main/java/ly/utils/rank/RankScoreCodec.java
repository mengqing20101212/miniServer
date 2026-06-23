package ly.utils.rank;

/** Redis ZSet 业务分数校验工具。 */
public final class RankScoreCodec {
  private static final long MAX_SAFE_REDIS_INTEGER_SCORE = 9_007_199_254_740_991L;

  private RankScoreCodec() {}

  public static double encode(long score) {
    if (score < 0) {
      throw new IllegalArgumentException("rank score must be >= 0");
    }
    if (score > MAX_SAFE_REDIS_INTEGER_SCORE) {
      throw new IllegalArgumentException("rank score is too large for Redis double, score=" + score);
    }
    return score;
  }

  public static long decode(double redisScore) {
    return (long) redisScore;
  }
}
