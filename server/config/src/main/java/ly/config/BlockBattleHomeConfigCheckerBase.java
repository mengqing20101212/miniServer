package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class BlockBattleHomeConfigCheckerBase extends AbstractConfigChecker<BlockBattleHomeConfig> {
  @Override
  public String getConfigFileName() {
    return "blockBattleHome.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "sceneInfo", "INT"),
        new ConfigColumnMeta(3, "image", "INT"),
        new ConfigColumnMeta(4, "Dec", "STRING"),
        new ConfigColumnMeta(5, "missionId", "INT"),
        new ConfigColumnMeta(6, "unlockDec", "STRING"));
  }
}
