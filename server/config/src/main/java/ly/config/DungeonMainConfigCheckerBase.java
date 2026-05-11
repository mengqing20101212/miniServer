package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class DungeonMainConfigCheckerBase extends AbstractConfigChecker<DungeonMainConfig> {
  @Override
  public String getConfigFileName() {
    return "dungeonMain.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "beizhu", "STRING"),
        new ConfigColumnMeta(2, "name", "STRING"),
        new ConfigColumnMeta(3, "timeShow", "STRING"),
        new ConfigColumnMeta(4, "titleWordPic", "INT"),
        new ConfigColumnMeta(5, "title", "STRING"),
        new ConfigColumnMeta(6, "rewards", "STRING"),
        new ConfigColumnMeta(7, "background", "INT"),
        new ConfigColumnMeta(8, "bossModel", "INT"),
        new ConfigColumnMeta(9, "bossAction", "STRING"),
        new ConfigColumnMeta(10, "scale", "INT"),
        new ConfigColumnMeta(11, "rotation", "STRING"),
        new ConfigColumnMeta(12, "modelShift", "STRING"));
  }
}
