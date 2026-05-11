package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivitySsrConfigCheckerBase extends AbstractConfigChecker<ActivitySsrConfig> {
  @Override
  public String getConfigFileName() {
    return "activitySsr.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "tpye", "INT"),
        new ConfigColumnMeta(2, "rewardShow", "STRING"),
        new ConfigColumnMeta(3, "activityTaskId", "INT"),
        new ConfigColumnMeta(4, "scheDuling", "INT"),
        new ConfigColumnMeta(5, "name", "STRING"),
        new ConfigColumnMeta(6, "picture", "INT"),
        new ConfigColumnMeta(7, "coordinate", "STRING"),
        new ConfigColumnMeta(8, "bg", "INT"),
        new ConfigColumnMeta(9, "size", "STRING"),
        new ConfigColumnMeta(10, "coordinateS", "STRING"),
        new ConfigColumnMeta(11, "sizeS", "STRING"),
        new ConfigColumnMeta(12, "coordinateH", "STRING"),
        new ConfigColumnMeta(13, "sizeH", "STRING"));
  }
}
