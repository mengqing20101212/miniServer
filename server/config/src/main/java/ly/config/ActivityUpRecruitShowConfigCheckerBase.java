package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityUpRecruitShowConfigCheckerBase extends AbstractConfigChecker<ActivityUpRecruitShowConfig> {
  @Override
  public String getConfigFileName() {
    return "activityUpRecruitShow.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "scheDuling", "INT"),
        new ConfigColumnMeta(2, "heroShowStyleType", "INT"),
        new ConfigColumnMeta(3, "name", "STRING"),
        new ConfigColumnMeta(4, "name2", "STRING"),
        new ConfigColumnMeta(5, "namePos", "STRING"),
        new ConfigColumnMeta(6, "namePos2", "STRING"),
        new ConfigColumnMeta(7, "rolePic", "INT"),
        new ConfigColumnMeta(8, "rolePicPosOffset", "STRING"),
        new ConfigColumnMeta(9, "rolePicScaleOffset", "STRING"),
        new ConfigColumnMeta(10, "rolePic2", "INT"),
        new ConfigColumnMeta(11, "rolePicPosOffset2", "STRING"),
        new ConfigColumnMeta(12, "rolePicScaleOffset2", "STRING"),
        new ConfigColumnMeta(13, "getDesPicResId", "INT"),
        new ConfigColumnMeta(14, "getDesText", "STRING"),
        new ConfigColumnMeta(15, "skillPicResId1", "INT"),
        new ConfigColumnMeta(16, "skillPicResId1PosOffset", "STRING"),
        new ConfigColumnMeta(17, "skillPicResId2", "INT"),
        new ConfigColumnMeta(18, "skillPicResId2PosOffset", "STRING"),
        new ConfigColumnMeta(19, "jumpType", "INT"),
        new ConfigColumnMeta(20, "sceneId", "INT"),
        new ConfigColumnMeta(21, "turnId", "INT"),
        new ConfigColumnMeta(22, "drawCardDecPic", "INT"),
        new ConfigColumnMeta(23, "bgPic", "INT"),
        new ConfigColumnMeta(24, "bgHeroPics", "STRING"),
        new ConfigColumnMeta(25, "bgPicPosOffset", "STRING"),
        new ConfigColumnMeta(26, "bgPicScaleOffset", "STRING"),
        new ConfigColumnMeta(27, "holographic", "INT"),
        new ConfigColumnMeta(28, "trueActivityId", "INT"),
        new ConfigColumnMeta(29, "SpecialEffects", "INT"));
  }
}
