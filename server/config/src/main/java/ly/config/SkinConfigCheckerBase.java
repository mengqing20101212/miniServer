package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SkinConfigCheckerBase extends AbstractConfigChecker<SkinConfig> {
  @Override
  public String getConfigFileName() {
    return "skin.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "heroId", "INT"),
        new ConfigColumnMeta(2, "name", "STRING"),
        new ConfigColumnMeta(3, "skinName", "STRING"),
        new ConfigColumnMeta(4, "skinGet", "STRING"),
        new ConfigColumnMeta(5, "turnId", "INT"),
        new ConfigColumnMeta(6, "modelResource", "INT"),
        new ConfigColumnMeta(7, "battleResource", "INT"),
        new ConfigColumnMeta(8, "showResource", "INT"),
        new ConfigColumnMeta(9, "heroPerformance", "STRING"),
        new ConfigColumnMeta(10, "endPerformance", "STRING"),
        new ConfigColumnMeta(11, "activityNpc", "INT"),
        new ConfigColumnMeta(12, "cardBust", "INT"));
  }
}
