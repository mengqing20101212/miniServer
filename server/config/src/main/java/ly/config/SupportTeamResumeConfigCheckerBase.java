package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SupportTeamResumeConfigCheckerBase extends AbstractConfigChecker<SupportTeamResumeConfig> {
  @Override
  public String getConfigFileName() {
    return "supportTeamResume.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "dropId", "INT"),
        new ConfigColumnMeta(2, "dropShow", "INT"),
        new ConfigColumnMeta(3, "group", "INT"),
        new ConfigColumnMeta(4, "heroId", "INT"),
        new ConfigColumnMeta(5, "beizhu", "STRING"));
  }
}
