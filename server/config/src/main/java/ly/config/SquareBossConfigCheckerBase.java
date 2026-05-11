package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SquareBossConfigCheckerBase extends AbstractConfigChecker<SquareBossConfig> {
  @Override
  public String getConfigFileName() {
    return "squareBoss.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "weight", "INT"),
        new ConfigColumnMeta(3, "bossHp", "INT"),
        new ConfigColumnMeta(4, "skillTime", "STRING"),
        new ConfigColumnMeta(5, "chargeTime", "INT"),
        new ConfigColumnMeta(6, "chargeShowTime", "STRING"),
        new ConfigColumnMeta(7, "shield", "STRING"),
        new ConfigColumnMeta(8, "skillPara", "STRING"));
  }
}
