package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class GuideImageConfigCheckerBase extends AbstractConfigChecker<GuideImageConfig> {
  @Override
  public String getConfigFileName() {
    return "guideImage.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "beizhu", "string"),
        new ConfigColumnMeta(2, "title", "string"),
        new ConfigColumnMeta(3, "resourceList", "string"),
        new ConfigColumnMeta(4, "triggerNext", "int"));
  }
}
