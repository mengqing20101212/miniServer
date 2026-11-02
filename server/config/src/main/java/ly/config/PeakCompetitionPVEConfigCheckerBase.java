package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class PeakCompetitionPVEConfigCheckerBase extends AbstractConfigChecker<PeakCompetitionPVEConfig> {
  @Override
  public String getConfigFileName() {
    return "peakCompetitionPVE.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "baseName", "string"),
        new ConfigColumnMeta(2, "unlockLv", "int"),
        new ConfigColumnMeta(3, "baseStage", "string"),
        new ConfigColumnMeta(4, "icon", "int"),
        new ConfigColumnMeta(5, "stageDec", "string"));
  }
}
