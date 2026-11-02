package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class PokerBounusConfigCheckerBase extends AbstractConfigChecker<PokerBounusConfig> {
  @Override
  public String getConfigFileName() {
    return "pokerBounus.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "type", "int"),
        new ConfigColumnMeta(3, "member", "string"),
        new ConfigColumnMeta(4, "para", "int"),
        new ConfigColumnMeta(5, "firstClass", "int"),
        new ConfigColumnMeta(6, "classRank", "int"),
        new ConfigColumnMeta(7, "score", "int"),
        new ConfigColumnMeta(8, "hintType", "int"));
  }
}
