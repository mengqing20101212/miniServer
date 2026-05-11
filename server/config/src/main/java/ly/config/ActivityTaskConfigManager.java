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
 * File: ActivityTaskConfigManager
 */
public class ActivityTaskConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final ActivityTaskConfigManager instance = new ActivityTaskConfigManager();
  private static final ActivityTaskConfigManagerImpl instanceImplA = new ActivityTaskConfigManagerImpl();
  private static final ActivityTaskConfigManagerImpl instanceImplB = new ActivityTaskConfigManagerImpl();

  public static ActivityTaskConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static ActivityTaskConfigManagerImpl getStandby() {
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
    ActivityTaskConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class ActivityTaskConfigManagerImpl extends AbstractConfigManger {
    private List<ActivityTaskConfig> configList = List.of();
    private Map<Integer, ActivityTaskConfig> configMap = Map.of();

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
      ActivityTaskConfigChecker checker = new ActivityTaskConfigChecker();
      checker.checkHeader(logger, configDir);
      List<ActivityTaskConfig> newList = new ArrayList<>();
      Map<Integer, ActivityTaskConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 16) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          String title = null;
          String name = null;
          int questType = 0;
          int scheDuling = 0;
          int page = 0;
          int condition = 0;
          String rewardShow = null;
          int drop = 0;
          int redirectionId = 0;
          int priority = 0;
          int pointType = 0;
          int point = 0;
          String startTime = null;
          String endTime = null;
          int titlePicId = 0;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 任务标题
            if (!arr[1].trim().isEmpty()) {
              title = arr[1].trim();
            }

            // 解析 任务名称
            if (!arr[2].trim().isEmpty()) {
              name = arr[2].trim();
            }

            // 解析 任务类型
            if (!arr[3].trim().isEmpty()) {
              questType = Integer.parseInt(arr[3].trim());
            }

            // 解析 活动排期
            if (!arr[4].trim().isEmpty()) {
              scheDuling = Integer.parseInt(arr[4].trim());
            }

            // 解析 所属页签
            if (!arr[5].trim().isEmpty()) {
              page = Integer.parseInt(arr[5].trim());
            }

            // 解析 内容类型
            if (!arr[6].trim().isEmpty()) {
              condition = Integer.parseInt(arr[6].trim());
            }

            // 解析 奖励展示
            if (!arr[7].trim().isEmpty()) {
              rewardShow = arr[7].trim();
            }

            // 解析 实际掉落
            if (!arr[8].trim().isEmpty()) {
              drop = Integer.parseInt(arr[8].trim());
            }

            // 解析 跳转
            if (!arr[9].trim().isEmpty()) {
              redirectionId = Integer.parseInt(arr[9].trim());
            }

            // 解析 展示优先级
            if (!arr[10].trim().isEmpty()) {
              priority = Integer.parseInt(arr[10].trim());
            }

            // 解析 积分类型
            if (!arr[11].trim().isEmpty()) {
              pointType = Integer.parseInt(arr[11].trim());
            }

            // 解析 获得积分
            if (!arr[12].trim().isEmpty()) {
              point = Integer.parseInt(arr[12].trim());
            }

            // 解析 开始时间
            if (!arr[13].trim().isEmpty()) {
              startTime = arr[13].trim();
            }

            // 解析 结束时间
            if (!arr[14].trim().isEmpty()) {
              endTime = arr[14].trim();
            }

            // 解析 任务标签图片
            if (!arr[15].trim().isEmpty()) {
              titlePicId = Integer.parseInt(arr[15].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          ActivityTaskConfig config = new ActivityTaskConfig(id, title, name, questType, scheDuling, page, condition, rewardShow, drop, redirectionId, priority, pointType, point, startTime, endTime, titlePicId);
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

    public List<ActivityTaskConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivityTaskConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "activityTask.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
