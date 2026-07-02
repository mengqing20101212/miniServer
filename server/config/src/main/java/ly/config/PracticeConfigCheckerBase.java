package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class PracticeConfigCheckerBase extends AbstractConfigChecker<PracticeConfig> {
  @Override
  public String getConfigFileName() {
    return "practice.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "group", "INT"),
        new ConfigColumnMeta(2, "name", "STRING"),
        new ConfigColumnMeta(3, "dropId", "INT"),
        new ConfigColumnMeta(4, "dropShow", "STRING"),
        new ConfigColumnMeta(5, "sceneId", "INT"),
        new ConfigColumnMeta(6, "nextId", "INT"),
        new ConfigColumnMeta(7, "lastId", "INT"),
        new ConfigColumnMeta(8, "describe", "STRING"));
  }
}
