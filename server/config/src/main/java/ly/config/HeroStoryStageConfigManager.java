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
 * File: HeroStoryStageConfigManager
 */
public class HeroStoryStageConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final HeroStoryStageConfigManager instance = new HeroStoryStageConfigManager();
  private static final HeroStoryStageConfigManagerImpl instanceImplA = new HeroStoryStageConfigManagerImpl();
  private static final HeroStoryStageConfigManagerImpl instanceImplB = new HeroStoryStageConfigManagerImpl();

  public static HeroStoryStageConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static HeroStoryStageConfigManagerImpl getStandby() {
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
    HeroStoryStageConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class HeroStoryStageConfigManagerImpl extends AbstractConfigManger {
    private List<HeroStoryStageConfig> configList = List.of();
    private Map<Integer, HeroStoryStageConfig> configMap = Map.of();

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
      HeroStoryStageConfigChecker checker = new HeroStoryStageConfigChecker();
      checker.checkHeader(logger, configDir);
      List<HeroStoryStageConfig> newList = new ArrayList<>();
      Map<Integer, HeroStoryStageConfig> newMap = new HashMap<>();
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
          String stageName = null;
          int groupId = 0;
          int nextId = 0;
          int lastId = 0;
          int sceneId = 0;
          int statusBonus = 0;
          int dropShow = 0;
          int storyBanner = 0;
          String storyWord = null;
          String mechanism = null;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 名称
            if (!arr[1].trim().isEmpty()) {
              stageName = arr[1].trim();
            }

            // 解析 组ID
            if (!arr[2].trim().isEmpty()) {
              groupId = Integer.parseInt(arr[2].trim());
            }

            // 解析 下一关id
            if (!arr[3].trim().isEmpty()) {
              nextId = Integer.parseInt(arr[3].trim());
            }

            // 解析 上一关id
            if (!arr[4].trim().isEmpty()) {
              lastId = Integer.parseInt(arr[4].trim());
            }

            // 解析 实际关卡ID
            if (!arr[5].trim().isEmpty()) {
              sceneId = Integer.parseInt(arr[5].trim());
            }

            // 解析 属性加成
            if (!arr[6].trim().isEmpty()) {
              statusBonus = Integer.parseInt(arr[6].trim());
            }

            // 解析 奖励预览
            if (!arr[7].trim().isEmpty()) {
              dropShow = Integer.parseInt(arr[7].trim());
            }

            // 解析 章节图片
            if (!arr[8].trim().isEmpty()) {
              storyBanner = Integer.parseInt(arr[8].trim());
            }

            // 解析 章节名称
            if (!arr[9].trim().isEmpty()) {
              storyWord = arr[9].trim();
            }

            // 解析 关卡详情
            if (!arr[10].trim().isEmpty()) {
              mechanism = arr[10].trim();
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          HeroStoryStageConfig config = new HeroStoryStageConfig(id, stageName, groupId, nextId, lastId, sceneId, statusBonus, dropShow, storyBanner, storyWord, mechanism);
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

    public List<HeroStoryStageConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, HeroStoryStageConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "heroStoryStage.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
