package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class RobotPoolConfigCheckerBase extends AbstractConfigChecker<RobotPoolConfig> {
  @Override
  public String getConfigFileName() {
    return "robotPool.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "robotId", "STRING"),
        new ConfigColumnMeta(2, "weight", "STRING"),
        new ConfigColumnMeta(3, "levelmin", "INT"),
        new ConfigColumnMeta(4, "levelmax", "INT"));
  }
}
