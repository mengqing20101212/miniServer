package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SkillGroupConfig {
  public final int id;

  public final String name;

  public final String description;

  public final String skillList;

  public final java.util.List<Integer> skillIcon;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SkillGroupConfig(int id, String name, String description, String skillList, java.util.List<Integer> skillIcon) {
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
