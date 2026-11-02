package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class MainStoryShowConfigCheckerBase extends AbstractConfigChecker<MainStoryShowConfig> {
  @Override
  public String getConfigFileName() {
    return "mainStoryShow.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "keyPos", "int"),
        new ConfigColumnMeta(3, "activityNpcId", "int"),
        new ConfigColumnMeta(4, "keyAction", "string"),
        new ConfigColumnMeta(5, "touchAction", "string"),
        new ConfigColumnMeta(6, "returnAction", "string"),
        new ConfigColumnMeta(7, "speakerName", "string"),
        new ConfigColumnMeta(8, "question", "string"),
        new ConfigColumnMeta(9, "bubbleShift", "string"),
        new ConfigColumnMeta(10, "questionShift", "string"),
        new ConfigColumnMeta(11, "answerList", "string"),
        new ConfigColumnMeta(12, "reactionList", "string"),
        new ConfigColumnMeta(13, "changeList", "string"));
  }
}
