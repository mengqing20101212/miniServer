package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ReserverecruitConfigCheckerBase extends AbstractConfigChecker<ReserverecruitConfig> {
  @Override
  public String getConfigFileName() {
    return "reserverecruit.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "welfareId", "int"),
        new ConfigColumnMeta(2, "recruitNum", "int"),
        new ConfigColumnMeta(3, "awardList", "string"),
        new ConfigColumnMeta(4, "awardRelativePro", "string"),
        new ConfigColumnMeta(5, "desc", "string"),
        new ConfigColumnMeta(6, "dayLimit", "int"),
        new ConfigColumnMeta(7, "Guaranteed", "int"),
        new ConfigColumnMeta(8, "Guaranteerange", "string"),
        new ConfigColumnMeta(9, "intervaltime", "int"),
        new ConfigColumnMeta(10, "Receiveaward", "int"),
        new ConfigColumnMeta(11, "ssrupperlimit", "int"));
  }
}
