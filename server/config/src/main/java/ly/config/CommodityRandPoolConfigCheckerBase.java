package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CommodityRandPoolConfigCheckerBase extends AbstractConfigChecker<CommodityRandPoolConfig> {
  @Override
  public String getConfigFileName() {
    return "commodityRandPool.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "grade", "int"),
        new ConfigColumnMeta(2, "school", "int"),
        new ConfigColumnMeta(3, "group", "int"),
        new ConfigColumnMeta(4, "des", "string"),
        new ConfigColumnMeta(5, "poolOneLevel", "string"),
        new ConfigColumnMeta(6, "poolOne", "string"),
        new ConfigColumnMeta(7, "poolTwoLevel", "string"),
        new ConfigColumnMeta(8, "poolTwo", "string"),
        new ConfigColumnMeta(9, "poolThreeLevel", "string"),
        new ConfigColumnMeta(10, "poolThree", "string"),
        new ConfigColumnMeta(11, "poolFourLevel", "string"),
        new ConfigColumnMeta(12, "poolFour", "string"),
        new ConfigColumnMeta(13, "poolFiveLevel", "string"),
        new ConfigColumnMeta(14, "poolFive", "string"),
        new ConfigColumnMeta(15, "poolSixLevel", "string"),
        new ConfigColumnMeta(16, "poolSix", "string"),
        new ConfigColumnMeta(17, "poolSevenLevel", "string"),
        new ConfigColumnMeta(18, "poolSeven", "string"),
        new ConfigColumnMeta(19, "poolEightLevel", "string"),
        new ConfigColumnMeta(20, "poolEight", "string"));
  }
}
