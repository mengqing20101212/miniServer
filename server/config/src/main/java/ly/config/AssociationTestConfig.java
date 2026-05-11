package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class AssociationTestConfig {
  /**编号*/
  public final int id;

  /**周*/
  public final int week;

  /**日*/
  public final int day;

  /**英雄ID*/
  public final String heroID;

  /**备注*/
  public final String beizhu;

  /**成功奖励*/
  public final String dropSuccess;

  /**失败奖励*/
  public final String dropFail;

  /**奖励预览*/
  public final String dropShow;

  /**测试次数*/
  public final int testTimes;

  /**每次消耗体力*/
  public final int stamina;

  /**题目池*/
  public final String questionPool;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public AssociationTestConfig(int id, int week, int day, String heroID, String beizhu, String dropSuccess, String dropFail, String dropShow, int testTimes, int stamina, String questionPool) {
    this.id = id;
    this.week = week;
    this.day = day;
    this.heroID = heroID;
    this.beizhu = beizhu;
    this.dropSuccess = dropSuccess;
    this.dropFail = dropFail;
    this.dropShow = dropShow;
    this.testTimes = testTimes;
    this.stamina = stamina;
    this.questionPool = questionPool;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
