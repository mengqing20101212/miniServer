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
 * File: PatrolMainConfigManager
 */
public class PatrolMainConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final PatrolMainConfigManager instance = new PatrolMainConfigManager();
  private static final PatrolMainConfigManagerImpl instanceImplA = new PatrolMainConfigManagerImpl();
  private static final PatrolMainConfigManagerImpl instanceImplB = new PatrolMainConfigManagerImpl();

  public static PatrolMainConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static PatrolMainConfigManagerImpl getStandby() {
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
    PatrolMainConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class PatrolMainConfigManagerImpl extends AbstractConfigManger {
    private List<PatrolMainConfig> configList = List.of();
    private Map<Integer, PatrolMainConfig> configMap = Map.of();

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
      PatrolMainConfigChecker checker = new PatrolMainConfigChecker();
      checker.checkHeader(logger, configDir);
      List<PatrolMainConfig> newList = new ArrayList<>();
      Map<Integer, PatrolMainConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 18) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          int groupId = 0;
          int cost = 0;
          int weight = 0;
          String name = null;
          int icon = 0;
          int dropGroupId = 0;
          String itemPre = null;
          int heroNumLimit = 0;
          int timeConsume = 0;
          int extraDropGroupId = 0;
          int extraDropPro = 0;
          String lvLimit = null;
          String desc = null;
          int startEvent = 0;
          String eventNum = null;
          String eventTime = null;
          String eventPro = null;
          try {
            // 解析 id
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 所属组别
            if (!arr[1].trim().isEmpty()) {
              groupId = Integer.parseInt(arr[1].trim());
            }

            // 解析 消耗体力
            if (!arr[2].trim().isEmpty()) {
              cost = Integer.parseInt(arr[2].trim());
            }

            // 解析 任务刷新权重
            if (!arr[3].trim().isEmpty()) {
              weight = Integer.parseInt(arr[3].trim());
            }

            // 解析 任务名称
            if (!arr[4].trim().isEmpty()) {
              name = arr[4].trim();
            }

            // 解析 图标
            if (!arr[5].trim().isEmpty()) {
              icon = Integer.parseInt(arr[5].trim());
            }

            // 解析 道具奖励
            if (!arr[6].trim().isEmpty()) {
              dropGroupId = Integer.parseInt(arr[6].trim());
            }

            // 解析 道具预览
            if (!arr[7].trim().isEmpty()) {
              itemPre = arr[7].trim();
            }

            // 解析 人数要求
            if (!arr[8].trim().isEmpty()) {
              heroNumLimit = Integer.parseInt(arr[8].trim());
            }

            // 解析 时间要求（秒？）
            if (!arr[9].trim().isEmpty()) {
              timeConsume = Integer.parseInt(arr[9].trim());
            }

            // 解析 特殊奖励
            if (!arr[10].trim().isEmpty()) {
              extraDropGroupId = Integer.parseInt(arr[10].trim());
            }

            // 解析 特殊奖励初识触发几率
            if (!arr[11].trim().isEmpty()) {
              extraDropPro = Integer.parseInt(arr[11].trim());
            }

            // 解析 等级出现范围
            if (!arr[12].trim().isEmpty()) {
              lvLimit = arr[12].trim();
            }

            // 解析 任务描述
            if (!arr[13].trim().isEmpty()) {
              desc = arr[13].trim();
            }

            // 解析 开始事件
            if (!arr[14].trim().isEmpty()) {
              startEvent = Integer.parseInt(arr[14].trim());
            }

            // 解析 触发事件数量
            if (!arr[15].trim().isEmpty()) {
              eventNum = arr[15].trim();
            }

            // 解析 触发时间随机范围（min）
            if (!arr[16].trim().isEmpty()) {
              eventTime = arr[16].trim();
            }

            // 解析 事件概率
            if (!arr[17].trim().isEmpty()) {
              eventPro = arr[17].trim();
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          PatrolMainConfig config = new PatrolMainConfig(id, groupId, cost, weight, name, icon, dropGroupId, itemPre, heroNumLimit, timeConsume, extraDropGroupId, extraDropPro, lvLimit, desc, startEvent, eventNum, eventTime, eventPro);
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

    public List<PatrolMainConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, PatrolMainConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "patrolMain.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
