package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SupportTeamResumeConfigCheckerBase extends AbstractConfigChecker<SupportTeamResumeConfig> {
  @Override
  public String getConfigFileName() {
    return "supportTeamResume.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "dropId", "int"),
        new ConfigColumnMeta(2, "dropShow", "int"),
        new ConfigColumnMeta(3, "group", "int"),
        new ConfigColumnMeta(4, "heroId", "int"),
        new ConfigColumnMeta(5, "beizhu", "string"));
  }
}
