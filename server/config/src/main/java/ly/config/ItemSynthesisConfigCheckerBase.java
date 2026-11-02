package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ItemSynthesisConfigCheckerBase extends AbstractConfigChecker<ItemSynthesisConfig> {
  @Override
  public String getConfigFileName() {
    return "itemSynthesis.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "newName", "string"),
        new ConfigColumnMeta(2, "oldItem", "int"),
        new ConfigColumnMeta(3, "oldName", "string"),
        new ConfigColumnMeta(4, "needNum", "int"),
        new ConfigColumnMeta(5, "level", "int"),
        new ConfigColumnMeta(6, "costItem", "int"),
        new ConfigColumnMeta(7, "costNum", "int"));
  }
}
