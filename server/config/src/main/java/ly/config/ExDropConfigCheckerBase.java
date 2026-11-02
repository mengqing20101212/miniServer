package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ExDropConfigCheckerBase extends AbstractConfigChecker<ExDropConfig> {
  @Override
  public String getConfigFileName() {
    return "exDrop.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "bonusParam", "int"),
        new ConfigColumnMeta(3, "bonusType", "int"),
        new ConfigColumnMeta(4, "sceneType", "string"));
  }
}
