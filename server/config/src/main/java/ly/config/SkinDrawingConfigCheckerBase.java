package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SkinDrawingConfigCheckerBase extends AbstractConfigChecker<SkinDrawingConfig> {
  @Override
  public String getConfigFileName() {
    return "skinDrawing.txt";
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
        new ConfigColumnMeta(6, "headResource", "INT"),
        new ConfigColumnMeta(7, "headResource_2", "INT"),
        new ConfigColumnMeta(8, "headResource_3", "INT"),
        new ConfigColumnMeta(9, "headResource_4", "INT"),
        new ConfigColumnMeta(10, "sSkillCutUp", "STRING"),
        new ConfigColumnMeta(11, "sSkipCutUp", "STRING"),
        new ConfigColumnMeta(12, "cardBust", "INT"));
  }
}
