package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class QuestionBankConfigCheckerBase extends AbstractConfigChecker<QuestionBankConfig> {
  @Override
  public String getConfigFileName() {
    return "questionBank.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "questionContent", "string"),
        new ConfigColumnMeta(2, "questionNote", "string"),
        new ConfigColumnMeta(3, "type", "int"),
        new ConfigColumnMeta(4, "questionShow", "int"),
        new ConfigColumnMeta(5, "questionPicture", "string"),
        new ConfigColumnMeta(6, "optionShow", "int"),
        new ConfigColumnMeta(7, "simpleA", "string"),
        new ConfigColumnMeta(8, "simpleB", "string"),
        new ConfigColumnMeta(9, "simpleC", "string"),
        new ConfigColumnMeta(10, "simpleD", "string"),
        new ConfigColumnMeta(11, "simpleAnswer", "string"),
        new ConfigColumnMeta(12, "simpleNote", "string"),
        new ConfigColumnMeta(13, "hardA", "string"),
        new ConfigColumnMeta(14, "hardB", "string"),
        new ConfigColumnMeta(15, "hardC", "string"),
        new ConfigColumnMeta(16, "hardD", "string"),
        new ConfigColumnMeta(17, "hardAnswer", "string"),
        new ConfigColumnMeta(18, "hardNote", "string"));
  }
}
