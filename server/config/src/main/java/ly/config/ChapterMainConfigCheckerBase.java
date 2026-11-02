package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ChapterMainConfigCheckerBase extends AbstractConfigChecker<ChapterMainConfig> {
  @Override
  public String getConfigFileName() {
    return "chapterMain.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "patterntype", "int"),
        new ConfigColumnMeta(2, "storyName", "string"),
        new ConfigColumnMeta(3, "chapterShow", "string"),
        new ConfigColumnMeta(4, "unlockLv", "int"),
        new ConfigColumnMeta(5, "storyId", "int"),
        new ConfigColumnMeta(6, "nextChapter", "int"),
        new ConfigColumnMeta(7, "lastChapter", "int"),
        new ConfigColumnMeta(8, "chapterStage", "string"),
        new ConfigColumnMeta(9, "starReward", "string"),
        new ConfigColumnMeta(10, "rewardShow", "string"),
        new ConfigColumnMeta(11, "unlockWord", "string"),
        new ConfigColumnMeta(12, "background", "int"),
        new ConfigColumnMeta(13, "introduce", "string"));
  }
}
