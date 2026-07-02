package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SupportTeamExpConfigCheckerBase extends AbstractConfigChecker<SupportTeamExpConfig> {
  @Override
  public String getConfigFileName() {
    return "supportTeamExp.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "modelId", "INT"),
        new ConfigColumnMeta(1, "level", "INT"),
        new ConfigColumnMeta(2, "exp", "INT"));
  }
}
