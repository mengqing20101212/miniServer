package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CookMixConfigCheckerBase extends AbstractConfigChecker<CookMixConfig> {
  @Override
  public String getConfigFileName() {
    return "cookMix.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "minArea", "INT"),
        new ConfigColumnMeta(2, "maxArea", "INT"),
        new ConfigColumnMeta(3, "dropId", "INT"));
  }
}
