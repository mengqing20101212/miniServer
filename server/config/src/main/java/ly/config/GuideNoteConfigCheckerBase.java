package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class GuideNoteConfigCheckerBase extends AbstractConfigChecker<GuideNoteConfig> {
  @Override
  public String getConfigFileName() {
    return "guideNote.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "guideCond", "LIST<INT>"),
        new ConfigColumnMeta(2, "guidePara1", "STRING"),
        new ConfigColumnMeta(3, "linkPart", "STRING"),
        new ConfigColumnMeta(4, "linkParam", "INT"),
        new ConfigColumnMeta(5, "uiType", "INT"),
        new ConfigColumnMeta(6, "linkShift", "STRING"),
        new ConfigColumnMeta(7, "noticeType", "INT"),
        new ConfigColumnMeta(8, "word", "STRING"),
        new ConfigColumnMeta(9, "endType", "LIST<INT>"),
        new ConfigColumnMeta(10, "endPara", "STRING"),
        new ConfigColumnMeta(11, "turnId", "INT"),
        new ConfigColumnMeta(12, "beizhu", "STRING"));
  }
}
