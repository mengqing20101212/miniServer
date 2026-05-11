package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class Drop2ConfigCheckerBase extends AbstractConfigChecker<Drop2Config> {
  @Override
  public String getConfigFileName() {
    return "drop2.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "dropType", "INT"),
        new ConfigColumnMeta(2, "des", "STRING"),
        new ConfigColumnMeta(3, "dropList", "STRING"),
        new ConfigColumnMeta(4, "itemPro", "STRING"),
        new ConfigColumnMeta(5, "itemRelativePro", "STRING"),
        new ConfigColumnMeta(6, "dropTimes", "STRING"),
        new ConfigColumnMeta(7, "dropCounts", "STRING"));
  }
}
