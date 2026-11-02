package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class GuildRefreshShopConfigCheckerBase extends AbstractConfigChecker<GuildRefreshShopConfig> {
  @Override
  public String getConfigFileName() {
    return "guildRefreshShop.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "itemId", "int"),
        new ConfigColumnMeta(2, "itemNum", "int"),
        new ConfigColumnMeta(3, "weight", "int"),
        new ConfigColumnMeta(4, "currencyType", "int"),
        new ConfigColumnMeta(5, "price", "int"),
        new ConfigColumnMeta(6, "discount", "int"),
        new ConfigColumnMeta(7, "level", "int"),
        new ConfigColumnMeta(8, "coloredEggs", "string"));
  }
}
