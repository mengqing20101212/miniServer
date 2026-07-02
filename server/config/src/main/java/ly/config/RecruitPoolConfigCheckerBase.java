package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class RecruitPoolConfigCheckerBase extends AbstractConfigChecker<RecruitPoolConfig> {
  @Override
  public String getConfigFileName() {
    return "recruitPool.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "recruitType", "INT"),
        new ConfigColumnMeta(3, "showStyleType", "INT"),
        new ConfigColumnMeta(4, "oneDrawId", "INT"),
        new ConfigColumnMeta(5, "tenDrawId", "INT"),
        new ConfigColumnMeta(6, "scheDuling", "INT"),
        new ConfigColumnMeta(7, "type", "INT"),
        new ConfigColumnMeta(8, "timesShowType", "STRING"),
        new ConfigColumnMeta(9, "video", "STRING"),
        new ConfigColumnMeta(10, "lastDateShow", "STRING"),
        new ConfigColumnMeta(11, "chanceText", "STRING"),
        new ConfigColumnMeta(12, "topId", "INT"),
        new ConfigColumnMeta(13, "recruitEndTopId", "INT"),
        new ConfigColumnMeta(14, "recruitmentshow", "INT"),
        new ConfigColumnMeta(15, "trueActivityId", "INT"));
  }
}
