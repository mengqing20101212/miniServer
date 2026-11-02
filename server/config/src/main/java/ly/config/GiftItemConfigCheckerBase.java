package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class GiftItemConfigCheckerBase extends AbstractConfigChecker<GiftItemConfig> {
  @Override
  public String getConfigFileName() {
    return "giftItem.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "description", "string"),
        new ConfigColumnMeta(2, "type", "int"),
        new ConfigColumnMeta(3, "school", "int"),
        new ConfigColumnMeta(4, "grade", "int"),
        new ConfigColumnMeta(5, "sequence", "int"),
        new ConfigColumnMeta(6, "dropGroup", "int"),
        new ConfigColumnMeta(7, "selectDropGroup", "string"),
        new ConfigColumnMeta(8, "dropSelectShow", "string"),
        new ConfigColumnMeta(9, "giftTypeShow", "string"),
        new ConfigColumnMeta(10, "typeJudge", "int"));
  }
}
