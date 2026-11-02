package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class HeroAdvanceConfigCheckerBase extends AbstractConfigChecker<HeroAdvanceConfig> {
  @Override
  public String getConfigFileName() {
    return "heroAdvance.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "modelName", "int"),
        new ConfigColumnMeta(2, "sequence", "int"),
        new ConfigColumnMeta(3, "attrType", "string"),
        new ConfigColumnMeta(4, "attrNum", "string"),
        new ConfigColumnMeta(5, "skillPoint", "int"),
        new ConfigColumnMeta(6, "skillPointNum", "int"),
        new ConfigColumnMeta(7, "advanceItem", "string"),
        new ConfigColumnMeta(8, "replaceItems", "int"),
        new ConfigColumnMeta(9, "describe", "string"),
        new ConfigColumnMeta(10, "getItem", "int"),
        new ConfigColumnMeta(11, "drop", "int"),
        new ConfigColumnMeta(12, "dropShow", "int"),
        new ConfigColumnMeta(13, "itemDes", "string"));
  }
}
