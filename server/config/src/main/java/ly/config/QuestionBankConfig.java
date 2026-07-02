package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class QuestionBankConfig {
  /**ID*/
  public final int id;

  /**题干*/
  public final String questionContent;

  /**题干备注*/
  public final String questionNote;

  /**题目类型*/
  public final int type;

  /**题目形式*/
  public final int questionShow;

  /**题干图片*/
  public final String questionPicture;

  /**选项形式*/
  public final int optionShow;

  /**简单A选项*/
  public final String simpleA;

  /**简单B选项*/
  public final String simpleB;

  /**简单C选项*/
  public final String simpleC;

  /**简单D选项*/
  public final String simpleD;

  /**简单题答案*/
  public final String simpleAnswer;

  /**简单题备注*/
  public final String simpleNote;

  /**困难A选项*/
  public final String hardA;

  /**困难B选项*/
  public final String hardB;

  /**困难C选项*/
  public final String hardC;

  /**困难D选项*/
  public final String hardD;

  /**困难题答案*/
  public final String hardAnswer;

  /**困难题备注*/
  public final String hardNote;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public QuestionBankConfig(int id, String questionContent, String questionNote, int type, int questionShow, String questionPicture, int optionShow, String simpleA, String simpleB, String simpleC, String simpleD, String simpleAnswer, String simpleNote, String hardA, String hardB, String hardC, String hardD, String hardAnswer, String hardNote) {
    this.id = id;
    this.questionContent = questionContent;
    this.questionNote = questionNote;
    this.type = type;
    this.questionShow = questionShow;
    this.questionPicture = questionPicture;
    this.optionShow = optionShow;
    this.simpleA = simpleA;
    this.simpleB = simpleB;
    this.simpleC = simpleC;
    this.simpleD = simpleD;
    this.simpleAnswer = simpleAnswer;
    this.simpleNote = simpleNote;
    this.hardA = hardA;
    this.hardB = hardB;
    this.hardC = hardC;
    this.hardD = hardD;
    this.hardAnswer = hardAnswer;
    this.hardNote = hardNote;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
