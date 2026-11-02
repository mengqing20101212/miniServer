package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SubtitleConfigCheckerBase extends AbstractConfigChecker<SubtitleConfig> {
  @Override
  public String getConfigFileName() {
    return "subtitle.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "name", "string"),
        new ConfigColumnMeta(1, "index", "int"),
        new ConfigColumnMeta(2, "style", "list<int>"),
        new ConfigColumnMeta(3, "start_time", "float"),
        new ConfigColumnMeta(4, "end_time", "float"),
        new ConfigColumnMeta(5, "content", "string"));
  }
}
