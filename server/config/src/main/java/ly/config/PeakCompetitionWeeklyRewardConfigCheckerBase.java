package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class PeakCompetitionWeeklyRewardConfigCheckerBase extends AbstractConfigChecker<PeakCompetitionWeeklyRewardConfig> {
  @Override
  public String getConfigFileName() {
    return "peakCompetitionWeeklyReward.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "grade", "STRING"),
        new ConfigColumnMeta(2, "gradeShow", "STRING"),
        new ConfigColumnMeta(3, "awardWeek", "STRING"),
        new ConfigColumnMeta(4, "awardPre", "STRING"));
  }
}
