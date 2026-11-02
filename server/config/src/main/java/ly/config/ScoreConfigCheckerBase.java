package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ScoreConfigCheckerBase extends AbstractConfigChecker<ScoreConfig> {
  @Override
  public String getConfigFileName() {
    return "score.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "level", "int"),
        new ConfigColumnMeta(1, "heroLevelTarget", "int"),
        new ConfigColumnMeta(2, "breakTarget", "int"),
        new ConfigColumnMeta(3, "advanceTarget", "int"),
        new ConfigColumnMeta(4, "awakenTarget", "int"),
        new ConfigColumnMeta(5, "suitTarget", "int"),
        new ConfigColumnMeta(6, "supportTarget", "int"),
        new ConfigColumnMeta(7, "heroLevelCoefficient", "float"),
        new ConfigColumnMeta(8, "breakCoefficient", "float"),
        new ConfigColumnMeta(9, "advanceCoefficient", "float"),
        new ConfigColumnMeta(10, "awakenCoefficient", "float"),
        new ConfigColumnMeta(11, "suitCoefficient", "float"),
        new ConfigColumnMeta(12, "supportCoefficient", "float"));
  }
}
