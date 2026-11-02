package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class BattleControlConfigCheckerBase extends AbstractConfigChecker<BattleControlConfig> {
  @Override
  public String getConfigFileName() {
    return "battleControl.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "lineupId", "int"),
        new ConfigColumnMeta(3, "isOnlineBattle", "int"),
        new ConfigColumnMeta(4, "loadingTime", "int"),
        new ConfigColumnMeta(5, "readyTime", "int"),
        new ConfigColumnMeta(6, "changeHeroTime", "int"),
        new ConfigColumnMeta(7, "isShare", "int"),
        new ConfigColumnMeta(8, "chaoiceWaitTime", "int"),
        new ConfigColumnMeta(9, "isSaveDate", "int"));
  }
}
