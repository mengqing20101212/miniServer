package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CreditConfigCheckerBase extends AbstractConfigChecker<CreditConfig> {
  @Override
  public String getConfigFileName() {
    return "credit.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "activityList", "INT"),
        new ConfigColumnMeta(2, "sceneId", "STRING"),
        new ConfigColumnMeta(3, "grouoId", "INT"),
        new ConfigColumnMeta(4, "ScoreMin", "INT"),
        new ConfigColumnMeta(5, "ScoreMax", "INT"),
        new ConfigColumnMeta(6, "lable", "STRING"),
        new ConfigColumnMeta(7, "lableMin", "INT"),
        new ConfigColumnMeta(8, "lableMax", "INT"),
        new ConfigColumnMeta(9, "dec", "STRING"));
  }
}
