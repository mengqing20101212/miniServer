package ly.utils.rank;

/**
 * 排行榜 ZSet member 编码，负责在同分时按先到达时间排序。
 *
 * <p>Redis ZSet 排序规则是：先按 score 排序；score 相同再按 member 字典序排序。
 * 本模块查询榜单使用反向顺序，因此把时间编码成 {@code Long.MAX_VALUE - seconds}：
 * 越早达到分数，seconds 越小，编码后的 member 越大，反向查询时自然排在前面。
 */
public final class RankMemberCodec {
  private RankMemberCodec() {}

  public static String encode(long playerId, long scoreTimeMillis) {
    long scoreTimeSeconds = Math.max(0L, scoreTimeMillis / 1000L);
    long inverseSeconds = Long.MAX_VALUE - scoreTimeSeconds;
    // 固定宽度保证字符串字典序等价于数值大小；playerId 追加在后面作为同秒兜底排序。
    return String.format("%019d:%020d", inverseSeconds, playerId);
  }

  public static DecodedMember decode(String member) {
    if (member == null || member.isBlank()) {
      throw new IllegalArgumentException("rank member is blank");
    }
    String[] parts = member.split(":", 2);
    if (parts.length != 2) {
      throw new IllegalArgumentException("invalid rank member: " + member);
    }
    long inverseSeconds = Long.parseLong(parts[0]);
    long playerId = Long.parseLong(parts[1]);
    long scoreTimeSeconds = Long.MAX_VALUE - inverseSeconds;
    return new DecodedMember(playerId, scoreTimeSeconds * 1000L);
  }

  public record DecodedMember(long playerId, long scoreTimeMillis) {}
}
