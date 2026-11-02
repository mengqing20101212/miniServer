package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class GuideMainConfigCheckerBase extends AbstractConfigChecker<GuideMainConfig> {
  @Override
  public String getConfigFileName() {
    return "guideMain.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "beizhu", "string"),
        new ConfigColumnMeta(2, "checkNext", "int"),
        new ConfigColumnMeta(3, "guideCond", "list<int>"),
        new ConfigColumnMeta(4, "guidePara1", "string"),
        new ConfigColumnMeta(5, "isMask", "int"),
        new ConfigColumnMeta(6, "stepList", "list<int>"),
        new ConfigColumnMeta(7, "keyStep", "int"),
        new ConfigColumnMeta(8, "vanishType", "int"),
        new ConfigColumnMeta(9, "vanishPara", "list<int>"),
        new ConfigColumnMeta(10, "canSkip", "int"),
        new ConfigColumnMeta(11, "weight", "int"),
        new ConfigColumnMeta(12, "isLocal", "int"),
        new ConfigColumnMeta(13, "autoTrigger", "int"));
  }
}
