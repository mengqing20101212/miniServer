package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class StrongRoadTaskConfigCheckerBase extends AbstractConfigChecker<StrongRoadTaskConfig> {
  @Override
  public String getConfigFileName() {
    return "strongRoadTask.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "missionId1", "int"),
        new ConfigColumnMeta(2, "des1", "string"),
        new ConfigColumnMeta(3, "missionId2", "int"),
        new ConfigColumnMeta(4, "des2", "string"),
        new ConfigColumnMeta(5, "missionId3", "int"),
        new ConfigColumnMeta(6, "des3", "string"),
        new ConfigColumnMeta(7, "drop", "int"),
        new ConfigColumnMeta(8, "dropShow", "int"),
        new ConfigColumnMeta(9, "isShow", "int"));
  }
}
