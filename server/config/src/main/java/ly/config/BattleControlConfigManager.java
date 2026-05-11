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
 * File: BattleControlConfigManager
 */
public class BattleControlConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final BattleControlConfigManager instance = new BattleControlConfigManager();
  private static final BattleControlConfigManagerImpl instanceImplA = new BattleControlConfigManagerImpl();
  private static final BattleControlConfigManagerImpl instanceImplB = new BattleControlConfigManagerImpl();

  public static BattleControlConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static BattleControlConfigManagerImpl getStandby() {
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
    BattleControlConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class BattleControlConfigManagerImpl extends AbstractConfigManger {
    private List<BattleControlConfig> configList = List.of();
    private Map<Integer, BattleControlConfig> configMap = Map.of();

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
      BattleControlConfigChecker checker = new BattleControlConfigChecker();
      checker.checkHeader(logger, configDir);
      List<BattleControlConfig> newList = new ArrayList<>();
      Map<Integer, BattleControlConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 10) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          String name = null;
          int lineupId = 0;
          int isOnlineBattle = 0;
          int loadingTime = 0;
          int readyTime = 0;
          int changeHeroTime = 0;
          int isShare = 0;
          int chaoiceWaitTime = 0;
          int isSaveDate = 0;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 活动名称
            if (!arr[1].trim().isEmpty()) {
              name = arr[1].trim();
            }

            // 解析 阵容id
            if (!arr[2].trim().isEmpty()) {
              lineupId = Integer.parseInt(arr[2].trim());
            }

            // 解析 是否是服务器战斗
            if (!arr[3].trim().isEmpty()) {
              isOnlineBattle = Integer.parseInt(arr[3].trim());
            }

            // 解析 在线战斗通用LOADING界面最大等待时间
            if (!arr[4].trim().isEmpty()) {
              loadingTime = Integer.parseInt(arr[4].trim());
            }

            // 解析 战前准备时间限制
            if (!arr[5].trim().isEmpty()) {
              readyTime = Integer.parseInt(arr[5].trim());
            }

            // 解析 战前换阵容时间限制（单位s）
            if (!arr[6].trim().isEmpty()) {
              changeHeroTime = Integer.parseInt(arr[6].trim());
            }

            // 解析 时间是否共用
            if (!arr[7].trim().isEmpty()) {
              isShare = Integer.parseInt(arr[7].trim());
            }

            // 解析 每回合行动时间限制（单位s）
            if (!arr[8].trim().isEmpty()) {
              chaoiceWaitTime = Integer.parseInt(arr[8].trim());
            }

            // 解析 是否保存战斗数据
            if (!arr[9].trim().isEmpty()) {
              isSaveDate = Integer.parseInt(arr[9].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          BattleControlConfig config = new BattleControlConfig(id, name, lineupId, isOnlineBattle, loadingTime, readyTime, changeHeroTime, isShare, chaoiceWaitTime, isSaveDate);
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

    public List<BattleControlConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, BattleControlConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "battleControl.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
