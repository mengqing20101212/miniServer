package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ShopFreeDicConfigCheckerBase extends AbstractConfigChecker<ShopFreeDicConfig> {
  @Override
  public String getConfigFileName() {
    return "shopFreeDic.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "showShopId", "INT"),
        new ConfigColumnMeta(2, "trueShopId", "INT"),
        new ConfigColumnMeta(3, "showCommodityId", "INT"),
        new ConfigColumnMeta(4, "trueCommodityId", "INT"));
  }
}
