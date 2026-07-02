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
 * File: EntrustConfigManager
 */
public class EntrustConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final EntrustConfigManager instance = new EntrustConfigManager();
  private static final EntrustConfigManagerImpl instanceImplA = new EntrustConfigManagerImpl();
  private static final EntrustConfigManagerImpl instanceImplB = new EntrustConfigManagerImpl();

  public static EntrustConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static EntrustConfigManagerImpl getStandby() {
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
    EntrustConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class EntrustConfigManagerImpl extends AbstractConfigManger {
    private List<EntrustConfig> configList = List.of();
    private Map<Integer, EntrustConfig> configMap = Map.of();

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
      EntrustConfigChecker checker = new EntrustConfigChecker();
      checker.checkHeader(logger, configDir);
      List<EntrustConfig> newList = new ArrayList<>();
      Map<Integer, EntrustConfig> newMap = new HashMap<>();
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
          int star = 0;
          int unlockType = 0;
          int unlockCondition = 0;
          String unlockDec = null;
          String reward = null;
          int drop = 0;
          int dropPercent = 0;
          int time = 0;
          int type = 0;
          int percent = 0;
          int heroNum = 0;
          String taskName = null;
          String timeGroup = null;
          String action = null;
          String position = null;
          int iconResId = 0;
          try {
            // 解析 id
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 所属组别
            if (!arr[1].trim().isEmpty()) {
              groupId = Integer.parseInt(arr[1].trim());
            }

            // 解析 星级
            if (!arr[2].trim().isEmpty()) {
              star = Integer.parseInt(arr[2].trim());
            }

            // 解析 解锁类型
            if (!arr[3].trim().isEmpty()) {
              unlockType = Integer.parseInt(arr[3].trim());
            }

            // 解析 解锁条件
            if (!arr[4].trim().isEmpty()) {
              unlockCondition = Integer.parseInt(arr[4].trim());
            }

            // 解析 解锁条件说明
            if (!arr[5].trim().isEmpty()) {
              unlockDec = arr[5].trim();
            }

            // 解析 主要奖励
            if (!arr[6].trim().isEmpty()) {
              reward = arr[6].trim();
            }

            // 解析 额外奖励
            if (!arr[7].trim().isEmpty()) {
              drop = Integer.parseInt(arr[7].trim());
            }

            // 解析 额外奖励概率
            if (!arr[8].trim().isEmpty()) {
              dropPercent = Integer.parseInt(arr[8].trim());
            }

            // 解析 时间上限
            if (!arr[9].trim().isEmpty()) {
              time = Integer.parseInt(arr[9].trim());
            }

            // 解析 加成类型
            if (!arr[10].trim().isEmpty()) {
              type = Integer.parseInt(arr[10].trim());
            }

            // 解析 类型加成千分比
            if (!arr[11].trim().isEmpty()) {
              percent = Integer.parseInt(arr[11].trim());
            }

            // 解析 上阵数量
            if (!arr[12].trim().isEmpty()) {
              heroNum = Integer.parseInt(arr[12].trim());
            }

            // 解析 任务名称
            if (!arr[13].trim().isEmpty()) {
              taskName = arr[13].trim();
            }

            // 解析 时间段(结束点)(分钟)
            if (!arr[14].trim().isEmpty()) {
              timeGroup = arr[14].trim();
            }

            // 解析 挂机时间对应动作( 图片)
            if (!arr[15].trim().isEmpty()) {
              action = arr[15].trim();
            }

            // 解析 节点位置(x,y)
            if (!arr[16].trim().isEmpty()) {
              position = arr[16].trim();
            }

            // 解析 图标
            if (!arr[17].trim().isEmpty()) {
              iconResId = Integer.parseInt(arr[17].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          EntrustConfig config = new EntrustConfig(id, groupId, star, unlockType, unlockCondition, unlockDec, reward, drop, dropPercent, time, type, percent, heroNum, taskName, timeGroup, action, position, iconResId);
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

    public List<EntrustConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, EntrustConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "entrust.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
