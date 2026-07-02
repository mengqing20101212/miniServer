package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class WantedStageConfigCheckerBase extends AbstractConfigChecker<WantedStageConfig> {
  @Override
  public String getConfigFileName() {
    return "wantedStage.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "missionTitle", "STRING"),
        new ConfigColumnMeta(2, "titleRes", "INT"),
        new ConfigColumnMeta(3, "titleDesc", "STRING"),
        new ConfigColumnMeta(4, "missionDesc", "STRING"),
        new ConfigColumnMeta(5, "missionPic", "INT"),
        new ConfigColumnMeta(6, "star", "INT"),
        new ConfigColumnMeta(7, "rewardShow", "STRING"),
        new ConfigColumnMeta(8, "sceneId", "INT"));
  }
}
