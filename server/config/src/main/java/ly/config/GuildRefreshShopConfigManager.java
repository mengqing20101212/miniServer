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
 * File: GuildRefreshShopConfigManager
 */
public class GuildRefreshShopConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final GuildRefreshShopConfigManager instance = new GuildRefreshShopConfigManager();
  private static final GuildRefreshShopConfigManagerImpl instanceImplA = new GuildRefreshShopConfigManagerImpl();
  private static final GuildRefreshShopConfigManagerImpl instanceImplB = new GuildRefreshShopConfigManagerImpl();

  public static GuildRefreshShopConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static GuildRefreshShopConfigManagerImpl getStandby() {
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
    GuildRefreshShopConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class GuildRefreshShopConfigManagerImpl extends AbstractConfigManger {
    private List<GuildRefreshShopConfig> configList = List.of();
    private Map<Integer, GuildRefreshShopConfig> configMap = Map.of();

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
      GuildRefreshShopConfigChecker checker = new GuildRefreshShopConfigChecker();
      checker.checkHeader(logger, configDir);
      List<GuildRefreshShopConfig> newList = new ArrayList<>();
      Map<Integer, GuildRefreshShopConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 9) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          int itemId = 0;
          int itemNum = 0;
          int weight = 0;
          int currencyType = 0;
          int price = 0;
          int discount = 0;
          int level = 0;
          String coloredEggs = null;
          try {
            // 解析 状态编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 物品ID
            if (!arr[1].trim().isEmpty()) {
              itemId = Integer.parseInt(arr[1].trim());
            }

            // 解析 物品数量
            if (!arr[2].trim().isEmpty()) {
              itemNum = Integer.parseInt(arr[2].trim());
            }

            // 解析 权重
            if (!arr[3].trim().isEmpty()) {
              weight = Integer.parseInt(arr[3].trim());
            }

            // 解析 货币类型
            if (!arr[4].trim().isEmpty()) {
              currencyType = Integer.parseInt(arr[4].trim());
            }

            // 解析 价格
            if (!arr[5].trim().isEmpty()) {
              price = Integer.parseInt(arr[5].trim());
            }

            // 解析 是否打折
            if (!arr[6].trim().isEmpty()) {
              discount = Integer.parseInt(arr[6].trim());
            }

            // 解析 社团等级
            if (!arr[7].trim().isEmpty()) {
              level = Integer.parseInt(arr[7].trim());
            }

            // 解析 特殊奖励次数
            if (!arr[8].trim().isEmpty()) {
              coloredEggs = arr[8].trim();
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          GuildRefreshShopConfig config = new GuildRefreshShopConfig(id, itemId, itemNum, weight, currencyType, price, discount, level, coloredEggs);
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

    public List<GuildRefreshShopConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, GuildRefreshShopConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "guildRefreshShop.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
