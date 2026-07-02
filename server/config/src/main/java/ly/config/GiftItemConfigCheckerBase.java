package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class GiftItemConfigCheckerBase extends AbstractConfigChecker<GiftItemConfig> {
  @Override
  public String getConfigFileName() {
    return "giftItem.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "description", "STRING"),
        new ConfigColumnMeta(2, "type", "INT"),
        new ConfigColumnMeta(3, "school", "INT"),
        new ConfigColumnMeta(4, "grade", "INT"),
        new ConfigColumnMeta(5, "sequence", "INT"),
        new ConfigColumnMeta(6, "dropGroup", "INT"),
        new ConfigColumnMeta(7, "selectDropGroup", "STRING"),
        new ConfigColumnMeta(8, "dropSelectShow", "STRING"),
        new ConfigColumnMeta(9, "giftTypeShow", "STRING"),
        new ConfigColumnMeta(10, "typeJudge", "INT"));
  }
}
