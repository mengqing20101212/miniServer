package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ManualMain2ConfigCheckerBase extends AbstractConfigChecker<ManualMain2Config> {
  @Override
  public String getConfigFileName() {
    return "manualMain2.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "dailyNum", "STRING"),
        new ConfigColumnMeta(2, "page", "INT"),
        new ConfigColumnMeta(3, "showNum", "STRING"),
        new ConfigColumnMeta(4, "priority", "INT"),
        new ConfigColumnMeta(5, "questType", "INT"),
        new ConfigColumnMeta(6, "condition", "INT"),
        new ConfigColumnMeta(7, "weight", "INT"),
        new ConfigColumnMeta(8, "rewardShow", "STRING"),
        new ConfigColumnMeta(9, "drop", "INT"),
        new ConfigColumnMeta(10, "redirectionId", "INT"),
        new ConfigColumnMeta(11, "dailyNum2", "STRING"),
        new ConfigColumnMeta(12, "nameIcon", "STRING"),
        new ConfigColumnMeta(13, "dailyIcon", "STRING"),
        new ConfigColumnMeta(14, "typeIcon", "INT"),
        new ConfigColumnMeta(15, "lvShow", "INT"));
  }
}
