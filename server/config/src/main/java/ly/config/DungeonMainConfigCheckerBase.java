package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class DungeonMainConfigCheckerBase extends AbstractConfigChecker<DungeonMainConfig> {
  @Override
  public String getConfigFileName() {
    return "dungeonMain.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "beizhu", "string"),
        new ConfigColumnMeta(2, "name", "string"),
        new ConfigColumnMeta(3, "timeShow", "string"),
        new ConfigColumnMeta(4, "titleWordPic", "int"),
        new ConfigColumnMeta(5, "title", "string"),
        new ConfigColumnMeta(6, "rewards", "string"),
        new ConfigColumnMeta(7, "background", "int"),
        new ConfigColumnMeta(8, "bossModel", "int"),
        new ConfigColumnMeta(9, "bossAction", "string"),
        new ConfigColumnMeta(10, "scale", "int"),
        new ConfigColumnMeta(11, "rotation", "string"),
        new ConfigColumnMeta(12, "modelShift", "string"));
  }
}
