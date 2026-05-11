package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SupportTeamInfoConfig {
  /**编号*/
  public final int id;

  /**应援团名称*/
  public final String teamName;

  /**应援技能*/
  public final String teamSkill;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SupportTeamInfoConfig(int id, String teamName, String teamSkill) {
    this.id = id;
    this.teamName = teamName;
    this.teamSkill = teamSkill;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
