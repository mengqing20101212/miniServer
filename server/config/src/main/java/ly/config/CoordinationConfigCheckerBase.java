package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CoordinationConfigCheckerBase extends AbstractConfigChecker<CoordinationConfig> {
  @Override
  public String getConfigFileName() {
    return "coordination.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "position", "int"),
        new ConfigColumnMeta(1, "levelUnlock", "int"),
        new ConfigColumnMeta(2, "coordinationUnlock", "int"));
  }
}
