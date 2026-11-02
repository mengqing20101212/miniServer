package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class PvpOnLineConfigCheckerBase extends AbstractConfigChecker<PvpOnLineConfig> {
  @Override
  public String getConfigFileName() {
    return "pvpOnLine.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "duan", "string"),
        new ConfigColumnMeta(2, "duanBigName", "string"),
        new ConfigColumnMeta(3, "duanBig", "int"),
        new ConfigColumnMeta(4, "duanSmall", "int"),
        new ConfigColumnMeta(5, "duanIconBig", "int"),
        new ConfigColumnMeta(6, "duanIconSmall", "int"),
        new ConfigColumnMeta(7, "starNum", "int"),
        new ConfigColumnMeta(8, "starCollect", "int"),
        new ConfigColumnMeta(9, "eloKValue", "float"),
        new ConfigColumnMeta(10, "scoreMax", "int"),
        new ConfigColumnMeta(11, "scoreProtection", "int"),
        new ConfigColumnMeta(12, "isScoreProtection", "int"),
        new ConfigColumnMeta(13, "winningStreak", "int"),
        new ConfigColumnMeta(14, "dropProtection", "int"),
        new ConfigColumnMeta(15, "duanProtection", "int"),
        new ConfigColumnMeta(16, "bestMatchScore", "int"),
        new ConfigColumnMeta(17, "bestMatchTime", "int"),
        new ConfigColumnMeta(18, "bestMatchStar", "int"),
        new ConfigColumnMeta(19, "fuzzyMatchScore", "int"),
        new ConfigColumnMeta(20, "fuzzyMatchTime", "int"),
        new ConfigColumnMeta(21, "fuzzyMatchStar", "int"),
        new ConfigColumnMeta(22, "leastMatchScore", "int"),
        new ConfigColumnMeta(23, "leastMatchTime", "int"),
        new ConfigColumnMeta(24, "leastMatchStar", "int"),
        new ConfigColumnMeta(25, "isTimeOutRebotMatch", "int"),
        new ConfigColumnMeta(26, "isLoseRobotMatch", "int"),
        new ConfigColumnMeta(27, "isPick", "int"),
        new ConfigColumnMeta(28, "winReward", "int"),
        new ConfigColumnMeta(29, "loseReward", "int"),
        new ConfigColumnMeta(30, "honorLimit", "int"),
        new ConfigColumnMeta(31, "awardWeek", "string"),
        new ConfigColumnMeta(32, "awardWeekPre", "string"),
        new ConfigColumnMeta(33, "bossTimeSetting", "string"),
        new ConfigColumnMeta(34, "robotPool", "int"),
        new ConfigColumnMeta(35, "sceneMatch", "int"),
        new ConfigColumnMeta(36, "bestMatchLevelDiff", "int"));
  }
}
