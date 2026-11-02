package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class PeakCompetitionWeeklyRewardConfigCheckerBase extends AbstractConfigChecker<PeakCompetitionWeeklyRewardConfig> {
  @Override
  public String getConfigFileName() {
    return "peakCompetitionWeeklyReward.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "grade", "string"),
        new ConfigColumnMeta(2, "gradeShow", "string"),
        new ConfigColumnMeta(3, "awardWeek", "string"),
        new ConfigColumnMeta(4, "awardPre", "string"));
  }
}
