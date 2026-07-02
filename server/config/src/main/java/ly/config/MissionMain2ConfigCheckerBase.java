package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class MissionMain2ConfigCheckerBase extends AbstractConfigChecker<MissionMain2Config> {
  @Override
  public String getConfigFileName() {
    return "missionMain2.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "beizhu", "STRING"),
        new ConfigColumnMeta(2, "condition", "INT"),
        new ConfigColumnMeta(3, "para", "INT"),
        new ConfigColumnMeta(4, "para2", "INT"),
        new ConfigColumnMeta(5, "targetValue", "INT"),
        new ConfigColumnMeta(6, "change_methods", "INT"));
  }
}
