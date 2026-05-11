package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class AdvancedStageConfigCheckerBase extends AbstractConfigChecker<AdvancedStageConfig> {
  @Override
  public String getConfigFileName() {
    return "advancedStage.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "stageType", "INT"),
        new ConfigColumnMeta(2, "floor", "INT"),
        new ConfigColumnMeta(3, "cost", "INT"),
        new ConfigColumnMeta(4, "advance", "INT"),
        new ConfigColumnMeta(5, "sceneId", "INT"),
        new ConfigColumnMeta(6, "dropSelection", "INT"),
        new ConfigColumnMeta(7, "dropList", "STRING"),
        new ConfigColumnMeta(8, "upIcon", "STRING"),
        new ConfigColumnMeta(9, "dropGroup", "STRING"),
        new ConfigColumnMeta(10, "name", "STRING"),
        new ConfigColumnMeta(11, "lockTips", "STRING"),
        new ConfigColumnMeta(12, "preStage", "INT"),
        new ConfigColumnMeta(13, "nextStage", "INT"),
        new ConfigColumnMeta(14, "needLv", "INT"),
        new ConfigColumnMeta(15, "bossHead", "INT"),
        new ConfigColumnMeta(16, "model", "STRING"),
        new ConfigColumnMeta(17, "action", "STRING"),
        new ConfigColumnMeta(18, "word", "STRING"),
        new ConfigColumnMeta(19, "decorate1", "STRING"),
        new ConfigColumnMeta(20, "decorate2", "STRING"),
        new ConfigColumnMeta(21, "decorate3", "STRING"),
        new ConfigColumnMeta(22, "bossId", "STRING"),
        new ConfigColumnMeta(23, "scaling", "STRING"),
        new ConfigColumnMeta(24, "offset", "STRING"),
        new ConfigColumnMeta(25, "offsetX", "STRING"),
        new ConfigColumnMeta(26, "offsetAngle", "STRING"),
        new ConfigColumnMeta(27, "showType", "STRING"),
        new ConfigColumnMeta(28, "selectionDis", "STRING"),
        new ConfigColumnMeta(29, "trigger", "STRING"),
        new ConfigColumnMeta(30, "spineModelResId", "INT"),
        new ConfigColumnMeta(31, "spineScale", "INT"),
        new ConfigColumnMeta(32, "spinePosOffset", "STRING"),
        new ConfigColumnMeta(33, "spineAnimation", "STRING"),
        new ConfigColumnMeta(34, "dropExpect", "STRING"),
        new ConfigColumnMeta(35, "recommendLv", "INT"),
        new ConfigColumnMeta(36, "recommendtype", "INT"),
        new ConfigColumnMeta(37, "recommendhero", "STRING"),
        new ConfigColumnMeta(38, "battleTipText", "STRING"),
        new ConfigColumnMeta(39, "selectionType", "INT"),
        new ConfigColumnMeta(40, "rankType", "INT"),
        new ConfigColumnMeta(41, "activityControlId", "INT"),
        new ConfigColumnMeta(42, "webDes", "STRING"),
        new ConfigColumnMeta(43, "dropExpectAdd", "STRING"),
        new ConfigColumnMeta(44, "recommendHero1", "INT"),
        new ConfigColumnMeta(45, "recommendHero2", "INT"),
        new ConfigColumnMeta(46, "iconType", "INT"),
        new ConfigColumnMeta(47, "noticeIcon", "INT"),
        new ConfigColumnMeta(48, "firstDropExpect", "INT"),
        new ConfigColumnMeta(49, "decorate2Background", "INT"));
  }
}
