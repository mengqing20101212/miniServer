package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class HeroAwakenConfigCheckerBase extends AbstractConfigChecker<HeroAwakenConfig> {
  @Override
  public String getConfigFileName() {
    return "heroAwaken.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "modelName", "int"),
        new ConfigColumnMeta(2, "sequence", "int"),
        new ConfigColumnMeta(3, "awakenType", "int"),
        new ConfigColumnMeta(4, "awakenskill_1", "int"),
        new ConfigColumnMeta(5, "awakenskill_2", "int"),
        new ConfigColumnMeta(6, "awakenskill_3", "int"),
        new ConfigColumnMeta(7, "awakenskill_s1", "int"),
        new ConfigColumnMeta(8, "awakenskill_s2", "int"),
        new ConfigColumnMeta(9, "moreAwakenSkill1", "int"),
        new ConfigColumnMeta(10, "moreAwakenSkill2", "int"),
        new ConfigColumnMeta(11, "moreAwakenSkill3", "int"),
        new ConfigColumnMeta(12, "attrType", "int"),
        new ConfigColumnMeta(13, "attrNum", "int"),
        new ConfigColumnMeta(14, "awakenPhase", "string"),
        new ConfigColumnMeta(15, "awakenIcon", "int"),
        new ConfigColumnMeta(16, "awakenTitle", "string"),
        new ConfigColumnMeta(17, "awakenAttrDes", "string"),
        new ConfigColumnMeta(18, "awakenItem", "string"),
        new ConfigColumnMeta(19, "awakenCurrencyType", "int"),
        new ConfigColumnMeta(20, "awakenCurrencyNum", "int"),
        new ConfigColumnMeta(21, "isReset", "int"),
        new ConfigColumnMeta(22, "retainItem", "string"),
        new ConfigColumnMeta(23, "currencyType", "int"),
        new ConfigColumnMeta(24, "currencyNum", "int"));
  }
}
