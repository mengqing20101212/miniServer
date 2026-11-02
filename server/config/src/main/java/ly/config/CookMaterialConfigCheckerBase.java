package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CookMaterialConfigCheckerBase extends AbstractConfigChecker<CookMaterialConfig> {
  @Override
  public String getConfigFileName() {
    return "cookMaterial.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "type", "int"),
        new ConfigColumnMeta(3, "classify", "int"),
        new ConfigColumnMeta(4, "para", "int"),
        new ConfigColumnMeta(5, "star", "int"),
        new ConfigColumnMeta(6, "showId", "int"),
        new ConfigColumnMeta(7, "price", "int"),
        new ConfigColumnMeta(8, "note", "string"),
        new ConfigColumnMeta(9, "rewardId", "int"),
        new ConfigColumnMeta(10, "rewardNum", "int"),
        new ConfigColumnMeta(11, "friendNum", "int"),
        new ConfigColumnMeta(12, "critRewardNum", "int"),
        new ConfigColumnMeta(13, "critFriendNum", "int"),
        new ConfigColumnMeta(14, "exRewardNum", "int"),
        new ConfigColumnMeta(15, "exFriendNum", "int"),
        new ConfigColumnMeta(16, "qteRewardNum", "string"),
        new ConfigColumnMeta(17, "qteFriendNum", "string"));
  }
}
