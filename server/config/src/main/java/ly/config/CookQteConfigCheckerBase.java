package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CookQteConfigCheckerBase extends AbstractConfigChecker<CookQteConfig> {
  @Override
  public String getConfigFileName() {
    return "cookQte.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "type", "INT"),
        new ConfigColumnMeta(2, "qtePara", "STRING"),
        new ConfigColumnMeta(3, "bonusList", "STRING"),
        new ConfigColumnMeta(4, "pointList", "STRING"),
        new ConfigColumnMeta(5, "turn", "INT"),
        new ConfigColumnMeta(6, "time", "STRING"),
        new ConfigColumnMeta(7, "setList", "STRING"));
  }
}
