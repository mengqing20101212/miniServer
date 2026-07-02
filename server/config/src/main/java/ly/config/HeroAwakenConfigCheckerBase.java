package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class HeroAwakenConfigCheckerBase extends AbstractConfigChecker<HeroAwakenConfig> {
  @Override
  public String getConfigFileName() {
    return "heroAwaken.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "modelName", "INT"),
        new ConfigColumnMeta(2, "sequence", "INT"),
        new ConfigColumnMeta(3, "awakenType", "INT"),
        new ConfigColumnMeta(4, "awakenskill_1", "INT"),
        new ConfigColumnMeta(5, "awakenskill_2", "INT"),
        new ConfigColumnMeta(6, "awakenskill_3", "INT"),
        new ConfigColumnMeta(7, "awakenskill_s1", "INT"),
        new ConfigColumnMeta(8, "awakenskill_s2", "INT"),
        new ConfigColumnMeta(9, "moreAwakenSkill1", "INT"),
        new ConfigColumnMeta(10, "moreAwakenSkill2", "INT"),
        new ConfigColumnMeta(11, "moreAwakenSkill3", "INT"),
        new ConfigColumnMeta(12, "attrType", "INT"),
        new ConfigColumnMeta(13, "attrNum", "INT"),
        new ConfigColumnMeta(14, "awakenPhase", "STRING"),
        new ConfigColumnMeta(15, "awakenIcon", "INT"),
        new ConfigColumnMeta(16, "awakenTitle", "STRING"),
        new ConfigColumnMeta(17, "awakenAttrDes", "STRING"),
        new ConfigColumnMeta(18, "awakenItem", "STRING"),
        new ConfigColumnMeta(19, "awakenCurrencyType", "INT"),
        new ConfigColumnMeta(20, "awakenCurrencyNum", "INT"),
        new ConfigColumnMeta(21, "isReset", "INT"),
        new ConfigColumnMeta(22, "retainItem", "STRING"),
        new ConfigColumnMeta(23, "currencyType", "INT"),
        new ConfigColumnMeta(24, "currencyNum", "INT"));
  }
}
