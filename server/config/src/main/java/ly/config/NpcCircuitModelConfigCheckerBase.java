package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class NpcCircuitModelConfigCheckerBase extends AbstractConfigChecker<NpcCircuitModelConfig> {
  @Override
  public String getConfigFileName() {
    return "npcCircuitModel.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "itemId", "INT"),
        new ConfigColumnMeta(2, "des", "STRING"),
        new ConfigColumnMeta(3, "type", "STRING"),
        new ConfigColumnMeta(4, "suitId", "INT"),
        new ConfigColumnMeta(5, "quality", "INT"),
        new ConfigColumnMeta(6, "pos", "INT"),
        new ConfigColumnMeta(7, "sequence", "INT"),
        new ConfigColumnMeta(8, "mainAttr", "STRING"),
        new ConfigColumnMeta(9, "startSubAttr", "STRING"),
        new ConfigColumnMeta(10, "lvSubAttr", "STRING"));
  }
}
