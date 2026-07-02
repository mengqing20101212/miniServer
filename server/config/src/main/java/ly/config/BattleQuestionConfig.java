package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class BattleQuestionConfig {
  /**ID*/
  public final int id;

  /**题干*/
  public final String questionContent;

  /**标题图片*/
  public final int questionPic;

  /**正确答案*/
  public final int answer;

  /**选项1文字*/
  public final String answer1;

  /**选项1反馈文字*/
  public final String answerResult1;

  /**选项1效果*/
  public final int answerEffect1;

  /**选项2文字*/
  public final String answer2;

  /**选项2反馈文字*/
  public final String answerResult2;

  /**选项2效果*/
  public final int answerEffect2;

  /**选项3文字*/
  public final String answer3;

  /**选项3反馈文字*/
  public final String answerResult3;

  /**选项3效果*/
  public final int answerEffect3;

  /**选项4文字*/
  public final String answer4;

  /**选项4反馈文字*/
  public final String answerResult4;

  /**选项4效果*/
  public final int answerEffect4;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public BattleQuestionConfig(int id, String questionContent, int questionPic, int answer, String answer1, String answerResult1, int answerEffect1, String answer2, String answerResult2, int answerEffect2, String answer3, String answerResult3, int answerEffect3, String answer4, String answerResult4, int answerEffect4) {
    this.id = id;
    this.questionContent = questionContent;
    this.questionPic = questionPic;
    this.answer = answer;
    this.answer1 = answer1;
    this.answerResult1 = answerResult1;
    this.answerEffect1 = answerEffect1;
    this.answer2 = answer2;
    this.answerResult2 = answerResult2;
    this.answerEffect2 = answerEffect2;
    this.answer3 = answer3;
    this.answerResult3 = answerResult3;
    this.answerEffect3 = answerEffect3;
    this.answer4 = answer4;
    this.answerResult4 = answerResult4;
    this.answerEffect4 = answerEffect4;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
