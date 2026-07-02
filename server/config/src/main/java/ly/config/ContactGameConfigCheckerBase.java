package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ContactGameConfigCheckerBase extends AbstractConfigChecker<ContactGameConfig> {
  @Override
  public String getConfigFileName() {
    return "contactGame.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "title", "STRING"),
        new ConfigColumnMeta(2, "missionList", "INT"),
        new ConfigColumnMeta(3, "targetNum", "INT"),
        new ConfigColumnMeta(4, "missNum", "INT"),
        new ConfigColumnMeta(5, "targetTag", "INT"),
        new ConfigColumnMeta(6, "rightPool", "STRING"),
        new ConfigColumnMeta(7, "randomNum", "INT"),
        new ConfigColumnMeta(8, "pool", "STRING"));
  }
}
