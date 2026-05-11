package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class HeroSkillGainEnergyConfig {
  /**序号*/
  public final int id;

  /**英雄ID*/
  public final int heroId;

  /**备注*/
  public final String beizhu;

  /**技能位置*/
  public final int skillPos;

  /**技能等级*/
  public final int skillLevel;

  /**获得能量*/
  public final int gainEnergy;

  /**觉醒后,获得能量*/
  public final int awakeGainEnergy;

  /**关联特性*/
  public final int characteristicId;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public HeroSkillGainEnergyConfig(int id, int heroId, String beizhu, int skillPos, int skillLevel, int gainEnergy, int awakeGainEnergy, int characteristicId) {
    this.id = id;
    this.heroId = heroId;
    this.beizhu = beizhu;
    this.skillPos = skillPos;
    this.skillLevel = skillLevel;
    this.gainEnergy = gainEnergy;
    this.awakeGainEnergy = awakeGainEnergy;
    this.characteristicId = characteristicId;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
