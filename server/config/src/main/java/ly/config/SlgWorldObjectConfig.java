package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SlgWorldObjectConfig {
  public final int id;

  public final String kind;

  public final int resourceId;

  public final long amount;

  public final long health;

  public final long reward;

  public final long gatherMillis;

  public final int countPerRegion;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SlgWorldObjectConfig(int id, String kind, int resourceId, long amount, long health, long reward, long gatherMillis, int countPerRegion) {
    this.id = id;
    this.kind = kind;
    this.resourceId = resourceId;
    this.amount = amount;
    this.health = health;
    this.reward = reward;
    this.gatherMillis = gatherMillis;
    this.countPerRegion = countPerRegion;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}

  // @@@@@自定义方法结束区@@@@@
}
