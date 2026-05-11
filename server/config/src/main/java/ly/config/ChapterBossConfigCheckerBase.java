package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ChapterBossConfigCheckerBase extends AbstractConfigChecker<ChapterBossConfig> {
  @Override
  public String getConfigFileName() {
    return "chapterBoss.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "starNum", "INT"),
        new ConfigColumnMeta(2, "type", "INT"),
        new ConfigColumnMeta(3, "dropShow", "STRING"),
        new ConfigColumnMeta(4, "drop", "INT"),
        new ConfigColumnMeta(5, "sceneId", "INT"),
        new ConfigColumnMeta(6, "npcId", "STRING"),
        new ConfigColumnMeta(7, "modelId", "INT"),
        new ConfigColumnMeta(8, "resourceNpc", "INT"),
        new ConfigColumnMeta(9, "resourceIcon", "INT"),
        new ConfigColumnMeta(10, "iconNum", "INT"));
  }
}
