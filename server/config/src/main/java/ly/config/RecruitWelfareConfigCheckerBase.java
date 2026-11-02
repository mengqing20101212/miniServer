package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class RecruitWelfareConfigCheckerBase extends AbstractConfigChecker<RecruitWelfareConfig> {
  @Override
  public String getConfigFileName() {
    return "recruitWelfare.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "welfareQuality", "string"),
        new ConfigColumnMeta(2, "qualityWelfareInterval", "string"),
        new ConfigColumnMeta(3, "qualityWelfareAwardId", "string"),
        new ConfigColumnMeta(4, "welfareHero", "int"));
  }
}
