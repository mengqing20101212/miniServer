package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class MainStoryShowConfigCheckerBase extends AbstractConfigChecker<MainStoryShowConfig> {
  @Override
  public String getConfigFileName() {
    return "mainStoryShow.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "keyPos", "INT"),
        new ConfigColumnMeta(3, "activityNpcId", "INT"),
        new ConfigColumnMeta(4, "keyAction", "STRING"),
        new ConfigColumnMeta(5, "touchAction", "STRING"),
        new ConfigColumnMeta(6, "returnAction", "STRING"),
        new ConfigColumnMeta(7, "speakerName", "STRING"),
        new ConfigColumnMeta(8, "question", "STRING"),
        new ConfigColumnMeta(9, "bubbleShift", "STRING"),
        new ConfigColumnMeta(10, "questionShift", "STRING"),
        new ConfigColumnMeta(11, "answerList", "STRING"),
        new ConfigColumnMeta(12, "reactionList", "STRING"),
        new ConfigColumnMeta(13, "changeList", "STRING"));
  }
}
