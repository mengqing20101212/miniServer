package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class GuildLevelConfigCheckerBase extends AbstractConfigChecker<GuildLevelConfig> {
  @Override
  public String getConfigFileName() {
    return "guildLevel.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "level", "INT"),
        new ConfigColumnMeta(2, "fundsCost", "INT"),
        new ConfigColumnMeta(3, "donateNum", "INT"),
        new ConfigColumnMeta(4, "Contribution", "STRING"),
        new ConfigColumnMeta(5, "contributionMax", "INT"),
        new ConfigColumnMeta(6, "maintainCost", "INT"),
        new ConfigColumnMeta(7, "maintainCostUrgent", "INT"),
        new ConfigColumnMeta(8, "numberLimit", "INT"),
        new ConfigColumnMeta(9, "officialLimit", "STRING"),
        new ConfigColumnMeta(10, "functionList", "STRING"),
        new ConfigColumnMeta(11, "wealShopLv", "INT"),
        new ConfigColumnMeta(12, "prestigeShopLV", "INT"),
        new ConfigColumnMeta(13, "badgeMax", "INT"),
        new ConfigColumnMeta(14, "dailyAffordableNum", "INT"),
        new ConfigColumnMeta(15, "dailyLimitNum", "INT"),
        new ConfigColumnMeta(16, "weeklyAffordableNum", "INT"),
        new ConfigColumnMeta(17, "weeklyLimitNum", "INT"),
        new ConfigColumnMeta(18, "refreshShopNum", "INT"),
        new ConfigColumnMeta(19, "refreshShopPositionNum", "INT"),
        new ConfigColumnMeta(20, "refreshShopBuyNum", "INT"),
        new ConfigColumnMeta(21, "giftBagNum", "STRING"));
  }
}
