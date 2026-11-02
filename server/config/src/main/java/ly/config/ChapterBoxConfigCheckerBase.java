package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ChapterBoxConfigCheckerBase extends AbstractConfigChecker<ChapterBoxConfig> {
  @Override
  public String getConfigFileName() {
    return "chapterBox.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "chapterMainId", "int"),
        new ConfigColumnMeta(2, "drop", "int"),
        new ConfigColumnMeta(3, "dropShow", "int"),
        new ConfigColumnMeta(4, "patterntype", "int"));
  }
}
