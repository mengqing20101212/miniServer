package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class TestNpcConfigCheckerBase extends AbstractConfigChecker<TestNpcConfig> {
  @Override
  public String getConfigFileName() {
    return "testNpc.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "level", "INT"),
        new ConfigColumnMeta(3, "maxHP", "INT"),
        new ConfigColumnMeta(4, "attack", "INT"),
        new ConfigColumnMeta(5, "defence", "INT"),
        new ConfigColumnMeta(6, "speed", "INT"),
        new ConfigColumnMeta(7, "crit", "INT"),
        new ConfigColumnMeta(8, "critRatio", "INT"),
        new ConfigColumnMeta(9, "effectHit", "INT"),
        new ConfigColumnMeta(10, "effectDodge", "INT"),
        new ConfigColumnMeta(11, "skillList", "STRING"),
        new ConfigColumnMeta(12, "Sskill", "STRING"),
        new ConfigColumnMeta(13, "aiName", "STRING"),
        new ConfigColumnMeta(14, "modelId", "INT"));
  }
}
