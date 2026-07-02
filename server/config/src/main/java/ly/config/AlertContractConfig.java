package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class AlertContractConfig {
  /**关卡组id*/
  public final int id;

  /**层级*/
  public final int level;

  /**敌人数值信息*/
  public final String enemyInfo;

  /**敌人源核信息*/
  public final String enemyCircuitInfo;

  /**敌人系数*/
  public final String enemyPara;

  /**实际关卡id*/
  public final String sceneId;

  /**奖励id*/
  public final int dropId;

  /**首通奖励*/
  public final int firstDrop;

  /**奖励展示*/
  public final String dropShow;

  /**关卡提示*/
  public final String hint;

  /**扫荡图片背景图片*/
  public final int tipPic;

  /**背景图片（主界面）*/
  public final int background;

  /**背景图片（关卡内布阵和敌人）*/
  public final int backgroundIn;

  /**英雄立绘资源id*/
  public final int heroPicId;

  /**推荐英雄Id组(,)*/
  public final String recommendHeroIds;

  /**推荐类型显示组(1辅 2 群  3单  4控)*/
  public final String recommendTypes;

  /**推荐阵容平均等级*/
  public final int avgLineupLevel;

  /**每日奖励*/
  public final int dropDay;

  /**每日奖励展示*/
  public final int dropDayShow;

  /**阶段层数*/
  public final int targetLevel;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public AlertContractConfig(int id, int level, String enemyInfo, String enemyCircuitInfo, String enemyPara, String sceneId, int dropId, int firstDrop, String dropShow, String hint, int tipPic, int background, int backgroundIn, int heroPicId, String recommendHeroIds, String recommendTypes, int avgLineupLevel, int dropDay, int dropDayShow, int targetLevel) {
    this.id = id;
    this.level = level;
    this.enemyInfo = enemyInfo;
    this.enemyCircuitInfo = enemyCircuitInfo;
    this.enemyPara = enemyPara;
    this.sceneId = sceneId;
    this.dropId = dropId;
    this.firstDrop = firstDrop;
    this.dropShow = dropShow;
    this.hint = hint;
    this.tipPic = tipPic;
    this.background = background;
    this.backgroundIn = backgroundIn;
    this.heroPicId = heroPicId;
    this.recommendHeroIds = recommendHeroIds;
    this.recommendTypes = recommendTypes;
    this.avgLineupLevel = avgLineupLevel;
    this.dropDay = dropDay;
    this.dropDayShow = dropDayShow;
    this.targetLevel = targetLevel;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
