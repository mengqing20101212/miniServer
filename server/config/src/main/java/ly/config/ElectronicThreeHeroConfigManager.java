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
 * File: ElectronicThreeHeroConfigManager
 */
public class ElectronicThreeHeroConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final ElectronicThreeHeroConfigManager instance = new ElectronicThreeHeroConfigManager();
  private static final ElectronicThreeHeroConfigManagerImpl instanceImplA = new ElectronicThreeHeroConfigManagerImpl();
  private static final ElectronicThreeHeroConfigManagerImpl instanceImplB = new ElectronicThreeHeroConfigManagerImpl();

  public static ElectronicThreeHeroConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static ElectronicThreeHeroConfigManagerImpl getStandby() {
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
    ElectronicThreeHeroConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class ElectronicThreeHeroConfigManagerImpl extends AbstractConfigManger {
    private List<ElectronicThreeHeroConfig> configList = List.of();
    private Map<Integer, ElectronicThreeHeroConfig> configMap = Map.of();

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
      ElectronicThreeHeroConfigChecker checker = new ElectronicThreeHeroConfigChecker();
      checker.checkHeader(logger, configDir);
      List<ElectronicThreeHeroConfig> newList = new ArrayList<>();
      Map<Integer, ElectronicThreeHeroConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 27) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          int heroId = 0;
          String heroDesc = null;
          int pro = 0;
          int group = 0;
          String circuitAttr1 = null;
          int circuit2 = 0;
          String circuitAttr2 = null;
          int circuit3 = 0;
          String circuitAttr3 = null;
          int circuit4 = 0;
          String circuitAttr4 = null;
          int circuit5 = 0;
          String circuitAttr5 = null;
          int circuit6 = 0;
          String circuitAttr6 = null;
          int circuit7 = 0;
          String circuitAttr7 = null;
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

            // 解析 英雄特性描述
            if (!arr[2].trim().isEmpty()) {
              heroDesc = arr[2].trim();
            }

            // 解析 权重
            if (!arr[3].trim().isEmpty()) {
              pro = Integer.parseInt(arr[3].trim());
            }

            // 解析 组
            if (!arr[4].trim().isEmpty()) {
              group = Integer.parseInt(arr[4].trim());
            }

            // 解析 1号位置源核属性
            if (!arr[5].trim().isEmpty()) {
              circuitAttr1 = arr[5].trim();
            }

            // 解析 2号位置源核
            if (!arr[6].trim().isEmpty()) {
              circuit2 = Integer.parseInt(arr[6].trim());
            }

            // 解析 2号位置源核属性
            if (!arr[7].trim().isEmpty()) {
              circuitAttr2 = arr[7].trim();
            }

            // 解析 3号位置源核
            if (!arr[8].trim().isEmpty()) {
              circuit3 = Integer.parseInt(arr[8].trim());
            }

            // 解析 3号位置源核属性
            if (!arr[9].trim().isEmpty()) {
              circuitAttr3 = arr[9].trim();
            }

            // 解析 4号位置源核
            if (!arr[10].trim().isEmpty()) {
              circuit4 = Integer.parseInt(arr[10].trim());
            }

            // 解析 4号位置源核属性
            if (!arr[11].trim().isEmpty()) {
              circuitAttr4 = arr[11].trim();
            }

            // 解析 5号位置源核
            if (!arr[12].trim().isEmpty()) {
              circuit5 = Integer.parseInt(arr[12].trim());
            }

            // 解析 5号位置源核属性
            if (!arr[13].trim().isEmpty()) {
              circuitAttr5 = arr[13].trim();
            }

            // 解析 6号位置源核
            if (!arr[14].trim().isEmpty()) {
              circuit6 = Integer.parseInt(arr[14].trim());
            }

            // 解析 6号位置源核属性
            if (!arr[15].trim().isEmpty()) {
              circuitAttr6 = arr[15].trim();
            }

            // 解析 7号位置源核
            if (!arr[16].trim().isEmpty()) {
              circuit7 = Integer.parseInt(arr[16].trim());
            }

            // 解析 7号位置源核属性
            if (!arr[17].trim().isEmpty()) {
              circuitAttr7 = arr[17].trim();
            }

            // 解析 生命
            if (!arr[18].trim().isEmpty()) {
              maxHP = Integer.parseInt(arr[18].trim());
            }

            // 解析 攻击
            if (!arr[19].trim().isEmpty()) {
              attack = Integer.parseInt(arr[19].trim());
            }

            // 解析 防御
            if (!arr[20].trim().isEmpty()) {
              defence = Integer.parseInt(arr[20].trim());
            }

            // 解析 速度
            if (!arr[21].trim().isEmpty()) {
              speed = Integer.parseInt(arr[21].trim());
            }

            // 解析 暴击
            if (!arr[22].trim().isEmpty()) {
              crit = Integer.parseInt(arr[22].trim());
            }

            // 解析 暴伤
            if (!arr[23].trim().isEmpty()) {
              critRatio = Integer.parseInt(arr[23].trim());
            }

            // 解析 命中
            if (!arr[24].trim().isEmpty()) {
              effectHit = Integer.parseInt(arr[24].trim());
            }

            // 解析 抵抗
            if (!arr[25].trim().isEmpty()) {
              effectDodge = Integer.parseInt(arr[25].trim());
            }

            // 解析 回能
            if (!arr[26].trim().isEmpty()) {
              spCoe = Integer.parseInt(arr[26].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          ElectronicThreeHeroConfig config = new ElectronicThreeHeroConfig(id, heroId, heroDesc, pro, group, circuitAttr1, circuit2, circuitAttr2, circuit3, circuitAttr3, circuit4, circuitAttr4, circuit5, circuitAttr5, circuit6, circuitAttr6, circuit7, circuitAttr7, maxHP, attack, defence, speed, crit, critRatio, effectHit, effectDodge, spCoe);
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

    public List<ElectronicThreeHeroConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ElectronicThreeHeroConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "electronicThreeHero.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
