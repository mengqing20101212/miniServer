package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ShopInfo1ConfigCheckerBase extends AbstractConfigChecker<ShopInfo1Config> {
  @Override
  public String getConfigFileName() {
    return "shopInfo1.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "grade", "int"),
        new ConfigColumnMeta(2, "school", "int"),
        new ConfigColumnMeta(3, "name", "string"),
        new ConfigColumnMeta(4, "type", "int"),
        new ConfigColumnMeta(5, "showType", "int"),
        new ConfigColumnMeta(6, "shopPara", "string"),
        new ConfigColumnMeta(7, "commodityList", "string"),
        new ConfigColumnMeta(8, "openType", "int"),
        new ConfigColumnMeta(9, "RefreshBarType", "int"),
        new ConfigColumnMeta(10, "openPara1", "int"),
        new ConfigColumnMeta(11, "openPara2", "int"),
        new ConfigColumnMeta(12, "openType2", "int"),
        new ConfigColumnMeta(13, "openPara3", "int"),
        new ConfigColumnMeta(14, "topId", "int"),
        new ConfigColumnMeta(15, "background", "int"),
        new ConfigColumnMeta(16, "subStoreShow", "int"),
        new ConfigColumnMeta(17, "shopShow", "int"));
  }
}
