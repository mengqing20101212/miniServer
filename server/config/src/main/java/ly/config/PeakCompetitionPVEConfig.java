package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class PeakCompetitionPVEConfig {
  /**编号*/
  public final int id;

  /**据点名称*/
  public final String baseName;

  /**解锁等级*/
  public final int unlockLv;

  /**对应关卡*/
  public final String baseStage;

  /**展示ICON*/
  public final int icon;

  /**关卡描述*/
  public final String stageDec;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public PeakCompetitionPVEConfig(int id, String baseName, int unlockLv, String baseStage, int icon, String stageDec) {
    this.id = id;
    this.baseName = baseName;
    this.unlockLv = unlockLv;
    this.baseStage = baseStage;
    this.icon = icon;
    this.stageDec = stageDec;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
