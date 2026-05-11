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
 * File: RecruitPoolConfigManager
 */
public class RecruitPoolConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final RecruitPoolConfigManager instance = new RecruitPoolConfigManager();
  private static final RecruitPoolConfigManagerImpl instanceImplA = new RecruitPoolConfigManagerImpl();
  private static final RecruitPoolConfigManagerImpl instanceImplB = new RecruitPoolConfigManagerImpl();

  public static RecruitPoolConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static RecruitPoolConfigManagerImpl getStandby() {
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
    RecruitPoolConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class RecruitPoolConfigManagerImpl extends AbstractConfigManger {
    private List<RecruitPoolConfig> configList = List.of();
    private Map<Integer, RecruitPoolConfig> configMap = Map.of();

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
      RecruitPoolConfigChecker checker = new RecruitPoolConfigChecker();
      checker.checkHeader(logger, configDir);
      List<RecruitPoolConfig> newList = new ArrayList<>();
      Map<Integer, RecruitPoolConfig> newMap = new HashMap<>();
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
          String name = null;
          int recruitType = 0;
          int showStyleType = 0;
          int oneDrawId = 0;
          int tenDrawId = 0;
          int scheDuling = 0;
          int type = 0;
          String timesShowType = null;
          String video = null;
          String lastDateShow = null;
          String chanceText = null;
          int topId = 0;
          int recruitEndTopId = 0;
          int recruitmentshow = 0;
          int trueActivityId = 0;
          try {
            // 解析 招募编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 招募名称
            if (!arr[1].trim().isEmpty()) {
              name = arr[1].trim();
            }

            // 解析 招募卡池类型
            if (!arr[2].trim().isEmpty()) {
              recruitType = Integer.parseInt(arr[2].trim());
            }

            // 解析 招募显示模板类型
            if (!arr[3].trim().isEmpty()) {
              showStyleType = Integer.parseInt(arr[3].trim());
            }

            // 解析 单抽卡池id
            if (!arr[4].trim().isEmpty()) {
              oneDrawId = Integer.parseInt(arr[4].trim());
            }

            // 解析 十抽卡池id
            if (!arr[5].trim().isEmpty()) {
              tenDrawId = Integer.parseInt(arr[5].trim());
            }

            // 解析 活动排期
            if (!arr[6].trim().isEmpty()) {
              scheDuling = Integer.parseInt(arr[6].trim());
            }

            // 解析 卡池类型(目前没用)
            if (!arr[7].trim().isEmpty()) {
              type = Integer.parseInt(arr[7].trim());
            }

            // 解析 首次招募展示
            if (!arr[8].trim().isEmpty()) {
              timesShowType = arr[8].trim();
            }

            // 解析 播放招募视频(名称)
            if (!arr[9].trim().isEmpty()) {
              video = arr[9].trim();
            }

            // 解析 显示持续时间
            if (!arr[10].trim().isEmpty()) {
              lastDateShow = arr[10].trim();
            }

            // 解析 概率文本
            if (!arr[11].trim().isEmpty()) {
              chanceText = arr[11].trim();
            }

            // 解析 标题条ID
            if (!arr[12].trim().isEmpty()) {
              topId = Integer.parseInt(arr[12].trim());
            }

            // 解析 招募结果ID
            if (!arr[13].trim().isEmpty()) {
              recruitEndTopId = Integer.parseInt(arr[13].trim());
            }

            // 解析 是否显示在招募
            if (!arr[14].trim().isEmpty()) {
              recruitmentshow = Integer.parseInt(arr[14].trim());
            }

            // 解析 对应的活动id
            if (!arr[15].trim().isEmpty()) {
              trueActivityId = Integer.parseInt(arr[15].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          RecruitPoolConfig config = new RecruitPoolConfig(id, name, recruitType, showStyleType, oneDrawId, tenDrawId, scheDuling, type, timesShowType, video, lastDateShow, chanceText, topId, recruitEndTopId, recruitmentshow, trueActivityId);
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

    public List<RecruitPoolConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, RecruitPoolConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "recruitPool.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
