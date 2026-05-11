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
 * File: HeroCharacteristicConfigManager
 */
public class HeroCharacteristicConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final HeroCharacteristicConfigManager instance = new HeroCharacteristicConfigManager();
  private static final HeroCharacteristicConfigManagerImpl instanceImplA = new HeroCharacteristicConfigManagerImpl();
  private static final HeroCharacteristicConfigManagerImpl instanceImplB = new HeroCharacteristicConfigManagerImpl();

  public static HeroCharacteristicConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static HeroCharacteristicConfigManagerImpl getStandby() {
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
    HeroCharacteristicConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class HeroCharacteristicConfigManagerImpl extends AbstractConfigManger {
    private List<HeroCharacteristicConfig> configList = List.of();
    private Map<Integer, HeroCharacteristicConfig> configMap = Map.of();

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
      HeroCharacteristicConfigChecker checker = new HeroCharacteristicConfigChecker();
      checker.checkHeader(logger, configDir);
      List<HeroCharacteristicConfig> newList = new ArrayList<>();
      Map<Integer, HeroCharacteristicConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 14) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          String beizhu = null;
          int heroId = 0;
          int skillGroup = 0;
          String heroFetters = null;
          int advanced = 0;
          String quality = null;
          String qualityNum = null;
          String heroType = null;
          String heroTypeNum = null;
          int characterType = 0;
          int characterNum = 0;
          String activationDes = null;
          String des = null;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 备注
            if (!arr[1].trim().isEmpty()) {
              beizhu = arr[1].trim();
            }

            // 解析 角色ID
            if (!arr[2].trim().isEmpty()) {
              heroId = Integer.parseInt(arr[2].trim());
            }

            // 解析 技能组
            if (!arr[3].trim().isEmpty()) {
              skillGroup = Integer.parseInt(arr[3].trim());
            }

            // 解析 羁绊角色
            if (!arr[4].trim().isEmpty()) {
              heroFetters = arr[4].trim();
            }

            // 解析 星级
            if (!arr[5].trim().isEmpty()) {
              advanced = Integer.parseInt(arr[5].trim());
            }

            // 解析 品质
            if (!arr[6].trim().isEmpty()) {
              quality = arr[6].trim();
            }

            // 解析 数量
            if (!arr[7].trim().isEmpty()) {
              qualityNum = arr[7].trim();
            }

            // 解析 类型
            if (!arr[8].trim().isEmpty()) {
              heroType = arr[8].trim();
            }

            // 解析 数量
            if (!arr[9].trim().isEmpty()) {
              heroTypeNum = arr[9].trim();
            }

            // 解析 角色类别
            if (!arr[10].trim().isEmpty()) {
              characterType = Integer.parseInt(arr[10].trim());
            }

            // 解析 角色类别数量
            if (!arr[11].trim().isEmpty()) {
              characterNum = Integer.parseInt(arr[11].trim());
            }

            // 解析 激活需求
            if (!arr[12].trim().isEmpty()) {
              activationDes = arr[12].trim();
            }

            // 解析 技能描述
            if (!arr[13].trim().isEmpty()) {
              des = arr[13].trim();
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          HeroCharacteristicConfig config = new HeroCharacteristicConfig(id, beizhu, heroId, skillGroup, heroFetters, advanced, quality, qualityNum, heroType, heroTypeNum, characterType, characterNum, activationDes, des);
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

    public List<HeroCharacteristicConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, HeroCharacteristicConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "heroCharacteristic.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
