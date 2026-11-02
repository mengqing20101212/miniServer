package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class RecruitInfoConfigCheckerBase extends AbstractConfigChecker<RecruitInfoConfig> {
  @Override
  public String getConfigFileName() {
    return "recruitInfo.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "welfareId", "int"),
        new ConfigColumnMeta(2, "recruitType", "int"),
        new ConfigColumnMeta(3, "scheDuling", "int"),
        new ConfigColumnMeta(4, "trueActivityId", "int"),
        new ConfigColumnMeta(5, "item", "int"),
        new ConfigColumnMeta(6, "turnId", "int"),
        new ConfigColumnMeta(7, "num", "int"),
        new ConfigColumnMeta(8, "recruitNum", "int"),
        new ConfigColumnMeta(9, "awardId", "int"),
        new ConfigColumnMeta(10, "sumAwardId", "string"),
        new ConfigColumnMeta(11, "desc", "string"),
        new ConfigColumnMeta(12, "dayLimit", "int"),
        new ConfigColumnMeta(13, "dayLimitId", "int"));
  }
}
