package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivitySpecialConfigCheckerBase extends AbstractConfigChecker<ActivitySpecialConfig> {
  @Override
  public String getConfigFileName() {
    return "activitySpecial.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "scheDuling", "INT"),
        new ConfigColumnMeta(2, "name", "STRING"),
        new ConfigColumnMeta(3, "heroShowStyleType", "INT"),
        new ConfigColumnMeta(4, "rolePic", "INT"),
        new ConfigColumnMeta(5, "rolePicPosOffset", "STRING"),
        new ConfigColumnMeta(6, "rolePicScaleOffset", "STRING"),
        new ConfigColumnMeta(7, "titleResID", "INT"),
        new ConfigColumnMeta(8, "bgPic", "INT"),
        new ConfigColumnMeta(9, "holographic", "INT"),
        new ConfigColumnMeta(10, "trueActivityId", "INT"));
  }
}
