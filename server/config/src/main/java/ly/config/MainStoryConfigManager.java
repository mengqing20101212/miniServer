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
 * File: MainStoryConfigManager
 */
public class MainStoryConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final MainStoryConfigManager instance = new MainStoryConfigManager();
  private static final MainStoryConfigManagerImpl instanceImplA = new MainStoryConfigManagerImpl();
  private static final MainStoryConfigManagerImpl instanceImplB = new MainStoryConfigManagerImpl();

  public static MainStoryConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static MainStoryConfigManagerImpl getStandby() {
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
    MainStoryConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class MainStoryConfigManagerImpl extends AbstractConfigManger {
    private List<MainStoryConfig> configList = List.of();
    private Map<Integer, MainStoryConfig> configMap = Map.of();

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
      MainStoryConfigChecker checker = new MainStoryConfigChecker();
      checker.checkHeader(logger, configDir);
      List<MainStoryConfig> newList = new ArrayList<>();
      Map<Integer, MainStoryConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 17) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          String name = null;
          String titile = null;
          String target = null;
          String eventIds = null;
          int chapterRes = 0;
          int chapterNum = 0;
          int chapterId = 0;
          int requireLv = 0;
          int preIds = 0;
          int followID = 0;
          int dropGroupId = 0;
          String dropshow = null;
          String lockWord = null;
          int pic = 0;
          int showId = 0;
          int pic2 = 0;
          try {
            // 解析 ID（任务唯一id）
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 任务名称
            if (!arr[1].trim().isEmpty()) {
              name = arr[1].trim();
            }

            // 解析 任务栏处名称
            if (!arr[2].trim().isEmpty()) {
              titile = arr[2].trim();
            }

            // 解析 任务栏目标
            if (!arr[3].trim().isEmpty()) {
              target = arr[3].trim();
            }

            // 解析 事件ID（多个用,隔开）
            if (!arr[4].trim().isEmpty()) {
              eventIds = arr[4].trim();
            }

            // 解析 章节名称图片资源
            if (!arr[5].trim().isEmpty()) {
              chapterRes = Integer.parseInt(arr[5].trim());
            }

            // 解析 章节数字
            if (!arr[6].trim().isEmpty()) {
              chapterNum = Integer.parseInt(arr[6].trim());
            }

            // 解析 通关全息调查
            if (!arr[7].trim().isEmpty()) {
              chapterId = Integer.parseInt(arr[7].trim());
            }

            // 解析 解锁等级
            if (!arr[8].trim().isEmpty()) {
              requireLv = Integer.parseInt(arr[8].trim());
            }

            // 解析 前续任务ID
            if (!arr[9].trim().isEmpty()) {
              preIds = Integer.parseInt(arr[9].trim());
            }

            // 解析 后续任务ID
            if (!arr[10].trim().isEmpty()) {
              followID = Integer.parseInt(arr[10].trim());
            }

            // 解析 掉落
            if (!arr[11].trim().isEmpty()) {
              dropGroupId = Integer.parseInt(arr[11].trim());
            }

            // 解析 掉落展示
            if (!arr[12].trim().isEmpty()) {
              dropshow = arr[12].trim();
            }

            // 解析 未解锁提示
            if (!arr[13].trim().isEmpty()) {
              lockWord = arr[13].trim();
            }

            // 解析 图片
            if (!arr[14].trim().isEmpty()) {
              pic = Integer.parseInt(arr[14].trim());
            }

            // 解析 关联showID
            if (!arr[15].trim().isEmpty()) {
              showId = Integer.parseInt(arr[15].trim());
            }

            // 解析 图片2
            if (!arr[16].trim().isEmpty()) {
              pic2 = Integer.parseInt(arr[16].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          MainStoryConfig config = new MainStoryConfig(id, name, titile, target, eventIds, chapterRes, chapterNum, chapterId, requireLv, preIds, followID, dropGroupId, dropshow, lockWord, pic, showId, pic2);
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

    public List<MainStoryConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, MainStoryConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "mainStory.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
