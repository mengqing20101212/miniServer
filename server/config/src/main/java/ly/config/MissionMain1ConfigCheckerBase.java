package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class MissionMain1ConfigCheckerBase extends AbstractConfigChecker<MissionMain1Config> {
  @Override
  public String getConfigFileName() {
    return "missionMain1.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "beizhu", "string"),
        new ConfigColumnMeta(2, "condition", "int"),
        new ConfigColumnMeta(3, "para", "int"),
        new ConfigColumnMeta(4, "para2", "int"),
        new ConfigColumnMeta(5, "targetValue", "int"),
        new ConfigColumnMeta(6, "change_methods", "int"));
  }
}
