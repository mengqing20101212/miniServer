package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class DreamMainConfigCheckerBase extends AbstractConfigChecker<DreamMainConfig> {
  @Override
  public String getConfigFileName() {
    return "dreamMain.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "nameResource", "int"),
        new ConfigColumnMeta(3, "titleResource", "int"),
        new ConfigColumnMeta(4, "effectResource", "int"),
        new ConfigColumnMeta(5, "type", "int"),
        new ConfigColumnMeta(6, "ticketId", "int"),
        new ConfigColumnMeta(7, "ticketNum", "int"),
        new ConfigColumnMeta(8, "eventPool", "string"),
        new ConfigColumnMeta(9, "bgResource", "int"),
        new ConfigColumnMeta(10, "rewardList", "string"),
        new ConfigColumnMeta(11, "dropShow", "string"));
  }
}
