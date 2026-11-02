package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SkillGroupConfigCheckerBase extends AbstractConfigChecker<SkillGroupConfig> {
  @Override
  public String getConfigFileName() {
    return "skillGroup.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "description", "string"),
        new ConfigColumnMeta(3, "skillList", "string"),
        new ConfigColumnMeta(4, "skillIcon", "list<int>"));
  }
}
