package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class LimitedGitConditionConfigCheckerBase extends AbstractConfigChecker<LimitedGitConditionConfig> {
  @Override
  public String getConfigFileName() {
    return "limitedGitCondition.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "des", "string"),
        new ConfigColumnMeta(2, "missionId", "int"),
        new ConfigColumnMeta(3, "missionType", "int"),
        new ConfigColumnMeta(4, "levelMin", "int"),
        new ConfigColumnMeta(5, "levelMax", "int"),
        new ConfigColumnMeta(6, "isRepeat", "int"),
        new ConfigColumnMeta(7, "RechargeShopId", "int"),
        new ConfigColumnMeta(8, "giveCloseTime", "int"),
        new ConfigColumnMeta(9, "cycleTime", "int"),
        new ConfigColumnMeta(10, "activateType", "int"),
        new ConfigColumnMeta(11, "activateMax", "int"),
        new ConfigColumnMeta(12, "herocondition", "int"),
        new ConfigColumnMeta(13, "rechargecondition", "int"));
  }
}
