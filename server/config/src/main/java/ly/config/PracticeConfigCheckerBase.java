package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class PracticeConfigCheckerBase extends AbstractConfigChecker<PracticeConfig> {
  @Override
  public String getConfigFileName() {
    return "practice.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "group", "int"),
        new ConfigColumnMeta(2, "name", "string"),
        new ConfigColumnMeta(3, "dropId", "int"),
        new ConfigColumnMeta(4, "dropShow", "string"),
        new ConfigColumnMeta(5, "sceneId", "int"),
        new ConfigColumnMeta(6, "nextId", "int"),
        new ConfigColumnMeta(7, "lastId", "int"),
        new ConfigColumnMeta(8, "describe", "string"));
  }
}
