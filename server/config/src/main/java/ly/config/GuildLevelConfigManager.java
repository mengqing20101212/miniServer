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
 * File: GuildLevelConfigManager
 */
public class GuildLevelConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final GuildLevelConfigManager instance = new GuildLevelConfigManager();
  private static final GuildLevelConfigManagerImpl instanceImplA = new GuildLevelConfigManagerImpl();
  private static final GuildLevelConfigManagerImpl instanceImplB = new GuildLevelConfigManagerImpl();

  public static GuildLevelConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static GuildLevelConfigManagerImpl getStandby() {
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
    GuildLevelConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class GuildLevelConfigManagerImpl extends AbstractConfigManger {
    private List<GuildLevelConfig> configList = List.of();
    private Map<Integer, GuildLevelConfig> configMap = Map.of();

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
      GuildLevelConfigChecker checker = new GuildLevelConfigChecker();
      checker.checkHeader(logger, configDir);
      List<GuildLevelConfig> newList = new ArrayList<>();
      Map<Integer, GuildLevelConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 22) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          int level = 0;
          int fundsCost = 0;
          int donateNum = 0;
          String Contribution = null;
          int contributionMax = 0;
          int maintainCost = 0;
          int maintainCostUrgent = 0;
          int numberLimit = 0;
          String officialLimit = null;
          String functionList = null;
          int wealShopLv = 0;
          int prestigeShopLV = 0;
          int badgeMax = 0;
          int dailyAffordableNum = 0;
          int dailyLimitNum = 0;
          int weeklyAffordableNum = 0;
          int weeklyLimitNum = 0;
          int refreshShopNum = 0;
          int refreshShopPositionNum = 0;
          int refreshShopBuyNum = 0;
          String giftBagNum = null;
          try {
            // 解析 ID
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 社团等级
            if (!arr[1].trim().isEmpty()) {
              level = Integer.parseInt(arr[1].trim());
            }

            // 解析 升级经验
            if (!arr[2].trim().isEmpty()) {
              fundsCost = Integer.parseInt(arr[2].trim());
            }

            // 解析 捐献次数
            if (!arr[3].trim().isEmpty()) {
              donateNum = Integer.parseInt(arr[3].trim());
            }

            // 解析 捐献获得的贡献
            if (!arr[4].trim().isEmpty()) {
              Contribution = arr[4].trim();
            }

            // 解析 建设度最大值
            if (!arr[5].trim().isEmpty()) {
              contributionMax = Integer.parseInt(arr[5].trim());
            }

            // 解析 维护费用
            if (!arr[6].trim().isEmpty()) {
              maintainCost = Integer.parseInt(arr[6].trim());
            }

            // 解析 紧急维护费用
            if (!arr[7].trim().isEmpty()) {
              maintainCostUrgent = Integer.parseInt(arr[7].trim());
            }

            // 解析 公会人数上限
            if (!arr[8].trim().isEmpty()) {
              numberLimit = Integer.parseInt(arr[8].trim());
            }

            // 解析 官员人数上限
            if (!arr[9].trim().isEmpty()) {
              officialLimit = arr[9].trim();
            }

            // 解析 功能开启列表
            if (!arr[10].trim().isEmpty()) {
              functionList = arr[10].trim();
            }

            // 解析 福利商店等级
            if (!arr[11].trim().isEmpty()) {
              wealShopLv = Integer.parseInt(arr[11].trim());
            }

            // 解析 声望商店等级
            if (!arr[12].trim().isEmpty()) {
              prestigeShopLV = Integer.parseInt(arr[12].trim());
            }

            // 解析 社团资金上限
            if (!arr[13].trim().isEmpty()) {
              badgeMax = Integer.parseInt(arr[13].trim());
            }

            // 解析 每日超值礼包个数
            if (!arr[14].trim().isEmpty()) {
              dailyAffordableNum = Integer.parseInt(arr[14].trim());
            }

            // 解析 每日超值礼包限购次数
            if (!arr[15].trim().isEmpty()) {
              dailyLimitNum = Integer.parseInt(arr[15].trim());
            }

            // 解析 每周超值礼包个数
            if (!arr[16].trim().isEmpty()) {
              weeklyAffordableNum = Integer.parseInt(arr[16].trim());
            }

            // 解析 每周超值礼包限购次数
            if (!arr[17].trim().isEmpty()) {
              weeklyLimitNum = Integer.parseInt(arr[17].trim());
            }

            // 解析 宇宙商店刷新次数
            if (!arr[18].trim().isEmpty()) {
              refreshShopNum = Integer.parseInt(arr[18].trim());
            }

            // 解析 宇宙商店格子数量
            if (!arr[19].trim().isEmpty()) {
              refreshShopPositionNum = Integer.parseInt(arr[19].trim());
            }

            // 解析 宇宙商店特殊物品购买人次
            if (!arr[20].trim().isEmpty()) {
              refreshShopBuyNum = Integer.parseInt(arr[20].trim());
            }

            // 解析 社团红包次数
            if (!arr[21].trim().isEmpty()) {
              giftBagNum = arr[21].trim();
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          GuildLevelConfig config = new GuildLevelConfig(id, level, fundsCost, donateNum, Contribution, contributionMax, maintainCost, maintainCostUrgent, numberLimit, officialLimit, functionList, wealShopLv, prestigeShopLV, badgeMax, dailyAffordableNum, dailyLimitNum, weeklyAffordableNum, weeklyLimitNum, refreshShopNum, refreshShopPositionNum, refreshShopBuyNum, giftBagNum);
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

    public List<GuildLevelConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, GuildLevelConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "guildLevel.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
