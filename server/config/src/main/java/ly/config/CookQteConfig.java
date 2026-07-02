package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class CookQteConfig {
  /**编号*/
  public final int id;

  /**类型*/
  public final int type;

  /**参数*/
  public final String qtePara;

  /**加成系数（%）*/
  public final String bonusList;

  /**分数区间*/
  public final String pointList;

  /**轮次*/
  public final int turn;

  /**单圈时间（秒）*/
  public final String time;

  /**模块区间*/
  public final String setList;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public CookQteConfig(int id, int type, String qtePara, String bonusList, String pointList, int turn, String time, String setList) {
    this.id = id;
    this.type = type;
    this.qtePara = qtePara;
    this.bonusList = bonusList;
    this.pointList = pointList;
    this.turn = turn;
    this.time = time;
    this.setList = setList;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
