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
 * File: ElectronicFairHeroConfigManager
 */
public class ElectronicFairHeroConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final ElectronicFairHeroConfigManager instance = new ElectronicFairHeroConfigManager();
  private static final ElectronicFairHeroConfigManagerImpl instanceImplA = new ElectronicFairHeroConfigManagerImpl();
  private static final ElectronicFairHeroConfigManagerImpl instanceImplB = new ElectronicFairHeroConfigManagerImpl();

  public static ElectronicFairHeroConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static ElectronicFairHeroConfigManagerImpl getStandby() {
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
    ElectronicFairHeroConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class ElectronicFairHeroConfigManagerImpl extends AbstractConfigManger {
    private List<ElectronicFairHeroConfig> configList = List.of();
    private Map<Integer, ElectronicFairHeroConfig> configMap = Map.of();

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
      ElectronicFairHeroConfigChecker checker = new ElectronicFairHeroConfigChecker();
      checker.checkHeader(logger, configDir);
      List<ElectronicFairHeroConfig> newList = new ArrayList<>();
      Map<Integer, ElectronicFairHeroConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 18) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          int heroId = 0;
          int Level = 0;
          int star = 0;
          int awaken = 0;
          int skill1 = 0;
          int skill2 = 0;
          int skill3 = 0;
          int skillS = 0;
          int maxHP = 0;
          int attack = 0;
          int defence = 0;
          int speed = 0;
          int crit = 0;
          int critRatio = 0;
          int effectHit = 0;
          int effectDodge = 0;
          int spCoe = 0;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 英雄id
            if (!arr[1].trim().isEmpty()) {
              heroId = Integer.parseInt(arr[1].trim());
            }

            // 解析 等级
            if (!arr[2].trim().isEmpty()) {
              Level = Integer.parseInt(arr[2].trim());
            }

            // 解析 星级
            if (!arr[3].trim().isEmpty()) {
              star = Integer.parseInt(arr[3].trim());
            }

            // 解析 觉醒等级
            if (!arr[4].trim().isEmpty()) {
              awaken = Integer.parseInt(arr[4].trim());
            }

            // 解析 技能1等级
            if (!arr[5].trim().isEmpty()) {
              skill1 = Integer.parseInt(arr[5].trim());
            }

            // 解析 技能2等级
            if (!arr[6].trim().isEmpty()) {
              skill2 = Integer.parseInt(arr[6].trim());
            }

            // 解析 技能3等级
            if (!arr[7].trim().isEmpty()) {
              skill3 = Integer.parseInt(arr[7].trim());
            }

            // 解析 技能S等级
            if (!arr[8].trim().isEmpty()) {
              skillS = Integer.parseInt(arr[8].trim());
            }

            // 解析 生命
            if (!arr[9].trim().isEmpty()) {
              maxHP = Integer.parseInt(arr[9].trim());
            }

            // 解析 攻击
            if (!arr[10].trim().isEmpty()) {
              attack = Integer.parseInt(arr[10].trim());
            }

            // 解析 防御
            if (!arr[11].trim().isEmpty()) {
              defence = Integer.parseInt(arr[11].trim());
            }

            // 解析 速度
            if (!arr[12].trim().isEmpty()) {
              speed = Integer.parseInt(arr[12].trim());
            }

            // 解析 暴击
            if (!arr[13].trim().isEmpty()) {
              crit = Integer.parseInt(arr[13].trim());
            }

            // 解析 暴伤
            if (!arr[14].trim().isEmpty()) {
              critRatio = Integer.parseInt(arr[14].trim());
            }

            // 解析 命中
            if (!arr[15].trim().isEmpty()) {
              effectHit = Integer.parseInt(arr[15].trim());
            }

            // 解析 抵抗
            if (!arr[16].trim().isEmpty()) {
              effectDodge = Integer.parseInt(arr[16].trim());
            }

            // 解析 回能
            if (!arr[17].trim().isEmpty()) {
              spCoe = Integer.parseInt(arr[17].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          ElectronicFairHeroConfig config = new ElectronicFairHeroConfig(id, heroId, Level, star, awaken, skill1, skill2, skill3, skillS, maxHP, attack, defence, speed, crit, critRatio, effectHit, effectDodge, spCoe);
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

    public List<ElectronicFairHeroConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ElectronicFairHeroConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "electronicFairHero.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
