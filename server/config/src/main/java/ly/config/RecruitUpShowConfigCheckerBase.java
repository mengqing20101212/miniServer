package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class RecruitUpShowConfigCheckerBase extends AbstractConfigChecker<RecruitUpShowConfig> {
  @Override
  public String getConfigFileName() {
    return "recruitUpShow.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "scheDuling", "int"),
        new ConfigColumnMeta(2, "piecePicResId", "int"),
        new ConfigColumnMeta(3, "heroId", "int"),
        new ConfigColumnMeta(4, "heroName", "string"),
        new ConfigColumnMeta(5, "contentPicResId", "int"),
        new ConfigColumnMeta(6, "jumpType", "int"),
        new ConfigColumnMeta(7, "sceneId", "int"),
        new ConfigColumnMeta(8, "heroShowStyleType", "int"),
        new ConfigColumnMeta(9, "heroPicResId", "string"),
        new ConfigColumnMeta(10, "heroJumpId", "string"),
        new ConfigColumnMeta(11, "videoName", "string"),
        new ConfigColumnMeta(12, "videoHeroPic", "int"),
        new ConfigColumnMeta(13, "videoHeroInfoPic", "int"),
        new ConfigColumnMeta(14, "videoHeroPicPosOffset", "string"),
        new ConfigColumnMeta(15, "isShowHolograpicLogo", "int"),
        new ConfigColumnMeta(16, "isShowReturnLogo", "int"),
        new ConfigColumnMeta(17, "gifBagIcon", "int"),
        new ConfigColumnMeta(18, "gifBagTurn", "int"),
        new ConfigColumnMeta(19, "turnId", "int"),
        new ConfigColumnMeta(20, "trueActivityId", "int"));
  }
}
