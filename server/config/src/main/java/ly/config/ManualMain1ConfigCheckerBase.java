package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ManualMain1ConfigCheckerBase extends AbstractConfigChecker<ManualMain1Config> {
  @Override
  public String getConfigFileName() {
    return "manualMain1.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "dailyNum", "string"),
        new ConfigColumnMeta(2, "page", "int"),
        new ConfigColumnMeta(3, "beizhu", "string"),
        new ConfigColumnMeta(4, "showNum", "string"),
        new ConfigColumnMeta(5, "priority", "int"),
        new ConfigColumnMeta(6, "questType", "int"),
        new ConfigColumnMeta(7, "condition", "int"),
        new ConfigColumnMeta(8, "weight", "int"),
        new ConfigColumnMeta(9, "rewardShow", "string"),
        new ConfigColumnMeta(10, "drop", "int"),
        new ConfigColumnMeta(11, "redirectionId", "int"),
        new ConfigColumnMeta(12, "dailyNum2", "string"),
        new ConfigColumnMeta(13, "nameIcon", "string"),
        new ConfigColumnMeta(14, "dailyIcon", "string"),
        new ConfigColumnMeta(15, "typeIcon", "int"),
        new ConfigColumnMeta(16, "lvIndex", "string"),
        new ConfigColumnMeta(17, "lvShow", "int"),
        new ConfigColumnMeta(18, "chanelId", "int"));
  }
}
