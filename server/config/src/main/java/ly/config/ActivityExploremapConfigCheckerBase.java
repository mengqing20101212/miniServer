package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityExploremapConfigCheckerBase extends AbstractConfigChecker<ActivityExploremapConfig> {
  @Override
  public String getConfigFileName() {
    return "activityExploremap.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "scheDuling", "int"),
        new ConfigColumnMeta(2, "layers", "int"),
        new ConfigColumnMeta(3, "Exploreid", "int"),
        new ConfigColumnMeta(4, "pictureid", "int"),
        new ConfigColumnMeta(5, "specialPicture", "string"),
        new ConfigColumnMeta(6, "regionalText1", "string"),
        new ConfigColumnMeta(7, "regionalText2", "string"),
        new ConfigColumnMeta(8, "mapCoordinates", "string"));
  }
}
