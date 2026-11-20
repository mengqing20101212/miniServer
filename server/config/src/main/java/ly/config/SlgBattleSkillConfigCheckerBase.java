package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SlgBattleSkillConfigCheckerBase extends AbstractConfigChecker<SlgBattleSkillConfig> {
  @Override
  public String getConfigFileName() {
    return "slgBattleSkill.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "heroSkillId", "int"),
        new ConfigColumnMeta(2, "effectType", "int"),
        new ConfigColumnMeta(3, "targetRule", "int"),
        new ConfigColumnMeta(4, "targetCount", "int"),
        new ConfigColumnMeta(5, "actionRate", "int"));
  }
}
