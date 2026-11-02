package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class RankConfigCheckerBase extends AbstractConfigChecker<RankConfig> {
  @Override
  public String getConfigFileName() {
    return "rank.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "type", "int"),
        new ConfigColumnMeta(2, "name", "string"),
        new ConfigColumnMeta(3, "subtype", "int"),
        new ConfigColumnMeta(4, "subtype_name", "string"),
        new ConfigColumnMeta(5, "no_ranking", "string"),
        new ConfigColumnMeta(6, "rankNum", "int"),
        new ConfigColumnMeta(7, "serverType", "int"),
        new ConfigColumnMeta(8, "serverSubtype", "int"),
        new ConfigColumnMeta(9, "serverInfoName", "string"),
        new ConfigColumnMeta(10, "defaultPage", "string"));
  }
}
