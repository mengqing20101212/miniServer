package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class TriggerSceneConfigCheckerBase extends AbstractConfigChecker<TriggerSceneConfig> {
  @Override
  public String getConfigFileName() {
    return "triggerScene.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "sceneId", "INT"),
        new ConfigColumnMeta(2, "group", "INT"),
        new ConfigColumnMeta(3, "type", "INT"),
        new ConfigColumnMeta(4, "stageName", "STRING"),
        new ConfigColumnMeta(5, "stageName2", "STRING"),
        new ConfigColumnMeta(6, "stageName3", "STRING"),
        new ConfigColumnMeta(7, "preCost", "INT"),
        new ConfigColumnMeta(8, "cost", "INT"),
        new ConfigColumnMeta(9, "weight", "INT"),
        new ConfigColumnMeta(10, "time", "INT"),
        new ConfigColumnMeta(11, "bossId", "INT"),
        new ConfigColumnMeta(12, "sceneAvatar", "INT"),
        new ConfigColumnMeta(13, "dropPro", "STRING"),
        new ConfigColumnMeta(14, "bossDes", "STRING"),
        new ConfigColumnMeta(15, "scenePic", "INT"),
        new ConfigColumnMeta(16, "posOffset", "STRING"),
        new ConfigColumnMeta(17, "scale", "STRING"));
  }
}
