package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ContactQuestionConfig {
  /**编号*/
  public final int id;

  /**备注*/
  public final String beizhu;

  /**问题*/
  public final String question;

  /**问题类型*/
  public final int type;

  /**回答1*/
  public final String answer1;

  /**回答2*/
  public final String answer2;

  /**回答3*/
  public final String answer3;

  /**正确答案*/
  public final int rightAnswer;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ContactQuestionConfig(int id, String beizhu, String question, int type, String answer1, String answer2, String answer3, int rightAnswer) {
    this.id = id;
    this.beizhu = beizhu;
    this.question = question;
    this.type = type;
    this.answer1 = answer1;
    this.answer2 = answer2;
    this.answer3 = answer3;
    this.rightAnswer = rightAnswer;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
