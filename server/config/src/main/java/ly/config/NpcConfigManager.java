package ly.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import ly.AbstractConfigManger;
import ly.ConfigLoadException;
import ly.InterfaceConfigManagerProxy;
import ly.utils.KV;
import org.slf4j.Logger;

/*
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 * File: NpcConfigManager
 */
public class NpcConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final NpcConfigManager instance = new NpcConfigManager();
  private static final NpcConfigManagerImpl instanceImplA = new NpcConfigManagerImpl();
  private static final NpcConfigManagerImpl instanceImplB = new NpcConfigManagerImpl();

  public static NpcConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static NpcConfigManagerImpl getStandby() {
    return switched.get() ? instanceImplB : instanceImplA;
  }

  @Override
  public void loadConfig(Logger logger, String configDir) throws ConfigLoadException {
    getInstance().reload(logger, configDir);
  }

  @Override
  public void loadStandbyConfig(Logger logger, String configDir) throws ConfigLoadException {
    getStandby().reload(logger, configDir);
  }

  @Override
  public AbstractConfigManger switchConfig() {
    NpcConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class NpcConfigManagerImpl extends AbstractConfigManger {
    private List<NpcConfig> configList = List.of();
    private Map<Integer, NpcConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      String fileName = configDir + File.separator + getConfigFileName();
      File file = new File(fileName);
      if (!file.exists()) {
        logger.error(fileName + " does not exist");
        throw new ConfigLoadException("Config file does not exist :" + fileName);
      }
      NpcConfigChecker checker = new NpcConfigChecker();
      checker.checkHeader(logger, configDir);
      List<NpcConfig> newList = new ArrayList<>();
      Map<Integer, NpcConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 41) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          String beizhu = null;
          String name = null;
          int level = 0;
          int star = 0;
          int maxHP = 0;
          int attack = 0;
          int defence = 0;
          int speed = 0;
          int crit = 0;
          int critRatio = 0;
          int effectHit = 0;
          int effectDodge = 0;
          int spCoe = 0;
          int skill_1 = 0;
          int skill_2 = 0;
          int skill_3 = 0;
          int skill_s1 = 0;
          int skill_s2 = 0;
          String aiName = null;
          int modelId = 0;
          int headResource_3 = 0;
          int headResource_4 = 0;
          String sSkillCutUp = null;
          int isBoss = 0;
          String canRun = null;
          int relateId = 0;
          String extraSkillInfo = null;
          int forcedAICD = 0;
          String skills = null;
          String sSkills = null;
          int npcType = 0;
          int entityTags = 0;
          String sSkipCutUp = null;
          String changeColorInfo = null;
          int colorType = 0;
          String ShaderFresnel = null;
          int heroType = 0;
          int quality = 0;
          int characterType = 0;
          int awakenLv = 0;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 名字
            if (!arr[1].trim().isEmpty()) {
              beizhu = arr[1].trim();
            }

            // 解析 名字
            if (!arr[2].trim().isEmpty()) {
              name = arr[2].trim();
            }

            // 解析 等级
            if (!arr[3].trim().isEmpty()) {
              level = Integer.parseInt(arr[3].trim());
            }

            // 解析 星级
            if (!arr[4].trim().isEmpty()) {
              star = Integer.parseInt(arr[4].trim());
            }

            // 解析 生命上限
            if (!arr[5].trim().isEmpty()) {
              maxHP = Integer.parseInt(arr[5].trim());
            }

            // 解析 攻击
            if (!arr[6].trim().isEmpty()) {
              attack = Integer.parseInt(arr[6].trim());
            }

            // 解析 防御
            if (!arr[7].trim().isEmpty()) {
              defence = Integer.parseInt(arr[7].trim());
            }

            // 解析 速度
            if (!arr[8].trim().isEmpty()) {
              speed = Integer.parseInt(arr[8].trim());
            }

            // 解析 暴击
            if (!arr[9].trim().isEmpty()) {
              crit = Integer.parseInt(arr[9].trim());
            }

            // 解析 暴击伤害
            if (!arr[10].trim().isEmpty()) {
              critRatio = Integer.parseInt(arr[10].trim());
            }

            // 解析 效果命中
            if (!arr[11].trim().isEmpty()) {
              effectHit = Integer.parseInt(arr[11].trim());
            }

            // 解析 效果抵抗
            if (!arr[12].trim().isEmpty()) {
              effectDodge = Integer.parseInt(arr[12].trim());
            }

            // 解析 回能
            if (!arr[13].trim().isEmpty()) {
              spCoe = Integer.parseInt(arr[13].trim());
            }

            // 解析 常规技能
            if (!arr[14].trim().isEmpty()) {
              skill_1 = Integer.parseInt(arr[14].trim());
            }

            // 解析   null
            if (!arr[15].trim().isEmpty()) {
              skill_2 = Integer.parseInt(arr[15].trim());
            }

            // 解析   null
            if (!arr[16].trim().isEmpty()) {
              skill_3 = Integer.parseInt(arr[16].trim());
            }

            // 解析 超级技能
            if (!arr[17].trim().isEmpty()) {
              skill_s1 = Integer.parseInt(arr[17].trim());
            }

            // 解析 超级技能
            if (!arr[18].trim().isEmpty()) {
              skill_s2 = Integer.parseInt(arr[18].trim());
            }

            // 解析 AI模板
            if (!arr[19].trim().isEmpty()) {
              aiName = arr[19].trim();
            }

            // 解析 模型id
            if (!arr[20].trim().isEmpty()) {
              modelId = Integer.parseInt(arr[20].trim());
            }

            // 解析 右侧头像
            if (!arr[21].trim().isEmpty()) {
              headResource_3 = Integer.parseInt(arr[21].trim());
            }

            // 解析 立绘头像
            if (!arr[22].trim().isEmpty()) {
              headResource_4 = Integer.parseInt(arr[22].trim());
            }

            // 解析 s技能立绘切割坐标
            if (!arr[23].trim().isEmpty()) {
              sSkillCutUp = arr[23].trim();
            }

            // 解析 是否boss
            if (!arr[24].trim().isEmpty()) {
              isBoss = Integer.parseInt(arr[24].trim());
            }

            // 解析 能否逃跑
            if (!arr[25].trim().isEmpty()) {
              canRun = arr[25].trim();
            }

            // 解析 关联hero
            if (!arr[26].trim().isEmpty()) {
              relateId = Integer.parseInt(arr[26].trim());
            }

            // 解析 AI技能CD
            if (!arr[27].trim().isEmpty()) {
              extraSkillInfo = arr[27].trim();
            }

            // 解析 是否强制使用AI技能CD
            if (!arr[28].trim().isEmpty()) {
              forcedAICD = Integer.parseInt(arr[28].trim());
            }

            // 解析 技能列表
            if (!arr[29].trim().isEmpty()) {
              skills = arr[29].trim();
            }

            // 解析 S技能列表
            if (!arr[30].trim().isEmpty()) {
              sSkills = arr[30].trim();
            }

            // 解析 NPC类别
            if (!arr[31].trim().isEmpty()) {
              npcType = Integer.parseInt(arr[31].trim());
            }

            // 解析 NPC标记
            if (!arr[32].trim().isEmpty()) {
              entityTags = Integer.parseInt(arr[32].trim());
            }

            // 解析 s技能跳过立绘切割坐标
            if (!arr[33].trim().isEmpty()) {
              sSkipCutUp = arr[33].trim();
            }

            // 解析 变色类别
            if (!arr[34].trim().isEmpty()) {
              changeColorInfo = arr[34].trim();
            }

            // 解析 颜色类型
            if (!arr[35].trim().isEmpty()) {
              colorType = Integer.parseInt(arr[35].trim());
            }

            // 解析 菲尼尔颜色
            if (!arr[36].trim().isEmpty()) {
              ShaderFresnel = arr[36].trim();
            }

            // 解析 英雄类型
            if (!arr[37].trim().isEmpty()) {
              heroType = Integer.parseInt(arr[37].trim());
            }

            // 解析 品质
            if (!arr[38].trim().isEmpty()) {
              quality = Integer.parseInt(arr[38].trim());
            }

            // 解析 角色类别
            if (!arr[39].trim().isEmpty()) {
              characterType = Integer.parseInt(arr[39].trim());
            }

            // 解析 觉醒等级
            if (!arr[40].trim().isEmpty()) {
              awakenLv = Integer.parseInt(arr[40].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          NpcConfig config = new NpcConfig(id, beizhu, name, level, star, maxHP, attack, defence, speed, crit, critRatio, effectHit, effectDodge, spCoe, skill_1, skill_2, skill_3, skill_s1, skill_s2, aiName, modelId, headResource_3, headResource_4, sSkillCutUp, isBoss, canRun, relateId, extraSkillInfo, forcedAICD, skills, sSkills, npcType, entityTags, sSkipCutUp, changeColorInfo, colorType, ShaderFresnel, heroType, quality, characterType, awakenLv);
          config.afterLoad();
          newList.add(config);
          newMap.put(config.id, config);
        }
        checker.checkAfterParse(logger, newList);
        configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
        afterLoad();
      } catch (IOException e) {
        throw new ConfigLoadException("Config file could not be read :" + fileName);
      }
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    private List<Integer> parseIntList(String value) {
      if (value == null || value.trim().isEmpty()) { return new ArrayList<>(); }
      String[] parts = value.split(",");
      List<Integer> result = new ArrayList<>();
      for (String part : parts) {
        if (!part.trim().isEmpty()) { result.add(Integer.parseInt(part.trim())); }
      }
      return result;
    }

    private List<KV<Integer, Integer>> parseIntKVList(String value) {
      if (value == null || value.trim().isEmpty()) { return new ArrayList<>(); }
      List<KV<Integer, Integer>> result = new ArrayList<>();
      for (String pair : value.split(",")) {
        int idx = pair.indexOf(":");
        if (idx > 0) {
          result.add(new KV<>(Integer.parseInt(pair.substring(0, idx).trim()), Integer.parseInt(pair.substring(idx + 1).trim())));
        }
      }
      return result;
    }

    private List<KV<String, String>> parseStringKVList(String value) {
      if (value == null || value.trim().isEmpty()) { return new ArrayList<>(); }
      List<KV<String, String>> result = new ArrayList<>();
      for (String pair : value.split(",")) {
        int idx = pair.indexOf(":");
        if (idx > 0) { result.add(new KV<>(pair.substring(0, idx).trim(), pair.substring(idx + 1).trim())); }
      }
      return result;
    }

    public List<NpcConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, NpcConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "npc.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
