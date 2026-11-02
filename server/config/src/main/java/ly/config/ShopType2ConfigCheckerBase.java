package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ShopType2ConfigCheckerBase extends AbstractConfigChecker<ShopType2Config> {
  @Override
  public String getConfigFileName() {
    return "shopType2.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "type", "int"),
        new ConfigColumnMeta(3, "subShopList", "string"),
        new ConfigColumnMeta(4, "openType", "int"),
        new ConfigColumnMeta(5, "openPara1", "int"),
        new ConfigColumnMeta(6, "openPara2", "int"),
        new ConfigColumnMeta(7, "isShow", "int"),
        new ConfigColumnMeta(8, "showSort", "int"));
  }
}
