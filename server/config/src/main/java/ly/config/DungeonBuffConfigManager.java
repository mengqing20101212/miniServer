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
 * File: DungeonBuffConfigManager
 */
public class DungeonBuffConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final DungeonBuffConfigManager instance = new DungeonBuffConfigManager();
  private static final DungeonBuffConfigManagerImpl instanceImplA = new DungeonBuffConfigManagerImpl();
  private static final DungeonBuffConfigManagerImpl instanceImplB = new DungeonBuffConfigManagerImpl();

  public static DungeonBuffConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static DungeonBuffConfigManagerImpl getStandby() {
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
    DungeonBuffConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class DungeonBuffConfigManagerImpl extends AbstractConfigManger {
    private List<DungeonBuffConfig> configList = List.of();
    private Map<Integer, DungeonBuffConfig> configMap = Map.of();

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
      DungeonBuffConfigChecker checker = new DungeonBuffConfigChecker();
      checker.checkHeader(logger, configDir);
      List<DungeonBuffConfig> newList = new ArrayList<>();
      Map<Integer, DungeonBuffConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 15) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          String name = null;
          int kind = 0;
          int group = 0;
          int level = 0;
          int turn = 0;
          int effectType = 0;
          int buffId = 0;
          String discribe = null;
          int icon = 0;
          int iconBig = 0;
          int floor = 0;
          int frame = 0;
          int heroType = 0;
          int assembly = 0;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 名称
            if (!arr[1].trim().isEmpty()) {
              name = arr[1].trim();
            }

            // 解析 品质
            if (!arr[2].trim().isEmpty()) {
              kind = Integer.parseInt(arr[2].trim());
            }

            // 解析 所属组
            if (!arr[3].trim().isEmpty()) {
              group = Integer.parseInt(arr[3].trim());
            }

            // 解析 buff等级
            if (!arr[4].trim().isEmpty()) {
              level = Integer.parseInt(arr[4].trim());
            }

            // 解析 持续回合
            if (!arr[5].trim().isEmpty()) {
              turn = Integer.parseInt(arr[5].trim());
            }

            // 解析 效果枚举
            if (!arr[6].trim().isEmpty()) {
              effectType = Integer.parseInt(arr[6].trim());
            }

            // 解析 实际buffId
            if (!arr[7].trim().isEmpty()) {
              buffId = Integer.parseInt(arr[7].trim());
            }

            // 解析 描述
            if (!arr[8].trim().isEmpty()) {
              discribe = arr[8].trim();
            }

            // 解析 buff图标
            if (!arr[9].trim().isEmpty()) {
              icon = Integer.parseInt(arr[9].trim());
            }

            // 解析 buff大图标
            if (!arr[10].trim().isEmpty()) {
              iconBig = Integer.parseInt(arr[10].trim());
            }

            // 解析 buff类型底板
            if (!arr[11].trim().isEmpty()) {
              floor = Integer.parseInt(arr[11].trim());
            }

            // 解析 buff类型边框
            if (!arr[12].trim().isEmpty()) {
              frame = Integer.parseInt(arr[12].trim());
            }

            // 解析 buff类型icon
            if (!arr[13].trim().isEmpty()) {
              heroType = Integer.parseInt(arr[13].trim());
            }

            // 解析 组件
            if (!arr[14].trim().isEmpty()) {
              assembly = Integer.parseInt(arr[14].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          DungeonBuffConfig config = new DungeonBuffConfig(id, name, kind, group, level, turn, effectType, buffId, discribe, icon, iconBig, floor, frame, heroType, assembly);
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

    public List<DungeonBuffConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, DungeonBuffConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "dungeonBuff.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
