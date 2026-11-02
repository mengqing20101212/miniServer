package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CircuitAttrInfoConfigCheckerBase extends AbstractConfigChecker<CircuitAttrInfoConfig> {
  @Override
  public String getConfigFileName() {
    return "circuitAttrInfo.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "type", "int"),
        new ConfigColumnMeta(2, "description", "string"),
        new ConfigColumnMeta(3, "skillDescription", "string"),
        new ConfigColumnMeta(4, "attrType", "int"),
        new ConfigColumnMeta(5, "attrNum", "int"),
        new ConfigColumnMeta(6, "upgradeAdd", "int"),
        new ConfigColumnMeta(7, "name", "string"),
        new ConfigColumnMeta(8, "pos", "int"),
        new ConfigColumnMeta(9, "quality", "string"),
        new ConfigColumnMeta(10, "sequence", "int"),
        new ConfigColumnMeta(11, "skillId", "int"),
        new ConfigColumnMeta(12, "extraSkillId", "int"));
  }
}
