package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class PriceFacotrConfigCheckerBase extends AbstractConfigChecker<PriceFacotrConfig> {
  @Override
  public String getConfigFileName() {
    return "priceFacotr.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "level", "string"),
        new ConfigColumnMeta(2, "itemId", "int"),
        new ConfigColumnMeta(3, "price", "int"));
  }
}
