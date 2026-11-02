package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class PeakCompetitionAssemblyConfigCheckerBase extends AbstractConfigChecker<PeakCompetitionAssemblyConfig> {
  @Override
  public String getConfigFileName() {
    return "peakCompetitionAssembly.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "type", "int"),
        new ConfigColumnMeta(3, "skillID", "int"),
        new ConfigColumnMeta(4, "attrType", "int"),
        new ConfigColumnMeta(5, "attrNum", "int"),
        new ConfigColumnMeta(6, "heroAdvance", "int"),
        new ConfigColumnMeta(7, "skillLv", "int"),
        new ConfigColumnMeta(8, "quality", "string"),
        new ConfigColumnMeta(9, "limit", "int"),
        new ConfigColumnMeta(10, "description", "string"));
  }
}
