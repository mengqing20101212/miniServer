package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class HeroCharacteristicConfigCheckerBase extends AbstractConfigChecker<HeroCharacteristicConfig> {
  @Override
  public String getConfigFileName() {
    return "heroCharacteristic.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "beizhu", "STRING"),
        new ConfigColumnMeta(2, "heroId", "INT"),
        new ConfigColumnMeta(3, "skillGroup", "INT"),
        new ConfigColumnMeta(4, "heroFetters", "LIST<INT>"),
        new ConfigColumnMeta(5, "advanced", "INT"),
        new ConfigColumnMeta(6, "quality", "STRING"),
        new ConfigColumnMeta(7, "qualityNum", "STRING"),
        new ConfigColumnMeta(8, "heroType", "STRING"),
        new ConfigColumnMeta(9, "heroTypeNum", "STRING"),
        new ConfigColumnMeta(10, "characterType", "INT"),
        new ConfigColumnMeta(11, "characterNum", "INT"),
        new ConfigColumnMeta(12, "activationDes", "STRING"),
        new ConfigColumnMeta(13, "des", "STRING"));
  }
}
