package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ShopType2ConfigCheckerBase extends AbstractConfigChecker<ShopType2Config> {
  @Override
  public String getConfigFileName() {
    return "shopType2.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "type", "INT"),
        new ConfigColumnMeta(3, "subShopList", "STRING"),
        new ConfigColumnMeta(4, "openType", "INT"),
        new ConfigColumnMeta(5, "openPara1", "INT"),
        new ConfigColumnMeta(6, "openPara2", "INT"),
        new ConfigColumnMeta(7, "isShow", "INT"),
        new ConfigColumnMeta(8, "showSort", "INT"));
  }
}
