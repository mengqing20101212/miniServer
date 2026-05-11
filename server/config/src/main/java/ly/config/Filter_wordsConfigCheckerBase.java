package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class Filter_wordsConfigCheckerBase extends AbstractConfigChecker<Filter_wordsConfig> {
  @Override
  public String getConfigFileName() {
    return "filter_words.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "flag", "INT"));
  }
}
