package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class AcCommanderGiftConfigCheckerBase extends AbstractConfigChecker<AcCommanderGiftConfig> {
  @Override
  public String getConfigFileName() {
    return "acCommanderGift.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "heroid", "STRING"),
        new ConfigColumnMeta(2, "page", "INT"),
        new ConfigColumnMeta(3, "name", "STRING"),
        new ConfigColumnMeta(4, "rewardShow", "INT"),
        new ConfigColumnMeta(5, "drop", "INT"),
        new ConfigColumnMeta(6, "redirectionId", "INT"),
        new ConfigColumnMeta(7, "commanderShow", "STRING"));
  }
}
