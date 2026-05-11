package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class PvpOnLineConfigCheckerBase extends AbstractConfigChecker<PvpOnLineConfig> {
  @Override
  public String getConfigFileName() {
    return "pvpOnLine.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "duan", "STRING"),
        new ConfigColumnMeta(2, "duanBigName", "STRING"),
        new ConfigColumnMeta(3, "duanBig", "INT"),
        new ConfigColumnMeta(4, "duanSmall", "INT"),
        new ConfigColumnMeta(5, "duanIconBig", "INT"),
        new ConfigColumnMeta(6, "duanIconSmall", "INT"),
        new ConfigColumnMeta(7, "starNum", "INT"),
        new ConfigColumnMeta(8, "starCollect", "INT"),
        new ConfigColumnMeta(9, "eloKValue", "FLOAT"),
        new ConfigColumnMeta(10, "scoreMax", "INT"),
        new ConfigColumnMeta(11, "scoreProtection", "INT"),
        new ConfigColumnMeta(12, "isScoreProtection", "INT"),
        new ConfigColumnMeta(13, "winningStreak", "INT"),
        new ConfigColumnMeta(14, "dropProtection", "INT"),
        new ConfigColumnMeta(15, "duanProtection", "INT"),
        new ConfigColumnMeta(16, "bestMatchScore", "INT"),
        new ConfigColumnMeta(17, "bestMatchTime", "INT"),
        new ConfigColumnMeta(18, "bestMatchStar", "INT"),
        new ConfigColumnMeta(19, "fuzzyMatchScore", "INT"),
        new ConfigColumnMeta(20, "fuzzyMatchTime", "INT"),
        new ConfigColumnMeta(21, "fuzzyMatchStar", "INT"),
        new ConfigColumnMeta(22, "leastMatchScore", "INT"),
        new ConfigColumnMeta(23, "leastMatchTime", "INT"),
        new ConfigColumnMeta(24, "leastMatchStar", "INT"),
        new ConfigColumnMeta(25, "isTimeOutRebotMatch", "INT"),
        new ConfigColumnMeta(26, "isLoseRobotMatch", "INT"),
        new ConfigColumnMeta(27, "isPick", "INT"),
        new ConfigColumnMeta(28, "winReward", "INT"),
        new ConfigColumnMeta(29, "loseReward", "INT"),
        new ConfigColumnMeta(30, "honorLimit", "INT"),
        new ConfigColumnMeta(31, "awardWeek", "STRING"),
        new ConfigColumnMeta(32, "awardWeekPre", "STRING"),
        new ConfigColumnMeta(33, "bossTimeSetting", "STRING"),
        new ConfigColumnMeta(34, "robotPool", "INT"),
        new ConfigColumnMeta(35, "sceneMatch", "INT"),
        new ConfigColumnMeta(36, "bestMatchLevelDiff", "INT"));
  }
}
