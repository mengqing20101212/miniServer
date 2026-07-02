package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ManualMain1ConfigCheckerBase extends AbstractConfigChecker<ManualMain1Config> {
  @Override
  public String getConfigFileName() {
    return "manualMain1.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "dailyNum", "STRING"),
        new ConfigColumnMeta(2, "page", "INT"),
        new ConfigColumnMeta(3, "beizhu", "STRING"),
        new ConfigColumnMeta(4, "showNum", "STRING"),
        new ConfigColumnMeta(5, "priority", "INT"),
        new ConfigColumnMeta(6, "questType", "INT"),
        new ConfigColumnMeta(7, "condition", "INT"),
        new ConfigColumnMeta(8, "weight", "INT"),
        new ConfigColumnMeta(9, "rewardShow", "STRING"),
        new ConfigColumnMeta(10, "drop", "INT"),
        new ConfigColumnMeta(11, "redirectionId", "INT"),
        new ConfigColumnMeta(12, "dailyNum2", "STRING"),
        new ConfigColumnMeta(13, "nameIcon", "STRING"),
        new ConfigColumnMeta(14, "dailyIcon", "STRING"),
        new ConfigColumnMeta(15, "typeIcon", "INT"),
        new ConfigColumnMeta(16, "lvIndex", "STRING"),
        new ConfigColumnMeta(17, "lvShow", "INT"),
        new ConfigColumnMeta(18, "chanelId", "INT"));
  }
}
