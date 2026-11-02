package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ElectronicGameSeasonConfigCheckerBase extends AbstractConfigChecker<ElectronicGameSeasonConfig> {
  @Override
  public String getConfigFileName() {
    return "electronicGameSeason.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "duanBig", "int"),
        new ConfigColumnMeta(2, "duanSmall", "int"),
        new ConfigColumnMeta(3, "duan", "string"),
        new ConfigColumnMeta(4, "awardSeason", "int"),
        new ConfigColumnMeta(5, "awardSeasonPre", "string"));
  }
}
