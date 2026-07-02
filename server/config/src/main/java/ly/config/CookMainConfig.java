package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class CookMainConfig {
  /**编号*/
  public final int id;

  /**类型*/
  public final int type;

  /**name*/
  public final String name;

  /**料理图标*/
  public final int icon;

  /**星级*/
  public final int star;

  /**配方*/
  public final String recipe;

  /**所需技巧*/
  public final String cookSkill;

  /**制作后技巧增加值*/
  public final int skillExp;

  /**食谱增加技巧值*/
  public final int skillIncrease;

  /**基础奖励*/
  public final String baseReward;

  /**关联活动id*/
  public final int activityId;

  /**奖励埼玉好感度值*/
  public final int saitamaExp;

  /**实际奖励id*/
  public final int dropId;

  /**描述*/
  public final String word;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public CookMainConfig(int id, int type, String name, int icon, int star, String recipe, String cookSkill, int skillExp, int skillIncrease, String baseReward, int activityId, int saitamaExp, int dropId, String word) {
    this.id = id;
    this.type = type;
    this.name = name;
    this.icon = icon;
    this.star = star;
    this.recipe = recipe;
    this.cookSkill = cookSkill;
    this.skillExp = skillExp;
    this.skillIncrease = skillIncrease;
    this.baseReward = baseReward;
    this.activityId = activityId;
    this.saitamaExp = saitamaExp;
    this.dropId = dropId;
    this.word = word;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
