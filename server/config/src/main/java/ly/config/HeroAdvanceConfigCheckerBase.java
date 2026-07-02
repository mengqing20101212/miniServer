package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class HeroAdvanceConfigCheckerBase extends AbstractConfigChecker<HeroAdvanceConfig> {
  @Override
  public String getConfigFileName() {
    return "heroAdvance.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "modelName", "INT"),
        new ConfigColumnMeta(2, "sequence", "INT"),
        new ConfigColumnMeta(3, "attrType", "STRING"),
        new ConfigColumnMeta(4, "attrNum", "STRING"),
        new ConfigColumnMeta(5, "skillPoint", "INT"),
        new ConfigColumnMeta(6, "skillPointNum", "INT"),
        new ConfigColumnMeta(7, "advanceItem", "STRING"),
        new ConfigColumnMeta(8, "replaceItems", "INT"),
        new ConfigColumnMeta(9, "describe", "LIST"),
        new ConfigColumnMeta(10, "getItem", "INT"),
        new ConfigColumnMeta(11, "drop", "INT"),
        new ConfigColumnMeta(12, "dropShow", "INT"),
        new ConfigColumnMeta(13, "itemDes", "STRING"));
  }
}
