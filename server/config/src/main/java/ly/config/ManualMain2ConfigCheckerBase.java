package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ManualMain2ConfigCheckerBase extends AbstractConfigChecker<ManualMain2Config> {
  @Override
  public String getConfigFileName() {
    return "manualMain2.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "dailyNum", "string"),
        new ConfigColumnMeta(2, "page", "int"),
        new ConfigColumnMeta(3, "showNum", "string"),
        new ConfigColumnMeta(4, "priority", "int"),
        new ConfigColumnMeta(5, "questType", "int"),
        new ConfigColumnMeta(6, "condition", "int"),
        new ConfigColumnMeta(7, "weight", "int"),
        new ConfigColumnMeta(8, "rewardShow", "string"),
        new ConfigColumnMeta(9, "drop", "int"),
        new ConfigColumnMeta(10, "redirectionId", "int"),
        new ConfigColumnMeta(11, "dailyNum2", "string"),
        new ConfigColumnMeta(12, "nameIcon", "string"),
        new ConfigColumnMeta(13, "dailyIcon", "string"),
        new ConfigColumnMeta(14, "typeIcon", "int"),
        new ConfigColumnMeta(15, "lvShow", "int"));
  }
}
