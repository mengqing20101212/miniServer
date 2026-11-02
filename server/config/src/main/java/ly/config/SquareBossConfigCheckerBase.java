package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SquareBossConfigCheckerBase extends AbstractConfigChecker<SquareBossConfig> {
  @Override
  public String getConfigFileName() {
    return "squareBoss.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "weight", "int"),
        new ConfigColumnMeta(3, "bossHp", "int"),
        new ConfigColumnMeta(4, "skillTime", "string"),
        new ConfigColumnMeta(5, "chargeTime", "int"),
        new ConfigColumnMeta(6, "chargeShowTime", "string"),
        new ConfigColumnMeta(7, "shield", "string"),
        new ConfigColumnMeta(8, "skillPara", "string"));
  }
}
