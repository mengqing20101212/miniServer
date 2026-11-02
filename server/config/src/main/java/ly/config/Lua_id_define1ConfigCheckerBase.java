package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class Lua_id_define1ConfigCheckerBase extends AbstractConfigChecker<Lua_id_define1Config> {
  @Override
  public String getConfigFileName() {
    return "lua_id_define1.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "string"),
        new ConfigColumnMeta(1, "desc", "string"),
        new ConfigColumnMeta(2, "resid", "int"));
  }
}
