package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivitypasscompensateConfigCheckerBase extends AbstractConfigChecker<ActivitypasscompensateConfig> {
  @Override
  public String getConfigFileName() {
    return "activitypasscompensate.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "scheDuling", "int"),
        new ConfigColumnMeta(2, "score", "int"),
        new ConfigColumnMeta(3, "closeReward", "int"),
        new ConfigColumnMeta(4, "closeRewardShow", "int"),
        new ConfigColumnMeta(5, "inherit1", "string"),
        new ConfigColumnMeta(6, "inherit2", "string"),
        new ConfigColumnMeta(7, "inherit3", "string"),
        new ConfigColumnMeta(8, "mail", "int"),
        new ConfigColumnMeta(9, "describe1", "string"),
        new ConfigColumnMeta(10, "describe2", "string"),
        new ConfigColumnMeta(11, "item", "int"),
        new ConfigColumnMeta(12, "picture", "int"),
        new ConfigColumnMeta(13, "pictureOffset", "string"),
        new ConfigColumnMeta(14, "pictureOffset2", "string"),
        new ConfigColumnMeta(15, "passaWard1", "int"),
        new ConfigColumnMeta(16, "passaWard2", "int"));
  }
}
