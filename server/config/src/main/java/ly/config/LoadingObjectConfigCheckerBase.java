package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class LoadingObjectConfigCheckerBase extends AbstractConfigChecker<LoadingObjectConfig> {
  @Override
  public String getConfigFileName() {
    return "loadingObject.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "describe", "STRING"),
        new ConfigColumnMeta(2, "type", "INT"),
        new ConfigColumnMeta(3, "para", "STRING"),
        new ConfigColumnMeta(4, "videoId", "STRING"),
        new ConfigColumnMeta(5, "pictureId", "INT"),
        new ConfigColumnMeta(6, "bgColor", "STRING"),
        new ConfigColumnMeta(7, "audioId", "INT"),
        new ConfigColumnMeta(8, "isHaveTransition", "INT"));
  }
}
