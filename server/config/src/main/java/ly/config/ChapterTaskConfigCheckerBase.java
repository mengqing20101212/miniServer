package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ChapterTaskConfigCheckerBase extends AbstractConfigChecker<ChapterTaskConfig> {
  @Override
  public String getConfigFileName() {
    return "chapterTask.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "chapterMainId", "INT"),
        new ConfigColumnMeta(2, "missionId1", "INT"),
        new ConfigColumnMeta(3, "des1", "STRING"),
        new ConfigColumnMeta(4, "redirectionId1", "INT"),
        new ConfigColumnMeta(5, "missionId2", "INT"),
        new ConfigColumnMeta(6, "des2", "STRING"),
        new ConfigColumnMeta(7, "redirectionId2", "INT"),
        new ConfigColumnMeta(8, "missionId3", "INT"),
        new ConfigColumnMeta(9, "des3", "STRING"),
        new ConfigColumnMeta(10, "redirectionId3", "INT"),
        new ConfigColumnMeta(11, "drop", "INT"),
        new ConfigColumnMeta(12, "dropShow", "INT"));
  }
}
