package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class Mail_templateConfigCheckerBase extends AbstractConfigChecker<Mail_templateConfig> {
  @Override
  public String getConfigFileName() {
    return "mail_template.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "icon", "int"),
        new ConfigColumnMeta(1, "des", "string"),
        new ConfigColumnMeta(2, "part_begin", "int"),
        new ConfigColumnMeta(3, "part_end", "int"),
        new ConfigColumnMeta(4, "type", "int"),
        new ConfigColumnMeta(5, "stamp", "string"),
        new ConfigColumnMeta(6, "valid_time", "int"),
        new ConfigColumnMeta(7, "sender", "string"),
        new ConfigColumnMeta(8, "titile", "string"),
        new ConfigColumnMeta(9, "content", "string"),
        new ConfigColumnMeta(10, "attach", "string"));
  }
}
