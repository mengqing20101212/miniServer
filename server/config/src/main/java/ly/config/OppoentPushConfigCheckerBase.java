package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class OppoentPushConfigCheckerBase extends AbstractConfigChecker<OppoentPushConfig> {
  @Override
  public String getConfigFileName() {
    return "oppoentPush.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "rounds", "INT"),
        new ConfigColumnMeta(2, "levelMy", "INT"),
        new ConfigColumnMeta(3, "levelOpponent", "INT"),
        new ConfigColumnMeta(4, "difficultNum", "INT"),
        new ConfigColumnMeta(5, "normalNum", "INT"),
        new ConfigColumnMeta(6, "easyNum", "INT"),
        new ConfigColumnMeta(7, "robotNum", "INT"));
  }
}
