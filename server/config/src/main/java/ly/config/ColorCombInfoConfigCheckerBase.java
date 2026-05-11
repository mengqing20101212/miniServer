package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ColorCombInfoConfigCheckerBase extends AbstractConfigChecker<ColorCombInfoConfig> {
  @Override
  public String getConfigFileName() {
    return "colorCombInfo.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "groupId", "INT"),
        new ConfigColumnMeta(2, "squence", "INT"),
        new ConfigColumnMeta(3, "condition", "STRING"),
        new ConfigColumnMeta(4, "attrId", "INT"),
        new ConfigColumnMeta(5, "isSwitch", "INT"),
        new ConfigColumnMeta(6, "des", "STRING"));
  }
}
