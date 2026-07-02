package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SkillGroupConfigCheckerBase extends AbstractConfigChecker<SkillGroupConfig> {
  @Override
  public String getConfigFileName() {
    return "skillGroup.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "description", "STRING"),
        new ConfigColumnMeta(3, "skillList", "STRING"),
        new ConfigColumnMeta(4, "skillIcon", "LIST<INT>"));
  }
}
