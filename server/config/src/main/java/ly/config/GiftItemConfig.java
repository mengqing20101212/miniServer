package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class GiftItemConfig {
  /**编号*/
  public final int id;

  /**物品描述*/
  public final String description;

  /**类型*/
  public final int type;

  /**一级分类*/
  public final int school;

  /**二级分类*/
  public final int grade;

  /**三级分类*/
  public final int sequence;

  /**使用随机组*/
  public final int dropGroup;

  /**选择礼包掉落组*/
  public final String selectDropGroup;

  /**选择礼包选项*/
  public final String dropSelectShow;

  /**自选箱展示类型*/
  public final String giftTypeShow;

  /**是否是源核类型*/
  public final int typeJudge;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public GiftItemConfig(int id, String description, int type, int school, int grade, int sequence, int dropGroup, String selectDropGroup, String dropSelectShow, String giftTypeShow, int typeJudge) {
    this.id = id;
    this.description = description;
    this.type = type;
    this.school = school;
    this.grade = grade;
    this.sequence = sequence;
    this.dropGroup = dropGroup;
    this.selectDropGroup = selectDropGroup;
    this.dropSelectShow = dropSelectShow;
    this.giftTypeShow = giftTypeShow;
    this.typeJudge = typeJudge;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
