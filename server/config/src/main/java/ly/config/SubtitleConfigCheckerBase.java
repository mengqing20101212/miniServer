package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SubtitleConfigCheckerBase extends AbstractConfigChecker<SubtitleConfig> {
  @Override
  public String getConfigFileName() {
    return "subtitle.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "name", "STRING"),
        new ConfigColumnMeta(1, "index", "INT"),
        new ConfigColumnMeta(2, "style", "LIST<INT>"),
        new ConfigColumnMeta(3, "start_time", "FLOAT"),
        new ConfigColumnMeta(4, "end_time", "FLOAT"),
        new ConfigColumnMeta(5, "content", "STRING"));
  }
}
