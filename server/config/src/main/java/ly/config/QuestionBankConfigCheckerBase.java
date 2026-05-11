package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class QuestionBankConfigCheckerBase extends AbstractConfigChecker<QuestionBankConfig> {
  @Override
  public String getConfigFileName() {
    return "questionBank.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "questionContent", "STRING"),
        new ConfigColumnMeta(2, "questionNote", "STRING"),
        new ConfigColumnMeta(3, "type", "INT"),
        new ConfigColumnMeta(4, "questionShow", "INT"),
        new ConfigColumnMeta(5, "questionPicture", "STRING"),
        new ConfigColumnMeta(6, "optionShow", "INT"),
        new ConfigColumnMeta(7, "simpleA", "STRING"),
        new ConfigColumnMeta(8, "simpleB", "STRING"),
        new ConfigColumnMeta(9, "simpleC", "STRING"),
        new ConfigColumnMeta(10, "simpleD", "STRING"),
        new ConfigColumnMeta(11, "simpleAnswer", "STRING"),
        new ConfigColumnMeta(12, "simpleNote", "STRING"),
        new ConfigColumnMeta(13, "hardA", "STRING"),
        new ConfigColumnMeta(14, "hardB", "STRING"),
        new ConfigColumnMeta(15, "hardC", "STRING"),
        new ConfigColumnMeta(16, "hardD", "STRING"),
        new ConfigColumnMeta(17, "hardAnswer", "STRING"),
        new ConfigColumnMeta(18, "hardNote", "STRING"));
  }
}
