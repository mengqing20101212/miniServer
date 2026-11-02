package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class MainStoryConfigCheckerBase extends AbstractConfigChecker<MainStoryConfig> {
  @Override
  public String getConfigFileName() {
    return "mainStory.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "titile", "string"),
        new ConfigColumnMeta(3, "target", "string"),
        new ConfigColumnMeta(4, "eventIds", "string"),
        new ConfigColumnMeta(5, "chapterRes", "int"),
        new ConfigColumnMeta(6, "chapterNum", "int"),
        new ConfigColumnMeta(7, "chapterId", "int"),
        new ConfigColumnMeta(8, "requireLv", "int"),
        new ConfigColumnMeta(9, "preIds", "int"),
        new ConfigColumnMeta(10, "followID", "int"),
        new ConfigColumnMeta(11, "dropGroupId", "int"),
        new ConfigColumnMeta(12, "dropshow", "string"),
        new ConfigColumnMeta(13, "lockWord", "string"),
        new ConfigColumnMeta(14, "pic", "int"),
        new ConfigColumnMeta(15, "showId", "int"),
        new ConfigColumnMeta(16, "pic2", "int"));
  }
}
