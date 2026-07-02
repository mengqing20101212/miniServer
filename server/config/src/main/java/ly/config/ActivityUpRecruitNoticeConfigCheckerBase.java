package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityUpRecruitNoticeConfigCheckerBase extends AbstractConfigChecker<ActivityUpRecruitNoticeConfig> {
  @Override
  public String getConfigFileName() {
    return "activityUpRecruitNotice.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "scheDuling", "INT"),
        new ConfigColumnMeta(2, "name", "STRING"),
        new ConfigColumnMeta(3, "heroShowStyleType", "INT"),
        new ConfigColumnMeta(4, "rolePic", "INT"),
        new ConfigColumnMeta(5, "rolePicPosOffset", "STRING"),
        new ConfigColumnMeta(6, "rolePicScaleOffset", "STRING"),
        new ConfigColumnMeta(7, "getDesPicResId", "INT"),
        new ConfigColumnMeta(8, "skillPicResId1", "INT"),
        new ConfigColumnMeta(9, "skillPicResId1PosOffset", "STRING"),
        new ConfigColumnMeta(10, "skillPicResId2", "INT"),
        new ConfigColumnMeta(11, "skillPicResId2PosOffset", "STRING"),
        new ConfigColumnMeta(12, "jumpType", "INT"),
        new ConfigColumnMeta(13, "sceneId", "INT"),
        new ConfigColumnMeta(14, "drawCardDecPic", "INT"),
        new ConfigColumnMeta(15, "bgPic", "INT"),
        new ConfigColumnMeta(16, "bgHeroPics", "STRING"),
        new ConfigColumnMeta(17, "bgPicPosOffset", "STRING"),
        new ConfigColumnMeta(18, "bgPicScaleOffset", "STRING"),
        new ConfigColumnMeta(19, "numberPic", "STRING"),
        new ConfigColumnMeta(20, "activityId", "INT"),
        new ConfigColumnMeta(21, "trueActivityId", "INT"),
        new ConfigColumnMeta(22, "holographic", "INT"),
        new ConfigColumnMeta(23, "SpecialEffects", "INT"));
  }
}
