package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class BlockBattleConvergeConfigCheckerBase extends AbstractConfigChecker<BlockBattleConvergeConfig> {
  @Override
  public String getConfigFileName() {
    return "blockBattleConverge.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "type", "int"),
        new ConfigColumnMeta(2, "convergeCheck", "int"),
        new ConfigColumnMeta(3, "convergePara", "string"),
        new ConfigColumnMeta(4, "convergeParaAtack", "string"),
        new ConfigColumnMeta(5, "beizhu", "string"),
        new ConfigColumnMeta(6, "dec", "string"),
        new ConfigColumnMeta(7, "useScence", "int"));
  }
}
