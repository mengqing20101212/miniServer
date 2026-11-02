package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ChapterRequestConfigCheckerBase extends AbstractConfigChecker<ChapterRequestConfig> {
  @Override
  public String getConfigFileName() {
    return "chapterRequest.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "patterntype", "int"),
        new ConfigColumnMeta(2, "content", "string"),
        new ConfigColumnMeta(3, "beizhu", "string"),
        new ConfigColumnMeta(4, "dropGroup", "int"),
        new ConfigColumnMeta(5, "firstDrop", "int"),
        new ConfigColumnMeta(6, "target", "string"),
        new ConfigColumnMeta(7, "result", "string"));
  }
}
