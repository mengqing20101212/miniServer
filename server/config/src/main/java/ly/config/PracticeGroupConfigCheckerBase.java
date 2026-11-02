package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class PracticeGroupConfigCheckerBase extends AbstractConfigChecker<PracticeGroupConfig> {
  @Override
  public String getConfigFileName() {
    return "practiceGroup.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "resource", "int"),
        new ConfigColumnMeta(3, "show", "int"),
        new ConfigColumnMeta(4, "stage", "string"),
        new ConfigColumnMeta(5, "unlock", "int"),
        new ConfigColumnMeta(6, "unlockText", "string"),
        new ConfigColumnMeta(7, "quality", "int"));
  }
}
