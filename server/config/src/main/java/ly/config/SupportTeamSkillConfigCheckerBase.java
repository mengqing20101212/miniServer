package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SupportTeamSkillConfigCheckerBase extends AbstractConfigChecker<SupportTeamSkillConfig> {
  @Override
  public String getConfigFileName() {
    return "supportTeamSkill.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "teamSkillGroup", "int"),
        new ConfigColumnMeta(2, "nextId", "int"),
        new ConfigColumnMeta(3, "teamSkillLevel", "int"),
        new ConfigColumnMeta(4, "teamSkillId", "int"),
        new ConfigColumnMeta(5, "teamItem", "int"),
        new ConfigColumnMeta(6, "teamItemNum", "int"));
  }
}
