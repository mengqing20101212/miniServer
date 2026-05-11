package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SceneGradeConfigCheckerBase extends AbstractConfigChecker<SceneGradeConfig> {
  @Override
  public String getConfigFileName() {
    return "sceneGrade.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "beizhu", "STRING"),
        new ConfigColumnMeta(2, "grade1", "INT"),
        new ConfigColumnMeta(3, "grade2", "INT"),
        new ConfigColumnMeta(4, "grade3", "INT"),
        new ConfigColumnMeta(5, "grade4", "INT"),
        new ConfigColumnMeta(6, "grade5", "INT"));
  }
}
