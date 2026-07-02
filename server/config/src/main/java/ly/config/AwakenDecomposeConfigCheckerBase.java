package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class AwakenDecomposeConfigCheckerBase extends AbstractConfigChecker<AwakenDecomposeConfig> {
  @Override
  public String getConfigFileName() {
    return "awakenDecompose.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "itemId", "INT"),
        new ConfigColumnMeta(2, "name", "STRING"),
        new ConfigColumnMeta(3, "decomposeItemId", "STRING"));
  }
}
