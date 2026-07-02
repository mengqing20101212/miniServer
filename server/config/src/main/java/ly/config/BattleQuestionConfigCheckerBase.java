package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class BattleQuestionConfigCheckerBase extends AbstractConfigChecker<BattleQuestionConfig> {
  @Override
  public String getConfigFileName() {
    return "battleQuestion.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "questionContent", "STRING"),
        new ConfigColumnMeta(2, "questionPic", "INT"),
        new ConfigColumnMeta(3, "answer", "INT"),
        new ConfigColumnMeta(4, "answer1", "STRING"),
        new ConfigColumnMeta(5, "answerResult1", "STRING"),
        new ConfigColumnMeta(6, "answerEffect1", "INT"),
        new ConfigColumnMeta(7, "answer2", "STRING"),
        new ConfigColumnMeta(8, "answerResult2", "STRING"),
        new ConfigColumnMeta(9, "answerEffect2", "INT"),
        new ConfigColumnMeta(10, "answer3", "STRING"),
        new ConfigColumnMeta(11, "answerResult3", "STRING"),
        new ConfigColumnMeta(12, "answerEffect3", "INT"),
        new ConfigColumnMeta(13, "answer4", "STRING"),
        new ConfigColumnMeta(14, "answerResult4", "STRING"),
        new ConfigColumnMeta(15, "answerEffect4", "INT"));
  }
}
