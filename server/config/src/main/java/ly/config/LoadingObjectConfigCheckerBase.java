package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class LoadingObjectConfigCheckerBase extends AbstractConfigChecker<LoadingObjectConfig> {
  @Override
  public String getConfigFileName() {
    return "loadingObject.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "describe", "string"),
        new ConfigColumnMeta(2, "type", "int"),
        new ConfigColumnMeta(3, "para", "string"),
        new ConfigColumnMeta(4, "videoId", "string"),
        new ConfigColumnMeta(5, "pictureId", "int"),
        new ConfigColumnMeta(6, "bgColor", "string"),
        new ConfigColumnMeta(7, "audioId", "int"),
        new ConfigColumnMeta(8, "isHaveTransition", "int"));
  }
}
