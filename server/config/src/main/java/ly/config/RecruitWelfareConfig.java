package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class RecruitWelfareConfig {
  /**编号*/
  public final int id;

  /**保底品质*/
  public final String welfareQuality;

  /**品质保底次数*/
  public final String qualityWelfareInterval;

  /**品质保底奖池*/
  public final String qualityWelfareAwardId;

  /**保底英雄*/
  public final int welfareHero;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public RecruitWelfareConfig(int id, String welfareQuality, String qualityWelfareInterval, String qualityWelfareAwardId, int welfareHero) {
    this.id = id;
    this.welfareQuality = welfareQuality;
    this.qualityWelfareInterval = qualityWelfareInterval;
    this.qualityWelfareAwardId = qualityWelfareAwardId;
    this.welfareHero = welfareHero;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
