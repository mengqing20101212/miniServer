package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class TriggerSceneConfigCheckerBase extends AbstractConfigChecker<TriggerSceneConfig> {
  @Override
  public String getConfigFileName() {
    return "triggerScene.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "sceneId", "int"),
        new ConfigColumnMeta(2, "group", "int"),
        new ConfigColumnMeta(3, "type", "int"),
        new ConfigColumnMeta(4, "stageName", "string"),
        new ConfigColumnMeta(5, "stageName2", "string"),
        new ConfigColumnMeta(6, "stageName3", "string"),
        new ConfigColumnMeta(7, "preCost", "int"),
        new ConfigColumnMeta(8, "cost", "int"),
        new ConfigColumnMeta(9, "weight", "int"),
        new ConfigColumnMeta(10, "time", "int"),
        new ConfigColumnMeta(11, "bossId", "int"),
        new ConfigColumnMeta(12, "sceneAvatar", "int"),
        new ConfigColumnMeta(13, "dropPro", "string"),
        new ConfigColumnMeta(14, "bossDes", "string"),
        new ConfigColumnMeta(15, "scenePic", "int"),
        new ConfigColumnMeta(16, "posOffset", "string"),
        new ConfigColumnMeta(17, "scale", "string"));
  }
}
