package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SaitamaLevelConfigCheckerBase extends AbstractConfigChecker<SaitamaLevelConfig> {
  @Override
  public String getConfigFileName() {
    return "saitamaLevel.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "nextLv", "INT"),
        new ConfigColumnMeta(2, "dailyGift", "INT"),
        new ConfigColumnMeta(3, "dailyGiftShow", "INT"),
        new ConfigColumnMeta(4, "giftTimes", "INT"),
        new ConfigColumnMeta(5, "summonLevel", "INT"),
        new ConfigColumnMeta(6, "exdropLevel", "INT"),
        new ConfigColumnMeta(7, "cookStar", "INT"),
        new ConfigColumnMeta(8, "cookmixPara", "INT"),
        new ConfigColumnMeta(9, "cookCritical", "INT"),
        new ConfigColumnMeta(10, "cookSlot", "INT"),
        new ConfigColumnMeta(11, "overflowMax", "INT"),
        new ConfigColumnMeta(12, "conversionRate", "INT"),
        new ConfigColumnMeta(13, "eggRewardTimes", "INT"),
        new ConfigColumnMeta(14, "trianningRewardLv", "INT"),
        new ConfigColumnMeta(15, "infoList", "STRING"),
        new ConfigColumnMeta(16, "upgradeInfo", "STRING"),
        new ConfigColumnMeta(17, "headIcon", "INT"));
  }
}
