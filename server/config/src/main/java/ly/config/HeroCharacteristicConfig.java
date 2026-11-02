package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class HeroCharacteristicConfig {
  public final int id;

  public final String beizhu;

  public final int heroId;

  public final int skillGroup;

  public final java.util.List<Integer> heroFetters;

  public final int advanced;

  public final String quality;

  public final String qualityNum;

  public final String heroType;

  public final String heroTypeNum;

  public final int characterType;

  public final int characterNum;

  public final String activationDes;

  public final String des;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public HeroCharacteristicConfig(int id, String beizhu, int heroId, int skillGroup, java.util.List<Integer> heroFetters, int advanced, String quality, String qualityNum, String heroType, String heroTypeNum, int characterType, int characterNum, String activationDes, String des) {
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
