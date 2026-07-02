package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SupportTeamResumeConfig {
  /**编号（任务id）*/
  public final int id;

  /**奖励*/
  public final int dropId;

  /**奖励展示*/
  public final int dropShow;

  /**所属组组（1战斗，2成长，3游历）*/
  public final int group;

  /**对应英雄id*/
  public final int heroId;

  /**备注*/
  public final String beizhu;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SupportTeamResumeConfig(int id, int dropId, int dropShow, int group, int heroId, String beizhu) {
    this.id = id;
    this.dropId = dropId;
    this.dropShow = dropShow;
    this.group = group;
    this.heroId = heroId;
    this.beizhu = beizhu;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
