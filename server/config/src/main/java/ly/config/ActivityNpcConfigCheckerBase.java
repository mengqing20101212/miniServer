package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityNpcConfigCheckerBase extends AbstractConfigChecker<ActivityNpcConfig> {
  @Override
  public String getConfigFileName() {
    return "activityNpc.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "config_name", "STRING"),
        new ConfigColumnMeta(2, "characterModelId", "STRING"),
        new ConfigColumnMeta(3, "defaultAni", "STRING"),
        new ConfigColumnMeta(4, "bornShowAni", "STRING"),
        new ConfigColumnMeta(5, "clickType", "STRING"),
        new ConfigColumnMeta(6, "clickAniList", "STRING"),
        new ConfigColumnMeta(7, "clickCameraList", "STRING"),
        new ConfigColumnMeta(8, "moveDistance", "STRING"),
        new ConfigColumnMeta(9, "cameraDistance", "STRING"),
        new ConfigColumnMeta(10, "clickText", "STRING"),
        new ConfigColumnMeta(11, "groupText", "STRING"),
        new ConfigColumnMeta(12, "ActivityType", "STRING"),
        new ConfigColumnMeta(13, "param_1", "STRING"),
        new ConfigColumnMeta(14, "param_2", "STRING"),
        new ConfigColumnMeta(15, "npcGrounpId", "STRING"),
        new ConfigColumnMeta(16, "showPriority", "STRING"),
        new ConfigColumnMeta(17, "decorationId", "STRING"),
        new ConfigColumnMeta(18, "decorationPoint", "STRING"));
  }
}
