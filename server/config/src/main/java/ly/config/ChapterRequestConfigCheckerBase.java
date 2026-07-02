package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ChapterRequestConfigCheckerBase extends AbstractConfigChecker<ChapterRequestConfig> {
  @Override
  public String getConfigFileName() {
    return "chapterRequest.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "patterntype", "INT"),
        new ConfigColumnMeta(2, "content", "STRING"),
        new ConfigColumnMeta(3, "beizhu", "STRING"),
        new ConfigColumnMeta(4, "dropGroup", "INT"),
        new ConfigColumnMeta(5, "firstDrop", "INT"),
        new ConfigColumnMeta(6, "target", "STRING"),
        new ConfigColumnMeta(7, "result", "STRING"));
  }
}
