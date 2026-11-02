package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class TurnConfigCheckerBase extends AbstractConfigChecker<TurnConfig> {
  @Override
  public String getConfigFileName() {
    return "turn.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "description", "string"),
        new ConfigColumnMeta(2, "turnType", "int"),
        new ConfigColumnMeta(3, "uiMsg", "string"),
        new ConfigColumnMeta(4, "type", "int"),
        new ConfigColumnMeta(5, "type2", "int"),
        new ConfigColumnMeta(6, "isNet", "int"),
        new ConfigColumnMeta(7, "param", "string"),
        new ConfigColumnMeta(8, "param2", "string"),
        new ConfigColumnMeta(9, "activityId", "int"),
        new ConfigColumnMeta(10, "guideId", "int"),
        new ConfigColumnMeta(11, "frontUiMsg", "string"));
  }
}
