package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class PokerBounusConfigCheckerBase extends AbstractConfigChecker<PokerBounusConfig> {
  @Override
  public String getConfigFileName() {
    return "pokerBounus.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "type", "INT"),
        new ConfigColumnMeta(3, "member", "STRING"),
        new ConfigColumnMeta(4, "para", "INT"),
        new ConfigColumnMeta(5, "firstClass", "INT"),
        new ConfigColumnMeta(6, "classRank", "INT"),
        new ConfigColumnMeta(7, "score", "INT"),
        new ConfigColumnMeta(8, "hintType", "INT"));
  }
}
