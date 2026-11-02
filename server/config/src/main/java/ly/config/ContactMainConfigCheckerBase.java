package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ContactMainConfigCheckerBase extends AbstractConfigChecker<ContactMainConfig> {
  @Override
  public String getConfigFileName() {
    return "contactMain.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "englishName", "string"),
        new ConfigColumnMeta(3, "missionList", "string"),
        new ConfigColumnMeta(4, "resource", "int"),
        new ConfigColumnMeta(5, "headIcon", "int"),
        new ConfigColumnMeta(6, "missionShow", "string"),
        new ConfigColumnMeta(7, "rewardShow", "string"),
        new ConfigColumnMeta(8, "challengeTimes", "int"),
        new ConfigColumnMeta(9, "weekend", "string"),
        new ConfigColumnMeta(10, "weekendChallenge", "int"),
        new ConfigColumnMeta(11, "stamina", "int"),
        new ConfigColumnMeta(12, "eventPool", "string"));
  }
}
