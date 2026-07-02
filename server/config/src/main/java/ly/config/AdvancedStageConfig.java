package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class AdvancedStageConfig {
  /**编号*/
  public final int id;

  /**关卡类型*/
  public final int stageType;

  /**层数*/
  public final int floor;

  /**体力消耗*/
  public final int cost;

  /**预支体力消耗*/
  public final int advance;

  /**关卡id*/
  public final int sceneId;

  /**倾向选择*/
  public final int dropSelection;

  /**掉落预览*/
  public final String dropList;

  /**是否有up图标*/
  public final String upIcon;

  /**关卡掉落*/
  public final String dropGroup;

  /**名称*/
  public final String name;

  /**解锁提示*/
  public final String lockTips;

  /**前置章节*/
  public final int preStage;

  /**后置章节*/
  public final int nextStage;

  /**解锁等级*/
  public final int needLv;

  /**boss头像*/
  public final int bossHead;

  /**形象人物模型*/
  public final String model;

  /**形象人物动作*/
  public final String action;

  /**形象人物对话*/
  public final String word;

  /**中心装饰参数1*/
  public final String decorate1;

  /**中心装饰参数2*/
  public final String decorate2;

  /**中心装饰参数3*/
  public final String decorate3;

  /**bossID*/
  public final String bossId;

  /**缩放比例*/
  public final String scaling;

  /**Y轴偏移位置*/
  public final String offset;

  /**X轴偏移位置*/
  public final String offsetX;

  /**偏移角度*/
  public final String offsetAngle;

  /**是否特殊展示动作*/
  public final String showType;

  /**掉落倾向描述*/
  public final String selectionDis;

  /**突发事件触发概率*/
  public final String trigger;

  /**spine显示模型预设资源Id*/
  public final int spineModelResId;

  /**缩放比例*/
  public final int spineScale;

  /**位置偏移(x,y)*/
  public final String spinePosOffset;

  /**spine动画*/
  public final String spineAnimation;

  /**期望掉落数量*/
  public final String dropExpect;

  /**推荐等级*/
  public final int recommendLv;

  /**推荐类型*/
  public final int recommendtype;

  /**推荐英雄*/
  public final String recommendhero;

  /**战斗提示*/
  public final String battleTipText;

  /**倾向类型*/
  public final int selectionType;

  /**排行榜枚举*/
  public final int rankType;

  /**对应功能ID*/
  public final int activityControlId;

  /**GM平台描述*/
  public final String webDes;

  /**期望掉落效率提升值*/
  public final String dropExpectAdd;

  /**推荐英雄1*/
  public final int recommendHero1;

  /**推荐英雄2*/
  public final int recommendHero2;

  /**图标类型*/
  public final int iconType;

  /**获取提示图标*/
  public final int noticeIcon;

  /**首通期望掉落数量*/
  public final int firstDropExpect;

  /**英雄试炼角色背景*/
  public final int decorate2Background;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public AdvancedStageConfig(int id, int stageType, int floor, int cost, int advance, int sceneId, int dropSelection, String dropList, String upIcon, String dropGroup, String name, String lockTips, int preStage, int nextStage, int needLv, int bossHead, String model, String action, String word, String decorate1, String decorate2, String decorate3, String bossId, String scaling, String offset, String offsetX, String offsetAngle, String showType, String selectionDis, String trigger, int spineModelResId, int spineScale, String spinePosOffset, String spineAnimation, String dropExpect, int recommendLv, int recommendtype, String recommendhero, String battleTipText, int selectionType, int rankType, int activityControlId, String webDes, String dropExpectAdd, int recommendHero1, int recommendHero2, int iconType, int noticeIcon, int firstDropExpect, int decorate2Background) {
    this.id = id;
    this.stageType = stageType;
    this.floor = floor;
    this.cost = cost;
    this.advance = advance;
    this.sceneId = sceneId;
    this.dropSelection = dropSelection;
    this.dropList = dropList;
    this.upIcon = upIcon;
    this.dropGroup = dropGroup;
    this.name = name;
    this.lockTips = lockTips;
    this.preStage = preStage;
    this.nextStage = nextStage;
    this.needLv = needLv;
    this.bossHead = bossHead;
    this.model = model;
    this.action = action;
    this.word = word;
    this.decorate1 = decorate1;
    this.decorate2 = decorate2;
    this.decorate3 = decorate3;
    this.bossId = bossId;
    this.scaling = scaling;
    this.offset = offset;
    this.offsetX = offsetX;
    this.offsetAngle = offsetAngle;
    this.showType = showType;
    this.selectionDis = selectionDis;
    this.trigger = trigger;
    this.spineModelResId = spineModelResId;
    this.spineScale = spineScale;
    this.spinePosOffset = spinePosOffset;
    this.spineAnimation = spineAnimation;
    this.dropExpect = dropExpect;
    this.recommendLv = recommendLv;
    this.recommendtype = recommendtype;
    this.recommendhero = recommendhero;
    this.battleTipText = battleTipText;
    this.selectionType = selectionType;
    this.rankType = rankType;
    this.activityControlId = activityControlId;
    this.webDes = webDes;
    this.dropExpectAdd = dropExpectAdd;
    this.recommendHero1 = recommendHero1;
    this.recommendHero2 = recommendHero2;
    this.iconType = iconType;
    this.noticeIcon = noticeIcon;
    this.firstDropExpect = firstDropExpect;
    this.decorate2Background = decorate2Background;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
