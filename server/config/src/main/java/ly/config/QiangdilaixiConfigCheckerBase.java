package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class QiangdilaixiConfigCheckerBase extends AbstractConfigChecker<QiangdilaixiConfig> {
  @Override
  public String getConfigFileName() {
    return "qiangdilaixi.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "config_name", "STRING"),
        new ConfigColumnMeta(2, "name", "STRING"),
        new ConfigColumnMeta(3, "detail", "STRING"),
        new ConfigColumnMeta(4, "timeline", "STRING"),
        new ConfigColumnMeta(5, "UIResource", "INT"),
        new ConfigColumnMeta(6, "ScalePosOffset", "STRING"),
        new ConfigColumnMeta(7, "stopAt", "STRING"));
  }
}
