package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class AssociationTestConfigCheckerBase extends AbstractConfigChecker<AssociationTestConfig> {
  @Override
  public String getConfigFileName() {
    return "associationTest.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "week", "int"),
        new ConfigColumnMeta(2, "day", "int"),
        new ConfigColumnMeta(3, "heroID", "string"),
        new ConfigColumnMeta(4, "beizhu", "string"),
        new ConfigColumnMeta(5, "dropSuccess", "string"),
        new ConfigColumnMeta(6, "dropFail", "string"),
        new ConfigColumnMeta(7, "dropShow", "string"),
        new ConfigColumnMeta(8, "testTimes", "int"),
        new ConfigColumnMeta(9, "stamina", "int"),
        new ConfigColumnMeta(10, "questionPool", "string"));
  }
}
