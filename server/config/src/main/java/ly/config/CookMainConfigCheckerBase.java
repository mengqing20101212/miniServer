package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CookMainConfigCheckerBase extends AbstractConfigChecker<CookMainConfig> {
  @Override
  public String getConfigFileName() {
    return "cookMain.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "type", "int"),
        new ConfigColumnMeta(2, "name", "string"),
        new ConfigColumnMeta(3, "icon", "int"),
        new ConfigColumnMeta(4, "star", "int"),
        new ConfigColumnMeta(5, "recipe", "string"),
        new ConfigColumnMeta(6, "cookSkill", "string"),
        new ConfigColumnMeta(7, "skillExp", "int"),
        new ConfigColumnMeta(8, "skillIncrease", "int"),
        new ConfigColumnMeta(9, "baseReward", "string"),
        new ConfigColumnMeta(10, "activityId", "int"),
        new ConfigColumnMeta(11, "saitamaExp", "int"),
        new ConfigColumnMeta(12, "dropId", "int"),
        new ConfigColumnMeta(13, "word", "string"));
  }
}
