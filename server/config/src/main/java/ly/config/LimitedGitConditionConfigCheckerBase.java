package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class LimitedGitConditionConfigCheckerBase extends AbstractConfigChecker<LimitedGitConditionConfig> {
  @Override
  public String getConfigFileName() {
    return "limitedGitCondition.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "des", "STRING"),
        new ConfigColumnMeta(2, "missionId", "INT"),
        new ConfigColumnMeta(3, "missionType", "INT"),
        new ConfigColumnMeta(4, "levelMin", "INT"),
        new ConfigColumnMeta(5, "levelMax", "INT"),
        new ConfigColumnMeta(6, "isRepeat", "INT"),
        new ConfigColumnMeta(7, "RechargeShopId", "INT"),
        new ConfigColumnMeta(8, "giveCloseTime", "INT"),
        new ConfigColumnMeta(9, "cycleTime", "INT"),
        new ConfigColumnMeta(10, "activateType", "INT"),
        new ConfigColumnMeta(11, "activateMax", "INT"),
        new ConfigColumnMeta(12, "herocondition", "INT"),
        new ConfigColumnMeta(13, "rechargecondition", "INT"));
  }
}
