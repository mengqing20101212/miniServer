package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class BattleFieldConfigCheckerBase extends AbstractConfigChecker<BattleFieldConfig> {
  @Override
  public String getConfigFileName() {
    return "battleField.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "posListRed", "string"),
        new ConfigColumnMeta(2, "posListBlue", "string"),
        new ConfigColumnMeta(3, "frontAllRed", "string"),
        new ConfigColumnMeta(4, "frontAllBlue", "string"),
        new ConfigColumnMeta(5, "BFCenterRed", "string"),
        new ConfigColumnMeta(6, "BFCenterBlue", "string"),
        new ConfigColumnMeta(7, "BFCenter", "string"),
        new ConfigColumnMeta(8, "cameraListRed", "string"),
        new ConfigColumnMeta(9, "cameraListBlue", "string"),
        new ConfigColumnMeta(10, "posListRedSummon", "string"),
        new ConfigColumnMeta(11, "posListBlueSummon", "string"),
        new ConfigColumnMeta(12, "sceneConfig", "string"),
        new ConfigColumnMeta(13, "sceneConfigEnemy", "string"),
        new ConfigColumnMeta(14, "lineupType", "int"),
        new ConfigColumnMeta(15, "lineupTypeEnemy", "int"));
  }
}
