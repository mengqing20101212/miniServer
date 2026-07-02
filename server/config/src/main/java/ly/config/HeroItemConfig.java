package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class HeroItemConfig {
  /**编号*/
  public final int id;

  /**物品名称*/
  public final String name;

  /**类型*/
  public final int type;

  /**一级分类*/
  public final int school;

  /**二级分类*/
  public final int grade;

  /**三级分类*/
  public final int sequence;

  /**解锁英雄ID*/
  public final int heroId;

  /**分解返还*/
  public final String decomposeItemId;

  /**分解消耗货币类型*/
  public final int currencyType;

  /**分解消耗货币数量*/
  public final int currencyNum;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public HeroItemConfig(int id, String name, int type, int school, int grade, int sequence, int heroId, String decomposeItemId, int currencyType, int currencyNum) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.school = school;
    this.grade = grade;
    this.sequence = sequence;
    this.heroId = heroId;
    this.decomposeItemId = decomposeItemId;
    this.currencyType = currencyType;
    this.currencyNum = currencyNum;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
