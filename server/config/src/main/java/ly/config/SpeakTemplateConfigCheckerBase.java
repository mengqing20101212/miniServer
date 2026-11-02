package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SpeakTemplateConfigCheckerBase extends AbstractConfigChecker<SpeakTemplateConfig> {
  @Override
  public String getConfigFileName() {
    return "speakTemplate.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "templateId", "int"),
        new ConfigColumnMeta(2, "standId", "int"),
        new ConfigColumnMeta(3, "mosterName", "string"),
        new ConfigColumnMeta(4, "content", "string"),
        new ConfigColumnMeta(5, "answer", "int"));
  }
}
