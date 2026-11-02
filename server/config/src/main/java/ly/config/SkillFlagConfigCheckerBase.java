package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SkillFlagConfigCheckerBase extends AbstractConfigChecker<SkillFlagConfig> {
  @Override
  public String getConfigFileName() {
    return "skillFlag.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "des", "string"),
        new ConfigColumnMeta(2, "name", "string"),
        new ConfigColumnMeta(3, "Bgcolor", "string"));
  }
}
