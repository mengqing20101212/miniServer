package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class RecruitUpShowConfigCheckerBase extends AbstractConfigChecker<RecruitUpShowConfig> {
  @Override
  public String getConfigFileName() {
    return "recruitUpShow.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "scheDuling", "INT"),
        new ConfigColumnMeta(2, "piecePicResId", "INT"),
        new ConfigColumnMeta(3, "heroId", "INT"),
        new ConfigColumnMeta(4, "heroName", "STRING"),
        new ConfigColumnMeta(5, "contentPicResId", "INT"),
        new ConfigColumnMeta(6, "jumpType", "INT"),
        new ConfigColumnMeta(7, "sceneId", "INT"),
        new ConfigColumnMeta(8, "heroShowStyleType", "INT"),
        new ConfigColumnMeta(9, "heroPicResId", "STRING"),
        new ConfigColumnMeta(10, "heroJumpId", "STRING"),
        new ConfigColumnMeta(11, "videoName", "STRING"),
        new ConfigColumnMeta(12, "videoHeroPic", "INT"),
        new ConfigColumnMeta(13, "videoHeroInfoPic", "INT"),
        new ConfigColumnMeta(14, "videoHeroPicPosOffset", "STRING"),
        new ConfigColumnMeta(15, "isShowHolograpicLogo", "INT"),
        new ConfigColumnMeta(16, "isShowReturnLogo", "INT"),
        new ConfigColumnMeta(17, "gifBagIcon", "INT"),
        new ConfigColumnMeta(18, "gifBagTurn", "INT"),
        new ConfigColumnMeta(19, "turnId", "INT"),
        new ConfigColumnMeta(20, "trueActivityId", "INT"));
  }
}
