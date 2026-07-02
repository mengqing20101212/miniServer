package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class PeakCompetitionAssemblyConfigCheckerBase extends AbstractConfigChecker<PeakCompetitionAssemblyConfig> {
  @Override
  public String getConfigFileName() {
    return "peakCompetitionAssembly.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "type", "INT"),
        new ConfigColumnMeta(3, "skillID", "INT"),
        new ConfigColumnMeta(4, "attrType", "INT"),
        new ConfigColumnMeta(5, "attrNum", "INT"),
        new ConfigColumnMeta(6, "heroAdvance", "INT"),
        new ConfigColumnMeta(7, "skillLv", "INT"),
        new ConfigColumnMeta(8, "quality", "STRING"),
        new ConfigColumnMeta(9, "limit", "INT"),
        new ConfigColumnMeta(10, "description", "STRING"));
  }
}
