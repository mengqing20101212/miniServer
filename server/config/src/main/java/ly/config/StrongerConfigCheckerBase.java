package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class StrongerConfigCheckerBase extends AbstractConfigChecker<StrongerConfig> {
  @Override
  public String getConfigFileName() {
    return "stronger.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "resType", "int"),
        new ConfigColumnMeta(2, "resName", "string"),
        new ConfigColumnMeta(3, "resPic", "int"),
        new ConfigColumnMeta(4, "resDes", "string"),
        new ConfigColumnMeta(5, "resSort", "int"),
        new ConfigColumnMeta(6, "playType", "int"),
        new ConfigColumnMeta(7, "playName", "string"),
        new ConfigColumnMeta(8, "playStar", "int"),
        new ConfigColumnMeta(9, "playJump", "int"),
        new ConfigColumnMeta(10, "playSort", "int"),
        new ConfigColumnMeta(11, "itemId", "int"),
        new ConfigColumnMeta(12, "itemName", "string"),
        new ConfigColumnMeta(13, "itemSort", "int"));
  }
}
