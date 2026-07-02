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
 * File: SocietyQuestConfigManager
 */
public class SocietyQuestConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final SocietyQuestConfigManager instance = new SocietyQuestConfigManager();
  private static final SocietyQuestConfigManagerImpl instanceImplA = new SocietyQuestConfigManagerImpl();
  private static final SocietyQuestConfigManagerImpl instanceImplB = new SocietyQuestConfigManagerImpl();

  public static SocietyQuestConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static SocietyQuestConfigManagerImpl getStandby() {
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
    SocietyQuestConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class SocietyQuestConfigManagerImpl extends AbstractConfigManger {
    private List<SocietyQuestConfig> configList = List.of();
    private Map<Integer, SocietyQuestConfig> configMap = Map.of();

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
      SocietyQuestConfigChecker checker = new SocietyQuestConfigChecker();
      checker.checkHeader(logger, configDir);
      List<SocietyQuestConfig> newList = new ArrayList<>();
      Map<Integer, SocietyQuestConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 23) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          int questId = 0;
          int questId2 = 0;
          int minLevel = 0;
          int maxLevel = 0;
          String title = null;
          String name = null;
          String name2 = null;
          int star = 0;
          int rewardType = 0;
          String beizhu1 = null;
          int weights = 0;
          int isCooperate = 0;
          int isRare = 0;
          String rewardShow = null;
          int drop = 0;
          String beizhu2 = null;
          String beizhu3 = null;
          String beizhu4 = null;
          String beizhu5 = null;
          int redirectionId = 0;
          int redirectionId2 = 0;
          int goundId = 0;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 索引编号
            if (!arr[1].trim().isEmpty()) {
              questId = Integer.parseInt(arr[1].trim());
            }

            // 解析 索引编号2
            if (!arr[2].trim().isEmpty()) {
              questId2 = Integer.parseInt(arr[2].trim());
            }

            // 解析 最小等级
            if (!arr[3].trim().isEmpty()) {
              minLevel = Integer.parseInt(arr[3].trim());
            }

            // 解析 最大等级
            if (!arr[4].trim().isEmpty()) {
              maxLevel = Integer.parseInt(arr[4].trim());
            }

            // 解析 任务标题
            if (!arr[5].trim().isEmpty()) {
              title = arr[5].trim();
            }

            // 解析 任务名称
            if (!arr[6].trim().isEmpty()) {
              name = arr[6].trim();
            }

            // 解析 任务名称2
            if (!arr[7].trim().isEmpty()) {
              name2 = arr[7].trim();
            }

            // 解析 任务星级
            if (!arr[8].trim().isEmpty()) {
              star = Integer.parseInt(arr[8].trim());
            }

            // 解析 奖励类型
            if (!arr[9].trim().isEmpty()) {
              rewardType = Integer.parseInt(arr[9].trim());
            }

            // 解析 物品名称
            if (!arr[10].trim().isEmpty()) {
              beizhu1 = arr[10].trim();
            }

            // 解析 任务权重
            if (!arr[11].trim().isEmpty()) {
              weights = Integer.parseInt(arr[11].trim());
            }

            // 解析 协作标记
            if (!arr[12].trim().isEmpty()) {
              isCooperate = Integer.parseInt(arr[12].trim());
            }

            // 解析 稀有标记
            if (!arr[13].trim().isEmpty()) {
              isRare = Integer.parseInt(arr[13].trim());
            }

            // 解析 奖励展示
            if (!arr[14].trim().isEmpty()) {
              rewardShow = arr[14].trim();
            }

            // 解析 实际掉落
            if (!arr[15].trim().isEmpty()) {
              drop = Integer.parseInt(arr[15].trim());
            }

            // 解析 beizhu2
            if (!arr[16].trim().isEmpty()) {
              beizhu2 = arr[16].trim();
            }

            // 解析 beizhu3
            if (!arr[17].trim().isEmpty()) {
              beizhu3 = arr[17].trim();
            }

            // 解析 beizhu4
            if (!arr[18].trim().isEmpty()) {
              beizhu4 = arr[18].trim();
            }

            // 解析 beizhu5
            if (!arr[19].trim().isEmpty()) {
              beizhu5 = arr[19].trim();
            }

            // 解析 跳转
            if (!arr[20].trim().isEmpty()) {
              redirectionId = Integer.parseInt(arr[20].trim());
            }

            // 解析 跳转
            if (!arr[21].trim().isEmpty()) {
              redirectionId2 = Integer.parseInt(arr[21].trim());
            }

            // 解析 组ID
            if (!arr[22].trim().isEmpty()) {
              goundId = Integer.parseInt(arr[22].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          SocietyQuestConfig config = new SocietyQuestConfig(id, questId, questId2, minLevel, maxLevel, title, name, name2, star, rewardType, beizhu1, weights, isCooperate, isRare, rewardShow, drop, beizhu2, beizhu3, beizhu4, beizhu5, redirectionId, redirectionId2, goundId);
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

    public List<SocietyQuestConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SocietyQuestConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "societyQuest.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
