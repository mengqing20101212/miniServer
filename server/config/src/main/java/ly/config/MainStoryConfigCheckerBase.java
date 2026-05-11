package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class MainStoryConfigCheckerBase extends AbstractConfigChecker<MainStoryConfig> {
  @Override
  public String getConfigFileName() {
    return "mainStory.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "titile", "STRING"),
        new ConfigColumnMeta(3, "target", "STRING"),
        new ConfigColumnMeta(4, "eventIds", "STRING"),
        new ConfigColumnMeta(5, "chapterRes", "INT"),
        new ConfigColumnMeta(6, "chapterNum", "INT"),
        new ConfigColumnMeta(7, "chapterId", "INT"),
        new ConfigColumnMeta(8, "requireLv", "INT"),
        new ConfigColumnMeta(9, "preIds", "INT"),
        new ConfigColumnMeta(10, "followID", "INT"),
        new ConfigColumnMeta(11, "dropGroupId", "INT"),
        new ConfigColumnMeta(12, "dropshow", "STRING"),
        new ConfigColumnMeta(13, "lockWord", "STRING"),
        new ConfigColumnMeta(14, "pic", "INT"),
        new ConfigColumnMeta(15, "showId", "INT"),
        new ConfigColumnMeta(16, "pic2", "INT"));
  }
}
