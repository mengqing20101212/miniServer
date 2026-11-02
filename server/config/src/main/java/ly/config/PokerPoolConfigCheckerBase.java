package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class PokerPoolConfigCheckerBase extends AbstractConfigChecker<PokerPoolConfig> {
  @Override
  public String getConfigFileName() {
    return "pokerPool.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "randomNumber", "int"),
        new ConfigColumnMeta(3, "pool", "string"),
        new ConfigColumnMeta(4, "hinder", "string"));
  }
}
