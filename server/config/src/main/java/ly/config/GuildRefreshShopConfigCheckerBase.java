package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class GuildRefreshShopConfigCheckerBase extends AbstractConfigChecker<GuildRefreshShopConfig> {
  @Override
  public String getConfigFileName() {
    return "guildRefreshShop.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "itemId", "INT"),
        new ConfigColumnMeta(2, "itemNum", "INT"),
        new ConfigColumnMeta(3, "weight", "INT"),
        new ConfigColumnMeta(4, "currencyType", "INT"),
        new ConfigColumnMeta(5, "price", "INT"),
        new ConfigColumnMeta(6, "discount", "INT"),
        new ConfigColumnMeta(7, "level", "INT"),
        new ConfigColumnMeta(8, "coloredEggs", "STRING"));
  }
}
