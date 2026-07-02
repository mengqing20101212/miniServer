package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class PracticeGroupConfigCheckerBase extends AbstractConfigChecker<PracticeGroupConfig> {
  @Override
  public String getConfigFileName() {
    return "practiceGroup.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "resource", "INT"),
        new ConfigColumnMeta(3, "show", "INT"),
        new ConfigColumnMeta(4, "stage", "STRING"),
        new ConfigColumnMeta(5, "unlock", "INT"),
        new ConfigColumnMeta(6, "unlockText", "STRING"),
        new ConfigColumnMeta(7, "quality", "INT"));
  }
}
