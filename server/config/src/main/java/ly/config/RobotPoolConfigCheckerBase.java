package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class RobotPoolConfigCheckerBase extends AbstractConfigChecker<RobotPoolConfig> {
  @Override
  public String getConfigFileName() {
    return "robotPool.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "robotId", "string"),
        new ConfigColumnMeta(2, "weight", "string"),
        new ConfigColumnMeta(3, "levelmin", "int"),
        new ConfigColumnMeta(4, "levelmax", "int"));
  }
}
