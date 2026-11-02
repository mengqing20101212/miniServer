package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SlgWorldObjectConfigCheckerBase extends AbstractConfigChecker<SlgWorldObjectConfig> {
  @Override
  public String getConfigFileName() {
    return "slgWorldObject.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "kind", "string"),
        new ConfigColumnMeta(2, "resourceId", "int"),
        new ConfigColumnMeta(3, "amount", "long"),
        new ConfigColumnMeta(4, "health", "long"),
        new ConfigColumnMeta(5, "reward", "long"),
        new ConfigColumnMeta(6, "gatherMillis", "long"),
        new ConfigColumnMeta(7, "countPerRegion", "int"));
  }
}
