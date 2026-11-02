package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class GuideMainConfig {
  public final int id;

  public final String beizhu;

  public final int checkNext;

  public final java.util.List<Integer> guideCond;

  public final String guidePara1;

  public final int isMask;

  public final java.util.List<Integer> stepList;

  public final int keyStep;

  public final int vanishType;

  public final java.util.List<Integer> vanishPara;

  public final int canSkip;

  public final int weight;

  public final int isLocal;

  public final int autoTrigger;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public GuideMainConfig(int id, String beizhu, int checkNext, java.util.List<Integer> guideCond, String guidePara1, int isMask, java.util.List<Integer> stepList, int keyStep, int vanishType, java.util.List<Integer> vanishPara, int canSkip, int weight, int isLocal, int autoTrigger) {
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
