package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class PvpOnLineSeasonConfigCheckerBase extends AbstractConfigChecker<PvpOnLineSeasonConfig> {
  @Override
  public String getConfigFileName() {
    return "pvpOnLineSeason.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "duanBig", "INT"),
        new ConfigColumnMeta(2, "duanSmall", "INT"),
        new ConfigColumnMeta(3, "duan", "STRING"),
        new ConfigColumnMeta(4, "duanReset", "INT"),
        new ConfigColumnMeta(5, "eloPercentage", "INT"),
        new ConfigColumnMeta(6, "eloFixedValue", "INT"),
        new ConfigColumnMeta(7, "awardSeason", "INT"),
        new ConfigColumnMeta(8, "awardSeasonPre", "STRING"));
  }
}
