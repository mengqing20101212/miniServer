package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SlgWorldObjectConfig {
  /**模板 ID*/
  public final int id;

  /**对象类型*/
  public final String kind;

  /**奖励资源 ID*/
  public final int resourceId;

  /**初始库存*/
  public final long amount;

  /**野怪战斗模板ID*/
  public final int monsterTemplateId;

  /**击杀奖励数量*/
  public final long reward;

  /**采集时长（毫秒）*/
  public final long gatherMillis;

  /**每区目标数量*/
  public final int countPerRegion;

  /**本区域巡检周期（秒）*/
  public final int checkIntervalSec;

  /**各区域首次巡检错峰上限（秒）*/
  public final int jitterSec;

  /**每轮巡检最多补点数量*/
  public final int batchSize;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SlgWorldObjectConfig(int id, String kind, int resourceId, long amount, int monsterTemplateId, long reward, long gatherMillis, int countPerRegion, int checkIntervalSec, int jitterSec, int batchSize) {
    this.id = id;
    this.kind = kind;
    this.resourceId = resourceId;
    this.amount = amount;
    this.monsterTemplateId = monsterTemplateId;
    this.reward = reward;
    this.gatherMillis = gatherMillis;
    this.countPerRegion = countPerRegion;
    this.checkIntervalSec = checkIntervalSec;
    this.jitterSec = jitterSec;
    this.batchSize = batchSize;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
