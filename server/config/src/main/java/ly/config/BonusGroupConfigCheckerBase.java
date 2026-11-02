package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class BonusGroupConfigCheckerBase extends AbstractConfigChecker<BonusGroupConfig> {
  @Override
  public String getConfigFileName() {
    return "bonusGroup.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "bonusList", "string"),
        new ConfigColumnMeta(2, "bonusPro", "string"),
        new ConfigColumnMeta(3, "bonusTimes", "string"),
        new ConfigColumnMeta(4, "bonusRelativePro", "string"),
        new ConfigColumnMeta(5, "beizhu", "string"));
  }
}
