package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class PatrolHeroConfigCheckerBase extends AbstractConfigChecker<PatrolHeroConfig> {
  @Override
  public String getConfigFileName() {
    return "patrolHero.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "workSentiment", "string"),
        new ConfigColumnMeta(3, "workDay", "string"),
        new ConfigColumnMeta(4, "workDayDesc", "string"),
        new ConfigColumnMeta(5, "heroFetter", "int"),
        new ConfigColumnMeta(6, "baseAdd", "int"),
        new ConfigColumnMeta(7, "extraAdd", "int"));
  }
}
