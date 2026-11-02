package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class GuideNoteConfig {
  public final int id;

  public final java.util.List<Integer> guideCond;

  public final String guidePara1;

  public final String linkPart;

  public final int linkParam;

  public final int uiType;

  public final String linkShift;

  public final int noticeType;

  public final String word;

  public final java.util.List<Integer> endType;

  public final String endPara;

  public final int turnId;

  public final String beizhu;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public GuideNoteConfig(int id, java.util.List<Integer> guideCond, String guidePara1, String linkPart, int linkParam, int uiType, String linkShift, int noticeType, String word, java.util.List<Integer> endType, String endPara, int turnId, String beizhu) {
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
