package ly.logic.rank;

import ly.utils.rank.AbstractRank;
import ly.utils.rank.RankConfig;
import ly.utils.rank.RankEntry;
import ly.utils.rank.RankType;

/** 当前游戏服维度的战力榜。 */
public class PowerRank extends AbstractRank {
  public static final String NAME_PREFIX = "power";

  private final String serverId;

  public PowerRank(String serverId) {
    // 战力榜第一版作为永久榜，最多保留 10000 名，不自动结算。
    super(RankConfig.permanent(10_000));
    this.serverId = serverId;
  }

  @Override
  public String getName() {
    return NAME_PREFIX + ":" + serverId;
  }

  @Override
  public RankType getRankType() {
    return RankType.POWER;
  }

  @Override
  protected Object[] getRankKeyParts() {
    return new Object[] {serverId};
  }

  @Override
  protected void settlePlayer(RankEntry entry) {
    // 永久战力榜默认不自动结算；后续赛季化或活动化时由具体榜实现发奖逻辑。
  }
}
