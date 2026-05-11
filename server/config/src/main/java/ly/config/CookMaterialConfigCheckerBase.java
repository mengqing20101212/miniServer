package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CookMaterialConfigCheckerBase extends AbstractConfigChecker<CookMaterialConfig> {
  @Override
  public String getConfigFileName() {
    return "cookMaterial.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "type", "INT"),
        new ConfigColumnMeta(3, "classify", "INT"),
        new ConfigColumnMeta(4, "para", "INT"),
        new ConfigColumnMeta(5, "star", "INT"),
        new ConfigColumnMeta(6, "showId", "INT"),
        new ConfigColumnMeta(7, "price", "INT"),
        new ConfigColumnMeta(8, "note", "STRING"),
        new ConfigColumnMeta(9, "rewardId", "INT"),
        new ConfigColumnMeta(10, "rewardNum", "INT"),
        new ConfigColumnMeta(11, "friendNum", "INT"),
        new ConfigColumnMeta(12, "critRewardNum", "INT"),
        new ConfigColumnMeta(13, "critFriendNum", "INT"),
        new ConfigColumnMeta(14, "exRewardNum", "INT"),
        new ConfigColumnMeta(15, "exFriendNum", "INT"),
        new ConfigColumnMeta(16, "qteRewardNum", "STRING"),
        new ConfigColumnMeta(17, "qteFriendNum", "STRING"));
  }
}
