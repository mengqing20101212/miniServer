package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class DungeonEventConfigCheckerBase extends AbstractConfigChecker<DungeonEventConfig> {
  @Override
  public String getConfigFileName() {
    return "dungeonEvent.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "groupId", "int"),
        new ConfigColumnMeta(2, "groupNum", "int"),
        new ConfigColumnMeta(3, "type", "int"),
        new ConfigColumnMeta(4, "eventName", "string"),
        new ConfigColumnMeta(5, "eventNameRes", "int"),
        new ConfigColumnMeta(6, "dis", "string"),
        new ConfigColumnMeta(7, "baseRes", "int"),
        new ConfigColumnMeta(8, "titleRes", "int"),
        new ConfigColumnMeta(9, "contentRes", "string"),
        new ConfigColumnMeta(10, "para1", "string"),
        new ConfigColumnMeta(11, "para2", "string"),
        new ConfigColumnMeta(12, "para3", "int"),
        new ConfigColumnMeta(13, "showContent", "string"),
        new ConfigColumnMeta(14, "showType", "int"),
        new ConfigColumnMeta(15, "baseResGray", "int"));
  }
}
