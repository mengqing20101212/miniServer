package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityNpcGroupConfigCheckerBase extends AbstractConfigChecker<ActivityNpcGroupConfig> {
  @Override
  public String getConfigFileName() {
    return "activityNpcGroup.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "defaultAni", "STRING"),
        new ConfigColumnMeta(2, "p1", "STRING"),
        new ConfigColumnMeta(3, "groupName", "STRING"),
        new ConfigColumnMeta(4, "clickAniList", "STRING"),
        new ConfigColumnMeta(5, "moveDistance", "STRING"),
        new ConfigColumnMeta(6, "cameraHight", "STRING"),
        new ConfigColumnMeta(7, "cameraDistance", "STRING"),
        new ConfigColumnMeta(8, "npcDistance", "STRING"),
        new ConfigColumnMeta(9, "textPrefabType", "STRING"));
  }
}
