package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class WantedMainConfigCheckerBase extends AbstractConfigChecker<WantedMainConfig> {
  @Override
  public String getConfigFileName() {
    return "wantedMain.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "rank", "string"),
        new ConfigColumnMeta(2, "rankUpNum", "int"),
        new ConfigColumnMeta(3, "nextId", "int"),
        new ConfigColumnMeta(4, "lastId", "int"),
        new ConfigColumnMeta(5, "mission", "string"));
  }
}
