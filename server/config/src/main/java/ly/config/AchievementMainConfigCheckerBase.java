package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class AchievementMainConfigCheckerBase extends AbstractConfigChecker<AchievementMainConfig> {
  @Override
  public String getConfigFileName() {
    return "achievementMain.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "group", "INT"),
        new ConfigColumnMeta(2, "groupServer", "INT"),
        new ConfigColumnMeta(3, "isIfGroup", "INT"),
        new ConfigColumnMeta(4, "groupLv", "INT"),
        new ConfigColumnMeta(5, "priority", "INT"),
        new ConfigColumnMeta(6, "firstType", "INT"),
        new ConfigColumnMeta(7, "secondType", "INT"),
        new ConfigColumnMeta(8, "name", "STRING"),
        new ConfigColumnMeta(9, "des", "STRING"),
        new ConfigColumnMeta(10, "missionId", "INT"),
        new ConfigColumnMeta(11, "point", "INT"),
        new ConfigColumnMeta(12, "rewardShow", "STRING"),
        new ConfigColumnMeta(13, "dropId", "INT"),
        new ConfigColumnMeta(14, "index", "INT"));
  }
}
