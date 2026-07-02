package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SocietyCleanCityRepairConfigCheckerBase extends AbstractConfigChecker<SocietyCleanCityRepairConfig> {
  @Override
  public String getConfigFileName() {
    return "societyCleanCityRepair.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "group", "INT"),
        new ConfigColumnMeta(2, "payItem", "STRING"),
        new ConfigColumnMeta(3, "drop", "INT"),
        new ConfigColumnMeta(4, "desc", "STRING"));
  }
}
