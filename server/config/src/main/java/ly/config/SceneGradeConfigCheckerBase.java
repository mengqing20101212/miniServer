package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SceneGradeConfigCheckerBase extends AbstractConfigChecker<SceneGradeConfig> {
  @Override
  public String getConfigFileName() {
    return "sceneGrade.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "beizhu", "string"),
        new ConfigColumnMeta(2, "grade1", "int"),
        new ConfigColumnMeta(3, "grade2", "int"),
        new ConfigColumnMeta(4, "grade3", "int"),
        new ConfigColumnMeta(5, "grade4", "int"),
        new ConfigColumnMeta(6, "grade5", "int"));
  }
}
