package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class WantedStageConfigCheckerBase extends AbstractConfigChecker<WantedStageConfig> {
  @Override
  public String getConfigFileName() {
    return "wantedStage.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "missionTitle", "string"),
        new ConfigColumnMeta(2, "titleRes", "int"),
        new ConfigColumnMeta(3, "titleDesc", "string"),
        new ConfigColumnMeta(4, "missionDesc", "string"),
        new ConfigColumnMeta(5, "missionPic", "int"),
        new ConfigColumnMeta(6, "star", "int"),
        new ConfigColumnMeta(7, "rewardShow", "string"),
        new ConfigColumnMeta(8, "sceneId", "int"));
  }
}
