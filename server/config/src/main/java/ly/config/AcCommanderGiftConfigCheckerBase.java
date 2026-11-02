package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class AcCommanderGiftConfigCheckerBase extends AbstractConfigChecker<AcCommanderGiftConfig> {
  @Override
  public String getConfigFileName() {
    return "acCommanderGift.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "heroid", "string"),
        new ConfigColumnMeta(2, "page", "int"),
        new ConfigColumnMeta(3, "name", "string"),
        new ConfigColumnMeta(4, "rewardShow", "int"),
        new ConfigColumnMeta(5, "drop", "int"),
        new ConfigColumnMeta(6, "redirectionId", "int"),
        new ConfigColumnMeta(7, "commanderShow", "string"));
  }
}
