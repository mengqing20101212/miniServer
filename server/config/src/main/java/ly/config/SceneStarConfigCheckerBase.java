package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SceneStarConfigCheckerBase extends AbstractConfigChecker<SceneStarConfig> {
  @Override
  public String getConfigFileName() {
    return "sceneStar.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "type", "INT"),
        new ConfigColumnMeta(2, "parax", "INT"),
        new ConfigColumnMeta(3, "paray", "INT"),
        new ConfigColumnMeta(4, "description", "STRING"));
  }
}
