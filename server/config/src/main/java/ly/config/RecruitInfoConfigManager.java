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
 * File: RecruitInfoConfigManager
 */
public class RecruitInfoConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final RecruitInfoConfigManager instance = new RecruitInfoConfigManager();
  private static final RecruitInfoConfigManagerImpl instanceImplA = new RecruitInfoConfigManagerImpl();
  private static final RecruitInfoConfigManagerImpl instanceImplB = new RecruitInfoConfigManagerImpl();

  public static RecruitInfoConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static RecruitInfoConfigManagerImpl getStandby() {
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
    RecruitInfoConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class RecruitInfoConfigManagerImpl extends AbstractConfigManger {
    private List<RecruitInfoConfig> configList = List.of();
    private Map<Integer, RecruitInfoConfig> configMap = Map.of();

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
      RecruitInfoConfigChecker checker = new RecruitInfoConfigChecker();
      checker.checkHeader(logger, configDir);
      List<RecruitInfoConfig> newList = new ArrayList<>();
      Map<Integer, RecruitInfoConfig> newMap = new HashMap<>();
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
          int welfareId = 0;
          int recruitType = 0;
          int scheDuling = 0;
          int trueActivityId = 0;
          int item = 0;
          int turnId = 0;
          int num = 0;
          int recruitNum = 0;
          int awardId = 0;
          String sumAwardId = null;
          String desc = null;
          int dayLimit = 0;
          int dayLimitId = 0;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 品质保底类型
            if (!arr[1].trim().isEmpty()) {
              welfareId = Integer.parseInt(arr[1].trim());
            }

            // 解析 招募类型
            if (!arr[2].trim().isEmpty()) {
              recruitType = Integer.parseInt(arr[2].trim());
            }

            // 解析 活动排期
            if (!arr[3].trim().isEmpty()) {
              scheDuling = Integer.parseInt(arr[3].trim());
            }

            // 解析 对应的活动id
            if (!arr[4].trim().isEmpty()) {
              trueActivityId = Integer.parseInt(arr[4].trim());
            }

            // 解析 招募道具
            if (!arr[5].trim().isEmpty()) {
              item = Integer.parseInt(arr[5].trim());
            }

            // 解析 跳转ID
            if (!arr[6].trim().isEmpty()) {
              turnId = Integer.parseInt(arr[6].trim());
            }

            // 解析 消耗数量
            if (!arr[7].trim().isEmpty()) {
              num = Integer.parseInt(arr[7].trim());
            }

            // 解析 招募次数
            if (!arr[8].trim().isEmpty()) {
              recruitNum = Integer.parseInt(arr[8].trim());
            }

            // 解析 对应掉落组
            if (!arr[9].trim().isEmpty()) {
              awardId = Integer.parseInt(arr[9].trim());
            }

            // 解析 累计次数掉落组
            if (!arr[10].trim().isEmpty()) {
              sumAwardId = arr[10].trim();
            }

            // 解析 注释
            if (!arr[11].trim().isEmpty()) {
              desc = arr[11].trim();
            }

            // 解析 每日上限
            if (!arr[12].trim().isEmpty()) {
              dayLimit = Integer.parseInt(arr[12].trim());
            }

            // 解析 抽卡上限ID
            if (!arr[13].trim().isEmpty()) {
              dayLimitId = Integer.parseInt(arr[13].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          RecruitInfoConfig config = new RecruitInfoConfig(id, welfareId, recruitType, scheDuling, trueActivityId, item, turnId, num, recruitNum, awardId, sumAwardId, desc, dayLimit, dayLimitId);
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

    public List<RecruitInfoConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, RecruitInfoConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "recruitInfo.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
