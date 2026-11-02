package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityUpRecruitShowConfigCheckerBase extends AbstractConfigChecker<ActivityUpRecruitShowConfig> {
  @Override
  public String getConfigFileName() {
    return "activityUpRecruitShow.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "scheDuling", "int"),
        new ConfigColumnMeta(2, "heroShowStyleType", "int"),
        new ConfigColumnMeta(3, "name", "string"),
        new ConfigColumnMeta(4, "name2", "string"),
        new ConfigColumnMeta(5, "namePos", "string"),
        new ConfigColumnMeta(6, "namePos2", "string"),
        new ConfigColumnMeta(7, "rolePic", "int"),
        new ConfigColumnMeta(8, "rolePicPosOffset", "string"),
        new ConfigColumnMeta(9, "rolePicScaleOffset", "string"),
        new ConfigColumnMeta(10, "rolePic2", "int"),
        new ConfigColumnMeta(11, "rolePicPosOffset2", "string"),
        new ConfigColumnMeta(12, "rolePicScaleOffset2", "string"),
        new ConfigColumnMeta(13, "getDesPicResId", "int"),
        new ConfigColumnMeta(14, "getDesText", "string"),
        new ConfigColumnMeta(15, "skillPicResId1", "int"),
        new ConfigColumnMeta(16, "skillPicResId1PosOffset", "string"),
        new ConfigColumnMeta(17, "skillPicResId2", "int"),
        new ConfigColumnMeta(18, "skillPicResId2PosOffset", "string"),
        new ConfigColumnMeta(19, "jumpType", "int"),
        new ConfigColumnMeta(20, "sceneId", "int"),
        new ConfigColumnMeta(21, "turnId", "int"),
        new ConfigColumnMeta(22, "drawCardDecPic", "int"),
        new ConfigColumnMeta(23, "bgPic", "int"),
        new ConfigColumnMeta(24, "bgHeroPics", "string"),
        new ConfigColumnMeta(25, "bgPicPosOffset", "string"),
        new ConfigColumnMeta(26, "bgPicScaleOffset", "string"),
        new ConfigColumnMeta(27, "holographic", "int"),
        new ConfigColumnMeta(28, "trueActivityId", "int"),
        new ConfigColumnMeta(29, "SpecialEffects", "int"));
  }
}
