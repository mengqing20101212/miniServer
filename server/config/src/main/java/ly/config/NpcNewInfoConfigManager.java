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
 * File: NpcNewInfoConfigManager
 */
public class NpcNewInfoConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final NpcNewInfoConfigManager instance = new NpcNewInfoConfigManager();
  private static final NpcNewInfoConfigManagerImpl instanceImplA = new NpcNewInfoConfigManagerImpl();
  private static final NpcNewInfoConfigManagerImpl instanceImplB = new NpcNewInfoConfigManagerImpl();

  public static NpcNewInfoConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static NpcNewInfoConfigManagerImpl getStandby() {
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
    NpcNewInfoConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class NpcNewInfoConfigManagerImpl extends AbstractConfigManger {
    private List<NpcNewInfoConfig> configList = List.of();
    private Map<Integer, NpcNewInfoConfig> configMap = Map.of();

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
      NpcNewInfoConfigChecker checker = new NpcNewInfoConfigChecker();
      checker.checkHeader(logger, configDir);
      List<NpcNewInfoConfig> newList = new ArrayList<>();
      Map<Integer, NpcNewInfoConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 31) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          String beizhu = null;
          String circuitName = null;
          String name = null;
          int genType = 0;
          int checkId = 0;
          int level = 0;
          int star = 0;
          int advance = 0;
          int awakenLv = 0;
          String skillLv = null;
          String sSkillLv = null;
          int circuitLv = 0;
          int circuitQuality = 0;
          int circuitInfo = 0;
          int maxHPCoe = 0;
          int attackCoe = 0;
          int defenceCoe = 0;
          int speedCoe = 0;
          int critCoe = 0;
          int critRatioCoe = 0;
          int effectHitCoe = 0;
          int effectDodgeCoe = 0;
          String skills = null;
          String sSkills = null;
          String aiName = null;
          int isBoss = 0;
          String canRun = null;
          String extraSkillInfo = null;
          int npcType = 0;
          int entityTags = 0;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 名字
            if (!arr[1].trim().isEmpty()) {
              beizhu = arr[1].trim();
            }

            // 解析 源核类型
            if (!arr[2].trim().isEmpty()) {
              circuitName = arr[2].trim();
            }

            // 解析 名字
            if (!arr[3].trim().isEmpty()) {
              name = arr[3].trim();
            }

            // 解析 类型
            if (!arr[4].trim().isEmpty()) {
              genType = Integer.parseInt(arr[4].trim());
            }

            // 解析 索引id
            if (!arr[5].trim().isEmpty()) {
              checkId = Integer.parseInt(arr[5].trim());
            }

            // 解析 等级
            if (!arr[6].trim().isEmpty()) {
              level = Integer.parseInt(arr[6].trim());
            }

            // 解析 星级
            if (!arr[7].trim().isEmpty()) {
              star = Integer.parseInt(arr[7].trim());
            }

            // 解析 进阶等级
            if (!arr[8].trim().isEmpty()) {
              advance = Integer.parseInt(arr[8].trim());
            }

            // 解析 觉醒等级
            if (!arr[9].trim().isEmpty()) {
              awakenLv = Integer.parseInt(arr[9].trim());
            }

            // 解析 技能等级
            if (!arr[10].trim().isEmpty()) {
              skillLv = arr[10].trim();
            }

            // 解析 S技能等级
            if (!arr[11].trim().isEmpty()) {
              sSkillLv = arr[11].trim();
            }

            // 解析 源核等级
            if (!arr[12].trim().isEmpty()) {
              circuitLv = Integer.parseInt(arr[12].trim());
            }

            // 解析 源核品质
            if (!arr[13].trim().isEmpty()) {
              circuitQuality = Integer.parseInt(arr[13].trim());
            }

            // 解析 源核模板
            if (!arr[14].trim().isEmpty()) {
              circuitInfo = Integer.parseInt(arr[14].trim());
            }

            // 解析 生命上限系数
            if (!arr[15].trim().isEmpty()) {
              maxHPCoe = Integer.parseInt(arr[15].trim());
            }

            // 解析 攻击系数
            if (!arr[16].trim().isEmpty()) {
              attackCoe = Integer.parseInt(arr[16].trim());
            }

            // 解析 防御系数
            if (!arr[17].trim().isEmpty()) {
              defenceCoe = Integer.parseInt(arr[17].trim());
            }

            // 解析 速度系数
            if (!arr[18].trim().isEmpty()) {
              speedCoe = Integer.parseInt(arr[18].trim());
            }

            // 解析 暴击系数
            if (!arr[19].trim().isEmpty()) {
              critCoe = Integer.parseInt(arr[19].trim());
            }

            // 解析 暴击伤害系数
            if (!arr[20].trim().isEmpty()) {
              critRatioCoe = Integer.parseInt(arr[20].trim());
            }

            // 解析 效果命中系数
            if (!arr[21].trim().isEmpty()) {
              effectHitCoe = Integer.parseInt(arr[21].trim());
            }

            // 解析 效果抵抗系数
            if (!arr[22].trim().isEmpty()) {
              effectDodgeCoe = Integer.parseInt(arr[22].trim());
            }

            // 解析 技能列表
            if (!arr[23].trim().isEmpty()) {
              skills = arr[23].trim();
            }

            // 解析 S技能列表
            if (!arr[24].trim().isEmpty()) {
              sSkills = arr[24].trim();
            }

            // 解析 AI模板
            if (!arr[25].trim().isEmpty()) {
              aiName = arr[25].trim();
            }

            // 解析 是否boss
            if (!arr[26].trim().isEmpty()) {
              isBoss = Integer.parseInt(arr[26].trim());
            }

            // 解析 能否逃跑
            if (!arr[27].trim().isEmpty()) {
              canRun = arr[27].trim();
            }

            // 解析 AI技能CD
            if (!arr[28].trim().isEmpty()) {
              extraSkillInfo = arr[28].trim();
            }

            // 解析 NPC类别
            if (!arr[29].trim().isEmpty()) {
              npcType = Integer.parseInt(arr[29].trim());
            }

            // 解析 NPC标记
            if (!arr[30].trim().isEmpty()) {
              entityTags = Integer.parseInt(arr[30].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          NpcNewInfoConfig config = new NpcNewInfoConfig(id, beizhu, circuitName, name, genType, checkId, level, star, advance, awakenLv, skillLv, sSkillLv, circuitLv, circuitQuality, circuitInfo, maxHPCoe, attackCoe, defenceCoe, speedCoe, critCoe, critRatioCoe, effectHitCoe, effectDodgeCoe, skills, sSkills, aiName, isBoss, canRun, extraSkillInfo, npcType, entityTags);
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

    public List<NpcNewInfoConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, NpcNewInfoConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "npcNewInfo.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
