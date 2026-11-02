package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class HitWordConfigCheckerBase extends AbstractConfigChecker<HitWordConfig> {
  @Override
  public String getConfigFileName() {
    return "hitWord.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "initialOffset", "string"),
        new ConfigColumnMeta(3, "initialScale", "string"),
        new ConfigColumnMeta(4, "initialAlpha", "string"),
        new ConfigColumnMeta(5, "appearScale", "string"),
        new ConfigColumnMeta(6, "appearAlpha", "string"),
        new ConfigColumnMeta(7, "toAppearTime", "string"),
        new ConfigColumnMeta(8, "appearLastTime", "string"),
        new ConfigColumnMeta(9, "disappearScale", "string"),
        new ConfigColumnMeta(10, "disappearAlpha", "string"),
        new ConfigColumnMeta(11, "disappearTime", "string"),
        new ConfigColumnMeta(12, "nextPosOffset", "string"));
  }
}
