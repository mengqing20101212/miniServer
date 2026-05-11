package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class GuideNoteConfig {
  /**编号*/
  public final int id;

  /**引导触发条件*/
  public final String guideCond;

  /**触发参数1*/
  public final String guidePara1;

  /**关联组件*/
  public final String linkPart;

  /**关联组件查询参数*/
  public final int linkParam;

  /**界面类型*/
  public final int uiType;

  /**出现偏移*/
  public final String linkShift;

  /**提示类型*/
  public final int noticeType;

  /**提示内容*/
  public final String word;

  /**结束条件*/
  public final String endType;

  /**结束参数*/
  public final String endPara;

  /**跳转id*/
  public final int turnId;

  /**备注*/
  public final String beizhu;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public GuideNoteConfig(int id, String guideCond, String guidePara1, String linkPart, int linkParam, int uiType, String linkShift, int noticeType, String word, String endType, String endPara, int turnId, String beizhu) {
    this.id = id;
    this.guideCond = guideCond;
    this.guidePara1 = guidePara1;
    this.linkPart = linkPart;
    this.linkParam = linkParam;
    this.uiType = uiType;
    this.linkShift = linkShift;
    this.noticeType = noticeType;
    this.word = word;
    this.endType = endType;
    this.endPara = endPara;
    this.turnId = turnId;
    this.beizhu = beizhu;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
