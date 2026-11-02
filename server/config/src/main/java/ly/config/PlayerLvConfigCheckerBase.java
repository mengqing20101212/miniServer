package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class PlayerLvConfigCheckerBase extends AbstractConfigChecker<PlayerLvConfig> {
  @Override
  public String getConfigFileName() {
    return "playerLv.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "dropId", "int"),
        new ConfigColumnMeta(2, "hintId", "string"),
        new ConfigColumnMeta(3, "addStamina", "int"),
        new ConfigColumnMeta(4, "trialTimes", "int"),
        new ConfigColumnMeta(5, "dropShow", "int"));
  }
}
