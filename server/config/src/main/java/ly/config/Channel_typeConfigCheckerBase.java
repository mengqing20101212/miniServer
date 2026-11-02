package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class Channel_typeConfigCheckerBase extends AbstractConfigChecker<Channel_typeConfig> {
  @Override
  public String getConfigFileName() {
    return "channel_type.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "spname", "string"),
        new ConfigColumnMeta(2, "spid", "int"),
        new ConfigColumnMeta(3, "spcode", "string"),
        new ConfigColumnMeta(4, "spdownloadurl", "string"),
        new ConfigColumnMeta(5, "checkserverip", "string"),
        new ConfigColumnMeta(6, "checkserverport", "int"),
        new ConfigColumnMeta(7, "groupid", "int"),
        new ConfigColumnMeta(8, "check_login_test_url", "string"),
        new ConfigColumnMeta(9, "check_login_url", "string"),
        new ConfigColumnMeta(10, "login_interface", "string"),
        new ConfigColumnMeta(11, "appid", "string"),
        new ConfigColumnMeta(12, "appsec", "string"),
        new ConfigColumnMeta(13, "isExteral", "int"));
  }
}
