package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class HeroExpConfigCheckerBase extends AbstractConfigChecker<HeroExpConfig> {
  @Override
  public String getConfigFileName() {
    return "heroExp.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "level", "int"),
        new ConfigColumnMeta(1, "modelId", "int"),
        new ConfigColumnMeta(2, "exp", "int"),
        new ConfigColumnMeta(3, "retainExp", "int"),
        new ConfigColumnMeta(4, "currencyType", "int"),
        new ConfigColumnMeta(5, "currencyNum", "string"),
        new ConfigColumnMeta(6, "item", "string"));
  }
}
