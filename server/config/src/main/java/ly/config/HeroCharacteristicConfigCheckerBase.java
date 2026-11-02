package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class HeroCharacteristicConfigCheckerBase extends AbstractConfigChecker<HeroCharacteristicConfig> {
  @Override
  public String getConfigFileName() {
    return "heroCharacteristic.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "beizhu", "string"),
        new ConfigColumnMeta(2, "heroId", "int"),
        new ConfigColumnMeta(3, "skillGroup", "int"),
        new ConfigColumnMeta(4, "heroFetters", "list<int>"),
        new ConfigColumnMeta(5, "advanced", "int"),
        new ConfigColumnMeta(6, "quality", "string"),
        new ConfigColumnMeta(7, "qualityNum", "string"),
        new ConfigColumnMeta(8, "heroType", "string"),
        new ConfigColumnMeta(9, "heroTypeNum", "string"),
        new ConfigColumnMeta(10, "characterType", "int"),
        new ConfigColumnMeta(11, "characterNum", "int"),
        new ConfigColumnMeta(12, "activationDes", "string"),
        new ConfigColumnMeta(13, "des", "string"));
  }
}
