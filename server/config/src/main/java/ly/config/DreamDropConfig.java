package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class DreamDropConfig {
  /**编号*/
  public final int id;

  /**梦境*/
  public final int dreamMainId;

  /**评价*/
  public final String appraise;

  /**我方行动回合数*/
  public final int heroCount;

  /**奖励预览*/
  public final String dropShow;

  /**关卡掉落*/
  public final int drop;

  /**结算评价*/
  public final int settlement;

  /**显示内容*/
  public final String display;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public DreamDropConfig(int id, int dreamMainId, String appraise, int heroCount, String dropShow, int drop, int settlement, String display) {
    this.id = id;
    this.dreamMainId = dreamMainId;
    this.appraise = appraise;
    this.heroCount = heroCount;
    this.dropShow = dropShow;
    this.drop = drop;
    this.settlement = settlement;
    this.display = display;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
