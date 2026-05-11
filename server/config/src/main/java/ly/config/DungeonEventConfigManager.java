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
 * File: DungeonEventConfigManager
 */
public class DungeonEventConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final DungeonEventConfigManager instance = new DungeonEventConfigManager();
  private static final DungeonEventConfigManagerImpl instanceImplA = new DungeonEventConfigManagerImpl();
  private static final DungeonEventConfigManagerImpl instanceImplB = new DungeonEventConfigManagerImpl();

  public static DungeonEventConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static DungeonEventConfigManagerImpl getStandby() {
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
    DungeonEventConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class DungeonEventConfigManagerImpl extends AbstractConfigManger {
    private List<DungeonEventConfig> configList = List.of();
    private Map<Integer, DungeonEventConfig> configMap = Map.of();

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
      DungeonEventConfigChecker checker = new DungeonEventConfigChecker();
      checker.checkHeader(logger, configDir);
      List<DungeonEventConfig> newList = new ArrayList<>();
      Map<Integer, DungeonEventConfig> newMap = new HashMap<>();
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
          int groupId = 0;
          int groupNum = 0;
          int type = 0;
          String eventName = null;
          int eventNameRes = 0;
          String dis = null;
          int baseRes = 0;
          int titleRes = 0;
          String contentRes = null;
          String para1 = null;
          String para2 = null;
          int para3 = 0;
          String showContent = null;
          int showType = 0;
          int baseResGray = 0;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 组编号
            if (!arr[1].trim().isEmpty()) {
              groupId = Integer.parseInt(arr[1].trim());
            }

            // 解析 组内难度顺序
            if (!arr[2].trim().isEmpty()) {
              groupNum = Integer.parseInt(arr[2].trim());
            }

            // 解析 类型
            if (!arr[3].trim().isEmpty()) {
              type = Integer.parseInt(arr[3].trim());
            }

            // 解析 事件名称
            if (!arr[4].trim().isEmpty()) {
              eventName = arr[4].trim();
            }

            // 解析 事件名称资源Id(目前不用)
            if (!arr[5].trim().isEmpty()) {
              eventNameRes = Integer.parseInt(arr[5].trim());
            }

            // 解析 描述
            if (!arr[6].trim().isEmpty()) {
              dis = arr[6].trim();
            }

            // 解析 事件点底图资源id
            if (!arr[7].trim().isEmpty()) {
              baseRes = Integer.parseInt(arr[7].trim());
            }

            // 解析 事件标题资源id
            if (!arr[8].trim().isEmpty()) {
              titleRes = Integer.parseInt(arr[8].trim());
            }

            // 解析 事件内容资源id
            if (!arr[9].trim().isEmpty()) {
              contentRes = arr[9].trim();
            }

            // 解析 参数1
            if (!arr[10].trim().isEmpty()) {
              para1 = arr[10].trim();
            }

            // 解析 参数2
            if (!arr[11].trim().isEmpty()) {
              para2 = arr[11].trim();
            }

            // 解析 参数3
            if (!arr[12].trim().isEmpty()) {
              para3 = Integer.parseInt(arr[12].trim());
            }

            // 解析 显示内容
            if (!arr[13].trim().isEmpty()) {
              showContent = arr[13].trim();
            }

            // 解析 显示类型
            if (!arr[14].trim().isEmpty()) {
              showType = Integer.parseInt(arr[14].trim());
            }

            // 解析 事件点底图资源灰度id
            if (!arr[15].trim().isEmpty()) {
              baseResGray = Integer.parseInt(arr[15].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          DungeonEventConfig config = new DungeonEventConfig(id, groupId, groupNum, type, eventName, eventNameRes, dis, baseRes, titleRes, contentRes, para1, para2, para3, showContent, showType, baseResGray);
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

    public List<DungeonEventConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, DungeonEventConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "dungeonEvent.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
