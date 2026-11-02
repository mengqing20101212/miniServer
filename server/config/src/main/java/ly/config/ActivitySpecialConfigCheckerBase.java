package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivitySpecialConfigCheckerBase extends AbstractConfigChecker<ActivitySpecialConfig> {
  @Override
  public String getConfigFileName() {
    return "activitySpecial.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "scheDuling", "int"),
        new ConfigColumnMeta(2, "name", "string"),
        new ConfigColumnMeta(3, "heroShowStyleType", "int"),
        new ConfigColumnMeta(4, "rolePic", "int"),
        new ConfigColumnMeta(5, "rolePicPosOffset", "string"),
        new ConfigColumnMeta(6, "rolePicScaleOffset", "string"),
        new ConfigColumnMeta(7, "titleResID", "int"),
        new ConfigColumnMeta(8, "bgPic", "int"),
        new ConfigColumnMeta(9, "holographic", "int"),
        new ConfigColumnMeta(10, "trueActivityId", "int"));
  }
}
