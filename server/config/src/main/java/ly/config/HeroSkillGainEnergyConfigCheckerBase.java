package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class HeroSkillGainEnergyConfigCheckerBase extends AbstractConfigChecker<HeroSkillGainEnergyConfig> {
  @Override
  public String getConfigFileName() {
    return "heroSkillGainEnergy.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "heroId", "int"),
        new ConfigColumnMeta(2, "beizhu", "string"),
        new ConfigColumnMeta(3, "skillPos", "int"),
        new ConfigColumnMeta(4, "skillLevel", "int"),
        new ConfigColumnMeta(5, "gainEnergy", "int"),
        new ConfigColumnMeta(6, "awakeGainEnergy", "int"),
        new ConfigColumnMeta(7, "characteristicId", "int"));
  }
}
