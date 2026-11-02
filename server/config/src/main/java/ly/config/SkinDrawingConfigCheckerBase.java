package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SkinDrawingConfigCheckerBase extends AbstractConfigChecker<SkinDrawingConfig> {
  @Override
  public String getConfigFileName() {
    return "skinDrawing.xml";
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
        new ConfigColumnMeta(6, "headResource", "int"),
        new ConfigColumnMeta(7, "headResource_2", "int"),
        new ConfigColumnMeta(8, "headResource_3", "int"),
        new ConfigColumnMeta(9, "headResource_4", "int"),
        new ConfigColumnMeta(10, "sSkillCutUp", "string"),
        new ConfigColumnMeta(11, "sSkipCutUp", "string"),
        new ConfigColumnMeta(12, "cardBust", "int"));
  }
}
