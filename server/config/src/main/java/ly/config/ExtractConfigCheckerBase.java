package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ExtractConfigCheckerBase extends AbstractConfigChecker<ExtractConfig> {
  @Override
  public String getConfigFileName() {
    return "extract.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "level", "INT"),
        new ConfigColumnMeta(3, "ranking", "STRING"),
        new ConfigColumnMeta(4, "rankNum", "INT"),
        new ConfigColumnMeta(5, "city", "STRING"),
        new ConfigColumnMeta(6, "lines", "STRING"),
        new ConfigColumnMeta(7, "random", "INT"),
        new ConfigColumnMeta(8, "bodyPic", "INT"),
        new ConfigColumnMeta(9, "features", "STRING"),
        new ConfigColumnMeta(10, "timeline", "STRING"),
        new ConfigColumnMeta(11, "bg", "INT"),
        new ConfigColumnMeta(12, "englishName", "STRING"),
        new ConfigColumnMeta(13, "smallPicResId", "INT"),
        new ConfigColumnMeta(14, "smallPicPosOffset", "STRING"),
        new ConfigColumnMeta(15, "smallPicRotationOffset", "STRING"),
        new ConfigColumnMeta(16, "smallPicScale", "STRING"),
        new ConfigColumnMeta(17, "bodyQualityBgResId", "INT"),
        new ConfigColumnMeta(18, "nameQualityBgResId", "INT"),
        new ConfigColumnMeta(19, "isRepeatPlay", "INT"),
        new ConfigColumnMeta(20, "backgroundId", "INT"),
        new ConfigColumnMeta(21, "shareAnimation", "INT"),
        new ConfigColumnMeta(22, "sharePicScale", "STRING"),
        new ConfigColumnMeta(23, "sharePicPosOffset", "STRING"));
  }
}
