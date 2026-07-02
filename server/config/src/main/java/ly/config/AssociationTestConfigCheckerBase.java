package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class AssociationTestConfigCheckerBase extends AbstractConfigChecker<AssociationTestConfig> {
  @Override
  public String getConfigFileName() {
    return "associationTest.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "week", "INT"),
        new ConfigColumnMeta(2, "day", "INT"),
        new ConfigColumnMeta(3, "heroID", "STRING"),
        new ConfigColumnMeta(4, "beizhu", "STRING"),
        new ConfigColumnMeta(5, "dropSuccess", "STRING"),
        new ConfigColumnMeta(6, "dropFail", "STRING"),
        new ConfigColumnMeta(7, "dropShow", "STRING"),
        new ConfigColumnMeta(8, "testTimes", "INT"),
        new ConfigColumnMeta(9, "stamina", "INT"),
        new ConfigColumnMeta(10, "questionPool", "STRING"));
  }
}
