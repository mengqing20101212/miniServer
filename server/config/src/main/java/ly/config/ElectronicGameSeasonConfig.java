package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ElectronicGameSeasonConfig {
  /**编号*/
  public final int id;

  /**大段位*/
  public final int duanBig;

  /**小段位*/
  public final int duanSmall;

  /**备注*/
  public final String duan;

  /**每赛季结算奖励*/
  public final int awardSeason;

  /**每赛季结算奖励预览*/
  public final String awardSeasonPre;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ElectronicGameSeasonConfig(int id, int duanBig, int duanSmall, String duan, int awardSeason, String awardSeasonPre) {
    this.id = id;
    this.duanBig = duanBig;
    this.duanSmall = duanSmall;
    this.duan = duan;
    this.awardSeason = awardSeason;
    this.awardSeasonPre = awardSeasonPre;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
