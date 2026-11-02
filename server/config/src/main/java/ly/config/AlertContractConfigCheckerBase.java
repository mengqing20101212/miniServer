package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class AlertContractConfigCheckerBase extends AbstractConfigChecker<AlertContractConfig> {
  @Override
  public String getConfigFileName() {
    return "AlertContract.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "level", "int"),
        new ConfigColumnMeta(2, "enemyInfo", "string"),
        new ConfigColumnMeta(3, "enemyCircuitInfo", "string"),
        new ConfigColumnMeta(4, "enemyPara", "string"),
        new ConfigColumnMeta(5, "sceneId", "string"),
        new ConfigColumnMeta(6, "dropId", "int"),
        new ConfigColumnMeta(7, "firstDrop", "int"),
        new ConfigColumnMeta(8, "dropShow", "string"),
        new ConfigColumnMeta(9, "hint", "string"),
        new ConfigColumnMeta(10, "tipPic", "int"),
        new ConfigColumnMeta(11, "background", "int"),
        new ConfigColumnMeta(12, "backgroundIn", "int"),
        new ConfigColumnMeta(13, "heroPicId", "int"),
        new ConfigColumnMeta(14, "recommendHeroIds", "string"),
        new ConfigColumnMeta(15, "recommendTypes", "string"),
        new ConfigColumnMeta(16, "avgLineupLevel", "int"),
        new ConfigColumnMeta(17, "dropDay", "int"),
        new ConfigColumnMeta(18, "dropDayShow", "int"),
        new ConfigColumnMeta(19, "targetLevel", "int"));
  }
}
