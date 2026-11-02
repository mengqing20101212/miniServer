package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class Top_resources_setConfigCheckerBase extends AbstractConfigChecker<Top_resources_setConfig> {
  @Override
  public String getConfigFileName() {
    return "top_resources_set.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "desc", "string"),
        new ConfigColumnMeta(2, "title", "string"),
        new ConfigColumnMeta(3, "titleTypeJudgment", "int"),
        new ConfigColumnMeta(4, "showtime", "int"),
        new ConfigColumnMeta(5, "titleType", "int"),
        new ConfigColumnMeta(6, "icon", "string"),
        new ConfigColumnMeta(7, "titleWord", "string"),
        new ConfigColumnMeta(8, "show_type", "list<int>"),
        new ConfigColumnMeta(9, "fastTurnID", "list<int>"),
        new ConfigColumnMeta(10, "activeType", "list<int>"),
        new ConfigColumnMeta(11, "helpTurnId", "int"),
        new ConfigColumnMeta(12, "helpdesc", "int"),
        new ConfigColumnMeta(13, "activityId", "int"));
  }
}
