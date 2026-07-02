package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ShardActivityControlConfigCheckerBase extends AbstractConfigChecker<ShardActivityControlConfig> {
  @Override
  public String getConfigFileName() {
    return "shardActivityControl.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "scene", "INT"),
        new ConfigColumnMeta(2, "url", "STRING"));
  }
}
