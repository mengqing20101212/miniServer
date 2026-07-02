package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class NpcConfigCheckerBase extends AbstractConfigChecker<NpcConfig> {
  @Override
  public String getConfigFileName() {
    return "npc.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "beizhu", "STRING"),
        new ConfigColumnMeta(2, "name", "STRING"),
        new ConfigColumnMeta(3, "level", "INT"),
        new ConfigColumnMeta(4, "star", "INT"),
        new ConfigColumnMeta(5, "maxHP", "INT"),
        new ConfigColumnMeta(6, "attack", "INT"),
        new ConfigColumnMeta(7, "defence", "INT"),
        new ConfigColumnMeta(8, "speed", "INT"),
        new ConfigColumnMeta(9, "crit", "INT"),
        new ConfigColumnMeta(10, "critRatio", "INT"),
        new ConfigColumnMeta(11, "effectHit", "INT"),
        new ConfigColumnMeta(12, "effectDodge", "INT"),
        new ConfigColumnMeta(13, "spCoe", "INT"),
        new ConfigColumnMeta(14, "skill_1", "INT"),
        new ConfigColumnMeta(15, "skill_2", "INT"),
        new ConfigColumnMeta(16, "skill_3", "INT"),
        new ConfigColumnMeta(17, "skill_s1", "INT"),
        new ConfigColumnMeta(18, "skill_s2", "INT"),
        new ConfigColumnMeta(19, "aiName", "STRING"),
        new ConfigColumnMeta(20, "modelId", "INT"),
        new ConfigColumnMeta(21, "headResource_3", "INT"),
        new ConfigColumnMeta(22, "headResource_4", "INT"),
        new ConfigColumnMeta(23, "sSkillCutUp", "STRING"),
        new ConfigColumnMeta(24, "isBoss", "INT"),
        new ConfigColumnMeta(25, "canRun", "BOOL"),
        new ConfigColumnMeta(26, "relateId", "INT"),
        new ConfigColumnMeta(27, "extraSkillInfo", "STRING"),
        new ConfigColumnMeta(28, "forcedAICD", "INT"),
        new ConfigColumnMeta(29, "skills", "STRING"),
        new ConfigColumnMeta(30, "sSkills", "STRING"),
        new ConfigColumnMeta(31, "npcType", "INT"),
        new ConfigColumnMeta(32, "entityTags", "INT"),
        new ConfigColumnMeta(33, "sSkipCutUp", "STRING"),
        new ConfigColumnMeta(34, "changeColorInfo", "STRING"),
        new ConfigColumnMeta(35, "colorType", "INT"),
        new ConfigColumnMeta(36, "ShaderFresnel", "STRING"),
        new ConfigColumnMeta(37, "heroType", "INT"),
        new ConfigColumnMeta(38, "quality", "INT"),
        new ConfigColumnMeta(39, "characterType", "INT"),
        new ConfigColumnMeta(40, "awakenLv", "INT"));
  }
}
