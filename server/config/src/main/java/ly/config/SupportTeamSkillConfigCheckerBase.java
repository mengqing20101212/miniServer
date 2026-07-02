package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SupportTeamSkillConfigCheckerBase extends AbstractConfigChecker<SupportTeamSkillConfig> {
  @Override
  public String getConfigFileName() {
    return "supportTeamSkill.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "teamSkillGroup", "INT"),
        new ConfigColumnMeta(2, "nextId", "INT"),
        new ConfigColumnMeta(3, "teamSkillLevel", "INT"),
        new ConfigColumnMeta(4, "teamSkillId", "INT"),
        new ConfigColumnMeta(5, "teamItem", "INT"),
        new ConfigColumnMeta(6, "teamItemNum", "INT"));
  }
}
