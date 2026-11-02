package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class AchievementMainConfigCheckerBase extends AbstractConfigChecker<AchievementMainConfig> {
  @Override
  public String getConfigFileName() {
    return "achievementMain.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "group", "int"),
        new ConfigColumnMeta(2, "groupServer", "int"),
        new ConfigColumnMeta(3, "isIfGroup", "int"),
        new ConfigColumnMeta(4, "groupLv", "int"),
        new ConfigColumnMeta(5, "priority", "int"),
        new ConfigColumnMeta(6, "firstType", "int"),
        new ConfigColumnMeta(7, "secondType", "int"),
        new ConfigColumnMeta(8, "name", "string"),
        new ConfigColumnMeta(9, "des", "string"),
        new ConfigColumnMeta(10, "missionId", "int"),
        new ConfigColumnMeta(11, "point", "int"),
        new ConfigColumnMeta(12, "rewardShow", "string"),
        new ConfigColumnMeta(13, "dropId", "int"),
        new ConfigColumnMeta(14, "index", "int"));
  }
}
