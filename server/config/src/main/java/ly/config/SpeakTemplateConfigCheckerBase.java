package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SpeakTemplateConfigCheckerBase extends AbstractConfigChecker<SpeakTemplateConfig> {
  @Override
  public String getConfigFileName() {
    return "speakTemplate.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "templateId", "INT"),
        new ConfigColumnMeta(2, "standId", "INT"),
        new ConfigColumnMeta(3, "mosterName", "STRING"),
        new ConfigColumnMeta(4, "content", "STRING"),
        new ConfigColumnMeta(5, "answer", "INT"));
  }
}
