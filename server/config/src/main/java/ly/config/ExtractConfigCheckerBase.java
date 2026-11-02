package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ExtractConfigCheckerBase extends AbstractConfigChecker<ExtractConfig> {
  @Override
  public String getConfigFileName() {
    return "extract.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "level", "int"),
        new ConfigColumnMeta(3, "ranking", "string"),
        new ConfigColumnMeta(4, "rankNum", "int"),
        new ConfigColumnMeta(5, "city", "string"),
        new ConfigColumnMeta(6, "lines", "string"),
        new ConfigColumnMeta(7, "random", "int"),
        new ConfigColumnMeta(8, "bodyPic", "int"),
        new ConfigColumnMeta(9, "features", "string"),
        new ConfigColumnMeta(10, "timeline", "string"),
        new ConfigColumnMeta(11, "bg", "int"),
        new ConfigColumnMeta(12, "englishName", "string"),
        new ConfigColumnMeta(13, "smallPicResId", "int"),
        new ConfigColumnMeta(14, "smallPicPosOffset", "string"),
        new ConfigColumnMeta(15, "smallPicRotationOffset", "string"),
        new ConfigColumnMeta(16, "smallPicScale", "string"),
        new ConfigColumnMeta(17, "bodyQualityBgResId", "int"),
        new ConfigColumnMeta(18, "nameQualityBgResId", "int"),
        new ConfigColumnMeta(19, "isRepeatPlay", "int"),
        new ConfigColumnMeta(20, "backgroundId", "int"),
        new ConfigColumnMeta(21, "shareAnimation", "int"),
        new ConfigColumnMeta(22, "sharePicScale", "string"),
        new ConfigColumnMeta(23, "sharePicPosOffset", "string"));
  }
}
