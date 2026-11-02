package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class AtlasConfigCheckerBase extends AbstractConfigChecker<AtlasConfig> {
  @Override
  public String getConfigFileName() {
    return "atlas.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "sprite", "string"),
        new ConfigColumnMeta(2, "atlas", "int"),
        new ConfigColumnMeta(3, "des", "string"));
  }
}
