package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ContactEventConfigCheckerBase extends AbstractConfigChecker<ContactEventConfig> {
  @Override
  public String getConfigFileName() {
    return "contactEvent.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "type", "INT"),
        new ConfigColumnMeta(2, "beizhu", "STRING"),
        new ConfigColumnMeta(3, "para", "INT"),
        new ConfigColumnMeta(4, "startDialog", "STRING"),
        new ConfigColumnMeta(5, "rightDialog", "STRING"),
        new ConfigColumnMeta(6, "wrongDialog", "STRING"),
        new ConfigColumnMeta(7, "dropSuccess", "INT"),
        new ConfigColumnMeta(8, "dropFail", "INT"),
        new ConfigColumnMeta(9, "basicMail", "INT"));
  }
}
