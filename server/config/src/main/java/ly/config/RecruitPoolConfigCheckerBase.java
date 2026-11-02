package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class RecruitPoolConfigCheckerBase extends AbstractConfigChecker<RecruitPoolConfig> {
  @Override
  public String getConfigFileName() {
    return "recruitPool.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "recruitType", "int"),
        new ConfigColumnMeta(3, "showStyleType", "int"),
        new ConfigColumnMeta(4, "oneDrawId", "int"),
        new ConfigColumnMeta(5, "tenDrawId", "int"),
        new ConfigColumnMeta(6, "scheDuling", "int"),
        new ConfigColumnMeta(7, "type", "int"),
        new ConfigColumnMeta(8, "timesShowType", "string"),
        new ConfigColumnMeta(9, "video", "string"),
        new ConfigColumnMeta(10, "lastDateShow", "string"),
        new ConfigColumnMeta(11, "chanceText", "string"),
        new ConfigColumnMeta(12, "topId", "int"),
        new ConfigColumnMeta(13, "recruitEndTopId", "int"),
        new ConfigColumnMeta(14, "recruitmentshow", "int"),
        new ConfigColumnMeta(15, "trueActivityId", "int"));
  }
}
