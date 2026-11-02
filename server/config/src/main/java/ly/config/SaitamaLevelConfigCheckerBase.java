package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SaitamaLevelConfigCheckerBase extends AbstractConfigChecker<SaitamaLevelConfig> {
  @Override
  public String getConfigFileName() {
    return "saitamaLevel.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "nextLv", "int"),
        new ConfigColumnMeta(2, "dailyGift", "int"),
        new ConfigColumnMeta(3, "dailyGiftShow", "int"),
        new ConfigColumnMeta(4, "giftTimes", "int"),
        new ConfigColumnMeta(5, "summonLevel", "int"),
        new ConfigColumnMeta(6, "exdropLevel", "int"),
        new ConfigColumnMeta(7, "cookStar", "int"),
        new ConfigColumnMeta(8, "cookmixPara", "int"),
        new ConfigColumnMeta(9, "cookCritical", "int"),
        new ConfigColumnMeta(10, "cookSlot", "int"),
        new ConfigColumnMeta(11, "overflowMax", "int"),
        new ConfigColumnMeta(12, "conversionRate", "int"),
        new ConfigColumnMeta(13, "eggRewardTimes", "int"),
        new ConfigColumnMeta(14, "trianningRewardLv", "int"),
        new ConfigColumnMeta(15, "infoList", "string"),
        new ConfigColumnMeta(16, "upgradeInfo", "string"),
        new ConfigColumnMeta(17, "headIcon", "int"));
  }
}
