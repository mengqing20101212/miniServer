package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class DreamMainNewConfigCheckerBase extends AbstractConfigChecker<DreamMainNewConfig> {
  @Override
  public String getConfigFileName() {
    return "dreamMainNew.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "nextId", "int"),
        new ConfigColumnMeta(2, "name", "string"),
        new ConfigColumnMeta(3, "nameResource", "int"),
        new ConfigColumnMeta(4, "bgResource", "int"),
        new ConfigColumnMeta(5, "ticketId", "int"),
        new ConfigColumnMeta(6, "ticketNum", "int"),
        new ConfigColumnMeta(7, "dropList", "string"),
        new ConfigColumnMeta(8, "sceneList", "string"),
        new ConfigColumnMeta(9, "heroCount", "string"),
        new ConfigColumnMeta(10, "mechanism", "string"),
        new ConfigColumnMeta(11, "limit1", "string"),
        new ConfigColumnMeta(12, "limit2", "string"),
        new ConfigColumnMeta(13, "limit3", "string"),
        new ConfigColumnMeta(14, "limit4", "string"),
        new ConfigColumnMeta(15, "dropShow", "int"));
  }
}
