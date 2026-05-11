package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class PlayerLvConfigCheckerBase extends AbstractConfigChecker<PlayerLvConfig> {
  @Override
  public String getConfigFileName() {
    return "playerLv.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "dropId", "INT"),
        new ConfigColumnMeta(2, "hintId", "STRING"),
        new ConfigColumnMeta(3, "addStamina", "INT"),
        new ConfigColumnMeta(4, "trialTimes", "INT"),
        new ConfigColumnMeta(5, "dropShow", "INT"));
  }
}
