package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class DungeonEventConfigCheckerBase extends AbstractConfigChecker<DungeonEventConfig> {
  @Override
  public String getConfigFileName() {
    return "dungeonEvent.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "groupId", "INT"),
        new ConfigColumnMeta(2, "groupNum", "INT"),
        new ConfigColumnMeta(3, "type", "INT"),
        new ConfigColumnMeta(4, "eventName", "STRING"),
        new ConfigColumnMeta(5, "eventNameRes", "INT"),
        new ConfigColumnMeta(6, "dis", "STRING"),
        new ConfigColumnMeta(7, "baseRes", "INT"),
        new ConfigColumnMeta(8, "titleRes", "INT"),
        new ConfigColumnMeta(9, "contentRes", "STRING"),
        new ConfigColumnMeta(10, "para1", "STRING"),
        new ConfigColumnMeta(11, "para2", "STRING"),
        new ConfigColumnMeta(12, "para3", "INT"),
        new ConfigColumnMeta(13, "showContent", "STRING"),
        new ConfigColumnMeta(14, "showType", "INT"),
        new ConfigColumnMeta(15, "baseResGray", "INT"));
  }
}
