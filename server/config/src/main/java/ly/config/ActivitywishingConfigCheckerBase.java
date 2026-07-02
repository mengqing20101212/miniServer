package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivitywishingConfigCheckerBase extends AbstractConfigChecker<ActivitywishingConfig> {
  @Override
  public String getConfigFileName() {
    return "activitywishing.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "wishingNum", "INT"),
        new ConfigColumnMeta(2, "awardList", "STRING"),
        new ConfigColumnMeta(3, "awardRelativePro", "STRING"),
        new ConfigColumnMeta(4, "desc", "STRING"),
        new ConfigColumnMeta(5, "dayLimit", "INT"),
        new ConfigColumnMeta(6, "Guaranteed", "INT"),
        new ConfigColumnMeta(7, "Guaranteerange", "STRING"),
        new ConfigColumnMeta(8, "GuaranteerangeWeights", "STRING"),
        new ConfigColumnMeta(9, "intervaltime", "INT"),
        new ConfigColumnMeta(10, "Receiveaward", "INT"),
        new ConfigColumnMeta(11, "rewardShow", "STRING"),
        new ConfigColumnMeta(12, "pictures1", "STRING"),
        new ConfigColumnMeta(13, "pictures2", "STRING"));
  }
}
