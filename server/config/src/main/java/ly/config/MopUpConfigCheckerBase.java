package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class MopUpConfigCheckerBase extends AbstractConfigChecker<MopUpConfig> {
  @Override
  public String getConfigFileName() {
    return "mopUp.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "itemId", "int"),
        new ConfigColumnMeta(2, "chapterStage", "int"),
        new ConfigColumnMeta(3, "dropExpect", "int"));
  }
}
