package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SceneStarConfigCheckerBase extends AbstractConfigChecker<SceneStarConfig> {
  @Override
  public String getConfigFileName() {
    return "sceneStar.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "type", "int"),
        new ConfigColumnMeta(2, "parax", "int"),
        new ConfigColumnMeta(3, "paray", "int"),
        new ConfigColumnMeta(4, "description", "string"));
  }
}
