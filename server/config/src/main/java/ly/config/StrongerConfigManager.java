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
 * File: StrongerConfigManager
 */
public class StrongerConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final StrongerConfigManager instance = new StrongerConfigManager();
  private static final StrongerConfigManagerImpl instanceImplA = new StrongerConfigManagerImpl();
  private static final StrongerConfigManagerImpl instanceImplB = new StrongerConfigManagerImpl();

  public static StrongerConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static StrongerConfigManagerImpl getStandby() {
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
    StrongerConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class StrongerConfigManagerImpl extends AbstractConfigManger {
    private List<StrongerConfig> configList = List.of();
    private Map<Integer, StrongerConfig> configMap = Map.of();

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
      StrongerConfigChecker checker = new StrongerConfigChecker();
      checker.checkHeader(logger, configDir);
      List<StrongerConfig> newList = new ArrayList<>();
      Map<Integer, StrongerConfig> newMap = new HashMap<>();
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
          int resType = 0;
          String resName = null;
          int resPic = 0;
          String resDes = null;
          int resSort = 0;
          int playType = 0;
          String playName = null;
          int playStar = 0;
          int playJump = 0;
          int playSort = 0;
          int itemId = 0;
          String itemName = null;
          int itemSort = 0;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 资源类型
            if (!arr[1].trim().isEmpty()) {
              resType = Integer.parseInt(arr[1].trim());
            }

            // 解析 资源名称
            if (!arr[2].trim().isEmpty()) {
              resName = arr[2].trim();
            }

            // 解析 资源图标
            if (!arr[3].trim().isEmpty()) {
              resPic = Integer.parseInt(arr[3].trim());
            }

            // 解析 资源描述
            if (!arr[4].trim().isEmpty()) {
              resDes = arr[4].trim();
            }

            // 解析 资源排序
            if (!arr[5].trim().isEmpty()) {
              resSort = Integer.parseInt(arr[5].trim());
            }

            // 解析 玩法类型
            if (!arr[6].trim().isEmpty()) {
              playType = Integer.parseInt(arr[6].trim());
            }

            // 解析 玩法名称（作废）
            if (!arr[7].trim().isEmpty()) {
              playName = arr[7].trim();
            }

            // 解析 推荐星级
            if (!arr[8].trim().isEmpty()) {
              playStar = Integer.parseInt(arr[8].trim());
            }

            // 解析 跳转
            if (!arr[9].trim().isEmpty()) {
              playJump = Integer.parseInt(arr[9].trim());
            }

            // 解析 玩法排序
            if (!arr[10].trim().isEmpty()) {
              playSort = Integer.parseInt(arr[10].trim());
            }

            // 解析 道具类型
            if (!arr[11].trim().isEmpty()) {
              itemId = Integer.parseInt(arr[11].trim());
            }

            // 解析 道具名称（作废）
            if (!arr[12].trim().isEmpty()) {
              itemName = arr[12].trim();
            }

            // 解析 道具排序
            if (!arr[13].trim().isEmpty()) {
              itemSort = Integer.parseInt(arr[13].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          StrongerConfig config = new StrongerConfig(id, resType, resName, resPic, resDes, resSort, playType, playName, playStar, playJump, playSort, itemId, itemName, itemSort);
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

    public List<StrongerConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, StrongerConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "stronger.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
