package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class GuideMainConfigCheckerBase extends AbstractConfigChecker<GuideMainConfig> {
  @Override
  public String getConfigFileName() {
    return "guideMain.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "beizhu", "STRING"),
        new ConfigColumnMeta(2, "checkNext", "INT"),
        new ConfigColumnMeta(3, "guideCond", "LIST<INT>"),
        new ConfigColumnMeta(4, "guidePara1", "STRING"),
        new ConfigColumnMeta(5, "isMask", "INT"),
        new ConfigColumnMeta(6, "stepList", "LIST<INT>"),
        new ConfigColumnMeta(7, "keyStep", "INT"),
        new ConfigColumnMeta(8, "vanishType", "INT"),
        new ConfigColumnMeta(9, "vanishPara", "LIST<INT>"),
        new ConfigColumnMeta(10, "canSkip", "INT"),
        new ConfigColumnMeta(11, "weight", "INT"),
        new ConfigColumnMeta(12, "isLocal", "INT"),
        new ConfigColumnMeta(13, "autoTrigger", "INT"));
  }
}
