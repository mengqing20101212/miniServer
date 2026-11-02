package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class QuestionnaireConfigCheckerBase extends AbstractConfigChecker<QuestionnaireConfig> {
  @Override
  public String getConfigFileName() {
    return "questionnaire.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "level", "int"),
        new ConfigColumnMeta(2, "stratTime", "int"),
        new ConfigColumnMeta(3, "mail", "int"),
        new ConfigColumnMeta(4, "connect", "string"),
        new ConfigColumnMeta(5, "channelId", "string"));
  }
}
