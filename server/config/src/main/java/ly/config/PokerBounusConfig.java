package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class PokerBounusConfig {
  /**编号*/
  public final int id;

  /**名称*/
  public final String name;

  /**加分类型*/
  public final int type;

  /**需要牌型*/
  public final String member;

  /**参数*/
  public final int para;

  /**加成大分类*/
  public final int firstClass;

  /**分类级别*/
  public final int classRank;

  /**分数*/
  public final int score;

  /**关联加成提示位置*/
  public final int hintType;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public PokerBounusConfig(int id, String name, int type, String member, int para, int firstClass, int classRank, int score, int hintType) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.member = member;
    this.para = para;
    this.firstClass = firstClass;
    this.classRank = classRank;
    this.score = score;
    this.hintType = hintType;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
