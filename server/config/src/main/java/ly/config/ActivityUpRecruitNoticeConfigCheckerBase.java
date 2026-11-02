package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityUpRecruitNoticeConfigCheckerBase extends AbstractConfigChecker<ActivityUpRecruitNoticeConfig> {
  @Override
  public String getConfigFileName() {
    return "activityUpRecruitNotice.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "scheDuling", "int"),
        new ConfigColumnMeta(2, "name", "string"),
        new ConfigColumnMeta(3, "heroShowStyleType", "int"),
        new ConfigColumnMeta(4, "rolePic", "int"),
        new ConfigColumnMeta(5, "rolePicPosOffset", "string"),
        new ConfigColumnMeta(6, "rolePicScaleOffset", "string"),
        new ConfigColumnMeta(7, "getDesPicResId", "int"),
        new ConfigColumnMeta(8, "skillPicResId1", "int"),
        new ConfigColumnMeta(9, "skillPicResId1PosOffset", "string"),
        new ConfigColumnMeta(10, "skillPicResId2", "int"),
        new ConfigColumnMeta(11, "skillPicResId2PosOffset", "string"),
        new ConfigColumnMeta(12, "jumpType", "int"),
        new ConfigColumnMeta(13, "sceneId", "int"),
        new ConfigColumnMeta(14, "drawCardDecPic", "int"),
        new ConfigColumnMeta(15, "bgPic", "int"),
        new ConfigColumnMeta(16, "bgHeroPics", "string"),
        new ConfigColumnMeta(17, "bgPicPosOffset", "string"),
        new ConfigColumnMeta(18, "bgPicScaleOffset", "string"),
        new ConfigColumnMeta(19, "numberPic", "string"),
        new ConfigColumnMeta(20, "activityId", "int"),
        new ConfigColumnMeta(21, "trueActivityId", "int"),
        new ConfigColumnMeta(22, "holographic", "int"),
        new ConfigColumnMeta(23, "SpecialEffects", "int"));
  }
}
