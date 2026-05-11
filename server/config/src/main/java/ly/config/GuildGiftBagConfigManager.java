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
 * File: GuildGiftBagConfigManager
 */
public class GuildGiftBagConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final GuildGiftBagConfigManager instance = new GuildGiftBagConfigManager();
  private static final GuildGiftBagConfigManagerImpl instanceImplA = new GuildGiftBagConfigManagerImpl();
  private static final GuildGiftBagConfigManagerImpl instanceImplB = new GuildGiftBagConfigManagerImpl();

  public static GuildGiftBagConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static GuildGiftBagConfigManagerImpl getStandby() {
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
    GuildGiftBagConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class GuildGiftBagConfigManagerImpl extends AbstractConfigManger {
    private List<GuildGiftBagConfig> configList = List.of();
    private Map<Integer, GuildGiftBagConfig> configMap = Map.of();

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
      GuildGiftBagConfigChecker checker = new GuildGiftBagConfigChecker();
      checker.checkHeader(logger, configDir);
      List<GuildGiftBagConfig> newList = new ArrayList<>();
      Map<Integer, GuildGiftBagConfig> newMap = new HashMap<>();
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
          int type = 0;
          String name = null;
          int drop = 0;
          int cost = 0;
          int reward = 0;
          int Num = 0;
          String heroId = null;
          int active = 0;
          int recharge = 0;
          int giftIcon = 0;
          int tag = 0;
          String grading = null;
          try {
            // 解析 ID
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 礼包类型
            if (!arr[1].trim().isEmpty()) {
              type = Integer.parseInt(arr[1].trim());
            }

            // 解析 名字
            if (!arr[2].trim().isEmpty()) {
              name = arr[2].trim();
            }

            // 解析 掉落
            if (!arr[3].trim().isEmpty()) {
              drop = Integer.parseInt(arr[3].trim());
            }

            // 解析 单次消耗钻石
            if (!arr[4].trim().isEmpty()) {
              cost = Integer.parseInt(arr[4].trim());
            }

            // 解析 单次奖励声望
            if (!arr[5].trim().isEmpty()) {
              reward = Integer.parseInt(arr[5].trim());
            }

            // 解析 领取人数
            if (!arr[6].trim().isEmpty()) {
              Num = Integer.parseInt(arr[6].trim());
            }

            // 解析 英雄ID
            if (!arr[7].trim().isEmpty()) {
              heroId = arr[7].trim();
            }

            // 解析 活跃度
            if (!arr[8].trim().isEmpty()) {
              active = Integer.parseInt(arr[8].trim());
            }

            // 解析 充值金额
            if (!arr[9].trim().isEmpty()) {
              recharge = Integer.parseInt(arr[9].trim());
            }

            // 解析 礼包图标
            if (!arr[10].trim().isEmpty()) {
              giftIcon = Integer.parseInt(arr[10].trim());
            }

            // 解析 标签
            if (!arr[11].trim().isEmpty()) {
              tag = Integer.parseInt(arr[11].trim());
            }

            // 解析 红包分档名称显示
            if (!arr[12].trim().isEmpty()) {
              grading = arr[12].trim();
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          GuildGiftBagConfig config = new GuildGiftBagConfig(id, type, name, drop, cost, reward, Num, heroId, active, recharge, giftIcon, tag, grading);
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

    public List<GuildGiftBagConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, GuildGiftBagConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "guildGiftBag.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
