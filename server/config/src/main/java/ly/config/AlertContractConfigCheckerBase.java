package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class AlertContractConfigCheckerBase extends AbstractConfigChecker<AlertContractConfig> {
  @Override
  public String getConfigFileName() {
    return "AlertContract.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "level", "INT"),
        new ConfigColumnMeta(2, "enemyInfo", "STRING"),
        new ConfigColumnMeta(3, "enemyCircuitInfo", "STRING"),
        new ConfigColumnMeta(4, "enemyPara", "STRING"),
        new ConfigColumnMeta(5, "sceneId", "STRING"),
        new ConfigColumnMeta(6, "dropId", "INT"),
        new ConfigColumnMeta(7, "firstDrop", "INT"),
        new ConfigColumnMeta(8, "dropShow", "STRING"),
        new ConfigColumnMeta(9, "hint", "STRING"),
        new ConfigColumnMeta(10, "tipPic", "INT"),
        new ConfigColumnMeta(11, "background", "INT"),
        new ConfigColumnMeta(12, "backgroundIn", "INT"),
        new ConfigColumnMeta(13, "heroPicId", "INT"),
        new ConfigColumnMeta(14, "recommendHeroIds", "STRING"),
        new ConfigColumnMeta(15, "recommendTypes", "STRING"),
        new ConfigColumnMeta(16, "avgLineupLevel", "INT"),
        new ConfigColumnMeta(17, "dropDay", "INT"),
        new ConfigColumnMeta(18, "dropDayShow", "INT"),
        new ConfigColumnMeta(19, "targetLevel", "INT"));
  }
}
