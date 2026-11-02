package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class GuideNoteConfigCheckerBase extends AbstractConfigChecker<GuideNoteConfig> {
  @Override
  public String getConfigFileName() {
    return "guideNote.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "guideCond", "list<int>"),
        new ConfigColumnMeta(2, "guidePara1", "string"),
        new ConfigColumnMeta(3, "linkPart", "string"),
        new ConfigColumnMeta(4, "linkParam", "int"),
        new ConfigColumnMeta(5, "uiType", "int"),
        new ConfigColumnMeta(6, "linkShift", "string"),
        new ConfigColumnMeta(7, "noticeType", "int"),
        new ConfigColumnMeta(8, "word", "string"),
        new ConfigColumnMeta(9, "endType", "list<int>"),
        new ConfigColumnMeta(10, "endPara", "string"),
        new ConfigColumnMeta(11, "turnId", "int"),
        new ConfigColumnMeta(12, "beizhu", "string"));
  }
}
