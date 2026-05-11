package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class Channel_typeConfigCheckerBase extends AbstractConfigChecker<Channel_typeConfig> {
  @Override
  public String getConfigFileName() {
    return "channel_type.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "spname", "STRING"),
        new ConfigColumnMeta(2, "spid", "INT"),
        new ConfigColumnMeta(3, "spcode", "STRING"),
        new ConfigColumnMeta(4, "spdownloadurl", "STRING"),
        new ConfigColumnMeta(5, "checkserverip", "STRING"),
        new ConfigColumnMeta(6, "checkserverport", "INT"),
        new ConfigColumnMeta(7, "groupid", "INT"),
        new ConfigColumnMeta(8, "check_login_test_url", "STRING"),
        new ConfigColumnMeta(9, "check_login_url", "STRING"),
        new ConfigColumnMeta(10, "login_interface", "STRING"),
        new ConfigColumnMeta(11, "appid", "STRING"),
        new ConfigColumnMeta(12, "appsec", "STRING"),
        new ConfigColumnMeta(13, "isExteral", "INT"));
  }
}
