package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SaitamaSummonConfigCheckerBase extends AbstractConfigChecker<SaitamaSummonConfig> {
  @Override
  public String getConfigFileName() {
    return "saitamaSummon.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "type", "int"),
        new ConfigColumnMeta(3, "level", "int"),
        new ConfigColumnMeta(4, "icon", "int"),
        new ConfigColumnMeta(5, "showWord", "string"),
        new ConfigColumnMeta(6, "useEffect", "string"),
        new ConfigColumnMeta(7, "countWord", "string"),
        new ConfigColumnMeta(8, "eventID", "int"));
  }
}
