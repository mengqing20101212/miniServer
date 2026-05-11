package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class GuideMainConfig {
  /**编号*/
  public final int id;

  /**备注*/
  public final String beizhu;

  /**是否自动触发下一步*/
  public final int checkNext;

  /**引导触发条件*/
  public final String guideCond;

  /**触发参数1*/
  public final String guidePara1;

  /**是否有遮罩*/
  public final int isMask;

  /**步骤列表*/
  public final String stepList;

  /**关键步骤*/
  public final int keyStep;

  /**提前完成类型*/
  public final int vanishType;

  /**提前完成参数*/
  public final String vanishPara;

  /**引导是否可以跳过*/
  public final int canSkip;

  /**引导权重*/
  public final int weight;

  /**是否是单机*/
  public final int isLocal;

  /**是否自动触发*/
  public final int autoTrigger;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public GuideMainConfig(int id, String beizhu, int checkNext, String guideCond, String guidePara1, int isMask, String stepList, int keyStep, int vanishType, String vanishPara, int canSkip, int weight, int isLocal, int autoTrigger) {
    this.id = id;
    this.beizhu = beizhu;
    this.checkNext = checkNext;
    this.guideCond = guideCond;
    this.guidePara1 = guidePara1;
    this.isMask = isMask;
    this.stepList = stepList;
    this.keyStep = keyStep;
    this.vanishType = vanishType;
    this.vanishPara = vanishPara;
    this.canSkip = canSkip;
    this.weight = weight;
    this.isLocal = isLocal;
    this.autoTrigger = autoTrigger;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
