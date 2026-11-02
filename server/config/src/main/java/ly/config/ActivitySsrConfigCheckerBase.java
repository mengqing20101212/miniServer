package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivitySsrConfigCheckerBase extends AbstractConfigChecker<ActivitySsrConfig> {
  @Override
  public String getConfigFileName() {
    return "activitySsr.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "tpye", "int"),
        new ConfigColumnMeta(2, "rewardShow", "string"),
        new ConfigColumnMeta(3, "activityTaskId", "int"),
        new ConfigColumnMeta(4, "scheDuling", "int"),
        new ConfigColumnMeta(5, "name", "string"),
        new ConfigColumnMeta(6, "picture", "int"),
        new ConfigColumnMeta(7, "coordinate", "string"),
        new ConfigColumnMeta(8, "bg", "int"),
        new ConfigColumnMeta(9, "size", "string"),
        new ConfigColumnMeta(10, "coordinateS", "string"),
        new ConfigColumnMeta(11, "sizeS", "string"),
        new ConfigColumnMeta(12, "coordinateH", "string"),
        new ConfigColumnMeta(13, "sizeH", "string"));
  }
}
