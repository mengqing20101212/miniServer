package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ItemConfigCheckerBase extends AbstractConfigChecker<ItemConfig> {
  @Override
  public String getConfigFileName() {
    return "item.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "description", "STRING"),
        new ConfigColumnMeta(3, "index", "STRING"),
        new ConfigColumnMeta(4, "icon", "INT"),
        new ConfigColumnMeta(5, "subIcon", "INT"),
        new ConfigColumnMeta(6, "quality", "INT"),
        new ConfigColumnMeta(7, "type", "INT"),
        new ConfigColumnMeta(8, "bagTag", "INT"),
        new ConfigColumnMeta(9, "school", "INT"),
        new ConfigColumnMeta(10, "grade", "INT"),
        new ConfigColumnMeta(11, "sequence", "INT"),
        new ConfigColumnMeta(12, "stack", "INT"),
        new ConfigColumnMeta(13, "existType", "INT"),
        new ConfigColumnMeta(14, "existTime", "INT"),
        new ConfigColumnMeta(15, "canSell", "INT"),
        new ConfigColumnMeta(16, "sellItem", "STRING"),
        new ConfigColumnMeta(17, "onlyServer", "INT"),
        new ConfigColumnMeta(18, "knapsackType", "INT"),
        new ConfigColumnMeta(19, "accessWay", "STRING"),
        new ConfigColumnMeta(20, "useType", "INT"),
        new ConfigColumnMeta(21, "turnId", "INT"),
        new ConfigColumnMeta(22, "newTips", "INT"),
        new ConfigColumnMeta(23, "func", "STRING"),
        new ConfigColumnMeta(24, "level", "INT"));
  }
}
