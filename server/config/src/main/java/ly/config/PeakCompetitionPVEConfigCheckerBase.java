package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class PeakCompetitionPVEConfigCheckerBase extends AbstractConfigChecker<PeakCompetitionPVEConfig> {
  @Override
  public String getConfigFileName() {
    return "peakCompetitionPVE.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "baseName", "STRING"),
        new ConfigColumnMeta(2, "unlockLv", "INT"),
        new ConfigColumnMeta(3, "baseStage", "STRING"),
        new ConfigColumnMeta(4, "icon", "INT"),
        new ConfigColumnMeta(5, "stageDec", "STRING"));
  }
}
