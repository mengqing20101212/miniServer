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
 * File: ActivitysupplyConfigManager
 */
public class ActivitysupplyConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final ActivitysupplyConfigManager instance = new ActivitysupplyConfigManager();
  private static final ActivitysupplyConfigManagerImpl instanceImplA = new ActivitysupplyConfigManagerImpl();
  private static final ActivitysupplyConfigManagerImpl instanceImplB = new ActivitysupplyConfigManagerImpl();

  public static ActivitysupplyConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static ActivitysupplyConfigManagerImpl getStandby() {
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
    ActivitysupplyConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class ActivitysupplyConfigManagerImpl extends AbstractConfigManger {
    private List<ActivitysupplyConfig> configList = List.of();
    private Map<Integer, ActivitysupplyConfig> configMap = Map.of();

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
      ActivitysupplyConfigChecker checker = new ActivitysupplyConfigChecker();
      checker.checkHeader(logger, configDir);
      List<ActivitysupplyConfig> newList = new ArrayList<>();
      Map<Integer, ActivitysupplyConfig> newMap = new HashMap<>();
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
          int openType = 0;
          int scheDuling = 0;
          int shopId = 0;
          int drop = 0;
          int rechargShow = 0;
          String sloganName = null;
          int sloganBg = 0;
          int limitPara = 0;
          int rechargeShopId = 0;
          int refreshTime = 0;
          int moneyType = 0;
          int quantity = 0;
          try {
            // 解析 商品ID
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 开启类型
            if (!arr[1].trim().isEmpty()) {
              openType = Integer.parseInt(arr[1].trim());
            }

            // 解析 活动排期
            if (!arr[2].trim().isEmpty()) {
              scheDuling = Integer.parseInt(arr[2].trim());
            }

            // 解析 商店波次
            if (!arr[3].trim().isEmpty()) {
              shopId = Integer.parseInt(arr[3].trim());
            }

            // 解析 实际掉落
            if (!arr[4].trim().isEmpty()) {
              drop = Integer.parseInt(arr[4].trim());
            }

            // 解析 显示道具
            if (!arr[5].trim().isEmpty()) {
              rechargShow = Integer.parseInt(arr[5].trim());
            }

            // 解析 标语类型名字
            if (!arr[6].trim().isEmpty()) {
              sloganName = arr[6].trim();
            }

            // 解析 标语背景
            if (!arr[7].trim().isEmpty()) {
              sloganBg = Integer.parseInt(arr[7].trim());
            }

            // 解析 限购次数
            if (!arr[8].trim().isEmpty()) {
              limitPara = Integer.parseInt(arr[8].trim());
            }

            // 解析 商品ID
            if (!arr[9].trim().isEmpty()) {
              rechargeShopId = Integer.parseInt(arr[9].trim());
            }

            // 解析 刷新时间
            if (!arr[10].trim().isEmpty()) {
              refreshTime = Integer.parseInt(arr[10].trim());
            }

            // 解析 货币种类
            if (!arr[11].trim().isEmpty()) {
              moneyType = Integer.parseInt(arr[11].trim());
            }

            // 解析 货币数量
            if (!arr[12].trim().isEmpty()) {
              quantity = Integer.parseInt(arr[12].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          ActivitysupplyConfig config = new ActivitysupplyConfig(id, openType, scheDuling, shopId, drop, rechargShow, sloganName, sloganBg, limitPara, rechargeShopId, refreshTime, moneyType, quantity);
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

    public List<ActivitysupplyConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivitysupplyConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "activitysupply.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
