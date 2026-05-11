package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ContactMainConfigCheckerBase extends AbstractConfigChecker<ContactMainConfig> {
  @Override
  public String getConfigFileName() {
    return "contactMain.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "englishName", "STRING"),
        new ConfigColumnMeta(3, "missionList", "STRING"),
        new ConfigColumnMeta(4, "resource", "INT"),
        new ConfigColumnMeta(5, "headIcon", "INT"),
        new ConfigColumnMeta(6, "missionShow", "STRING"),
        new ConfigColumnMeta(7, "rewardShow", "STRING"),
        new ConfigColumnMeta(8, "challengeTimes", "INT"),
        new ConfigColumnMeta(9, "weekend", "STRING"),
        new ConfigColumnMeta(10, "weekendChallenge", "INT"),
        new ConfigColumnMeta(11, "stamina", "INT"),
        new ConfigColumnMeta(12, "eventPool", "STRING"));
  }
}
