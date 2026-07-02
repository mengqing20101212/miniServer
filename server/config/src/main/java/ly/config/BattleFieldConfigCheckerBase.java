package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class BattleFieldConfigCheckerBase extends AbstractConfigChecker<BattleFieldConfig> {
  @Override
  public String getConfigFileName() {
    return "battleField.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "posListRed", "STRING"),
        new ConfigColumnMeta(2, "posListBlue", "STRING"),
        new ConfigColumnMeta(3, "frontAllRed", "STRING"),
        new ConfigColumnMeta(4, "frontAllBlue", "STRING"),
        new ConfigColumnMeta(5, "BFCenterRed", "STRING"),
        new ConfigColumnMeta(6, "BFCenterBlue", "STRING"),
        new ConfigColumnMeta(7, "BFCenter", "STRING"),
        new ConfigColumnMeta(8, "cameraListRed", "STRING"),
        new ConfigColumnMeta(9, "cameraListBlue", "STRING"),
        new ConfigColumnMeta(10, "posListRedSummon", "STRING"),
        new ConfigColumnMeta(11, "posListBlueSummon", "STRING"),
        new ConfigColumnMeta(12, "sceneConfig", "STRING"),
        new ConfigColumnMeta(13, "sceneConfigEnemy", "STRING"),
        new ConfigColumnMeta(14, "lineupType", "INT"),
        new ConfigColumnMeta(15, "lineupTypeEnemy", "INT"));
  }
}
