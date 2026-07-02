package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class PatrolMainConfigCheckerBase extends AbstractConfigChecker<PatrolMainConfig> {
  @Override
  public String getConfigFileName() {
    return "patrolMain.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "groupId", "INT"),
        new ConfigColumnMeta(2, "cost", "INT"),
        new ConfigColumnMeta(3, "weight", "INT"),
        new ConfigColumnMeta(4, "name", "STRING"),
        new ConfigColumnMeta(5, "icon", "INT"),
        new ConfigColumnMeta(6, "dropGroupId", "INT"),
        new ConfigColumnMeta(7, "itemPre", "LIST"),
        new ConfigColumnMeta(8, "heroNumLimit", "INT"),
        new ConfigColumnMeta(9, "timeConsume", "INT"),
        new ConfigColumnMeta(10, "extraDropGroupId", "INT"),
        new ConfigColumnMeta(11, "extraDropPro", "INT"),
        new ConfigColumnMeta(12, "lvLimit", "STRING"),
        new ConfigColumnMeta(13, "desc", "STRING"),
        new ConfigColumnMeta(14, "startEvent", "INT"),
        new ConfigColumnMeta(15, "eventNum", "STRING"),
        new ConfigColumnMeta(16, "eventTime", "STRING"),
        new ConfigColumnMeta(17, "eventPro", "STRING"));
  }
}
