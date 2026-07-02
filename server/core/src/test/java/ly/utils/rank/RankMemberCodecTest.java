package ly.utils.rank;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RankMemberCodecTest {

  @Test
  public void shouldMakeEarlierTimeGreaterForReversedZSetTieBreak() {
    String earlier = RankMemberCodec.encode(1001L, 1_000L);
    String later = RankMemberCodec.encode(1002L, 2_000L);

    assertTrue(earlier.compareTo(later) > 0);
  }

  @Test
  public void shouldDecodePlayerIdAndSecondTimestamp() {
    String member = RankMemberCodec.encode(1001L, 1_234L);
    RankMemberCodec.DecodedMember decoded = RankMemberCodec.decode(member);

    assertEquals(1001L, decoded.playerId());
    assertEquals(1_000L, decoded.scoreTimeMillis());
  }

  @Test
  public void shouldAllowLargeBusinessScoreInRedisScore() {
    long score = 9_000_000_000_000_000L;

    assertEquals(score, RankScoreCodec.decode(RankScoreCodec.encode(score)));
  }
}
