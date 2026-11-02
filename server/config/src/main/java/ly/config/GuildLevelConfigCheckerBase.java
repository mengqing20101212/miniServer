package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class GuildLevelConfigCheckerBase extends AbstractConfigChecker<GuildLevelConfig> {
  @Override
  public String getConfigFileName() {
    return "guildLevel.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "level", "int"),
        new ConfigColumnMeta(2, "fundsCost", "int"),
        new ConfigColumnMeta(3, "donateNum", "int"),
        new ConfigColumnMeta(4, "Contribution", "string"),
        new ConfigColumnMeta(5, "contributionMax", "int"),
        new ConfigColumnMeta(6, "maintainCost", "int"),
        new ConfigColumnMeta(7, "maintainCostUrgent", "int"),
        new ConfigColumnMeta(8, "numberLimit", "int"),
        new ConfigColumnMeta(9, "officialLimit", "string"),
        new ConfigColumnMeta(10, "functionList", "string"),
        new ConfigColumnMeta(11, "wealShopLv", "int"),
        new ConfigColumnMeta(12, "prestigeShopLV", "int"),
        new ConfigColumnMeta(13, "badgeMax", "int"),
        new ConfigColumnMeta(14, "dailyAffordableNum", "int"),
        new ConfigColumnMeta(15, "dailyLimitNum", "int"),
        new ConfigColumnMeta(16, "weeklyAffordableNum", "int"),
        new ConfigColumnMeta(17, "weeklyLimitNum", "int"),
        new ConfigColumnMeta(18, "refreshShopNum", "int"),
        new ConfigColumnMeta(19, "refreshShopPositionNum", "int"),
        new ConfigColumnMeta(20, "refreshShopBuyNum", "int"),
        new ConfigColumnMeta(21, "giftBagNum", "string"));
  }
}
