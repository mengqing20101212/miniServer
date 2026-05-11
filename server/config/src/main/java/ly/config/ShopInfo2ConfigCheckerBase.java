package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ShopInfo2ConfigCheckerBase extends AbstractConfigChecker<ShopInfo2Config> {
  @Override
  public String getConfigFileName() {
    return "shopInfo2.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "grade", "INT"),
        new ConfigColumnMeta(2, "school", "INT"),
        new ConfigColumnMeta(3, "name", "STRING"),
        new ConfigColumnMeta(4, "type", "INT"),
        new ConfigColumnMeta(5, "showType", "INT"),
        new ConfigColumnMeta(6, "shopPara", "STRING"),
        new ConfigColumnMeta(7, "commodityList", "STRING"),
        new ConfigColumnMeta(8, "openType", "INT"),
        new ConfigColumnMeta(9, "RefreshBarType", "INT"),
        new ConfigColumnMeta(10, "openPara1", "INT"),
        new ConfigColumnMeta(11, "openPara2", "INT"),
        new ConfigColumnMeta(12, "openType2", "INT"),
        new ConfigColumnMeta(13, "openPara3", "INT"),
        new ConfigColumnMeta(14, "topId", "INT"),
        new ConfigColumnMeta(15, "background", "INT"),
        new ConfigColumnMeta(16, "subStoreShow", "INT"),
        new ConfigColumnMeta(17, "shopShow", "INT"));
  }
}
