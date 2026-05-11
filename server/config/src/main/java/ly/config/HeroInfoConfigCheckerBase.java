package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class HeroInfoConfigCheckerBase extends AbstractConfigChecker<HeroInfoConfig> {
  @Override
  public String getConfigFileName() {
    return "heroInfo.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "heroType", "INT"),
        new ConfigColumnMeta(3, "heroSex", "INT"),
        new ConfigColumnMeta(4, "modelResource", "INT"),
        new ConfigColumnMeta(5, "modelResourceScale", "STRING"),
        new ConfigColumnMeta(6, "battleResource", "INT"),
        new ConfigColumnMeta(7, "showResource", "INT"),
        new ConfigColumnMeta(8, "showResourceScale", "STRING"),
        new ConfigColumnMeta(9, "headResource", "INT"),
        new ConfigColumnMeta(10, "headResource_2", "INT"),
        new ConfigColumnMeta(11, "headResource_3", "INT"),
        new ConfigColumnMeta(12, "headResource_4", "INT"),
        new ConfigColumnMeta(13, "heropinyin", "STRING"),
        new ConfigColumnMeta(14, "heroPerformance", "STRING"),
        new ConfigColumnMeta(15, "heroCutUp", "STRING"),
        new ConfigColumnMeta(16, "sSkillCutUp", "STRING"),
        new ConfigColumnMeta(17, "sSkipCutUp", "STRING"),
        new ConfigColumnMeta(18, "isSP", "INT"),
        new ConfigColumnMeta(19, "quality", "INT"),
        new ConfigColumnMeta(20, "skill_1", "INT"),
        new ConfigColumnMeta(21, "skill_2", "INT"),
        new ConfigColumnMeta(22, "skill_3", "INT"),
        new ConfigColumnMeta(23, "skill_s1", "INT"),
        new ConfigColumnMeta(24, "skill_s2", "INT"),
        new ConfigColumnMeta(25, "moreAwakenSkill1", "INT"),
        new ConfigColumnMeta(26, "moreAwakenSkill2", "INT"),
        new ConfigColumnMeta(27, "moreAwakenSkill3", "INT"),
        new ConfigColumnMeta(28, "endPerformance", "STRING"),
        new ConfigColumnMeta(29, "heroDebris", "INT"),
        new ConfigColumnMeta(30, "herogachadoc", "INT"),
        new ConfigColumnMeta(31, "heroStateData", "INT"),
        new ConfigColumnMeta(32, "heroAwakenData", "INT"),
        new ConfigColumnMeta(33, "expModelId", "INT"),
        new ConfigColumnMeta(34, "starModelId", "INT"),
        new ConfigColumnMeta(35, "circuitId", "INT"),
        new ConfigColumnMeta(36, "perloadId", "INT"),
        new ConfigColumnMeta(37, "heroListPreloadId", "INT"),
        new ConfigColumnMeta(38, "pvpCamera", "STRING"),
        new ConfigColumnMeta(39, "teamCamera", "STRING"),
        new ConfigColumnMeta(40, "aiName", "STRING"),
        new ConfigColumnMeta(41, "attrRankId", "INT"),
        new ConfigColumnMeta(42, "getPpos", "INT"),
        new ConfigColumnMeta(43, "speicalBg", "INT"),
        new ConfigColumnMeta(44, "unlockBg", "INT"),
        new ConfigColumnMeta(45, "qualityIcon", "INT"),
        new ConfigColumnMeta(46, "activityNpcId", "INT"),
        new ConfigColumnMeta(47, "background", "INT"),
        new ConfigColumnMeta(48, "cardBust", "INT"),
        new ConfigColumnMeta(49, "supportTeamAttr", "STRING"),
        new ConfigColumnMeta(50, "supportItem", "INT"),
        new ConfigColumnMeta(51, "exchangeItem", "INT"),
        new ConfigColumnMeta(52, "backgroundId", "INT"),
        new ConfigColumnMeta(53, "timeType", "INT"),
        new ConfigColumnMeta(54, "startTime", "STRING"),
        new ConfigColumnMeta(55, "specialEndTime", "STRING"),
        new ConfigColumnMeta(56, "heroshare", "INT"),
        new ConfigColumnMeta(57, "herogroup", "INT"),
        new ConfigColumnMeta(58, "OpenServiceActivity", "INT"),
        new ConfigColumnMeta(59, "characterType", "INT"));
  }
}
