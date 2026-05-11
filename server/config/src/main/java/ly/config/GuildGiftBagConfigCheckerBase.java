package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class GuildGiftBagConfigCheckerBase extends AbstractConfigChecker<GuildGiftBagConfig> {
  @Override
  public String getConfigFileName() {
    return "guildGiftBag.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "type", "INT"),
        new ConfigColumnMeta(2, "name", "STRING"),
        new ConfigColumnMeta(3, "drop", "INT"),
        new ConfigColumnMeta(4, "cost", "INT"),
        new ConfigColumnMeta(5, "reward", "INT"),
        new ConfigColumnMeta(6, "Num", "INT"),
        new ConfigColumnMeta(7, "heroId", "STRING"),
        new ConfigColumnMeta(8, "active", "INT"),
        new ConfigColumnMeta(9, "recharge", "INT"),
        new ConfigColumnMeta(10, "giftIcon", "INT"),
        new ConfigColumnMeta(11, "tag", "INT"),
        new ConfigColumnMeta(12, "grading", "STRING"));
  }
}
