package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CreditConfigCheckerBase extends AbstractConfigChecker<CreditConfig> {
  @Override
  public String getConfigFileName() {
    return "credit.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "activityList", "int"),
        new ConfigColumnMeta(2, "sceneId", "string"),
        new ConfigColumnMeta(3, "grouoId", "int"),
        new ConfigColumnMeta(4, "ScoreMin", "int"),
        new ConfigColumnMeta(5, "ScoreMax", "int"),
        new ConfigColumnMeta(6, "lable", "string"),
        new ConfigColumnMeta(7, "lableMin", "int"),
        new ConfigColumnMeta(8, "lableMax", "int"),
        new ConfigColumnMeta(9, "dec", "string"));
  }
}
