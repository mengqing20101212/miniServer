package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CookMainConfigCheckerBase extends AbstractConfigChecker<CookMainConfig> {
  @Override
  public String getConfigFileName() {
    return "cookMain.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "type", "INT"),
        new ConfigColumnMeta(2, "name", "STRING"),
        new ConfigColumnMeta(3, "icon", "INT"),
        new ConfigColumnMeta(4, "star", "INT"),
        new ConfigColumnMeta(5, "recipe", "STRING"),
        new ConfigColumnMeta(6, "cookSkill", "STRING"),
        new ConfigColumnMeta(7, "skillExp", "INT"),
        new ConfigColumnMeta(8, "skillIncrease", "INT"),
        new ConfigColumnMeta(9, "baseReward", "STRING"),
        new ConfigColumnMeta(10, "activityId", "INT"),
        new ConfigColumnMeta(11, "saitamaExp", "INT"),
        new ConfigColumnMeta(12, "dropId", "INT"),
        new ConfigColumnMeta(13, "word", "STRING"));
  }
}
