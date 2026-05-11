package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SaitamaSummonConfigCheckerBase extends AbstractConfigChecker<SaitamaSummonConfig> {
  @Override
  public String getConfigFileName() {
    return "saitamaSummon.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "type", "INT"),
        new ConfigColumnMeta(3, "level", "INT"),
        new ConfigColumnMeta(4, "icon", "INT"),
        new ConfigColumnMeta(5, "showWord", "STRING"),
        new ConfigColumnMeta(6, "useEffect", "STRING"),
        new ConfigColumnMeta(7, "countWord", "STRING"),
        new ConfigColumnMeta(8, "eventID", "INT"));
  }
}
