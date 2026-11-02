package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class TestNpcConfigCheckerBase extends AbstractConfigChecker<TestNpcConfig> {
  @Override
  public String getConfigFileName() {
    return "testNpc.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "level", "int"),
        new ConfigColumnMeta(3, "maxHP", "int"),
        new ConfigColumnMeta(4, "attack", "int"),
        new ConfigColumnMeta(5, "defence", "int"),
        new ConfigColumnMeta(6, "speed", "int"),
        new ConfigColumnMeta(7, "crit", "int"),
        new ConfigColumnMeta(8, "critRatio", "int"),
        new ConfigColumnMeta(9, "effectHit", "int"),
        new ConfigColumnMeta(10, "effectDodge", "int"),
        new ConfigColumnMeta(11, "skillList", "string"),
        new ConfigColumnMeta(12, "Sskill", "string"),
        new ConfigColumnMeta(13, "aiName", "string"),
        new ConfigColumnMeta(14, "modelId", "int"));
  }
}
