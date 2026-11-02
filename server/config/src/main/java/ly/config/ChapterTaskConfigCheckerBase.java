package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ChapterTaskConfigCheckerBase extends AbstractConfigChecker<ChapterTaskConfig> {
  @Override
  public String getConfigFileName() {
    return "chapterTask.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "chapterMainId", "int"),
        new ConfigColumnMeta(2, "missionId1", "int"),
        new ConfigColumnMeta(3, "des1", "string"),
        new ConfigColumnMeta(4, "redirectionId1", "int"),
        new ConfigColumnMeta(5, "missionId2", "int"),
        new ConfigColumnMeta(6, "des2", "string"),
        new ConfigColumnMeta(7, "redirectionId2", "int"),
        new ConfigColumnMeta(8, "missionId3", "int"),
        new ConfigColumnMeta(9, "des3", "string"),
        new ConfigColumnMeta(10, "redirectionId3", "int"),
        new ConfigColumnMeta(11, "drop", "int"),
        new ConfigColumnMeta(12, "dropShow", "int"));
  }
}
