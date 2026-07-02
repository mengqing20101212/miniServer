package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class BlockBattleConvergeConfigCheckerBase extends AbstractConfigChecker<BlockBattleConvergeConfig> {
  @Override
  public String getConfigFileName() {
    return "blockBattleConverge.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "type", "INT"),
        new ConfigColumnMeta(2, "convergeCheck", "INT"),
        new ConfigColumnMeta(3, "convergePara", "STRING"),
        new ConfigColumnMeta(4, "convergeParaAtack", "STRING"),
        new ConfigColumnMeta(5, "beizhu", "STRING"),
        new ConfigColumnMeta(6, "dec", "STRING"),
        new ConfigColumnMeta(7, "useScence", "INT"));
  }
}
