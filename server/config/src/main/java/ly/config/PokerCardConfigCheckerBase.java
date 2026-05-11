package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class PokerCardConfigCheckerBase extends AbstractConfigChecker<PokerCardConfig> {
  @Override
  public String getConfigFileName() {
    return "pokerCard.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "pic", "INT"),
        new ConfigColumnMeta(3, "cutUp", "STRING"),
        new ConfigColumnMeta(4, "picBase", "INT"),
        new ConfigColumnMeta(5, "typePic", "INT"),
        new ConfigColumnMeta(6, "rarePic", "INT"),
        new ConfigColumnMeta(7, "bounusList", "LIST<INT>"));
  }
}
