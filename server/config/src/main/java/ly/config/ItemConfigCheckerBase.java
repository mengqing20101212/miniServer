package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ItemConfigCheckerBase extends AbstractConfigChecker<ItemConfig> {
  @Override
  public String getConfigFileName() {
    return "item.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "description", "string"),
        new ConfigColumnMeta(3, "index", "string"),
        new ConfigColumnMeta(4, "icon", "int"),
        new ConfigColumnMeta(5, "subIcon", "int"),
        new ConfigColumnMeta(6, "quality", "int"),
        new ConfigColumnMeta(7, "type", "int"),
        new ConfigColumnMeta(8, "bagTag", "int"),
        new ConfigColumnMeta(9, "school", "int"),
        new ConfigColumnMeta(10, "grade", "int"),
        new ConfigColumnMeta(11, "sequence", "int"),
        new ConfigColumnMeta(12, "stack", "int"),
        new ConfigColumnMeta(13, "existType", "int"),
        new ConfigColumnMeta(14, "existTime", "int"),
        new ConfigColumnMeta(15, "canSell", "int"),
        new ConfigColumnMeta(16, "sellItem", "string"),
        new ConfigColumnMeta(17, "onlyServer", "int"),
        new ConfigColumnMeta(18, "knapsackType", "int"),
        new ConfigColumnMeta(19, "accessWay", "string"),
        new ConfigColumnMeta(20, "useType", "int"),
        new ConfigColumnMeta(21, "turnId", "int"),
        new ConfigColumnMeta(22, "newTips", "int"),
        new ConfigColumnMeta(23, "func", "string"),
        new ConfigColumnMeta(24, "level", "int"));
  }
}
