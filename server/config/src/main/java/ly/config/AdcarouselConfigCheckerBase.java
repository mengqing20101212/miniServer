package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class AdcarouselConfigCheckerBase extends AbstractConfigChecker<AdcarouselConfig> {
  @Override
  public String getConfigFileName() {
    return "adcarousel.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "beizhu", "STRING"),
        new ConfigColumnMeta(2, "adcarousel", "INT"),
        new ConfigColumnMeta(3, "adcarousel2", "INT"),
        new ConfigColumnMeta(4, "timeType", "INT"),
        new ConfigColumnMeta(5, "startTime", "STRING"),
        new ConfigColumnMeta(6, "endTime", "STRING"),
        new ConfigColumnMeta(7, "specialEndTime", "LIST<INT>"),
        new ConfigColumnMeta(8, "jump1", "INT"),
        new ConfigColumnMeta(9, "order", "INT"),
        new ConfigColumnMeta(10, "level_limit", "LIST<INT>"),
        new ConfigColumnMeta(11, "OpenServiceActivity", "INT"));
  }
}
