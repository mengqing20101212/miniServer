package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class GuildGiftBagConfigCheckerBase extends AbstractConfigChecker<GuildGiftBagConfig> {
  @Override
  public String getConfigFileName() {
    return "guildGiftBag.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "type", "int"),
        new ConfigColumnMeta(2, "name", "string"),
        new ConfigColumnMeta(3, "drop", "int"),
        new ConfigColumnMeta(4, "cost", "int"),
        new ConfigColumnMeta(5, "reward", "int"),
        new ConfigColumnMeta(6, "Num", "int"),
        new ConfigColumnMeta(7, "heroId", "string"),
        new ConfigColumnMeta(8, "active", "int"),
        new ConfigColumnMeta(9, "recharge", "int"),
        new ConfigColumnMeta(10, "giftIcon", "int"),
        new ConfigColumnMeta(11, "tag", "int"),
        new ConfigColumnMeta(12, "grading", "string"));
  }
}
