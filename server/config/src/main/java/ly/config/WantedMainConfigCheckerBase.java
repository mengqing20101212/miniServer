package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class WantedMainConfigCheckerBase extends AbstractConfigChecker<WantedMainConfig> {
  @Override
  public String getConfigFileName() {
    return "wantedMain.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "rank", "STRING"),
        new ConfigColumnMeta(2, "rankUpNum", "INT"),
        new ConfigColumnMeta(3, "nextId", "INT"),
        new ConfigColumnMeta(4, "lastId", "INT"),
        new ConfigColumnMeta(5, "mission", "STRING"));
  }
}
