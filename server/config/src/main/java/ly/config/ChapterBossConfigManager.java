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
 * File: ChapterBossConfigManager
 */
public class ChapterBossConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final ChapterBossConfigManager instance = new ChapterBossConfigManager();
  private static final ChapterBossConfigManagerImpl instanceImplA = new ChapterBossConfigManagerImpl();
  private static final ChapterBossConfigManagerImpl instanceImplB = new ChapterBossConfigManagerImpl();

  public static ChapterBossConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static ChapterBossConfigManagerImpl getStandby() {
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
    ChapterBossConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class ChapterBossConfigManagerImpl extends AbstractConfigManger {
    private List<ChapterBossConfig> configList = List.of();
    private Map<Integer, ChapterBossConfig> configMap = Map.of();

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
      ChapterBossConfigChecker checker = new ChapterBossConfigChecker();
      checker.checkHeader(logger, configDir);
      List<ChapterBossConfig> newList = new ArrayList<>();
      Map<Integer, ChapterBossConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 11) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          int starNum = 0;
          int type = 0;
          String dropShow = null;
          int drop = 0;
          int sceneId = 0;
          String npcId = null;
          int modelId = 0;
          int resourceNpc = 0;
          int resourceIcon = 0;
          int iconNum = 0;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 星星数量
            if (!arr[1].trim().isEmpty()) {
              starNum = Integer.parseInt(arr[1].trim());
            }

            // 解析 奖励类型
            if (!arr[2].trim().isEmpty()) {
              type = Integer.parseInt(arr[2].trim());
            }

            // 解析 奖励展示
            if (!arr[3].trim().isEmpty()) {
              dropShow = arr[3].trim();
            }

            // 解析 实际掉落
            if (!arr[4].trim().isEmpty()) {
              drop = Integer.parseInt(arr[4].trim());
            }

            // 解析 关卡id
            if (!arr[5].trim().isEmpty()) {
              sceneId = Integer.parseInt(arr[5].trim());
            }

            // 解析 怪物id
            if (!arr[6].trim().isEmpty()) {
              npcId = arr[6].trim();
            }

            // 解析 模型id
            if (!arr[7].trim().isEmpty()) {
              modelId = Integer.parseInt(arr[7].trim());
            }

            // 解析 外显怪物半身像
            if (!arr[8].trim().isEmpty()) {
              resourceNpc = Integer.parseInt(arr[8].trim());
            }

            // 解析 外显奖励图标
            if (!arr[9].trim().isEmpty()) {
              resourceIcon = Integer.parseInt(arr[9].trim());
            }

            // 解析 外显奖励数量
            if (!arr[10].trim().isEmpty()) {
              iconNum = Integer.parseInt(arr[10].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          ChapterBossConfig config = new ChapterBossConfig(id, starNum, type, dropShow, drop, sceneId, npcId, modelId, resourceNpc, resourceIcon, iconNum);
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

    public List<ChapterBossConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ChapterBossConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "chapterBoss.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
