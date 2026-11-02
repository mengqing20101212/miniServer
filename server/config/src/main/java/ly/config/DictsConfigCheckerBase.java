package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class DictsConfigCheckerBase extends AbstractConfigChecker<DictsConfig> {
  @Override
  public String getConfigFileName() {
    return "dicts.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "desc", "string"),
        new ConfigColumnMeta(2, "name_cn", "string"),
        new ConfigColumnMeta(3, "name_en", "string"),
        new ConfigColumnMeta(4, "desc_en", "string"));
  }
}
