package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ContactQuestionConfigCheckerBase extends AbstractConfigChecker<ContactQuestionConfig> {
  @Override
  public String getConfigFileName() {
    return "contactQuestion.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "beizhu", "STRING"),
        new ConfigColumnMeta(2, "question", "STRING"),
        new ConfigColumnMeta(3, "type", "INT"),
        new ConfigColumnMeta(4, "answer1", "STRING"),
        new ConfigColumnMeta(5, "answer2", "STRING"),
        new ConfigColumnMeta(6, "answer3", "STRING"),
        new ConfigColumnMeta(7, "rightAnswer", "INT"));
  }
}
