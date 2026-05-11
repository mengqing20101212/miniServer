package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class DreamMainConfigCheckerBase extends AbstractConfigChecker<DreamMainConfig> {
  @Override
  public String getConfigFileName() {
    return "dreamMain.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "nameResource", "INT"),
        new ConfigColumnMeta(3, "titleResource", "INT"),
        new ConfigColumnMeta(4, "effectResource", "INT"),
        new ConfigColumnMeta(5, "type", "INT"),
        new ConfigColumnMeta(6, "ticketId", "INT"),
        new ConfigColumnMeta(7, "ticketNum", "INT"),
        new ConfigColumnMeta(8, "eventPool", "STRING"),
        new ConfigColumnMeta(9, "bgResource", "INT"),
        new ConfigColumnMeta(10, "rewardList", "STRING"),
        new ConfigColumnMeta(11, "dropShow", "STRING"));
  }
}
