package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class PlayerHeadConfigCheckerBase extends AbstractConfigChecker<PlayerHeadConfig> {
  @Override
  public String getConfigFileName() {
    return "playerHead.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "des", "STRING"),
        new ConfigColumnMeta(3, "icon", "INT"),
        new ConfigColumnMeta(4, "unlockType", "INT"),
        new ConfigColumnMeta(5, "unlockPara", "STRING"));
  }
}
