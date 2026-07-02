package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class HeroCharacteristicConfig {
  /**编号*/
  public final int id;

  /**备注*/
  public final String beizhu;

  /**角色ID*/
  public final int heroId;

  /**技能组*/
  public final int skillGroup;

  /**羁绊角色*/
  public final String heroFetters;

  /**星级*/
  public final int advanced;

  /**品质*/
  public final String quality;

  /**数量*/
  public final String qualityNum;

  /**类型*/
  public final String heroType;

  /**数量*/
  public final String heroTypeNum;

  /**角色类别*/
  public final int characterType;

  /**角色类别数量*/
  public final int characterNum;

  /**激活需求*/
  public final String activationDes;

  /**技能描述*/
  public final String des;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public HeroCharacteristicConfig(int id, String beizhu, int heroId, int skillGroup, String heroFetters, int advanced, String quality, String qualityNum, String heroType, String heroTypeNum, int characterType, int characterNum, String activationDes, String des) {
    this.id = id;
    this.beizhu = beizhu;
    this.heroId = heroId;
    this.skillGroup = skillGroup;
    this.heroFetters = heroFetters;
    this.advanced = advanced;
    this.quality = quality;
    this.qualityNum = qualityNum;
    this.heroType = heroType;
    this.heroTypeNum = heroTypeNum;
    this.characterType = characterType;
    this.characterNum = characterNum;
    this.activationDes = activationDes;
    this.des = des;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
