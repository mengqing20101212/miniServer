package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SupportTeamExpConfig {
  /**升级模板id*/
  public final int modelId;

  /**等级*/
  public final int level;

  /**所需经验*/
  public final int exp;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SupportTeamExpConfig(int modelId, int level, int exp) {
    this.modelId = modelId;
    this.level = level;
    this.exp = exp;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
