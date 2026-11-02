package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ChapterBossConfigCheckerBase extends AbstractConfigChecker<ChapterBossConfig> {
  @Override
  public String getConfigFileName() {
    return "chapterBoss.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "starNum", "int"),
        new ConfigColumnMeta(2, "type", "int"),
        new ConfigColumnMeta(3, "dropShow", "string"),
        new ConfigColumnMeta(4, "drop", "int"),
        new ConfigColumnMeta(5, "sceneId", "int"),
        new ConfigColumnMeta(6, "npcId", "string"),
        new ConfigColumnMeta(7, "modelId", "int"),
        new ConfigColumnMeta(8, "resourceNpc", "int"),
        new ConfigColumnMeta(9, "resourceIcon", "int"),
        new ConfigColumnMeta(10, "iconNum", "int"));
  }
}
