package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class PokerCardConfigCheckerBase extends AbstractConfigChecker<PokerCardConfig> {
  @Override
  public String getConfigFileName() {
    return "pokerCard.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "pic", "int"),
        new ConfigColumnMeta(3, "cutUp", "string"),
        new ConfigColumnMeta(4, "picBase", "int"),
        new ConfigColumnMeta(5, "typePic", "int"),
        new ConfigColumnMeta(6, "rarePic", "int"),
        new ConfigColumnMeta(7, "bounusList", "list<int>"));
  }
}
