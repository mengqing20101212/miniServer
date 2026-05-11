package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SkinDrawingConfig {
  /**id*/
  public final int id;

  /**对应英雄*/
  public final int heroId;

  /**名字*/
  public final String name;

  /**皮肤名称*/
  public final String skinName;

  /**获取途径*/
  public final String skinGet;

  /**跳转ID*/
  public final int turnId;

  /**行动序列头像*/
  public final int headResource;

  /**右侧头像*/
  public final int headResource_2;

  /**立绘头像*/
  public final int headResource_3;

  /**全身立绘*/
  public final int headResource_4;

  /**s技能立绘切割坐标*/
  public final String sSkillCutUp;

  /**s技能跳过立绘切割坐标*/
  public final String sSkipCutUp;

  /**512尺寸半身像*/
  public final int cardBust;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SkinDrawingConfig(int id, int heroId, String name, String skinName, String skinGet, int turnId, int headResource, int headResource_2, int headResource_3, int headResource_4, String sSkillCutUp, String sSkipCutUp, int cardBust) {
    this.id = id;
    this.heroId = heroId;
    this.name = name;
    this.skinName = skinName;
    this.skinGet = skinGet;
    this.turnId = turnId;
    this.headResource = headResource;
    this.headResource_2 = headResource_2;
    this.headResource_3 = headResource_3;
    this.headResource_4 = headResource_4;
    this.sSkillCutUp = sSkillCutUp;
    this.sSkipCutUp = sSkipCutUp;
    this.cardBust = cardBust;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
