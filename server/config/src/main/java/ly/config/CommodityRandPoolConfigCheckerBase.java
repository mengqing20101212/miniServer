package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CommodityRandPoolConfigCheckerBase extends AbstractConfigChecker<CommodityRandPoolConfig> {
  @Override
  public String getConfigFileName() {
    return "commodityRandPool.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "grade", "INT"),
        new ConfigColumnMeta(2, "school", "INT"),
        new ConfigColumnMeta(3, "group", "INT"),
        new ConfigColumnMeta(4, "des", "STRING"),
        new ConfigColumnMeta(5, "poolOneLevel", "STRING"),
        new ConfigColumnMeta(6, "poolOne", "STRING"),
        new ConfigColumnMeta(7, "poolTwoLevel", "STRING"),
        new ConfigColumnMeta(8, "poolTwo", "STRING"),
        new ConfigColumnMeta(9, "poolThreeLevel", "STRING"),
        new ConfigColumnMeta(10, "poolThree", "STRING"),
        new ConfigColumnMeta(11, "poolFourLevel", "STRING"),
        new ConfigColumnMeta(12, "poolFour", "STRING"),
        new ConfigColumnMeta(13, "poolFiveLevel", "STRING"),
        new ConfigColumnMeta(14, "poolFive", "STRING"),
        new ConfigColumnMeta(15, "poolSixLevel", "STRING"),
        new ConfigColumnMeta(16, "poolSix", "STRING"),
        new ConfigColumnMeta(17, "poolSevenLevel", "STRING"),
        new ConfigColumnMeta(18, "poolSeven", "STRING"),
        new ConfigColumnMeta(19, "poolEightLevel", "STRING"),
        new ConfigColumnMeta(20, "poolEight", "STRING"));
  }
}
