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
 * File: AdcarouselConfigManager
 */
public class AdcarouselConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final AdcarouselConfigManager instance = new AdcarouselConfigManager();
  private static final AdcarouselConfigManagerImpl instanceImplA = new AdcarouselConfigManagerImpl();
  private static final AdcarouselConfigManagerImpl instanceImplB = new AdcarouselConfigManagerImpl();

  public static AdcarouselConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static AdcarouselConfigManagerImpl getStandby() {
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
    AdcarouselConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class AdcarouselConfigManagerImpl extends AbstractConfigManger {
    private List<AdcarouselConfig> configList = List.of();
    private Map<Integer, AdcarouselConfig> configMap = Map.of();

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
      AdcarouselConfigChecker checker = new AdcarouselConfigChecker();
      checker.checkHeader(logger, configDir);
      List<AdcarouselConfig> newList = new ArrayList<>();
      Map<Integer, AdcarouselConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 12) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          String beizhu = null;
          int adcarousel = 0;
          int adcarousel2 = 0;
          int timeType = 0;
          String startTime = null;
          String endTime = null;
          String specialEndTime = null;
          int jump1 = 0;
          int order = 0;
          String level_limit = null;
          int OpenServiceActivity = 0;
          try {
            // 解析 索引ID
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 备注
            if (!arr[1].trim().isEmpty()) {
              beizhu = arr[1].trim();
            }

            // 解析 轮播图ID
            if (!arr[2].trim().isEmpty()) {
              adcarousel = Integer.parseInt(arr[2].trim());
            }

            // 解析 轮播图ID
            if (!arr[3].trim().isEmpty()) {
              adcarousel2 = Integer.parseInt(arr[3].trim());
            }

            // 解析 时间类型
            if (!arr[4].trim().isEmpty()) {
              timeType = Integer.parseInt(arr[4].trim());
            }

            // 解析 开始时间
            if (!arr[5].trim().isEmpty()) {
              startTime = arr[5].trim();
            }

            // 解析 结束时间
            if (!arr[6].trim().isEmpty()) {
              endTime = arr[6].trim();
            }

            // 解析 特殊时间
            if (!arr[7].trim().isEmpty()) {
              specialEndTime = arr[7].trim();
            }

            // 解析 跳转功能
            if (!arr[8].trim().isEmpty()) {
              jump1 = Integer.parseInt(arr[8].trim());
            }

            // 解析 展板排序
            if (!arr[9].trim().isEmpty()) {
              order = Integer.parseInt(arr[9].trim());
            }

            // 解析 等级可见性
            if (!arr[10].trim().isEmpty()) {
              level_limit = arr[10].trim();
            }

            // 解析 开服区间
            if (!arr[11].trim().isEmpty()) {
              OpenServiceActivity = Integer.parseInt(arr[11].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          AdcarouselConfig config = new AdcarouselConfig(id, beizhu, adcarousel, adcarousel2, timeType, startTime, endTime, specialEndTime, jump1, order, level_limit, OpenServiceActivity);
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

    public List<AdcarouselConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, AdcarouselConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "adcarousel.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
