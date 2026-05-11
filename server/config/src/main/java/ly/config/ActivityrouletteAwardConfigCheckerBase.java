package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityrouletteAwardConfigCheckerBase extends AbstractConfigChecker<ActivityrouletteAwardConfig> {
  @Override
  public String getConfigFileName() {
    return "activityrouletteAward.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "scheDuling", "INT"),
        new ConfigColumnMeta(2, "prizeId", "INT"),
        new ConfigColumnMeta(3, "rewardShow", "STRING"),
        new ConfigColumnMeta(4, "drop", "INT"),
        new ConfigColumnMeta(5, "rewardWeight", "INT"),
        new ConfigColumnMeta(6, "rewardLevel", "INT"),
        new ConfigColumnMeta(7, "highest", "INT"));
  }
}
