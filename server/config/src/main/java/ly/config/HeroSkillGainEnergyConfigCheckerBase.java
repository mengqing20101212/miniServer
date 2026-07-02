package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class HeroSkillGainEnergyConfigCheckerBase extends AbstractConfigChecker<HeroSkillGainEnergyConfig> {
  @Override
  public String getConfigFileName() {
    return "heroSkillGainEnergy.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "heroId", "INT"),
        new ConfigColumnMeta(2, "beizhu", "STRING"),
        new ConfigColumnMeta(3, "skillPos", "INT"),
        new ConfigColumnMeta(4, "skillLevel", "INT"),
        new ConfigColumnMeta(5, "gainEnergy", "INT"),
        new ConfigColumnMeta(6, "awakeGainEnergy", "INT"),
        new ConfigColumnMeta(7, "characteristicId", "INT"));
  }
}
