package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ElectronicGameSeasonConfigCheckerBase extends AbstractConfigChecker<ElectronicGameSeasonConfig> {
  @Override
  public String getConfigFileName() {
    return "electronicGameSeason.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "duanBig", "INT"),
        new ConfigColumnMeta(2, "duanSmall", "INT"),
        new ConfigColumnMeta(3, "duan", "STRING"),
        new ConfigColumnMeta(4, "awardSeason", "INT"),
        new ConfigColumnMeta(5, "awardSeasonPre", "STRING"));
  }
}
