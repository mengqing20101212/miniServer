package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class Drop2ConfigCheckerBase extends AbstractConfigChecker<Drop2Config> {
  @Override
  public String getConfigFileName() {
    return "drop2.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "dropType", "int"),
        new ConfigColumnMeta(2, "des", "string"),
        new ConfigColumnMeta(3, "dropList", "string"),
        new ConfigColumnMeta(4, "itemPro", "string"),
        new ConfigColumnMeta(5, "itemRelativePro", "string"),
        new ConfigColumnMeta(6, "dropTimes", "string"),
        new ConfigColumnMeta(7, "dropCounts", "string"));
  }
}
