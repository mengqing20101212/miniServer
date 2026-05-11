package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class PokerPoolConfigCheckerBase extends AbstractConfigChecker<PokerPoolConfig> {
  @Override
  public String getConfigFileName() {
    return "pokerPool.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "randomNumber", "INT"),
        new ConfigColumnMeta(3, "pool", "STRING"),
        new ConfigColumnMeta(4, "hinder", "STRING"));
  }
}
