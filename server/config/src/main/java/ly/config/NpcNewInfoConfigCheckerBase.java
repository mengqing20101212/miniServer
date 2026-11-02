package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class NpcNewInfoConfigCheckerBase extends AbstractConfigChecker<NpcNewInfoConfig> {
  @Override
  public String getConfigFileName() {
    return "npcNewInfo.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "beizhu", "string"),
        new ConfigColumnMeta(2, "circuitName", "string"),
        new ConfigColumnMeta(3, "name", "string"),
        new ConfigColumnMeta(4, "genType", "int"),
        new ConfigColumnMeta(5, "checkId", "int"),
        new ConfigColumnMeta(6, "level", "int"),
        new ConfigColumnMeta(7, "star", "int"),
        new ConfigColumnMeta(8, "advance", "int"),
        new ConfigColumnMeta(9, "awakenLv", "int"),
        new ConfigColumnMeta(10, "skillLv", "string"),
        new ConfigColumnMeta(11, "sSkillLv", "string"),
        new ConfigColumnMeta(12, "circuitLv", "int"),
        new ConfigColumnMeta(13, "circuitQuality", "int"),
        new ConfigColumnMeta(14, "circuitInfo", "int"),
        new ConfigColumnMeta(15, "maxHPCoe", "int"),
        new ConfigColumnMeta(16, "attackCoe", "int"),
        new ConfigColumnMeta(17, "defenceCoe", "int"),
        new ConfigColumnMeta(18, "speedCoe", "int"),
        new ConfigColumnMeta(19, "critCoe", "int"),
        new ConfigColumnMeta(20, "critRatioCoe", "int"),
        new ConfigColumnMeta(21, "effectHitCoe", "int"),
        new ConfigColumnMeta(22, "effectDodgeCoe", "int"),
        new ConfigColumnMeta(23, "skills", "string"),
        new ConfigColumnMeta(24, "sSkills", "string"),
        new ConfigColumnMeta(25, "aiName", "string"),
        new ConfigColumnMeta(26, "isBoss", "int"),
        new ConfigColumnMeta(27, "canRun", "bool"),
        new ConfigColumnMeta(28, "extraSkillInfo", "string"),
        new ConfigColumnMeta(29, "npcType", "int"),
        new ConfigColumnMeta(30, "entityTags", "int"));
  }
}
