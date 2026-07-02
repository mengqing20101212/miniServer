package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class DreamEventConfigCheckerBase extends AbstractConfigChecker<DreamEventConfig> {
  @Override
  public String getConfigFileName() {
    return "dreamEvent.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "beizhu", "STRING"),
        new ConfigColumnMeta(2, "type", "INT"),
        new ConfigColumnMeta(3, "resourceId", "INT"),
        new ConfigColumnMeta(4, "headId", "INT"),
        new ConfigColumnMeta(5, "shift", "STRING"),
        new ConfigColumnMeta(6, "effectResource", "INT"),
        new ConfigColumnMeta(7, "missionTitle", "STRING"),
        new ConfigColumnMeta(8, "para", "STRING"));
  }
}
