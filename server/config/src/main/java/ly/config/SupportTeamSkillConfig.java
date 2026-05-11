package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SupportTeamSkillConfig {
  /**编号*/
  public final int id;

  /**技能组id*/
  public final int teamSkillGroup;

  /**下一级编号*/
  public final int nextId;

  /**技能等级*/
  public final int teamSkillLevel;

  /**技能id*/
  public final int teamSkillId;

  /**升级道具id*/
  public final int teamItem;

  /**升级道具数量*/
  public final int teamItemNum;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SupportTeamSkillConfig(int id, int teamSkillGroup, int nextId, int teamSkillLevel, int teamSkillId, int teamItem, int teamItemNum) {
    this.id = id;
    this.teamSkillGroup = teamSkillGroup;
    this.nextId = nextId;
    this.teamSkillLevel = teamSkillLevel;
    this.teamSkillId = teamSkillId;
    this.teamItem = teamItem;
    this.teamItemNum = teamItemNum;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
