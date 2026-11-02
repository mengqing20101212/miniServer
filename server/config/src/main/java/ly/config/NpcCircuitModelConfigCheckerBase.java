package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class NpcCircuitModelConfigCheckerBase extends AbstractConfigChecker<NpcCircuitModelConfig> {
  @Override
  public String getConfigFileName() {
    return "npcCircuitModel.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "itemId", "int"),
        new ConfigColumnMeta(2, "des", "string"),
        new ConfigColumnMeta(3, "type", "string"),
        new ConfigColumnMeta(4, "suitId", "int"),
        new ConfigColumnMeta(5, "quality", "int"),
        new ConfigColumnMeta(6, "pos", "int"),
        new ConfigColumnMeta(7, "sequence", "int"),
        new ConfigColumnMeta(8, "mainAttr", "string"),
        new ConfigColumnMeta(9, "startSubAttr", "string"),
        new ConfigColumnMeta(10, "lvSubAttr", "string"));
  }
}
