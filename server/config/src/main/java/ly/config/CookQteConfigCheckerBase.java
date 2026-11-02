package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CookQteConfigCheckerBase extends AbstractConfigChecker<CookQteConfig> {
  @Override
  public String getConfigFileName() {
    return "cookQte.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "type", "int"),
        new ConfigColumnMeta(2, "qtePara", "string"),
        new ConfigColumnMeta(3, "bonusList", "string"),
        new ConfigColumnMeta(4, "pointList", "string"),
        new ConfigColumnMeta(5, "turn", "int"),
        new ConfigColumnMeta(6, "time", "string"),
        new ConfigColumnMeta(7, "setList", "string"));
  }
}
