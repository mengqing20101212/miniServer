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
 * File: PatrolHeroConfigManager
 */
public class PatrolHeroConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final PatrolHeroConfigManager instance = new PatrolHeroConfigManager();
  private static final PatrolHeroConfigManagerImpl instanceImplA = new PatrolHeroConfigManagerImpl();
  private static final PatrolHeroConfigManagerImpl instanceImplB = new PatrolHeroConfigManagerImpl();

  public static PatrolHeroConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static PatrolHeroConfigManagerImpl getStandby() {
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
    PatrolHeroConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class PatrolHeroConfigManagerImpl extends AbstractConfigManger {
    private List<PatrolHeroConfig> configList = List.of();
    private Map<Integer, PatrolHeroConfig> configMap = Map.of();

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
      PatrolHeroConfigChecker checker = new PatrolHeroConfigChecker();
      checker.checkHeader(logger, configDir);
      List<PatrolHeroConfig> newList = new ArrayList<>();
      Map<Integer, PatrolHeroConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 8) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          String name = null;
          String workSentiment = null;
          String workDay = null;
          String workDayDesc = null;
          int heroFetter = 0;
          int baseAdd = 0;
          int extraAdd = 0;
          try {
            // 解析 id
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 名字
            if (!arr[1].trim().isEmpty()) {
              name = arr[1].trim();
            }

            // 解析 工作感言
            if (!arr[2].trim().isEmpty()) {
              workSentiment = arr[2].trim();
            }

            // 解析 工作日期
            if (!arr[3].trim().isEmpty()) {
              workDay = arr[3].trim();
            }

            // 解析 工作日期描述
            if (!arr[4].trim().isEmpty()) {
              workDayDesc = arr[4].trim();
            }

            // 解析 羁绊英雄
            if (!arr[5].trim().isEmpty()) {
              heroFetter = Integer.parseInt(arr[5].trim());
            }

            // 解析 基础掉落加成
            if (!arr[6].trim().isEmpty()) {
              baseAdd = Integer.parseInt(arr[6].trim());
            }

            // 解析 额外掉落触发几率提升
            if (!arr[7].trim().isEmpty()) {
              extraAdd = Integer.parseInt(arr[7].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          PatrolHeroConfig config = new PatrolHeroConfig(id, name, workSentiment, workDay, workDayDesc, heroFetter, baseAdd, extraAdd);
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

    public List<PatrolHeroConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, PatrolHeroConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "patrolHero.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
