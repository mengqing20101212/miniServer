package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class AdvancedStageConfigCheckerBase extends AbstractConfigChecker<AdvancedStageConfig> {
  @Override
  public String getConfigFileName() {
    return "advancedStage.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "stageType", "int"),
        new ConfigColumnMeta(2, "floor", "int"),
        new ConfigColumnMeta(3, "cost", "int"),
        new ConfigColumnMeta(4, "advance", "int"),
        new ConfigColumnMeta(5, "sceneId", "int"),
        new ConfigColumnMeta(6, "dropSelection", "int"),
        new ConfigColumnMeta(7, "dropList", "string"),
        new ConfigColumnMeta(8, "upIcon", "string"),
        new ConfigColumnMeta(9, "dropGroup", "string"),
        new ConfigColumnMeta(10, "name", "string"),
        new ConfigColumnMeta(11, "lockTips", "string"),
        new ConfigColumnMeta(12, "preStage", "int"),
        new ConfigColumnMeta(13, "nextStage", "int"),
        new ConfigColumnMeta(14, "needLv", "int"),
        new ConfigColumnMeta(15, "bossHead", "int"),
        new ConfigColumnMeta(16, "model", "string"),
        new ConfigColumnMeta(17, "action", "string"),
        new ConfigColumnMeta(18, "word", "string"),
        new ConfigColumnMeta(19, "decorate1", "string"),
        new ConfigColumnMeta(20, "decorate2", "string"),
        new ConfigColumnMeta(21, "decorate3", "string"),
        new ConfigColumnMeta(22, "bossId", "string"),
        new ConfigColumnMeta(23, "scaling", "string"),
        new ConfigColumnMeta(24, "offset", "string"),
        new ConfigColumnMeta(25, "offsetX", "string"),
        new ConfigColumnMeta(26, "offsetAngle", "string"),
        new ConfigColumnMeta(27, "showType", "string"),
        new ConfigColumnMeta(28, "selectionDis", "string"),
        new ConfigColumnMeta(29, "trigger", "string"),
        new ConfigColumnMeta(30, "spineModelResId", "int"),
        new ConfigColumnMeta(31, "spineScale", "int"),
        new ConfigColumnMeta(32, "spinePosOffset", "string"),
        new ConfigColumnMeta(33, "spineAnimation", "string"),
        new ConfigColumnMeta(34, "dropExpect", "string"),
        new ConfigColumnMeta(35, "recommendLv", "int"),
        new ConfigColumnMeta(36, "recommendtype", "int"),
        new ConfigColumnMeta(37, "recommendhero", "string"),
        new ConfigColumnMeta(38, "battleTipText", "string"),
        new ConfigColumnMeta(39, "selectionType", "int"),
        new ConfigColumnMeta(40, "rankType", "int"),
        new ConfigColumnMeta(41, "activityControlId", "int"),
        new ConfigColumnMeta(42, "webDes", "string"),
        new ConfigColumnMeta(43, "dropExpectAdd", "string"),
        new ConfigColumnMeta(44, "recommendHero1", "int"),
        new ConfigColumnMeta(45, "recommendHero2", "int"),
        new ConfigColumnMeta(46, "iconType", "int"),
        new ConfigColumnMeta(47, "noticeIcon", "int"),
        new ConfigColumnMeta(48, "firstDropExpect", "int"),
        new ConfigColumnMeta(49, "decorate2Background", "int"));
  }
}
