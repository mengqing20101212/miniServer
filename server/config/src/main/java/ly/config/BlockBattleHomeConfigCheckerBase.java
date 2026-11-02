package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class BlockBattleHomeConfigCheckerBase extends AbstractConfigChecker<BlockBattleHomeConfig> {
  @Override
  public String getConfigFileName() {
    return "blockBattleHome.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "sceneInfo", "int"),
        new ConfigColumnMeta(3, "image", "int"),
        new ConfigColumnMeta(4, "Dec", "string"),
        new ConfigColumnMeta(5, "missionId", "int"),
        new ConfigColumnMeta(6, "unlockDec", "string"));
  }
}
