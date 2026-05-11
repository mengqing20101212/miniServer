package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivitypasscompensateConfigCheckerBase extends AbstractConfigChecker<ActivitypasscompensateConfig> {
  @Override
  public String getConfigFileName() {
    return "activitypasscompensate.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "scheDuling", "INT"),
        new ConfigColumnMeta(2, "score", "INT"),
        new ConfigColumnMeta(3, "closeReward", "INT"),
        new ConfigColumnMeta(4, "closeRewardShow", "INT"),
        new ConfigColumnMeta(5, "inherit1", "STRING"),
        new ConfigColumnMeta(6, "inherit2", "STRING"),
        new ConfigColumnMeta(7, "inherit3", "STRING"),
        new ConfigColumnMeta(8, "mail", "INT"),
        new ConfigColumnMeta(9, "describe1", "STRING"),
        new ConfigColumnMeta(10, "describe2", "STRING"),
        new ConfigColumnMeta(11, "item", "INT"),
        new ConfigColumnMeta(12, "picture", "INT"),
        new ConfigColumnMeta(13, "pictureOffset", "STRING"),
        new ConfigColumnMeta(14, "pictureOffset2", "STRING"),
        new ConfigColumnMeta(15, "passaWard1", "INT"),
        new ConfigColumnMeta(16, "passaWard2", "INT"));
  }
}
