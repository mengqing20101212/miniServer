package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityInfoConfigCheckerBase extends AbstractConfigChecker<ActivityInfoConfig> {
  @Override
  public String getConfigFileName() {
    return "activityInfo.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "openType", "int"),
        new ConfigColumnMeta(3, "scheDuling", "int"),
        new ConfigColumnMeta(4, "openPara1", "int"),
        new ConfigColumnMeta(5, "openPara2", "int"),
        new ConfigColumnMeta(6, "openPara3", "int"),
        new ConfigColumnMeta(7, "timeType", "int"),
        new ConfigColumnMeta(8, "startTime", "string"),
        new ConfigColumnMeta(9, "endTime", "string"),
        new ConfigColumnMeta(10, "specialEndTime", "string"),
        new ConfigColumnMeta(11, "freshTime", "string"),
        new ConfigColumnMeta(12, "closeTime", "int"),
        new ConfigColumnMeta(13, "closeActivity", "int"),
        new ConfigColumnMeta(14, "OpenServiceActivity", "int"),
        new ConfigColumnMeta(15, "integralType", "int"),
        new ConfigColumnMeta(16, "integralStage", "string"),
        new ConfigColumnMeta(17, "integralReward", "string"),
        new ConfigColumnMeta(18, "integralRewardShow", "string"),
        new ConfigColumnMeta(19, "title", "string"),
        new ConfigColumnMeta(20, "picture", "string"),
        new ConfigColumnMeta(21, "description", "string"),
        new ConfigColumnMeta(22, "para1", "string"),
        new ConfigColumnMeta(23, "para2", "string"),
        new ConfigColumnMeta(24, "para3", "string"),
        new ConfigColumnMeta(25, "mailTemplateId", "int"),
        new ConfigColumnMeta(26, "exchangeResources", "string"),
        new ConfigColumnMeta(27, "enterType", "int"),
        new ConfigColumnMeta(28, "sort", "int"),
        new ConfigColumnMeta(29, "des", "int"),
        new ConfigColumnMeta(30, "destime", "string"),
        new ConfigColumnMeta(31, "timeDown", "int"),
        new ConfigColumnMeta(32, "desPic", "string"),
        new ConfigColumnMeta(33, "RechargeId", "string"),
        new ConfigColumnMeta(34, "iACTIVITYTYPE", "int"),
        new ConfigColumnMeta(35, "topId", "int"),
        new ConfigColumnMeta(36, "NoShow", "int"),
        new ConfigColumnMeta(37, "DisplayFunctionType", "int"),
        new ConfigColumnMeta(38, "DisplayFunctionParam", "string"));
  }
}
