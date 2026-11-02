package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SkinConfigCheckerBase extends AbstractConfigChecker<SkinConfig> {
  @Override
  public String getConfigFileName() {
    return "skin.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "heroId", "int"),
        new ConfigColumnMeta(2, "name", "string"),
        new ConfigColumnMeta(3, "skinName", "string"),
        new ConfigColumnMeta(4, "skinGet", "string"),
        new ConfigColumnMeta(5, "turnId", "int"),
        new ConfigColumnMeta(6, "modelResource", "int"),
        new ConfigColumnMeta(7, "battleResource", "int"),
        new ConfigColumnMeta(8, "showResource", "int"),
        new ConfigColumnMeta(9, "heroPerformance", "string"),
        new ConfigColumnMeta(10, "endPerformance", "string"),
        new ConfigColumnMeta(11, "activityNpc", "int"),
        new ConfigColumnMeta(12, "cardBust", "int"));
  }
}
