package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SceneStrategyConfig {
  /**编号*/
  public final int id;

  /**备注*/
  public final String beizhu;

  /**bossID*/
  public final int npcId;

  /**通关策略*/
  public final String strategyDec;

  /**推荐角色*/
  public final String heroId;

  /**下一关id*/
  public final int nextId;

  /**上一关id*/
  public final int lastId;

  /**关卡组*/
  public final int sceneGroup;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SceneStrategyConfig(int id, String beizhu, int npcId, String strategyDec, String heroId, int nextId, int lastId, int sceneGroup) {
    this.id = id;
    this.beizhu = beizhu;
    this.npcId = npcId;
    this.strategyDec = strategyDec;
    this.heroId = heroId;
    this.nextId = nextId;
    this.lastId = lastId;
    this.sceneGroup = sceneGroup;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
