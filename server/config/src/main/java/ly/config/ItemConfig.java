package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ItemConfig {
  /**编号*/
  public final int id;

  /**物品名称*/
  public final String name;

  /**物品描述*/
  public final String description;

  /**索引名*/
  public final String index;

  /**物品图标*/
  public final int icon;

  /**主源核辅助图标*/
  public final int subIcon;

  /**品质*/
  public final int quality;

  /**类型*/
  public final int type;

  /**类型标签*/
  public final int bagTag;

  /**一级分类*/
  public final int school;

  /**二级分类*/
  public final int grade;

  /**三级分类*/
  public final int sequence;

  /**堆叠数量*/
  public final int stack;

  /**存在时间类型*/
  public final int existType;

  /**存在时间*/
  public final int existTime;

  /**是否能出售*/
  public final int canSell;

  /**出售获得物品*/
  public final String sellItem;

  /**是否获得后在服务器使用*/
  public final int onlyServer;

  /**在背包中分类*/
  public final int knapsackType;

  /**获取途径*/
  public final String accessWay;

  /**是否可以使用*/
  public final int useType;

  /**使用跳转*/
  public final int turnId;

  /**新物品提示*/
  public final int newTips;

  /**使用触发函数*/
  public final String func;

  /**级别*/
  public final int level;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ItemConfig(int id, String name, String description, String index, int icon, int subIcon, int quality, int type, int bagTag, int school, int grade, int sequence, int stack, int existType, int existTime, int canSell, String sellItem, int onlyServer, int knapsackType, String accessWay, int useType, int turnId, int newTips, String func, int level) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.index = index;
    this.icon = icon;
    this.subIcon = subIcon;
    this.quality = quality;
    this.type = type;
    this.bagTag = bagTag;
    this.school = school;
    this.grade = grade;
    this.sequence = sequence;
    this.stack = stack;
    this.existType = existType;
    this.existTime = existTime;
    this.canSell = canSell;
    this.sellItem = sellItem;
    this.onlyServer = onlyServer;
    this.knapsackType = knapsackType;
    this.accessWay = accessWay;
    this.useType = useType;
    this.turnId = turnId;
    this.newTips = newTips;
    this.func = func;
    this.level = level;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
