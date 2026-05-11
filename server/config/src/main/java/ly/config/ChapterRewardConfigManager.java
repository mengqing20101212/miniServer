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
 * File: ChapterRewardConfigManager
 */
public class ChapterRewardConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final ChapterRewardConfigManager instance = new ChapterRewardConfigManager();
  private static final ChapterRewardConfigManagerImpl instanceImplA = new ChapterRewardConfigManagerImpl();
  private static final ChapterRewardConfigManagerImpl instanceImplB = new ChapterRewardConfigManagerImpl();

  public static ChapterRewardConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static ChapterRewardConfigManagerImpl getStandby() {
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
    ChapterRewardConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class ChapterRewardConfigManagerImpl extends AbstractConfigManger {
    private List<ChapterRewardConfig> configList = List.of();
    private Map<Integer, ChapterRewardConfig> configMap = Map.of();

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
      ChapterRewardConfigChecker checker = new ChapterRewardConfigChecker();
      checker.checkHeader(logger, configDir);
      List<ChapterRewardConfig> newList = new ArrayList<>();
      Map<Integer, ChapterRewardConfig> newMap = new HashMap<>();
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
          int StageId = 0;
          int type = 0;
          String rewardShow = null;
          int drop = 0;
          String word1 = null;
          String word2 = null;
          String outGradientColor = null;
          String word3 = null;
          String word4 = null;
          String inGradientColor = null;
          String word5 = null;
          int icon = 0;
          int popUp = 0;
          int popUpUIIcon = 0;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 关卡ID
            if (!arr[1].trim().isEmpty()) {
              StageId = Integer.parseInt(arr[1].trim());
            }

            // 解析 奖励类型
            if (!arr[2].trim().isEmpty()) {
              type = Integer.parseInt(arr[2].trim());
            }

            // 解析 奖励展示
            if (!arr[3].trim().isEmpty()) {
              rewardShow = arr[3].trim();
            }

            // 解析 实际掉落
            if (!arr[4].trim().isEmpty()) {
              drop = Integer.parseInt(arr[4].trim());
            }

            // 解析 外显文字1
            if (!arr[5].trim().isEmpty()) {
              word1 = arr[5].trim();
            }

            // 解析 外显文字2
            if (!arr[6].trim().isEmpty()) {
              word2 = arr[6].trim();
            }

            // 解析 外显渐变颜色(,)
            if (!arr[7].trim().isEmpty()) {
              outGradientColor = arr[7].trim();
            }

            // 解析 内显文字1
            if (!arr[8].trim().isEmpty()) {
              word3 = arr[8].trim();
            }

            // 解析 内显文字2
            if (!arr[9].trim().isEmpty()) {
              word4 = arr[9].trim();
            }

            // 解析 内显渐变颜色(,)
            if (!arr[10].trim().isEmpty()) {
              inGradientColor = arr[10].trim();
            }

            // 解析 内显文字3
            if (!arr[11].trim().isEmpty()) {
              word5 = arr[11].trim();
            }

            // 解析 图片显示 atlas
            if (!arr[12].trim().isEmpty()) {
              icon = Integer.parseInt(arr[12].trim());
            }

            // 解析 不可领取时是否弹出
            if (!arr[13].trim().isEmpty()) {
              popUp = Integer.parseInt(arr[13].trim());
            }

            // 解析 弹出界面中的图片显示(texture)
            if (!arr[14].trim().isEmpty()) {
              popUpUIIcon = Integer.parseInt(arr[14].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          ChapterRewardConfig config = new ChapterRewardConfig(id, StageId, type, rewardShow, drop, word1, word2, outGradientColor, word3, word4, inGradientColor, word5, icon, popUp, popUpUIIcon);
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

    public List<ChapterRewardConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ChapterRewardConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "chapterReward.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
