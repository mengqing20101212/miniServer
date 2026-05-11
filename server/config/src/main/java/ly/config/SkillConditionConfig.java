package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SkillConditionConfig {
  /**编号*/
  public final int id;

  /**类型*/
  public final int type;

  /**备注*/
  public final String des;

  /**效果参数*/
  public final String param_1;

  /**效果参数*/
  public final String param_2;

  /**效果参数*/
  public final String param_3;

  /**效果参数*/
  public final String param_4;

  /**效果参数*/
  public final String param_5;

  /**效果参数*/
  public final String param_6;

  /**效果参数*/
  public final String param_7;

  /**效果参数*/
  public final String param_8;

  /**效果参数*/
  public final String param_9;

  /**效果参数*/
  public final String param_10;

  /**英雄id*/
  public final int heroId;

  /**技能序列*/
  public final int skillSequence;

  /**效果序列*/
  public final int effectSequence;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SkillConditionConfig(int id, int type, String des, String param_1, String param_2, String param_3, String param_4, String param_5, String param_6, String param_7, String param_8, String param_9, String param_10, int heroId, int skillSequence, int effectSequence) {
    this.id = id;
    this.type = type;
    this.des = des;
    this.param_1 = param_1;
    this.param_2 = param_2;
    this.param_3 = param_3;
    this.param_4 = param_4;
    this.param_5 = param_5;
    this.param_6 = param_6;
    this.param_7 = param_7;
    this.param_8 = param_8;
    this.param_9 = param_9;
    this.param_10 = param_10;
    this.heroId = heroId;
    this.skillSequence = skillSequence;
    this.effectSequence = effectSequence;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
