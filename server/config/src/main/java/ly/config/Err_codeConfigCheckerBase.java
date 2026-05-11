package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class Err_codeConfigCheckerBase extends AbstractConfigChecker<Err_codeConfig> {
  @Override
  public String getConfigFileName() {
    return "err_code.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "define", "STRING"),
        new ConfigColumnMeta(2, "des", "STRING"),
        new ConfigColumnMeta(3, "handletype", "INT"));
  }
}
