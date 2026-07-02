package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityInfoConfigCheckerBase extends AbstractConfigChecker<ActivityInfoConfig> {
  @Override
  public String getConfigFileName() {
    return "activityInfo.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "openType", "INT"),
        new ConfigColumnMeta(3, "scheDuling", "INT"),
        new ConfigColumnMeta(4, "openPara1", "INT"),
        new ConfigColumnMeta(5, "openPara2", "INT"),
        new ConfigColumnMeta(6, "openPara3", "INT"),
        new ConfigColumnMeta(7, "timeType", "INT"),
        new ConfigColumnMeta(8, "startTime", "STRING"),
        new ConfigColumnMeta(9, "endTime", "STRING"),
        new ConfigColumnMeta(10, "specialEndTime", "STRING"),
        new ConfigColumnMeta(11, "freshTime", "STRING"),
        new ConfigColumnMeta(12, "closeTime", "INT"),
        new ConfigColumnMeta(13, "closeActivity", "INT"),
        new ConfigColumnMeta(14, "OpenServiceActivity", "INT"),
        new ConfigColumnMeta(15, "integralType", "INT"),
        new ConfigColumnMeta(16, "integralStage", "STRING"),
        new ConfigColumnMeta(17, "integralReward", "STRING"),
        new ConfigColumnMeta(18, "integralRewardShow", "STRING"),
        new ConfigColumnMeta(19, "title", "STRING"),
        new ConfigColumnMeta(20, "picture", "STRING"),
        new ConfigColumnMeta(21, "description", "STRING"),
        new ConfigColumnMeta(22, "para1", "STRING"),
        new ConfigColumnMeta(23, "para2", "STRING"),
        new ConfigColumnMeta(24, "para3", "STRING"),
        new ConfigColumnMeta(25, "mailTemplateId", "INT"),
        new ConfigColumnMeta(26, "exchangeResources", "STRING"),
        new ConfigColumnMeta(27, "enterType", "INT"),
        new ConfigColumnMeta(28, "sort", "INT"),
        new ConfigColumnMeta(29, "des", "INT"),
        new ConfigColumnMeta(30, "destime", "STRING"),
        new ConfigColumnMeta(31, "timeDown", "INT"),
        new ConfigColumnMeta(32, "desPic", "STRING"),
        new ConfigColumnMeta(33, "RechargeId", "STRING"),
        new ConfigColumnMeta(34, "iACTIVITYTYPE", "INT"),
        new ConfigColumnMeta(35, "topId", "INT"),
        new ConfigColumnMeta(36, "NoShow", "INT"),
        new ConfigColumnMeta(37, "DisplayFunctionType", "INT"),
        new ConfigColumnMeta(38, "DisplayFunctionParam", "STRING"));
  }
}
