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
 * File: ExtractConfigManager
 */
public class ExtractConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final ExtractConfigManager instance = new ExtractConfigManager();
  private static final ExtractConfigManagerImpl instanceImplA = new ExtractConfigManagerImpl();
  private static final ExtractConfigManagerImpl instanceImplB = new ExtractConfigManagerImpl();

  public static ExtractConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static ExtractConfigManagerImpl getStandby() {
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
    ExtractConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class ExtractConfigManagerImpl extends AbstractConfigManger {
    private List<ExtractConfig> configList = List.of();
    private Map<Integer, ExtractConfig> configMap = Map.of();

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
      ExtractConfigChecker checker = new ExtractConfigChecker();
      checker.checkHeader(logger, configDir);
      List<ExtractConfig> newList = new ArrayList<>();
      Map<Integer, ExtractConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 24) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          String name = null;
          int level = 0;
          String ranking = null;
          int rankNum = 0;
          String city = null;
          String lines = null;
          int random = 0;
          int bodyPic = 0;
          String features = null;
          String timeline = null;
          int bg = 0;
          String englishName = null;
          int smallPicResId = 0;
          String smallPicPosOffset = null;
          String smallPicRotationOffset = null;
          String smallPicScale = null;
          int bodyQualityBgResId = 0;
          int nameQualityBgResId = 0;
          int isRepeatPlay = 0;
          int backgroundId = 0;
          int shareAnimation = 0;
          String sharePicScale = null;
          String sharePicPosOffset = null;
          try {
            // 解析 英雄
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 备注
            if (!arr[1].trim().isEmpty()) {
              name = arr[1].trim();
            }

            // 解析 等级
            if (!arr[2].trim().isEmpty()) {
              level = Integer.parseInt(arr[2].trim());
            }

            // 解析 排行
            if (!arr[3].trim().isEmpty()) {
              ranking = arr[3].trim();
            }

            // 解析 排名
            if (!arr[4].trim().isEmpty()) {
              rankNum = Integer.parseInt(arr[4].trim());
            }

            // 解析 城市
            if (!arr[5].trim().isEmpty()) {
              city = arr[5].trim();
            }

            // 解析 宣言
            if (!arr[6].trim().isEmpty()) {
              lines = arr[6].trim();
            }

            // 解析 随机展示
            if (!arr[7].trim().isEmpty()) {
              random = Integer.parseInt(arr[7].trim());
            }

            // 解析 角色半身像
            if (!arr[8].trim().isEmpty()) {
              bodyPic = Integer.parseInt(arr[8].trim());
            }

            // 解析 简短的描述
            if (!arr[9].trim().isEmpty()) {
              features = arr[9].trim();
            }

            // 解析 人物招募动作
            if (!arr[10].trim().isEmpty()) {
              timeline = arr[10].trim();
            }

            // 解析 招募动画背景
            if (!arr[11].trim().isEmpty()) {
              bg = Integer.parseInt(arr[11].trim());
            }

            // 解析 英文名
            if (!arr[12].trim().isEmpty()) {
              englishName = arr[12].trim();
            }

            // 解析 角色小图片资源Id(目前不用)
            if (!arr[13].trim().isEmpty()) {
              smallPicResId = Integer.parseInt(arr[13].trim());
            }

            // 解析 招募英雄位置
            if (!arr[14].trim().isEmpty()) {
              smallPicPosOffset = arr[14].trim();
            }

            // 解析 招募英雄角度
            if (!arr[15].trim().isEmpty()) {
              smallPicRotationOffset = arr[15].trim();
            }

            // 解析 招募英雄缩放
            if (!arr[16].trim().isEmpty()) {
              smallPicScale = arr[16].trim();
            }

            // 解析 英雄品质底框
            if (!arr[17].trim().isEmpty()) {
              bodyQualityBgResId = Integer.parseInt(arr[17].trim());
            }

            // 解析 名字底框
            if (!arr[18].trim().isEmpty()) {
              nameQualityBgResId = Integer.parseInt(arr[18].trim());
            }

            // 解析 重复播放英雄展示动画
            if (!arr[19].trim().isEmpty()) {
              isRepeatPlay = Integer.parseInt(arr[19].trim());
            }

            // 解析 英雄模型背景预设
            if (!arr[20].trim().isEmpty()) {
              backgroundId = Integer.parseInt(arr[20].trim());
            }

            // 解析 分享使用立绘
            if (!arr[21].trim().isEmpty()) {
              shareAnimation = Integer.parseInt(arr[21].trim());
            }

            // 解析 分享立绘缩放
            if (!arr[22].trim().isEmpty()) {
              sharePicScale = arr[22].trim();
            }

            // 解析 分享立绘位置
            if (!arr[23].trim().isEmpty()) {
              sharePicPosOffset = arr[23].trim();
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          ExtractConfig config = new ExtractConfig(id, name, level, ranking, rankNum, city, lines, random, bodyPic, features, timeline, bg, englishName, smallPicResId, smallPicPosOffset, smallPicRotationOffset, smallPicScale, bodyQualityBgResId, nameQualityBgResId, isRepeatPlay, backgroundId, shareAnimation, sharePicScale, sharePicPosOffset);
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

    public List<ExtractConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ExtractConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "extract.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
