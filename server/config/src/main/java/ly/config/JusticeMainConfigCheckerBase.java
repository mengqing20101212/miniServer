package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class JusticeMainConfigCheckerBase extends AbstractConfigChecker<JusticeMainConfig> {
  @Override
  public String getConfigFileName() {
    return "justiceMain.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "stageGroup", "string"),
        new ConfigColumnMeta(3, "dropId", "int"),
        new ConfigColumnMeta(4, "dropShow", "string"),
        new ConfigColumnMeta(5, "nextZone", "int"),
        new ConfigColumnMeta(6, "lastZone", "int"),
        new ConfigColumnMeta(7, "background", "int"),
        new ConfigColumnMeta(8, "finishSmallBackground", "int"));
  }
}
