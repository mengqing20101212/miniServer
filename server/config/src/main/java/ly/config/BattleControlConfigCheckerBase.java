package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class BattleControlConfigCheckerBase extends AbstractConfigChecker<BattleControlConfig> {
  @Override
  public String getConfigFileName() {
    return "battleControl.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "lineupId", "INT"),
        new ConfigColumnMeta(3, "isOnlineBattle", "INT"),
        new ConfigColumnMeta(4, "loadingTime", "INT"),
        new ConfigColumnMeta(5, "readyTime", "INT"),
        new ConfigColumnMeta(6, "changeHeroTime", "INT"),
        new ConfigColumnMeta(7, "isShare", "INT"),
        new ConfigColumnMeta(8, "chaoiceWaitTime", "INT"),
        new ConfigColumnMeta(9, "isSaveDate", "INT"));
  }
}
