package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class RankConfigCheckerBase extends AbstractConfigChecker<RankConfig> {
  @Override
  public String getConfigFileName() {
    return "rank.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "type", "INT"),
        new ConfigColumnMeta(2, "name", "STRING"),
        new ConfigColumnMeta(3, "subtype", "INT"),
        new ConfigColumnMeta(4, "subtype_name", "STRING"),
        new ConfigColumnMeta(5, "no_ranking", "STRING"),
        new ConfigColumnMeta(6, "rankNum", "INT"),
        new ConfigColumnMeta(7, "serverType", "INT"),
        new ConfigColumnMeta(8, "serverSubtype", "INT"),
        new ConfigColumnMeta(9, "serverInfoName", "STRING"),
        new ConfigColumnMeta(10, "defaultPage", "STRING"));
  }
}
