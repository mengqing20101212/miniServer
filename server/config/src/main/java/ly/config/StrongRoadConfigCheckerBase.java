package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class StrongRoadConfigCheckerBase extends AbstractConfigChecker<StrongRoadConfig> {
  @Override
  public String getConfigFileName() {
    return "strongRoad.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "type", "INT"),
        new ConfigColumnMeta(2, "para", "STRING"),
        new ConfigColumnMeta(3, "iconShow", "INT"),
        new ConfigColumnMeta(4, "show", "INT"),
        new ConfigColumnMeta(5, "name", "STRING"),
        new ConfigColumnMeta(6, "icon", "INT"),
        new ConfigColumnMeta(7, "turnId", "INT"),
        new ConfigColumnMeta(8, "des", "STRING"));
  }
}
