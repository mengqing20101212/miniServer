package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class BlockBattleChallengeRewardConfigCheckerBase extends AbstractConfigChecker<BlockBattleChallengeRewardConfig> {
  @Override
  public String getConfigFileName() {
    return "blockBattleChallengeReward.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "prestigeNum", "INT"),
        new ConfigColumnMeta(2, "des", "STRING"),
        new ConfigColumnMeta(3, "drop", "INT"),
        new ConfigColumnMeta(4, "dropShow", "INT"));
  }
}
