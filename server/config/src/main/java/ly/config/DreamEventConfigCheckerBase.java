package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class DreamEventConfigCheckerBase extends AbstractConfigChecker<DreamEventConfig> {
  @Override
  public String getConfigFileName() {
    return "dreamEvent.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "beizhu", "string"),
        new ConfigColumnMeta(2, "type", "int"),
        new ConfigColumnMeta(3, "resourceId", "int"),
        new ConfigColumnMeta(4, "headId", "int"),
        new ConfigColumnMeta(5, "shift", "string"),
        new ConfigColumnMeta(6, "effectResource", "int"),
        new ConfigColumnMeta(7, "missionTitle", "string"),
        new ConfigColumnMeta(8, "para", "string"));
  }
}
