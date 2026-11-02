package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityNpcGroupConfigCheckerBase extends AbstractConfigChecker<ActivityNpcGroupConfig> {
  @Override
  public String getConfigFileName() {
    return "activityNpcGroup.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "defaultAni", "string"),
        new ConfigColumnMeta(2, "p1", "string"),
        new ConfigColumnMeta(3, "groupName", "string"),
        new ConfigColumnMeta(4, "clickAniList", "string"),
        new ConfigColumnMeta(5, "moveDistance", "string"),
        new ConfigColumnMeta(6, "cameraHight", "string"),
        new ConfigColumnMeta(7, "cameraDistance", "string"),
        new ConfigColumnMeta(8, "npcDistance", "string"),
        new ConfigColumnMeta(9, "textPrefabType", "string"));
  }
}
