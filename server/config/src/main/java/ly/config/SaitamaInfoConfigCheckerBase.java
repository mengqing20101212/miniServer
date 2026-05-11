package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SaitamaInfoConfigCheckerBase extends AbstractConfigChecker<SaitamaInfoConfig> {
  @Override
  public String getConfigFileName() {
    return "saitamaInfo.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "title", "STRING"),
        new ConfigColumnMeta(2, "icon", "INT"),
        new ConfigColumnMeta(3, "label", "STRING"),
        new ConfigColumnMeta(4, "word", "STRING"),
        new ConfigColumnMeta(5, "preWord", "STRING"));
  }
}
