package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class Top_resources_setConfigCheckerBase extends AbstractConfigChecker<Top_resources_setConfig> {
  @Override
  public String getConfigFileName() {
    return "top_resources_set.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "desc", "STRING"),
        new ConfigColumnMeta(2, "title", "STRING"),
        new ConfigColumnMeta(3, "titleTypeJudgment", "INT"),
        new ConfigColumnMeta(4, "showtime", "INT"),
        new ConfigColumnMeta(5, "titleType", "INT"),
        new ConfigColumnMeta(6, "icon", "STRING"),
        new ConfigColumnMeta(7, "titleWord", "STRING"),
        new ConfigColumnMeta(8, "show_type", "LIST<INT>"),
        new ConfigColumnMeta(9, "fastTurnID", "LIST<INT>"),
        new ConfigColumnMeta(10, "activeType", "LIST<INT>"),
        new ConfigColumnMeta(11, "helpTurnId", "INT"),
        new ConfigColumnMeta(12, "helpdesc", "INT"),
        new ConfigColumnMeta(13, "activityId", "INT"));
  }
}
