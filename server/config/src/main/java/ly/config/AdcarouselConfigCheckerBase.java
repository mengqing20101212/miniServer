package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class AdcarouselConfigCheckerBase extends AbstractConfigChecker<AdcarouselConfig> {
  @Override
  public String getConfigFileName() {
    return "adcarousel.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "beizhu", "string"),
        new ConfigColumnMeta(2, "adcarousel", "int"),
        new ConfigColumnMeta(3, "adcarousel2", "int"),
        new ConfigColumnMeta(4, "timeType", "int"),
        new ConfigColumnMeta(5, "startTime", "string"),
        new ConfigColumnMeta(6, "endTime", "string"),
        new ConfigColumnMeta(7, "specialEndTime", "list<int>"),
        new ConfigColumnMeta(8, "jump1", "int"),
        new ConfigColumnMeta(9, "order", "int"),
        new ConfigColumnMeta(10, "level_limit", "list<int>"),
        new ConfigColumnMeta(11, "OpenServiceActivity", "int"));
  }
}
