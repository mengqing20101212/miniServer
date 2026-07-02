package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class StrongRoadTaskConfigCheckerBase extends AbstractConfigChecker<StrongRoadTaskConfig> {
  @Override
  public String getConfigFileName() {
    return "strongRoadTask.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "missionId1", "INT"),
        new ConfigColumnMeta(2, "des1", "STRING"),
        new ConfigColumnMeta(3, "missionId2", "INT"),
        new ConfigColumnMeta(4, "des2", "STRING"),
        new ConfigColumnMeta(5, "missionId3", "INT"),
        new ConfigColumnMeta(6, "des3", "STRING"),
        new ConfigColumnMeta(7, "drop", "INT"),
        new ConfigColumnMeta(8, "dropShow", "INT"),
        new ConfigColumnMeta(9, "isShow", "INT"));
  }
}
