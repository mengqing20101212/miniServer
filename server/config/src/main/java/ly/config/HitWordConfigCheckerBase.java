package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class HitWordConfigCheckerBase extends AbstractConfigChecker<HitWordConfig> {
  @Override
  public String getConfigFileName() {
    return "hitWord.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "initialOffset", "STRING"),
        new ConfigColumnMeta(3, "initialScale", "STRING"),
        new ConfigColumnMeta(4, "initialAlpha", "STRING"),
        new ConfigColumnMeta(5, "appearScale", "STRING"),
        new ConfigColumnMeta(6, "appearAlpha", "STRING"),
        new ConfigColumnMeta(7, "toAppearTime", "STRING"),
        new ConfigColumnMeta(8, "appearLastTime", "STRING"),
        new ConfigColumnMeta(9, "disappearScale", "STRING"),
        new ConfigColumnMeta(10, "disappearAlpha", "STRING"),
        new ConfigColumnMeta(11, "disappearTime", "STRING"),
        new ConfigColumnMeta(12, "nextPosOffset", "STRING"));
  }
}
