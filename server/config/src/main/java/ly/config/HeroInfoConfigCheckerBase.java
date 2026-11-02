package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class HeroInfoConfigCheckerBase extends AbstractConfigChecker<HeroInfoConfig> {
  @Override
  public String getConfigFileName() {
    return "heroInfo.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "heroType", "int"),
        new ConfigColumnMeta(3, "heroSex", "int"),
        new ConfigColumnMeta(4, "modelResource", "int"),
        new ConfigColumnMeta(5, "modelResourceScale", "string"),
        new ConfigColumnMeta(6, "battleResource", "int"),
        new ConfigColumnMeta(7, "showResource", "int"),
        new ConfigColumnMeta(8, "showResourceScale", "string"),
        new ConfigColumnMeta(9, "headResource", "int"),
        new ConfigColumnMeta(10, "headResource_2", "int"),
        new ConfigColumnMeta(11, "headResource_3", "int"),
        new ConfigColumnMeta(12, "headResource_4", "int"),
        new ConfigColumnMeta(13, "heropinyin", "string"),
        new ConfigColumnMeta(14, "heroPerformance", "string"),
        new ConfigColumnMeta(15, "heroCutUp", "string"),
        new ConfigColumnMeta(16, "sSkillCutUp", "string"),
        new ConfigColumnMeta(17, "sSkipCutUp", "string"),
        new ConfigColumnMeta(18, "isSP", "int"),
        new ConfigColumnMeta(19, "quality", "int"),
        new ConfigColumnMeta(20, "skill_1", "int"),
        new ConfigColumnMeta(21, "skill_2", "int"),
        new ConfigColumnMeta(22, "skill_3", "int"),
        new ConfigColumnMeta(23, "skill_s1", "int"),
        new ConfigColumnMeta(24, "skill_s2", "int"),
        new ConfigColumnMeta(25, "moreAwakenSkill1", "int"),
        new ConfigColumnMeta(26, "moreAwakenSkill2", "int"),
        new ConfigColumnMeta(27, "moreAwakenSkill3", "int"),
        new ConfigColumnMeta(28, "endPerformance", "string"),
        new ConfigColumnMeta(29, "heroDebris", "int"),
        new ConfigColumnMeta(30, "herogachadoc", "int"),
        new ConfigColumnMeta(31, "heroStateData", "int"),
        new ConfigColumnMeta(32, "heroAwakenData", "int"),
        new ConfigColumnMeta(33, "expModelId", "int"),
        new ConfigColumnMeta(34, "starModelId", "int"),
        new ConfigColumnMeta(35, "circuitId", "int"),
        new ConfigColumnMeta(36, "perloadId", "int"),
        new ConfigColumnMeta(37, "heroListPreloadId", "int"),
        new ConfigColumnMeta(38, "pvpCamera", "string"),
        new ConfigColumnMeta(39, "teamCamera", "string"),
        new ConfigColumnMeta(40, "aiName", "string"),
        new ConfigColumnMeta(41, "attrRankId", "int"),
        new ConfigColumnMeta(42, "getPpos", "int"),
        new ConfigColumnMeta(43, "speicalBg", "int"),
        new ConfigColumnMeta(44, "unlockBg", "int"),
        new ConfigColumnMeta(45, "qualityIcon", "int"),
        new ConfigColumnMeta(46, "activityNpcId", "int"),
        new ConfigColumnMeta(47, "background", "int"),
        new ConfigColumnMeta(48, "cardBust", "int"),
        new ConfigColumnMeta(49, "supportTeamAttr", "string"),
        new ConfigColumnMeta(50, "supportItem", "int"),
        new ConfigColumnMeta(51, "exchangeItem", "int"),
        new ConfigColumnMeta(52, "backgroundId", "int"),
        new ConfigColumnMeta(53, "timeType", "int"),
        new ConfigColumnMeta(54, "startTime", "string"),
        new ConfigColumnMeta(55, "specialEndTime", "string"),
        new ConfigColumnMeta(56, "heroshare", "int"),
        new ConfigColumnMeta(57, "herogroup", "int"),
        new ConfigColumnMeta(58, "OpenServiceActivity", "int"),
        new ConfigColumnMeta(59, "characterType", "int"));
  }
}
