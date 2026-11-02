package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class QiangdilaixiConfigCheckerBase extends AbstractConfigChecker<QiangdilaixiConfig> {
  @Override
  public String getConfigFileName() {
    return "qiangdilaixi.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "config_name", "string"),
        new ConfigColumnMeta(2, "name", "string"),
        new ConfigColumnMeta(3, "detail", "string"),
        new ConfigColumnMeta(4, "timeline", "string"),
        new ConfigColumnMeta(5, "UIResource", "int"),
        new ConfigColumnMeta(6, "ScalePosOffset", "string"),
        new ConfigColumnMeta(7, "stopAt", "string"));
  }
}
