package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class DictsConfigCheckerBase extends AbstractConfigChecker<DictsConfig> {
  @Override
  public String getConfigFileName() {
    return "dicts.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "desc", "STRING"),
        new ConfigColumnMeta(2, "name_cn", "STRING"),
        new ConfigColumnMeta(3, "name_en", "STRING"),
        new ConfigColumnMeta(4, "desc_en", "STRING"));
  }
}
