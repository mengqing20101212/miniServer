package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ReserverecruitConfigCheckerBase extends AbstractConfigChecker<ReserverecruitConfig> {
  @Override
  public String getConfigFileName() {
    return "reserverecruit.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "welfareId", "INT"),
        new ConfigColumnMeta(2, "recruitNum", "INT"),
        new ConfigColumnMeta(3, "awardList", "STRING"),
        new ConfigColumnMeta(4, "awardRelativePro", "STRING"),
        new ConfigColumnMeta(5, "desc", "STRING"),
        new ConfigColumnMeta(6, "dayLimit", "INT"),
        new ConfigColumnMeta(7, "Guaranteed", "INT"),
        new ConfigColumnMeta(8, "Guaranteerange", "STRING"),
        new ConfigColumnMeta(9, "intervaltime", "INT"),
        new ConfigColumnMeta(10, "Receiveaward", "INT"),
        new ConfigColumnMeta(11, "ssrupperlimit", "INT"));
  }
}
