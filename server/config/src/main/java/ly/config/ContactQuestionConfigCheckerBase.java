package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ContactQuestionConfigCheckerBase extends AbstractConfigChecker<ContactQuestionConfig> {
  @Override
  public String getConfigFileName() {
    return "contactQuestion.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "beizhu", "string"),
        new ConfigColumnMeta(2, "question", "string"),
        new ConfigColumnMeta(3, "type", "int"),
        new ConfigColumnMeta(4, "answer1", "string"),
        new ConfigColumnMeta(5, "answer2", "string"),
        new ConfigColumnMeta(6, "answer3", "string"),
        new ConfigColumnMeta(7, "rightAnswer", "int"));
  }
}
