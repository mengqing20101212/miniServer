package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class DreamMainNewConfigCheckerBase extends AbstractConfigChecker<DreamMainNewConfig> {
  @Override
  public String getConfigFileName() {
    return "dreamMainNew.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "nextId", "INT"),
        new ConfigColumnMeta(2, "name", "STRING"),
        new ConfigColumnMeta(3, "nameResource", "INT"),
        new ConfigColumnMeta(4, "bgResource", "INT"),
        new ConfigColumnMeta(5, "ticketId", "INT"),
        new ConfigColumnMeta(6, "ticketNum", "INT"),
        new ConfigColumnMeta(7, "dropList", "STRING"),
        new ConfigColumnMeta(8, "sceneList", "STRING"),
        new ConfigColumnMeta(9, "heroCount", "STRING"),
        new ConfigColumnMeta(10, "mechanism", "LIST"),
        new ConfigColumnMeta(11, "limit1", "STRING"),
        new ConfigColumnMeta(12, "limit2", "STRING"),
        new ConfigColumnMeta(13, "limit3", "STRING"),
        new ConfigColumnMeta(14, "limit4", "STRING"),
        new ConfigColumnMeta(15, "dropShow", "INT"));
  }
}
