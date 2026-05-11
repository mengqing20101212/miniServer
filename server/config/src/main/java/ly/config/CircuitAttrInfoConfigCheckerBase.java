package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CircuitAttrInfoConfigCheckerBase extends AbstractConfigChecker<CircuitAttrInfoConfig> {
  @Override
  public String getConfigFileName() {
    return "circuitAttrInfo.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "type", "INT"),
        new ConfigColumnMeta(2, "description", "STRING"),
        new ConfigColumnMeta(3, "skillDescription", "STRING"),
        new ConfigColumnMeta(4, "attrType", "INT"),
        new ConfigColumnMeta(5, "attrNum", "INT"),
        new ConfigColumnMeta(6, "upgradeAdd", "INT"),
        new ConfigColumnMeta(7, "name", "STRING"),
        new ConfigColumnMeta(8, "pos", "INT"),
        new ConfigColumnMeta(9, "quality", "STRING"),
        new ConfigColumnMeta(10, "sequence", "INT"),
        new ConfigColumnMeta(11, "skillId", "INT"),
        new ConfigColumnMeta(12, "extraSkillId", "INT"));
  }
}
