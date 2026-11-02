package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityControlConfigCheckerBase extends AbstractConfigChecker<ActivityControlConfig> {
  @Override
  public String getConfigFileName() {
    return "activityControl.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "unlockType", "int"),
        new ConfigColumnMeta(3, "unlockPara", "int"),
        new ConfigColumnMeta(4, "unlockPara2", "int"),
        new ConfigColumnMeta(5, "lineupId", "int"),
        new ConfigColumnMeta(6, "lineupLimit", "int"),
        new ConfigColumnMeta(7, "isOnlineBattle", "int"),
        new ConfigColumnMeta(8, "BgmId", "int"),
        new ConfigColumnMeta(9, "openType", "int"),
        new ConfigColumnMeta(10, "openPara", "string"),
        new ConfigColumnMeta(11, "guideId", "int"),
        new ConfigColumnMeta(12, "icon", "int"),
        new ConfigColumnMeta(13, "des", "string"),
        new ConfigColumnMeta(14, "timeDes", "string"),
        new ConfigColumnMeta(15, "rewardId", "string"),
        new ConfigColumnMeta(16, "openLimitDes", "string"),
        new ConfigColumnMeta(17, "turnId", "int"),
        new ConfigColumnMeta(18, "lineupTeamId", "int"),
        new ConfigColumnMeta(19, "saveBattleLog", "int"),
        new ConfigColumnMeta(20, "dayLimit", "int"),
        new ConfigColumnMeta(21, "weekLimit", "int"),
        new ConfigColumnMeta(22, "activityIcon", "string"),
        new ConfigColumnMeta(23, "activityName", "string"),
        new ConfigColumnMeta(24, "bgColour", "string"),
        new ConfigColumnMeta(25, "activityreward", "string"),
        new ConfigColumnMeta(26, "teamLv", "string"),
        new ConfigColumnMeta(27, "help", "int"),
        new ConfigColumnMeta(28, "noPrelock", "int"));
  }
}
