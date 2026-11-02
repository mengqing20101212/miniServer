package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class OppoentPushConfigCheckerBase extends AbstractConfigChecker<OppoentPushConfig> {
  @Override
  public String getConfigFileName() {
    return "oppoentPush.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "rounds", "int"),
        new ConfigColumnMeta(2, "levelMy", "int"),
        new ConfigColumnMeta(3, "levelOpponent", "int"),
        new ConfigColumnMeta(4, "difficultNum", "int"),
        new ConfigColumnMeta(5, "normalNum", "int"),
        new ConfigColumnMeta(6, "easyNum", "int"),
        new ConfigColumnMeta(7, "robotNum", "int"));
  }
}
