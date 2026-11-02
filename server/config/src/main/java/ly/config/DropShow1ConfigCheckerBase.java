package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class DropShow1ConfigCheckerBase extends AbstractConfigChecker<DropShow1Config> {
  @Override
  public String getConfigFileName() {
    return "dropShow1.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "beizhu", "string"),
        new ConfigColumnMeta(2, "beizhu2", "string"),
        new ConfigColumnMeta(3, "itemShow", "string"),
        new ConfigColumnMeta(4, "cornerMark", "string"));
  }
}
