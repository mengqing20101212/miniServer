package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CookMixConfigCheckerBase extends AbstractConfigChecker<CookMixConfig> {
  @Override
  public String getConfigFileName() {
    return "cookMix.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "minArea", "int"),
        new ConfigColumnMeta(2, "maxArea", "int"),
        new ConfigColumnMeta(3, "dropId", "int"));
  }
}
