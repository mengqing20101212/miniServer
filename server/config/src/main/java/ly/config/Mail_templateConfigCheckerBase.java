package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class Mail_templateConfigCheckerBase extends AbstractConfigChecker<Mail_templateConfig> {
  @Override
  public String getConfigFileName() {
    return "mail_template.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "icon", "INT"),
        new ConfigColumnMeta(1, "des", "STRING"),
        new ConfigColumnMeta(2, "part_begin", "INT"),
        new ConfigColumnMeta(3, "part_end", "INT"),
        new ConfigColumnMeta(4, "type", "INT"),
        new ConfigColumnMeta(5, "stamp", "STRING"),
        new ConfigColumnMeta(6, "valid_time", "INT"),
        new ConfigColumnMeta(7, "sender", "STRING"),
        new ConfigColumnMeta(8, "titile", "STRING"),
        new ConfigColumnMeta(9, "content", "STRING"),
        new ConfigColumnMeta(10, "attach", "STRING"));
  }
}
