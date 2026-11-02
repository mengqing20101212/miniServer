package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class NpcConfigCheckerBase extends AbstractConfigChecker<NpcConfig> {
  @Override
  public String getConfigFileName() {
    return "npc.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "beizhu", "string"),
        new ConfigColumnMeta(2, "name", "string"),
        new ConfigColumnMeta(3, "level", "int"),
        new ConfigColumnMeta(4, "star", "int"),
        new ConfigColumnMeta(5, "maxHP", "int"),
        new ConfigColumnMeta(6, "attack", "int"),
        new ConfigColumnMeta(7, "defence", "int"),
        new ConfigColumnMeta(8, "speed", "int"),
        new ConfigColumnMeta(9, "crit", "int"),
        new ConfigColumnMeta(10, "critRatio", "int"),
        new ConfigColumnMeta(11, "effectHit", "int"),
        new ConfigColumnMeta(12, "effectDodge", "int"),
        new ConfigColumnMeta(13, "spCoe", "int"),
        new ConfigColumnMeta(14, "skill_1", "int"),
        new ConfigColumnMeta(15, "skill_2", "int"),
        new ConfigColumnMeta(16, "skill_3", "int"),
        new ConfigColumnMeta(17, "skill_s1", "int"),
        new ConfigColumnMeta(18, "skill_s2", "int"),
        new ConfigColumnMeta(19, "aiName", "string"),
        new ConfigColumnMeta(20, "modelId", "int"),
        new ConfigColumnMeta(21, "headResource_3", "int"),
        new ConfigColumnMeta(22, "headResource_4", "int"),
        new ConfigColumnMeta(23, "sSkillCutUp", "string"),
        new ConfigColumnMeta(24, "isBoss", "int"),
        new ConfigColumnMeta(25, "canRun", "bool"),
        new ConfigColumnMeta(26, "relateId", "int"),
        new ConfigColumnMeta(27, "extraSkillInfo", "string"),
        new ConfigColumnMeta(28, "forcedAICD", "int"),
        new ConfigColumnMeta(29, "skills", "string"),
        new ConfigColumnMeta(30, "sSkills", "string"),
        new ConfigColumnMeta(31, "npcType", "int"),
        new ConfigColumnMeta(32, "entityTags", "int"),
        new ConfigColumnMeta(33, "sSkipCutUp", "string"),
        new ConfigColumnMeta(34, "changeColorInfo", "string"),
        new ConfigColumnMeta(35, "colorType", "int"),
        new ConfigColumnMeta(36, "ShaderFresnel", "string"),
        new ConfigColumnMeta(37, "heroType", "int"),
        new ConfigColumnMeta(38, "quality", "int"),
        new ConfigColumnMeta(39, "characterType", "int"),
        new ConfigColumnMeta(40, "awakenLv", "int"));
  }
}
