package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SkillSeperationConfig {
  /**主身动作*/
  public final String mainAni;

  /**分身动作（动作，延时）*/
  public final String sperationAni_1;

  /**分身动作（动作，延时）*/
  public final String sperationAni_2;

  /**分身动作（动作，延时）*/
  public final String sperationAni_3;

  /**分身动作（动作，延时）*/
  public final String sperationAni_4;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SkillSeperationConfig(String mainAni, String sperationAni_1, String sperationAni_2, String sperationAni_3, String sperationAni_4) {
    this.mainAni = mainAni;
    this.sperationAni_1 = sperationAni_1;
    this.sperationAni_2 = sperationAni_2;
    this.sperationAni_3 = sperationAni_3;
    this.sperationAni_4 = sperationAni_4;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
