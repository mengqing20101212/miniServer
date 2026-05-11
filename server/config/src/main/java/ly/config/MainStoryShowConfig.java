package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class MainStoryShowConfig {
  /**ID*/
  public final int id;

  /**名称(备注)*/
  public final String name;

  /**关键人位置枚举*/
  public final int keyPos;

  /**关键人npc*/
  public final int activityNpcId;

  /**关键人动作*/
  public final String keyAction;

  /**点击后动作*/
  public final String touchAction;

  /**返回主城动作*/
  public final String returnAction;

  /**说话者名称*/
  public final String speakerName;

  /**事件问题*/
  public final String question;

  /**气泡偏移*/
  public final String bubbleShift;

  /**问题框位置偏移*/
  public final String questionShift;

  /**选项list*/
  public final String answerList;

  /**选择反馈功能枚举list*/
  public final String reactionList;

  /**变化点枚举list*/
  public final String changeList;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public MainStoryShowConfig(int id, String name, int keyPos, int activityNpcId, String keyAction, String touchAction, String returnAction, String speakerName, String question, String bubbleShift, String questionShift, String answerList, String reactionList, String changeList) {
    this.id = id;
    this.name = name;
    this.keyPos = keyPos;
    this.activityNpcId = activityNpcId;
    this.keyAction = keyAction;
    this.touchAction = touchAction;
    this.returnAction = returnAction;
    this.speakerName = speakerName;
    this.question = question;
    this.bubbleShift = bubbleShift;
    this.questionShift = questionShift;
    this.answerList = answerList;
    this.reactionList = reactionList;
    this.changeList = changeList;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
