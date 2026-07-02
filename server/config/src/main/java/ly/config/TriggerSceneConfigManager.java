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
 * File: TriggerSceneConfigManager
 */
public class TriggerSceneConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final TriggerSceneConfigManager instance = new TriggerSceneConfigManager();
  private static final TriggerSceneConfigManagerImpl instanceImplA = new TriggerSceneConfigManagerImpl();
  private static final TriggerSceneConfigManagerImpl instanceImplB = new TriggerSceneConfigManagerImpl();

  public static TriggerSceneConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static TriggerSceneConfigManagerImpl getStandby() {
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
    TriggerSceneConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class TriggerSceneConfigManagerImpl extends AbstractConfigManger {
    private List<TriggerSceneConfig> configList = List.of();
    private Map<Integer, TriggerSceneConfig> configMap = Map.of();

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
      TriggerSceneConfigChecker checker = new TriggerSceneConfigChecker();
      checker.checkHeader(logger, configDir);
      List<TriggerSceneConfig> newList = new ArrayList<>();
      Map<Integer, TriggerSceneConfig> newMap = new HashMap<>();
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
          int sceneId = 0;
          int group = 0;
          int type = 0;
          String stageName = null;
          String stageName2 = null;
          String stageName3 = null;
          int preCost = 0;
          int cost = 0;
          int weight = 0;
          int time = 0;
          int bossId = 0;
          int sceneAvatar = 0;
          String dropPro = null;
          String bossDes = null;
          int scenePic = 0;
          String posOffset = null;
          String scale = null;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 关卡索引
            if (!arr[1].trim().isEmpty()) {
              sceneId = Integer.parseInt(arr[1].trim());
            }

            // 解析 随机分组
            if (!arr[2].trim().isEmpty()) {
              group = Integer.parseInt(arr[2].trim());
            }

            // 解析 所属类别
            if (!arr[3].trim().isEmpty()) {
              type = Integer.parseInt(arr[3].trim());
            }

            // 解析 副本名称
            if (!arr[4].trim().isEmpty()) {
              stageName = arr[4].trim();
            }

            // 解析 副本名称
            if (!arr[5].trim().isEmpty()) {
              stageName2 = arr[5].trim();
            }

            // 解析 副本名称
            if (!arr[6].trim().isEmpty()) {
              stageName3 = arr[6].trim();
            }

            // 解析 预先体力消耗
            if (!arr[7].trim().isEmpty()) {
              preCost = Integer.parseInt(arr[7].trim());
            }

            // 解析 胜利后体力消耗
            if (!arr[8].trim().isEmpty()) {
              cost = Integer.parseInt(arr[8].trim());
            }

            // 解析 权重
            if (!arr[9].trim().isEmpty()) {
              weight = Integer.parseInt(arr[9].trim());
            }

            // 解析 出现时间
            if (!arr[10].trim().isEmpty()) {
              time = Integer.parseInt(arr[10].trim());
            }

            // 解析 bossID
            if (!arr[11].trim().isEmpty()) {
              bossId = Integer.parseInt(arr[11].trim());
            }

            // 解析 关卡头像
            if (!arr[12].trim().isEmpty()) {
              sceneAvatar = Integer.parseInt(arr[12].trim());
            }

            // 解析 掉落预览
            if (!arr[13].trim().isEmpty()) {
              dropPro = arr[13].trim();
            }

            // 解析 关卡说明
            if (!arr[14].trim().isEmpty()) {
              bossDes = arr[14].trim();
            }

            // 解析 场景图片id
            if (!arr[15].trim().isEmpty()) {
              scenePic = Integer.parseInt(arr[15].trim());
            }

            // 解析 位置偏移
            if (!arr[16].trim().isEmpty()) {
              posOffset = arr[16].trim();
            }

            // 解析 缩放
            if (!arr[17].trim().isEmpty()) {
              scale = arr[17].trim();
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          TriggerSceneConfig config = new TriggerSceneConfig(id, sceneId, group, type, stageName, stageName2, stageName3, preCost, cost, weight, time, bossId, sceneAvatar, dropPro, bossDes, scenePic, posOffset, scale);
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

    public List<TriggerSceneConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, TriggerSceneConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "triggerScene.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
