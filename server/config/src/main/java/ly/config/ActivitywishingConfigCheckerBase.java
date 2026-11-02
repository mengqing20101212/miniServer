package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivitywishingConfigCheckerBase extends AbstractConfigChecker<ActivitywishingConfig> {
  @Override
  public String getConfigFileName() {
    return "activitywishing.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "wishingNum", "int"),
        new ConfigColumnMeta(2, "awardList", "string"),
        new ConfigColumnMeta(3, "awardRelativePro", "string"),
        new ConfigColumnMeta(4, "desc", "string"),
        new ConfigColumnMeta(5, "dayLimit", "int"),
        new ConfigColumnMeta(6, "Guaranteed", "int"),
        new ConfigColumnMeta(7, "Guaranteerange", "string"),
        new ConfigColumnMeta(8, "GuaranteerangeWeights", "string"),
        new ConfigColumnMeta(9, "intervaltime", "int"),
        new ConfigColumnMeta(10, "Receiveaward", "int"),
        new ConfigColumnMeta(11, "rewardShow", "string"),
        new ConfigColumnMeta(12, "pictures1", "string"),
        new ConfigColumnMeta(13, "pictures2", "string"));
  }
}
