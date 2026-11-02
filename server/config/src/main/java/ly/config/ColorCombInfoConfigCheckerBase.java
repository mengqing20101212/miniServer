package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ColorCombInfoConfigCheckerBase extends AbstractConfigChecker<ColorCombInfoConfig> {
  @Override
  public String getConfigFileName() {
    return "colorCombInfo.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "groupId", "int"),
        new ConfigColumnMeta(2, "squence", "int"),
        new ConfigColumnMeta(3, "condition", "string"),
        new ConfigColumnMeta(4, "attrId", "int"),
        new ConfigColumnMeta(5, "isSwitch", "int"),
        new ConfigColumnMeta(6, "des", "string"));
  }
}
