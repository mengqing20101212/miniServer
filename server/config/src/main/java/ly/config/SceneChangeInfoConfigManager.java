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
 * File: SceneChangeInfoConfigManager
 */
public class SceneChangeInfoConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final SceneChangeInfoConfigManager instance = new SceneChangeInfoConfigManager();
  private static final SceneChangeInfoConfigManagerImpl instanceImplA = new SceneChangeInfoConfigManagerImpl();
  private static final SceneChangeInfoConfigManagerImpl instanceImplB = new SceneChangeInfoConfigManagerImpl();

  public static SceneChangeInfoConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static SceneChangeInfoConfigManagerImpl getStandby() {
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
    SceneChangeInfoConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class SceneChangeInfoConfigManagerImpl extends AbstractConfigManger {
    private List<SceneChangeInfoConfig> configList = List.of();
    private Map<Integer, SceneChangeInfoConfig> configMap = Map.of();

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
      SceneChangeInfoConfigChecker checker = new SceneChangeInfoConfigChecker();
      checker.checkHeader(logger, configDir);
      List<SceneChangeInfoConfig> newList = new ArrayList<>();
      Map<Integer, SceneChangeInfoConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 13) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          String hpInherit = null;
          String spInherit = null;
          String energyInherit = null;
          String energyBarInherit = null;
          String sPowerInherit = null;
          String actionBarInherit = null;
          String skillCDInherit = null;
          String bonusInherit = null;
          String buffInherit = null;
          String globalRound = null;
          String campRound = null;
          String charRound = null;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 生命继承
            if (!arr[1].trim().isEmpty()) {
              hpInherit = arr[1].trim();
            }

            // 解析 气绝值继承
            if (!arr[2].trim().isEmpty()) {
              spInherit = arr[2].trim();
            }

            // 解析 鬼火继承
            if (!arr[3].trim().isEmpty()) {
              energyInherit = arr[3].trim();
            }

            // 解析 鬼火条继承
            if (!arr[4].trim().isEmpty()) {
              energyBarInherit = arr[4].trim();
            }

            // 解析 s能量继承
            if (!arr[5].trim().isEmpty()) {
              sPowerInherit = arr[5].trim();
            }

            // 解析 行动条继承
            if (!arr[6].trim().isEmpty()) {
              actionBarInherit = arr[6].trim();
            }

            // 解析 技能冷却继承
            if (!arr[7].trim().isEmpty()) {
              skillCDInherit = arr[7].trim();
            }

            // 解析 Bonus
            if (!arr[8].trim().isEmpty()) {
              bonusInherit = arr[8].trim();
            }

            // 解析 buff
            if (!arr[9].trim().isEmpty()) {
              buffInherit = arr[9].trim();
            }

            // 解析 全局论累计
            if (!arr[10].trim().isEmpty()) {
              globalRound = arr[10].trim();
            }

            // 解析 阵营轮累计
            if (!arr[11].trim().isEmpty()) {
              campRound = arr[11].trim();
            }

            // 解析 角色轮累计
            if (!arr[12].trim().isEmpty()) {
              charRound = arr[12].trim();
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          SceneChangeInfoConfig config = new SceneChangeInfoConfig(id, hpInherit, spInherit, energyInherit, energyBarInherit, sPowerInherit, actionBarInherit, skillCDInherit, bonusInherit, buffInherit, globalRound, campRound, charRound);
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

    public List<SceneChangeInfoConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SceneChangeInfoConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "sceneChangeInfo.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
