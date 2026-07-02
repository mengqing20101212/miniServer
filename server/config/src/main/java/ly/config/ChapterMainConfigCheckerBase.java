package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ChapterMainConfigCheckerBase extends AbstractConfigChecker<ChapterMainConfig> {
  @Override
  public String getConfigFileName() {
    return "chapterMain.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "patterntype", "INT"),
        new ConfigColumnMeta(2, "storyName", "STRING"),
        new ConfigColumnMeta(3, "chapterShow", "STRING"),
        new ConfigColumnMeta(4, "unlockLv", "INT"),
        new ConfigColumnMeta(5, "storyId", "INT"),
        new ConfigColumnMeta(6, "nextChapter", "INT"),
        new ConfigColumnMeta(7, "lastChapter", "INT"),
        new ConfigColumnMeta(8, "chapterStage", "STRING"),
        new ConfigColumnMeta(9, "starReward", "STRING"),
        new ConfigColumnMeta(10, "rewardShow", "STRING"),
        new ConfigColumnMeta(11, "unlockWord", "STRING"),
        new ConfigColumnMeta(12, "background", "INT"),
        new ConfigColumnMeta(13, "introduce", "STRING"));
  }
}
