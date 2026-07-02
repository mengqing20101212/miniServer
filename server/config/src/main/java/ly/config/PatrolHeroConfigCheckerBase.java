package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class PatrolHeroConfigCheckerBase extends AbstractConfigChecker<PatrolHeroConfig> {
  @Override
  public String getConfigFileName() {
    return "patrolHero.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "workSentiment", "STRING"),
        new ConfigColumnMeta(3, "workDay", "STRING"),
        new ConfigColumnMeta(4, "workDayDesc", "STRING"),
        new ConfigColumnMeta(5, "heroFetter", "INT"),
        new ConfigColumnMeta(6, "baseAdd", "INT"),
        new ConfigColumnMeta(7, "extraAdd", "INT"));
  }
}
