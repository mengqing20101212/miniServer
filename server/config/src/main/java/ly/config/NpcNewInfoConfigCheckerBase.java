package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class NpcNewInfoConfigCheckerBase extends AbstractConfigChecker<NpcNewInfoConfig> {
  @Override
  public String getConfigFileName() {
    return "npcNewInfo.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "beizhu", "STRING"),
        new ConfigColumnMeta(2, "circuitName", "STRING"),
        new ConfigColumnMeta(3, "name", "STRING"),
        new ConfigColumnMeta(4, "genType", "INT"),
        new ConfigColumnMeta(5, "checkId", "INT"),
        new ConfigColumnMeta(6, "level", "INT"),
        new ConfigColumnMeta(7, "star", "INT"),
        new ConfigColumnMeta(8, "advance", "INT"),
        new ConfigColumnMeta(9, "awakenLv", "INT"),
        new ConfigColumnMeta(10, "skillLv", "STRING"),
        new ConfigColumnMeta(11, "sSkillLv", "STRING"),
        new ConfigColumnMeta(12, "circuitLv", "INT"),
        new ConfigColumnMeta(13, "circuitQuality", "INT"),
        new ConfigColumnMeta(14, "circuitInfo", "INT"),
        new ConfigColumnMeta(15, "maxHPCoe", "INT"),
        new ConfigColumnMeta(16, "attackCoe", "INT"),
        new ConfigColumnMeta(17, "defenceCoe", "INT"),
        new ConfigColumnMeta(18, "speedCoe", "INT"),
        new ConfigColumnMeta(19, "critCoe", "INT"),
        new ConfigColumnMeta(20, "critRatioCoe", "INT"),
        new ConfigColumnMeta(21, "effectHitCoe", "INT"),
        new ConfigColumnMeta(22, "effectDodgeCoe", "INT"),
        new ConfigColumnMeta(23, "skills", "STRING"),
        new ConfigColumnMeta(24, "sSkills", "STRING"),
        new ConfigColumnMeta(25, "aiName", "STRING"),
        new ConfigColumnMeta(26, "isBoss", "INT"),
        new ConfigColumnMeta(27, "canRun", "BOOL"),
        new ConfigColumnMeta(28, "extraSkillInfo", "STRING"),
        new ConfigColumnMeta(29, "npcType", "INT"),
        new ConfigColumnMeta(30, "entityTags", "INT"));
  }
}
