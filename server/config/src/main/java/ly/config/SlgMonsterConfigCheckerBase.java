package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SlgMonsterConfigCheckerBase extends AbstractConfigChecker<SlgMonsterConfig> {
  @Override
  public String getConfigFileName() {
    return "slgMonster.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "hp", "long"),
        new ConfigColumnMeta(2, "attack", "long"),
        new ConfigColumnMeta(3, "defence", "long"),
        new ConfigColumnMeta(4, "activeSkill", "int"));
  }
}
