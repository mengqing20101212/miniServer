package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class HeroExpConfigCheckerBase extends AbstractConfigChecker<HeroExpConfig> {
  @Override
  public String getConfigFileName() {
    return "heroExp.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "level", "INT"),
        new ConfigColumnMeta(1, "modelId", "INT"),
        new ConfigColumnMeta(2, "exp", "INT"),
        new ConfigColumnMeta(3, "retainExp", "INT"),
        new ConfigColumnMeta(4, "currencyType", "INT"),
        new ConfigColumnMeta(5, "currencyNum", "STRING"),
        new ConfigColumnMeta(6, "item", "STRING"));
  }
}
