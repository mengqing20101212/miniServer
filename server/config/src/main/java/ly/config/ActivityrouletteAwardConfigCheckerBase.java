package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityrouletteAwardConfigCheckerBase extends AbstractConfigChecker<ActivityrouletteAwardConfig> {
  @Override
  public String getConfigFileName() {
    return "activityrouletteAward.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "scheDuling", "int"),
        new ConfigColumnMeta(2, "prizeId", "int"),
        new ConfigColumnMeta(3, "rewardShow", "string"),
        new ConfigColumnMeta(4, "drop", "int"),
        new ConfigColumnMeta(5, "rewardWeight", "int"),
        new ConfigColumnMeta(6, "rewardLevel", "int"),
        new ConfigColumnMeta(7, "highest", "int"));
  }
}
