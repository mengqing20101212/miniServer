package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityControlConfigCheckerBase extends AbstractConfigChecker<ActivityControlConfig> {
  @Override
  public String getConfigFileName() {
    return "activityControl.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "unlockType", "INT"),
        new ConfigColumnMeta(3, "unlockPara", "INT"),
        new ConfigColumnMeta(4, "unlockPara2", "INT"),
        new ConfigColumnMeta(5, "lineupId", "INT"),
        new ConfigColumnMeta(6, "lineupLimit", "INT"),
        new ConfigColumnMeta(7, "isOnlineBattle", "INT"),
        new ConfigColumnMeta(8, "BgmId", "INT"),
        new ConfigColumnMeta(9, "openType", "INT"),
        new ConfigColumnMeta(10, "openPara", "STRING"),
        new ConfigColumnMeta(11, "guideId", "INT"),
        new ConfigColumnMeta(12, "icon", "INT"),
        new ConfigColumnMeta(13, "des", "STRING"),
        new ConfigColumnMeta(14, "timeDes", "STRING"),
        new ConfigColumnMeta(15, "rewardId", "STRING"),
        new ConfigColumnMeta(16, "openLimitDes", "STRING"),
        new ConfigColumnMeta(17, "turnId", "INT"),
        new ConfigColumnMeta(18, "lineupTeamId", "INT"),
        new ConfigColumnMeta(19, "saveBattleLog", "INT"),
        new ConfigColumnMeta(20, "dayLimit", "INT"),
        new ConfigColumnMeta(21, "weekLimit", "INT"),
        new ConfigColumnMeta(22, "activityIcon", "STRING"),
        new ConfigColumnMeta(23, "activityName", "STRING"),
        new ConfigColumnMeta(24, "bgColour", "STRING"),
        new ConfigColumnMeta(25, "activityreward", "STRING"),
        new ConfigColumnMeta(26, "teamLv", "STRING"),
        new ConfigColumnMeta(27, "help", "INT"),
        new ConfigColumnMeta(28, "noPrelock", "INT"));
  }
}
