package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class TurnConfigCheckerBase extends AbstractConfigChecker<TurnConfig> {
  @Override
  public String getConfigFileName() {
    return "turn.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "description", "STRING"),
        new ConfigColumnMeta(2, "turnType", "INT"),
        new ConfigColumnMeta(3, "uiMsg", "STRING"),
        new ConfigColumnMeta(4, "type", "INT"),
        new ConfigColumnMeta(5, "type2", "INT"),
        new ConfigColumnMeta(6, "isNet", "INT"),
        new ConfigColumnMeta(7, "param", "STRING"),
        new ConfigColumnMeta(8, "param2", "STRING"),
        new ConfigColumnMeta(9, "activityId", "INT"),
        new ConfigColumnMeta(10, "guideId", "INT"),
        new ConfigColumnMeta(11, "frontUiMsg", "STRING"));
  }
}
