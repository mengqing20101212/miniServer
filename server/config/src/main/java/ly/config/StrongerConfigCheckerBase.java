package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class StrongerConfigCheckerBase extends AbstractConfigChecker<StrongerConfig> {
  @Override
  public String getConfigFileName() {
    return "stronger.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "resType", "INT"),
        new ConfigColumnMeta(2, "resName", "STRING"),
        new ConfigColumnMeta(3, "resPic", "INT"),
        new ConfigColumnMeta(4, "resDes", "STRING"),
        new ConfigColumnMeta(5, "resSort", "INT"),
        new ConfigColumnMeta(6, "playType", "INT"),
        new ConfigColumnMeta(7, "playName", "STRING"),
        new ConfigColumnMeta(8, "playStar", "INT"),
        new ConfigColumnMeta(9, "playJump", "INT"),
        new ConfigColumnMeta(10, "playSort", "INT"),
        new ConfigColumnMeta(11, "itemId", "INT"),
        new ConfigColumnMeta(12, "itemName", "STRING"),
        new ConfigColumnMeta(13, "itemSort", "INT"));
  }
}
