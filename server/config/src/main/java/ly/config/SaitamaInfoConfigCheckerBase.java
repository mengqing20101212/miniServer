package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SaitamaInfoConfigCheckerBase extends AbstractConfigChecker<SaitamaInfoConfig> {
  @Override
  public String getConfigFileName() {
    return "saitamaInfo.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "title", "string"),
        new ConfigColumnMeta(2, "icon", "int"),
        new ConfigColumnMeta(3, "label", "string"),
        new ConfigColumnMeta(4, "word", "string"),
        new ConfigColumnMeta(5, "preWord", "string"));
  }
}
