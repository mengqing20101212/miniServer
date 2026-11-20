package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class HeroInfoConfig {
  /**编号*/
  public final int id;

  /**名字*/
  public final String name;

  /**英雄类型*/
  public final int heroType;

  /**英雄性别*/
  public final int heroSex;

  /**战斗模型*/
  public final int modelResource;

  /**战斗模型缩放*/
  public final String modelResourceScale;

  /**布阵模型*/
  public final int battleResource;

  /**展示模型*/
  public final int showResource;

  /**展示模型缩放*/
  public final String showResourceScale;

  /**行动序列头像*/
  public final int headResource;

  /**右侧头像*/
  public final int headResource_2;

  /**立绘头像*/
  public final int headResource_3;

  /**全身立绘*/
  public final int headResource_4;

  /**英雄辅助列*/
  public final String heropinyin;

  /**英雄表演*/
  public final String heroPerformance;

  /**英雄列表未获得立绘切割坐标*/
  public final String heroCutUp;

  /**s技能立绘切割坐标*/
  public final String sSkillCutUp;

  /**s技能跳过立绘切割坐标*/
  public final String sSkipCutUp;

  /**SP英雄*/
  public final int isSP;

  /**品质*/
  public final int quality;

  /**常规技能*/
  public final int skill_1;

  /**  null*/
  public final int skill_2;

  /**  null*/
  public final int skill_3;

  /**超级技能*/
  public final int skill_s1;

  /**超级技能*/
  public final int skill_s2;

  /**多段觉醒技能*/
  public final int moreAwakenSkill1;

  /**多段觉醒技能*/
  public final int moreAwakenSkill2;

  /**多段觉醒技能*/
  public final int moreAwakenSkill3;

  /**结束镜头*/
  public final String endPerformance;

  /**英雄情报*/
  public final int heroDebris;

  /**英雄抽卡台词*/
  public final int herogachadoc;

  /**英雄升级升星模板*/
  public final int heroStateData;

  /**英雄觉醒模板*/
  public final int heroAwakenData;

  /**升级经验模板id*/
  public final int expModelId;

  /**升星经验模板id*/
  public final int starModelId;

  /**回路id*/
  public final int circuitId;

  /**预加载ID*/
  public final int perloadId;

  /**英雄列表预加载ID*/
  public final int heroListPreloadId;

  /**pvp摄像机参数*/
  public final String pvpCamera;

  /**组队摄像机参数*/
  public final String teamCamera;

  /**AI类型*/
  public final String aiName;

  /**属性评级id*/
  public final int attrRankId;

  /**获取后自动上阵位置*/
  public final int getPpos;

  /**角色特殊背景板*/
  public final int speicalBg;

  /**未获得时背景板*/
  public final int unlockBg;

  /**品质图标*/
  public final int qualityIcon;

  /**角色的功能NPC_ID*/
  public final int activityNpcId;

  /**英雄模型背景图*/
  public final int background;

  /**512尺寸半身像*/
  public final int cardBust;

  /**表彰加成(supportTeamHeroAttr表的attrClass)*/
  public final String supportTeamAttr;

  /**表彰使用货币*/
  public final int supportItem;

  /**情报兑换*/
  public final int exchangeItem;

  /**英雄模型背景预设*/
  public final int backgroundId;

  /**时间类型*/
  public final int timeType;

  /**开始时间*/
  public final String startTime;

  /**特殊时间*/
  public final String specialEndTime;

  /**是否分享*/
  public final int heroshare;

  /**相同英雄组*/
  public final int herogroup;

  /**开服区间*/
  public final int OpenServiceActivity;

  /**角色类别*/
  public final int characterType;

  /**解锁所需专属碎片数*/
  public final int unlockFragments;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public HeroInfoConfig(int id, String name, int heroType, int heroSex, int modelResource, String modelResourceScale, int battleResource, int showResource, String showResourceScale, int headResource, int headResource_2, int headResource_3, int headResource_4, String heropinyin, String heroPerformance, String heroCutUp, String sSkillCutUp, String sSkipCutUp, int isSP, int quality, int skill_1, int skill_2, int skill_3, int skill_s1, int skill_s2, int moreAwakenSkill1, int moreAwakenSkill2, int moreAwakenSkill3, String endPerformance, int heroDebris, int herogachadoc, int heroStateData, int heroAwakenData, int expModelId, int starModelId, int circuitId, int perloadId, int heroListPreloadId, String pvpCamera, String teamCamera, String aiName, int attrRankId, int getPpos, int speicalBg, int unlockBg, int qualityIcon, int activityNpcId, int background, int cardBust, String supportTeamAttr, int supportItem, int exchangeItem, int backgroundId, int timeType, String startTime, String specialEndTime, int heroshare, int herogroup, int OpenServiceActivity, int characterType, int unlockFragments) {
    this.id = id;
    this.name = name;
    this.heroType = heroType;
    this.heroSex = heroSex;
    this.modelResource = modelResource;
    this.modelResourceScale = modelResourceScale;
    this.battleResource = battleResource;
    this.showResource = showResource;
    this.showResourceScale = showResourceScale;
    this.headResource = headResource;
    this.headResource_2 = headResource_2;
    this.headResource_3 = headResource_3;
    this.headResource_4 = headResource_4;
    this.heropinyin = heropinyin;
    this.heroPerformance = heroPerformance;
    this.heroCutUp = heroCutUp;
    this.sSkillCutUp = sSkillCutUp;
    this.sSkipCutUp = sSkipCutUp;
    this.isSP = isSP;
    this.quality = quality;
    this.skill_1 = skill_1;
    this.skill_2 = skill_2;
    this.skill_3 = skill_3;
    this.skill_s1 = skill_s1;
    this.skill_s2 = skill_s2;
    this.moreAwakenSkill1 = moreAwakenSkill1;
    this.moreAwakenSkill2 = moreAwakenSkill2;
    this.moreAwakenSkill3 = moreAwakenSkill3;
    this.endPerformance = endPerformance;
    this.heroDebris = heroDebris;
    this.herogachadoc = herogachadoc;
    this.heroStateData = heroStateData;
    this.heroAwakenData = heroAwakenData;
    this.expModelId = expModelId;
    this.starModelId = starModelId;
    this.circuitId = circuitId;
    this.perloadId = perloadId;
    this.heroListPreloadId = heroListPreloadId;
    this.pvpCamera = pvpCamera;
    this.teamCamera = teamCamera;
    this.aiName = aiName;
    this.attrRankId = attrRankId;
    this.getPpos = getPpos;
    this.speicalBg = speicalBg;
    this.unlockBg = unlockBg;
    this.qualityIcon = qualityIcon;
    this.activityNpcId = activityNpcId;
    this.background = background;
    this.cardBust = cardBust;
    this.supportTeamAttr = supportTeamAttr;
    this.supportItem = supportItem;
    this.exchangeItem = exchangeItem;
    this.backgroundId = backgroundId;
    this.timeType = timeType;
    this.startTime = startTime;
    this.specialEndTime = specialEndTime;
    this.heroshare = heroshare;
    this.herogroup = herogroup;
    this.OpenServiceActivity = OpenServiceActivity;
    this.characterType = characterType;
    this.unlockFragments = unlockFragments;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
