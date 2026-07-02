package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ItemSynthesisConfigCheckerBase extends AbstractConfigChecker<ItemSynthesisConfig> {
  @Override
  public String getConfigFileName() {
    return "itemSynthesis.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "newName", "STRING"),
        new ConfigColumnMeta(2, "oldItem", "INT"),
        new ConfigColumnMeta(3, "oldName", "STRING"),
        new ConfigColumnMeta(4, "needNum", "INT"),
        new ConfigColumnMeta(5, "level", "INT"),
        new ConfigColumnMeta(6, "costItem", "INT"),
        new ConfigColumnMeta(7, "costNum", "INT"));
  }
}
