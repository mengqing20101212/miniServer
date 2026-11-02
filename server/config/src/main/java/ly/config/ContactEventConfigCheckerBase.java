package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ContactEventConfigCheckerBase extends AbstractConfigChecker<ContactEventConfig> {
  @Override
  public String getConfigFileName() {
    return "contactEvent.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "type", "int"),
        new ConfigColumnMeta(2, "beizhu", "string"),
        new ConfigColumnMeta(3, "para", "int"),
        new ConfigColumnMeta(4, "startDialog", "string"),
        new ConfigColumnMeta(5, "rightDialog", "string"),
        new ConfigColumnMeta(6, "wrongDialog", "string"),
        new ConfigColumnMeta(7, "dropSuccess", "int"),
        new ConfigColumnMeta(8, "dropFail", "int"),
        new ConfigColumnMeta(9, "basicMail", "int"));
  }
}
