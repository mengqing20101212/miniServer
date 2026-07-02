package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityExploremapConfigCheckerBase extends AbstractConfigChecker<ActivityExploremapConfig> {
  @Override
  public String getConfigFileName() {
    return "activityExploremap.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "scheDuling", "INT"),
        new ConfigColumnMeta(2, "layers", "INT"),
        new ConfigColumnMeta(3, "Exploreid", "INT"),
        new ConfigColumnMeta(4, "pictureid", "INT"),
        new ConfigColumnMeta(5, "specialPicture", "STRING"),
        new ConfigColumnMeta(6, "regionalText1", "STRING"),
        new ConfigColumnMeta(7, "regionalText2", "STRING"),
        new ConfigColumnMeta(8, "mapCoordinates", "STRING"));
  }
}
