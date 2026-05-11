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
 * File: RechargeShopConfigManager
 */
public class RechargeShopConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final RechargeShopConfigManager instance = new RechargeShopConfigManager();
  private static final RechargeShopConfigManagerImpl instanceImplA = new RechargeShopConfigManagerImpl();
  private static final RechargeShopConfigManagerImpl instanceImplB = new RechargeShopConfigManagerImpl();

  public static RechargeShopConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static RechargeShopConfigManagerImpl getStandby() {
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
    RechargeShopConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class RechargeShopConfigManagerImpl extends AbstractConfigManger {
    private List<RechargeShopConfig> configList = List.of();
    private Map<Integer, RechargeShopConfig> configMap = Map.of();

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
      RechargeShopConfigChecker checker = new RechargeShopConfigChecker();
      checker.checkHeader(logger, configDir);
      List<RechargeShopConfig> newList = new ArrayList<>();
      Map<Integer, RechargeShopConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 28) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          String name = null;
          String bugFDesc = null;
          String bugDesc = null;
          int bugType = 0;
          int levelUpperLimit = 0;
          int drop = 0;
          int dropShow = 0;
          int reward = 0;
          String give = null;
          int priceType = 0;
          int price = 0;
          int priceShow = 0;
          int PrePriceShow = 0;
          String page = null;
          int page1 = 0;
          int priority = 0;
          int missionID = 0;
          int cardPower = 0;
          int limtType = 0;
          int limt = 0;
          String icon = null;
          int duration = 0;
          String closeTime = null;
          int giveCloseTime = 0;
          int mail = 0;
          int activityId = 0;
          int buySupermarket = 0;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 商品名称名称
            if (!arr[1].trim().isEmpty()) {
              name = arr[1].trim();
            }

            // 解析 购买说明
            if (!arr[2].trim().isEmpty()) {
              bugFDesc = arr[2].trim();
            }

            // 解析 正常购买说明
            if (!arr[3].trim().isEmpty()) {
              bugDesc = arr[3].trim();
            }

            // 解析 购买物品类型
            if (!arr[4].trim().isEmpty()) {
              bugType = Integer.parseInt(arr[4].trim());
            }

            // 解析 显示等级
            if (!arr[5].trim().isEmpty()) {
              levelUpperLimit = Integer.parseInt(arr[5].trim());
            }

            // 解析 礼包后端
            if (!arr[6].trim().isEmpty()) {
              drop = Integer.parseInt(arr[6].trim());
            }

            // 解析 礼包前端
            if (!arr[7].trim().isEmpty()) {
              dropShow = Integer.parseInt(arr[7].trim());
            }

            // 解析 购买获得彩钻
            if (!arr[8].trim().isEmpty()) {
              reward = Integer.parseInt(arr[8].trim());
            }

            // 解析 购买赠送
            if (!arr[9].trim().isEmpty()) {
              give = arr[9].trim();
            }

            // 解析 价格类型
            if (!arr[10].trim().isEmpty()) {
              priceType = Integer.parseInt(arr[10].trim());
            }

            // 解析 价格1
            if (!arr[11].trim().isEmpty()) {
              price = Integer.parseInt(arr[11].trim());
            }

            // 解析 显示价格
            if (!arr[12].trim().isEmpty()) {
              priceShow = Integer.parseInt(arr[12].trim());
            }

            // 解析 原价
            if (!arr[13].trim().isEmpty()) {
              PrePriceShow = Integer.parseInt(arr[13].trim());
            }

            // 解析 页签
            if (!arr[14].trim().isEmpty()) {
              page = arr[14].trim();
            }

            // 解析 子页签
            if (!arr[15].trim().isEmpty()) {
              page1 = Integer.parseInt(arr[15].trim());
            }

            // 解析 展示优先级
            if (!arr[16].trim().isEmpty()) {
              priority = Integer.parseInt(arr[16].trim());
            }

            // 解析 触发条件
            if (!arr[17].trim().isEmpty()) {
              missionID = Integer.parseInt(arr[17].trim());
            }

            // 解析 是否月卡权限
            if (!arr[18].trim().isEmpty()) {
              cardPower = Integer.parseInt(arr[18].trim());
            }

            // 解析 限购类型
            if (!arr[19].trim().isEmpty()) {
              limtType = Integer.parseInt(arr[19].trim());
            }

            // 解析 限购数量
            if (!arr[20].trim().isEmpty()) {
              limt = Integer.parseInt(arr[20].trim());
            }

            // 解析 图标
            if (!arr[21].trim().isEmpty()) {
              icon = arr[21].trim();
            }

            // 解析 持续时间
            if (!arr[22].trim().isEmpty()) {
              duration = Integer.parseInt(arr[22].trim());
            }

            // 解析 下架时间
            if (!arr[23].trim().isEmpty()) {
              closeTime = arr[23].trim();
            }

            // 解析 限时礼包过期时间
            if (!arr[24].trim().isEmpty()) {
              giveCloseTime = Integer.parseInt(arr[24].trim());
            }

            // 解析 邮件ID
            if (!arr[25].trim().isEmpty()) {
              mail = Integer.parseInt(arr[25].trim());
            }

            // 解析 活动ID
            if (!arr[26].trim().isEmpty()) {
              activityId = Integer.parseInt(arr[26].trim());
            }

            // 解析 是否超市购买
            if (!arr[27].trim().isEmpty()) {
              buySupermarket = Integer.parseInt(arr[27].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          RechargeShopConfig config = new RechargeShopConfig(id, name, bugFDesc, bugDesc, bugType, levelUpperLimit, drop, dropShow, reward, give, priceType, price, priceShow, PrePriceShow, page, page1, priority, missionID, cardPower, limtType, limt, icon, duration, closeTime, giveCloseTime, mail, activityId, buySupermarket);
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

    public List<RechargeShopConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, RechargeShopConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "RechargeShop.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
