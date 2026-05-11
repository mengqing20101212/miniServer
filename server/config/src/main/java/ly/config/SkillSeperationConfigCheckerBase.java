package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SkillSeperationConfigCheckerBase extends AbstractConfigChecker<SkillSeperationConfig> {
  @Override
  public String getConfigFileName() {
    return "skillSeperation.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "mainAni", "STRING"),
        new ConfigColumnMeta(1, "sperationAni_1", "STRING"),
        new ConfigColumnMeta(2, "sperationAni_2", "STRING"),
        new ConfigColumnMeta(3, "sperationAni_3", "STRING"),
        new ConfigColumnMeta(4, "sperationAni_4", "STRING"));
  }
}
