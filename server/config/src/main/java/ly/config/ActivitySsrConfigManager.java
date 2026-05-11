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
 * File: ActivitySsrConfigManager
 */
public class ActivitySsrConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final ActivitySsrConfigManager instance = new ActivitySsrConfigManager();
  private static final ActivitySsrConfigManagerImpl instanceImplA = new ActivitySsrConfigManagerImpl();
  private static final ActivitySsrConfigManagerImpl instanceImplB = new ActivitySsrConfigManagerImpl();

  public static ActivitySsrConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static ActivitySsrConfigManagerImpl getStandby() {
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
    ActivitySsrConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class ActivitySsrConfigManagerImpl extends AbstractConfigManger {
    private List<ActivitySsrConfig> configList = List.of();
    private Map<Integer, ActivitySsrConfig> configMap = Map.of();

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
      ActivitySsrConfigChecker checker = new ActivitySsrConfigChecker();
      checker.checkHeader(logger, configDir);
      List<ActivitySsrConfig> newList = new ArrayList<>();
      Map<Integer, ActivitySsrConfig> newMap = new HashMap<>();
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
          int tpye = 0;
          String rewardShow = null;
          int activityTaskId = 0;
          int scheDuling = 0;
          String name = null;
          int picture = 0;
          String coordinate = null;
          int bg = 0;
          String size = null;
          String coordinateS = null;
          String sizeS = null;
          String coordinateH = null;
          String sizeH = null;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 类别
            if (!arr[1].trim().isEmpty()) {
              tpye = Integer.parseInt(arr[1].trim());
            }

            // 解析 积分奖励展示
            if (!arr[2].trim().isEmpty()) {
              rewardShow = arr[2].trim();
            }

            // 解析 对应任务ID
            if (!arr[3].trim().isEmpty()) {
              activityTaskId = Integer.parseInt(arr[3].trim());
            }

            // 解析 活动排期
            if (!arr[4].trim().isEmpty()) {
              scheDuling = Integer.parseInt(arr[4].trim());
            }

            // 解析 名字
            if (!arr[5].trim().isEmpty()) {
              name = arr[5].trim();
            }

            // 解析 立绘
            if (!arr[6].trim().isEmpty()) {
              picture = Integer.parseInt(arr[6].trim());
            }

            // 解析 坐标
            if (!arr[7].trim().isEmpty()) {
              coordinate = arr[7].trim();
            }

            // 解析 背景图
            if (!arr[8].trim().isEmpty()) {
              bg = Integer.parseInt(arr[8].trim());
            }

            // 解析 长宽
            if (!arr[9].trim().isEmpty()) {
              size = arr[9].trim();
            }

            // 解析 阴影坐标
            if (!arr[10].trim().isEmpty()) {
              coordinateS = arr[10].trim();
            }

            // 解析 阴影长宽
            if (!arr[11].trim().isEmpty()) {
              sizeS = arr[11].trim();
            }

            // 解析 半透坐标
            if (!arr[12].trim().isEmpty()) {
              coordinateH = arr[12].trim();
            }

            // 解析 半透长宽
            if (!arr[13].trim().isEmpty()) {
              sizeH = arr[13].trim();
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          ActivitySsrConfig config = new ActivitySsrConfig(id, tpye, rewardShow, activityTaskId, scheDuling, name, picture, coordinate, bg, size, coordinateS, sizeS, coordinateH, sizeH);
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

    public List<ActivitySsrConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivitySsrConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "activitySsr.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
