package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CookSkillConfigCheckerBase extends AbstractConfigChecker<CookSkillConfig> {
  @Override
  public String getConfigFileName() {
    return "cookSkill.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "skill", "INT"),
        new ConfigColumnMeta(1, "group", "INT"),
        new ConfigColumnMeta(2, "nextExp", "INT"),
        new ConfigColumnMeta(3, "bonus", "INT"));
  }
}
