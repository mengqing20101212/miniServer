package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class JusticeMainConfigCheckerBase extends AbstractConfigChecker<JusticeMainConfig> {
  @Override
  public String getConfigFileName() {
    return "justiceMain.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "stageGroup", "STRING"),
        new ConfigColumnMeta(3, "dropId", "INT"),
        new ConfigColumnMeta(4, "dropShow", "STRING"),
        new ConfigColumnMeta(5, "nextZone", "INT"),
        new ConfigColumnMeta(6, "lastZone", "INT"),
        new ConfigColumnMeta(7, "background", "INT"),
        new ConfigColumnMeta(8, "finishSmallBackground", "INT"));
  }
}
