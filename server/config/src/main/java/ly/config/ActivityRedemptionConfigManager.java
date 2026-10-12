package ly.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ly.AbstractConfigManger;
import ly.ConfigLoadException;
import ly.InterfaceConfigManagerProxy;
import ly.utils.KV;
import org.slf4j.Logger;

/*
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 * File: ActivityRedemptionConfigManager
 */
public class ActivityRedemptionConfigManager implements InterfaceConfigManagerProxy {
  public static ActivityRedemptionConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ActivityRedemptionConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ActivityRedemptionConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "activityRedemption.txt"; }

  public static class ActivityRedemptionConfigManagerImpl extends AbstractConfigManger {
    private List<ActivityRedemptionConfig> configList = List.of();
    private Map<Integer, ActivityRedemptionConfig> configMap = Map.of();

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
      ActivityRedemptionConfigChecker checker = new ActivityRedemptionConfigChecker();
      checker.checkHeader(logger, configDir);
      List<ActivityRedemptionConfig> newList = new ArrayList<>();
      Map<Integer, ActivityRedemptionConfig> newMap = new HashMap<>();
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
          String name = null;
          int questType = 0;
          int scheDuling = 0;
          String exchangeNum = null;
          int drop = 0;
          int dropShow = 0;
          int finishMax = 0;
          int reset = 0;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 任务名称
            if (!arr[1].trim().isEmpty()) {
              name = arr[1].trim();
            }

            // 解析 任务类型
            if (!arr[2].trim().isEmpty()) {
              questType = Integer.parseInt(arr[2].trim());
            }

            // 解析 活动排期
            if (!arr[3].trim().isEmpty()) {
              scheDuling = Integer.parseInt(arr[3].trim());
            }

            // 解析 兑换参数
            if (!arr[4].trim().isEmpty()) {
              exchangeNum = arr[4].trim();
            }

            // 解析 兑换奖励
            if (!arr[5].trim().isEmpty()) {
              drop = Integer.parseInt(arr[5].trim());
            }

            // 解析 奖励展示
            if (!arr[6].trim().isEmpty()) {
              dropShow = Integer.parseInt(arr[6].trim());
            }

            // 解析 可兑换次数
            if (!arr[7].trim().isEmpty()) {
              finishMax = Integer.parseInt(arr[7].trim());
            }

            // 解析 每日重置
            if (!arr[8].trim().isEmpty()) {
              reset = Integer.parseInt(arr[8].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          ActivityRedemptionConfig config = new ActivityRedemptionConfig(id, name, questType, scheDuling, exchangeNum, drop, dropShow, finishMax, reset);
          config.afterLoad();
          newList.add(config);
          newMap.put(config.id, config);
        }
        checker.checkAfterParse(logger, newList);
        configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
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

    public List<ActivityRedemptionConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivityRedemptionConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "activityRedemption.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
