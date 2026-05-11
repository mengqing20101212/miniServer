package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SceneGradeConfig {
  /**id*/
  public final int id;

  /**关卡备注*/
  public final String beizhu;

  /**SSS评级*/
  public final int grade1;

  /**SS评级*/
  public final int grade2;

  /**S评级*/
  public final int grade3;

  /**A评级*/
  public final int grade4;

  /**B评级*/
  public final int grade5;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SceneGradeConfig(int id, String beizhu, int grade1, int grade2, int grade3, int grade4, int grade5) {
    this.id = id;
    this.beizhu = beizhu;
    this.grade1 = grade1;
    this.grade2 = grade2;
    this.grade3 = grade3;
    this.grade4 = grade4;
    this.grade5 = grade5;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
