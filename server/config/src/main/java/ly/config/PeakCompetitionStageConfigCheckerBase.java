package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class PeakCompetitionStageConfigCheckerBase extends AbstractConfigChecker<PeakCompetitionStageConfig> {
  @Override
  public String getConfigFileName() {
    return "peakCompetitionStage.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "stageName", "string"),
        new ConfigColumnMeta(2, "groupId", "int"),
        new ConfigColumnMeta(3, "nextId", "int"),
        new ConfigColumnMeta(4, "lastId", "int"),
        new ConfigColumnMeta(5, "sceneId", "int"),
        new ConfigColumnMeta(6, "dropShow", "int"),
        new ConfigColumnMeta(7, "fixedRole", "string"));
  }
}
