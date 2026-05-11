package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SkillGroupConfig {
  /**技能组ID*/
  public final int id;

  /**技能组名称*/
  public final String name;

  /**技能组描述*/
  public final String description;

  /**技能组*/
  public final String skillList;

  /**技能图标*/
  public final String skillIcon;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SkillGroupConfig(int id, String name, String description, String skillList, String skillIcon) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.skillList = skillList;
    this.skillIcon = skillIcon;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
