package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class RecruitInfoConfigCheckerBase extends AbstractConfigChecker<RecruitInfoConfig> {
  @Override
  public String getConfigFileName() {
    return "recruitInfo.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "welfareId", "INT"),
        new ConfigColumnMeta(2, "recruitType", "INT"),
        new ConfigColumnMeta(3, "scheDuling", "INT"),
        new ConfigColumnMeta(4, "trueActivityId", "INT"),
        new ConfigColumnMeta(5, "item", "INT"),
        new ConfigColumnMeta(6, "turnId", "INT"),
        new ConfigColumnMeta(7, "num", "INT"),
        new ConfigColumnMeta(8, "recruitNum", "INT"),
        new ConfigColumnMeta(9, "awardId", "INT"),
        new ConfigColumnMeta(10, "sumAwardId", "STRING"),
        new ConfigColumnMeta(11, "desc", "STRING"),
        new ConfigColumnMeta(12, "dayLimit", "INT"),
        new ConfigColumnMeta(13, "dayLimitId", "INT"));
  }
}
