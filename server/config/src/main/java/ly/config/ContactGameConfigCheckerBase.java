package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ContactGameConfigCheckerBase extends AbstractConfigChecker<ContactGameConfig> {
  @Override
  public String getConfigFileName() {
    return "contactGame.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "title", "string"),
        new ConfigColumnMeta(2, "missionList", "int"),
        new ConfigColumnMeta(3, "targetNum", "int"),
        new ConfigColumnMeta(4, "missNum", "int"),
        new ConfigColumnMeta(5, "targetTag", "int"),
        new ConfigColumnMeta(6, "rightPool", "string"),
        new ConfigColumnMeta(7, "randomNum", "int"),
        new ConfigColumnMeta(8, "pool", "string"));
  }
}
