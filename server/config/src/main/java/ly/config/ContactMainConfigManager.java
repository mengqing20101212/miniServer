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
 * File: ContactMainConfigManager
 */
public class ContactMainConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final ContactMainConfigManager instance = new ContactMainConfigManager();
  private static final ContactMainConfigManagerImpl instanceImplA = new ContactMainConfigManagerImpl();
  private static final ContactMainConfigManagerImpl instanceImplB = new ContactMainConfigManagerImpl();

  public static ContactMainConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static ContactMainConfigManagerImpl getStandby() {
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
    ContactMainConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class ContactMainConfigManagerImpl extends AbstractConfigManger {
    private List<ContactMainConfig> configList = List.of();
    private Map<Integer, ContactMainConfig> configMap = Map.of();

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
      ContactMainConfigChecker checker = new ContactMainConfigChecker();
      checker.checkHeader(logger, configDir);
      List<ContactMainConfig> newList = new ArrayList<>();
      Map<Integer, ContactMainConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 13) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          String name = null;
          String englishName = null;
          String missionList = null;
          int resource = 0;
          int headIcon = 0;
          String missionShow = null;
          String rewardShow = null;
          int challengeTimes = 0;
          String weekend = null;
          int weekendChallenge = 0;
          int stamina = 0;
          String eventPool = null;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 名称
            if (!arr[1].trim().isEmpty()) {
              name = arr[1].trim();
            }

            // 解析 英文名字
            if (!arr[2].trim().isEmpty()) {
              englishName = arr[2].trim();
            }

            // 解析 解锁类型
            if (!arr[3].trim().isEmpty()) {
              missionList = arr[3].trim();
            }

            // 解析 立绘
            if (!arr[4].trim().isEmpty()) {
              resource = Integer.parseInt(arr[4].trim());
            }

            // 解析 头像
            if (!arr[5].trim().isEmpty()) {
              headIcon = Integer.parseInt(arr[5].trim());
            }

            // 解析 解锁条件展示
            if (!arr[6].trim().isEmpty()) {
              missionShow = arr[6].trim();
            }

            // 解析 奖励预览展示
            if (!arr[7].trim().isEmpty()) {
              rewardShow = arr[7].trim();
            }

            // 解析 每日挑战次数
            if (!arr[8].trim().isEmpty()) {
              challengeTimes = Integer.parseInt(arr[8].trim());
            }

            // 解析 休息日
            if (!arr[9].trim().isEmpty()) {
              weekend = arr[9].trim();
            }

            // 解析 休息挑战次数
            if (!arr[10].trim().isEmpty()) {
              weekendChallenge = Integer.parseInt(arr[10].trim());
            }

            // 解析 每次消耗体力
            if (!arr[11].trim().isEmpty()) {
              stamina = Integer.parseInt(arr[11].trim());
            }

            // 解析 事件池
            if (!arr[12].trim().isEmpty()) {
              eventPool = arr[12].trim();
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          ContactMainConfig config = new ContactMainConfig(id, name, englishName, missionList, resource, headIcon, missionShow, rewardShow, challengeTimes, weekend, weekendChallenge, stamina, eventPool);
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

    public List<ContactMainConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ContactMainConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "contactMain.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
