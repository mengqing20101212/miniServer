package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CookSkillConfigCheckerBase extends AbstractConfigChecker<CookSkillConfig> {
  @Override
  public String getConfigFileName() {
    return "cookSkill.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "skill", "int"),
        new ConfigColumnMeta(1, "group", "int"),
        new ConfigColumnMeta(2, "nextExp", "int"),
        new ConfigColumnMeta(3, "bonus", "int"));
  }
}
