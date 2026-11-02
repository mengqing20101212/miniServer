package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class StrongRoadConfigCheckerBase extends AbstractConfigChecker<StrongRoadConfig> {
  @Override
  public String getConfigFileName() {
    return "strongRoad.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "type", "int"),
        new ConfigColumnMeta(2, "para", "string"),
        new ConfigColumnMeta(3, "iconShow", "int"),
        new ConfigColumnMeta(4, "show", "int"),
        new ConfigColumnMeta(5, "name", "string"),
        new ConfigColumnMeta(6, "icon", "int"),
        new ConfigColumnMeta(7, "turnId", "int"),
        new ConfigColumnMeta(8, "des", "string"));
  }
}
