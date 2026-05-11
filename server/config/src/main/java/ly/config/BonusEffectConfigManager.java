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
 * File: BonusEffectConfigManager
 */
public class BonusEffectConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final BonusEffectConfigManager instance = new BonusEffectConfigManager();
  private static final BonusEffectConfigManagerImpl instanceImplA = new BonusEffectConfigManagerImpl();
  private static final BonusEffectConfigManagerImpl instanceImplB = new BonusEffectConfigManagerImpl();

  public static BonusEffectConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static BonusEffectConfigManagerImpl getStandby() {
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
    BonusEffectConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class BonusEffectConfigManagerImpl extends AbstractConfigManger {
    private List<BonusEffectConfig> configList = List.of();
    private Map<Integer, BonusEffectConfig> configMap = Map.of();

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
      BonusEffectConfigChecker checker = new BonusEffectConfigChecker();
      checker.checkHeader(logger, configDir);
      List<BonusEffectConfig> newList = new ArrayList<>();
      Map<Integer, BonusEffectConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 10) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          String description = null;
          int icon = 0;
          int bonusEffect = 0;
          String isBonusBuff = null;
          String bonusType = null;
          String param_1 = null;
          String isVanish = null;
          int group = 0;
          int level = 0;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 描述
            if (!arr[1].trim().isEmpty()) {
              description = arr[1].trim();
            }

            // 解析 图标
            if (!arr[2].trim().isEmpty()) {
              icon = Integer.parseInt(arr[2].trim());
            }

            // 解析 效果
            if (!arr[3].trim().isEmpty()) {
              bonusEffect = Integer.parseInt(arr[3].trim());
            }

            // 解析 是否为BuffId
            if (!arr[4].trim().isEmpty()) {
              isBonusBuff = arr[4].trim();
            }

            // 解析 效果类型
            if (!arr[5].trim().isEmpty()) {
              bonusType = arr[5].trim();
            }

            // 解析 被吃掉的效果
            if (!arr[6].trim().isEmpty()) {
              param_1 = arr[6].trim();
            }

            // 解析 是否立刻消失
            if (!arr[7].trim().isEmpty()) {
              isVanish = arr[7].trim();
            }

            // 解析 分组
            if (!arr[8].trim().isEmpty()) {
              group = Integer.parseInt(arr[8].trim());
            }

            // 解析 组内等级
            if (!arr[9].trim().isEmpty()) {
              level = Integer.parseInt(arr[9].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          BonusEffectConfig config = new BonusEffectConfig(id, description, icon, bonusEffect, isBonusBuff, bonusType, param_1, isVanish, group, level);
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

    public List<BonusEffectConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, BonusEffectConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "bonusEffect.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
