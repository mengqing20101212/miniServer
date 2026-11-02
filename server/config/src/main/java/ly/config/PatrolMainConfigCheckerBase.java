package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class PatrolMainConfigCheckerBase extends AbstractConfigChecker<PatrolMainConfig> {
  @Override
  public String getConfigFileName() {
    return "patrolMain.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "groupId", "int"),
        new ConfigColumnMeta(2, "cost", "int"),
        new ConfigColumnMeta(3, "weight", "int"),
        new ConfigColumnMeta(4, "name", "string"),
        new ConfigColumnMeta(5, "icon", "int"),
        new ConfigColumnMeta(6, "dropGroupId", "int"),
        new ConfigColumnMeta(7, "itemPre", "string"),
        new ConfigColumnMeta(8, "heroNumLimit", "int"),
        new ConfigColumnMeta(9, "timeConsume", "int"),
        new ConfigColumnMeta(10, "extraDropGroupId", "int"),
        new ConfigColumnMeta(11, "extraDropPro", "int"),
        new ConfigColumnMeta(12, "lvLimit", "string"),
        new ConfigColumnMeta(13, "desc", "string"),
        new ConfigColumnMeta(14, "startEvent", "int"),
        new ConfigColumnMeta(15, "eventNum", "string"),
        new ConfigColumnMeta(16, "eventTime", "string"),
        new ConfigColumnMeta(17, "eventPro", "string"));
  }
}
