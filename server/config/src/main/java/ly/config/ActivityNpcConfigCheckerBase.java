package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityNpcConfigCheckerBase extends AbstractConfigChecker<ActivityNpcConfig> {
  @Override
  public String getConfigFileName() {
    return "activityNpc.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "config_name", "string"),
        new ConfigColumnMeta(2, "characterModelId", "string"),
        new ConfigColumnMeta(3, "defaultAni", "string"),
        new ConfigColumnMeta(4, "bornShowAni", "string"),
        new ConfigColumnMeta(5, "clickType", "string"),
        new ConfigColumnMeta(6, "clickAniList", "string"),
        new ConfigColumnMeta(7, "clickCameraList", "string"),
        new ConfigColumnMeta(8, "moveDistance", "string"),
        new ConfigColumnMeta(9, "cameraDistance", "string"),
        new ConfigColumnMeta(10, "clickText", "string"),
        new ConfigColumnMeta(11, "groupText", "string"),
        new ConfigColumnMeta(12, "ActivityType", "string"),
        new ConfigColumnMeta(13, "param_1", "string"),
        new ConfigColumnMeta(14, "param_2", "string"),
        new ConfigColumnMeta(15, "npcGrounpId", "string"),
        new ConfigColumnMeta(16, "showPriority", "string"),
        new ConfigColumnMeta(17, "decorationId", "string"),
        new ConfigColumnMeta(18, "decorationPoint", "string"));
  }
}
