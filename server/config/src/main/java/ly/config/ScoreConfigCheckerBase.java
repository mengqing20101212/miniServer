package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ScoreConfigCheckerBase extends AbstractConfigChecker<ScoreConfig> {
  @Override
  public String getConfigFileName() {
    return "score.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "level", "INT"),
        new ConfigColumnMeta(1, "heroLevelTarget", "INT"),
        new ConfigColumnMeta(2, "breakTarget", "INT"),
        new ConfigColumnMeta(3, "advanceTarget", "INT"),
        new ConfigColumnMeta(4, "awakenTarget", "INT"),
        new ConfigColumnMeta(5, "suitTarget", "INT"),
        new ConfigColumnMeta(6, "supportTarget", "INT"),
        new ConfigColumnMeta(7, "heroLevelCoefficient", "FLOAT"),
        new ConfigColumnMeta(8, "breakCoefficient", "FLOAT"),
        new ConfigColumnMeta(9, "advanceCoefficient", "FLOAT"),
        new ConfigColumnMeta(10, "awakenCoefficient", "FLOAT"),
        new ConfigColumnMeta(11, "suitCoefficient", "FLOAT"),
        new ConfigColumnMeta(12, "supportCoefficient", "FLOAT"));
  }
}
