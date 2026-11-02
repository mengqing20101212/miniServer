package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class GuildAuthorityConfigCheckerBase extends AbstractConfigChecker<GuildAuthorityConfig> {
  @Override
  public String getConfigFileName() {
    return "guildAuthority.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "Name", "string"),
        new ConfigColumnMeta(2, "level", "int"),
        new ConfigColumnMeta(3, "upgrade", "int"),
        new ConfigColumnMeta(4, "downgrade", "int"),
        new ConfigColumnMeta(5, "dissolve", "int"),
        new ConfigColumnMeta(6, "examine", "int"),
        new ConfigColumnMeta(7, "kick", "int"),
        new ConfigColumnMeta(8, "publish", "int"),
        new ConfigColumnMeta(9, "notice", "int"),
        new ConfigColumnMeta(10, "welfareShop", "int"),
        new ConfigColumnMeta(11, "openBoss", "int"),
        new ConfigColumnMeta(12, "mail", "int"),
        new ConfigColumnMeta(13, "exit", "int"),
        new ConfigColumnMeta(14, "label", "int"),
        new ConfigColumnMeta(15, "guildName", "int"),
        new ConfigColumnMeta(16, "sign", "int"),
        new ConfigColumnMeta(17, "refreshShop", "int"));
  }
}
