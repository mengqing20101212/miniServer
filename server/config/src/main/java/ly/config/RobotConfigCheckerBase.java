package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class RobotConfigCheckerBase extends AbstractConfigChecker<RobotConfig> {
  @Override
  public String getConfigFileName() {
    return "robot.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "enemyInfo", "string"),
        new ConfigColumnMeta(2, "enemyCircuitInfo", "string"),
        new ConfigColumnMeta(3, "enemyPara", "string"),
        new ConfigColumnMeta(4, "npcId", "string"));
  }
}
