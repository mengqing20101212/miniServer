package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class GuildAuthorityConfigCheckerBase extends AbstractConfigChecker<GuildAuthorityConfig> {
  @Override
  public String getConfigFileName() {
    return "guildAuthority.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "Name", "STRING"),
        new ConfigColumnMeta(2, "level", "INT"),
        new ConfigColumnMeta(3, "upgrade", "INT"),
        new ConfigColumnMeta(4, "downgrade", "INT"),
        new ConfigColumnMeta(5, "dissolve", "INT"),
        new ConfigColumnMeta(6, "examine", "INT"),
        new ConfigColumnMeta(7, "kick", "INT"),
        new ConfigColumnMeta(8, "publish", "INT"),
        new ConfigColumnMeta(9, "notice", "INT"),
        new ConfigColumnMeta(10, "welfareShop", "INT"),
        new ConfigColumnMeta(11, "openBoss", "INT"),
        new ConfigColumnMeta(12, "mail", "INT"),
        new ConfigColumnMeta(13, "exit", "INT"),
        new ConfigColumnMeta(14, "label", "INT"),
        new ConfigColumnMeta(15, "guildName", "INT"),
        new ConfigColumnMeta(16, "sign", "INT"),
        new ConfigColumnMeta(17, "refreshShop", "INT"));
  }
}
