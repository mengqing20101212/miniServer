package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class GuildLevelConfig {
  /**ID*/
  public final int id;

  /**社团等级*/
  public final int level;

  /**升级经验*/
  public final int fundsCost;

  /**捐献次数*/
  public final int donateNum;

  /**捐献获得的贡献*/
  public final String Contribution;

  /**建设度最大值*/
  public final int contributionMax;

  /**维护费用*/
  public final int maintainCost;

  /**紧急维护费用*/
  public final int maintainCostUrgent;

  /**公会人数上限*/
  public final int numberLimit;

  /**官员人数上限*/
  public final String officialLimit;

  /**功能开启列表*/
  public final String functionList;

  /**福利商店等级*/
  public final int wealShopLv;

  /**声望商店等级*/
  public final int prestigeShopLV;

  /**社团资金上限*/
  public final int badgeMax;

  /**每日超值礼包个数*/
  public final int dailyAffordableNum;

  /**每日超值礼包限购次数*/
  public final int dailyLimitNum;

  /**每周超值礼包个数*/
  public final int weeklyAffordableNum;

  /**每周超值礼包限购次数*/
  public final int weeklyLimitNum;

  /**宇宙商店刷新次数*/
  public final int refreshShopNum;

  /**宇宙商店格子数量*/
  public final int refreshShopPositionNum;

  /**宇宙商店特殊物品购买人次*/
  public final int refreshShopBuyNum;

  /**社团红包次数*/
  public final String giftBagNum;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public GuildLevelConfig(int id, int level, int fundsCost, int donateNum, String Contribution, int contributionMax, int maintainCost, int maintainCostUrgent, int numberLimit, String officialLimit, String functionList, int wealShopLv, int prestigeShopLV, int badgeMax, int dailyAffordableNum, int dailyLimitNum, int weeklyAffordableNum, int weeklyLimitNum, int refreshShopNum, int refreshShopPositionNum, int refreshShopBuyNum, String giftBagNum) {
    this.id = id;
    this.level = level;
    this.fundsCost = fundsCost;
    this.donateNum = donateNum;
    this.Contribution = Contribution;
    this.contributionMax = contributionMax;
    this.maintainCost = maintainCost;
    this.maintainCostUrgent = maintainCostUrgent;
    this.numberLimit = numberLimit;
    this.officialLimit = officialLimit;
    this.functionList = functionList;
    this.wealShopLv = wealShopLv;
    this.prestigeShopLV = prestigeShopLV;
    this.badgeMax = badgeMax;
    this.dailyAffordableNum = dailyAffordableNum;
    this.dailyLimitNum = dailyLimitNum;
    this.weeklyAffordableNum = weeklyAffordableNum;
    this.weeklyLimitNum = weeklyLimitNum;
    this.refreshShopNum = refreshShopNum;
    this.refreshShopPositionNum = refreshShopPositionNum;
    this.refreshShopBuyNum = refreshShopBuyNum;
    this.giftBagNum = giftBagNum;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
