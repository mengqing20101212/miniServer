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
 * File: CommodityInfo2ConfigManager
 */
public class CommodityInfo2ConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final CommodityInfo2ConfigManager instance = new CommodityInfo2ConfigManager();
  private static final CommodityInfo2ConfigManagerImpl instanceImplA = new CommodityInfo2ConfigManagerImpl();
  private static final CommodityInfo2ConfigManagerImpl instanceImplB = new CommodityInfo2ConfigManagerImpl();

  public static CommodityInfo2ConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static CommodityInfo2ConfigManagerImpl getStandby() {
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
    CommodityInfo2ConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class CommodityInfo2ConfigManagerImpl extends AbstractConfigManger {
    private List<CommodityInfo2Config> configList = List.of();
    private Map<Integer, CommodityInfo2Config> configMap = Map.of();

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
      CommodityInfo2ConfigChecker checker = new CommodityInfo2ConfigChecker();
      checker.checkHeader(logger, configDir);
      List<CommodityInfo2Config> newList = new ArrayList<>();
      Map<Integer, CommodityInfo2Config> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 42) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          int type = 0;
          String typePara = null;
          int grade = 0;
          int school = 0;
          int group = 0;
          int sequence = 0;
          String name = null;
          String des = null;
          int icon = 0;
          String itemList = null;
          int limitType = 0;
          int limitPara = 0;
          int levelLowerLimit = 0;
          int levelUpperLimit = 0;
          int moneyType = 0;
          int price = 0;
          int priceShow = 0;
          int PrePriceShow = 0;
          int priceStepValue = 0;
          int timeType = 0;
          int activityID = 0;
          String startTime = null;
          String endTime = null;
          String specialEndTime = null;
          int isShowTime = 0;
          String sloganType = null;
          int startTimeTips = 0;
          String startTimeWord = null;
          int endTimeTips = 0;
          int groupId = 0;
          int batch = 0;
          int rechargeShopId = 0;
          int rechargeShopId1 = 0;
          int tabshow = 0;
          int herocondition = 0;
          int rechargecondition = 0;
          String firstCharge = null;
          String followupCharge = null;
          int autoOpenGift = 0;
          int extraShowItemId = 0;
          int OpenServiceActivity = 0;
          try {
            // 解析 商品ID
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 商品类型
            if (!arr[1].trim().isEmpty()) {
              type = Integer.parseInt(arr[1].trim());
            }

            // 解析 商品参数
            if (!arr[2].trim().isEmpty()) {
              typePara = arr[2].trim();
            }

            // 解析 一级商店id
            if (!arr[3].trim().isEmpty()) {
              grade = Integer.parseInt(arr[3].trim());
            }

            // 解析 二级商店id
            if (!arr[4].trim().isEmpty()) {
              school = Integer.parseInt(arr[4].trim());
            }

            // 解析 商品组id
            if (!arr[5].trim().isEmpty()) {
              group = Integer.parseInt(arr[5].trim());
            }

            // 解析 序列id
            if (!arr[6].trim().isEmpty()) {
              sequence = Integer.parseInt(arr[6].trim());
            }

            // 解析 商品名称
            if (!arr[7].trim().isEmpty()) {
              name = arr[7].trim();
            }

            // 解析 商品描述
            if (!arr[8].trim().isEmpty()) {
              des = arr[8].trim();
            }

            // 解析 商品图标
            if (!arr[9].trim().isEmpty()) {
              icon = Integer.parseInt(arr[9].trim());
            }

            // 解析 道具列表
            if (!arr[10].trim().isEmpty()) {
              itemList = arr[10].trim();
            }

            // 解析 限购类型
            if (!arr[11].trim().isEmpty()) {
              limitType = Integer.parseInt(arr[11].trim());
            }

            // 解析 限购参数
            if (!arr[12].trim().isEmpty()) {
              limitPara = Integer.parseInt(arr[12].trim());
            }

            // 解析 等级下限
            if (!arr[13].trim().isEmpty()) {
              levelLowerLimit = Integer.parseInt(arr[13].trim());
            }

            // 解析 等级上限
            if (!arr[14].trim().isEmpty()) {
              levelUpperLimit = Integer.parseInt(arr[14].trim());
            }

            // 解析 货币种类
            if (!arr[15].trim().isEmpty()) {
              moneyType = Integer.parseInt(arr[15].trim());
            }

            // 解析 价格
            if (!arr[16].trim().isEmpty()) {
              price = Integer.parseInt(arr[16].trim());
            }

            // 解析 显示价格
            if (!arr[17].trim().isEmpty()) {
              priceShow = Integer.parseInt(arr[17].trim());
            }

            // 解析 原价
            if (!arr[18].trim().isEmpty()) {
              PrePriceShow = Integer.parseInt(arr[18].trim());
            }

            // 解析 价格累进值
            if (!arr[19].trim().isEmpty()) {
              priceStepValue = Integer.parseInt(arr[19].trim());
            }

            // 解析 时间类型
            if (!arr[20].trim().isEmpty()) {
              timeType = Integer.parseInt(arr[20].trim());
            }

            // 解析 活动ID
            if (!arr[21].trim().isEmpty()) {
              activityID = Integer.parseInt(arr[21].trim());
            }

            // 解析 上架时间
            if (!arr[22].trim().isEmpty()) {
              startTime = arr[22].trim();
            }

            // 解析 下架时间
            if (!arr[23].trim().isEmpty()) {
              endTime = arr[23].trim();
            }

            // 解析 特殊时间
            if (!arr[24].trim().isEmpty()) {
              specialEndTime = arr[24].trim();
            }

            // 解析 是否显示时间
            if (!arr[25].trim().isEmpty()) {
              isShowTime = Integer.parseInt(arr[25].trim());
            }

            // 解析 标语类型
            if (!arr[26].trim().isEmpty()) {
              sloganType = arr[26].trim();
            }

            // 解析 上架提醒
            if (!arr[27].trim().isEmpty()) {
              startTimeTips = Integer.parseInt(arr[27].trim());
            }

            // 解析 上架提醒文字
            if (!arr[28].trim().isEmpty()) {
              startTimeWord = arr[28].trim();
            }

            // 解析 下架提醒
            if (!arr[29].trim().isEmpty()) {
              endTimeTips = Integer.parseInt(arr[29].trim());
            }

            // 解析 商品快捷组id
            if (!arr[30].trim().isEmpty()) {
              groupId = Integer.parseInt(arr[30].trim());
            }

            // 解析 是否批量购买
            if (!arr[31].trim().isEmpty()) {
              batch = Integer.parseInt(arr[31].trim());
            }

            // 解析 商品ID
            if (!arr[32].trim().isEmpty()) {
              rechargeShopId = Integer.parseInt(arr[32].trim());
            }

            // 解析 商品ID
            if (!arr[33].trim().isEmpty()) {
              rechargeShopId1 = Integer.parseInt(arr[33].trim());
            }

            // 解析 返利标签
            if (!arr[34].trim().isEmpty()) {
              tabshow = Integer.parseInt(arr[34].trim());
            }

            // 解析 英雄可见性
            if (!arr[35].trim().isEmpty()) {
              herocondition = Integer.parseInt(arr[35].trim());
            }

            // 解析 充值可见性
            if (!arr[36].trim().isEmpty()) {
              rechargecondition = Integer.parseInt(arr[36].trim());
            }

            // 解析 首充赠送描述
            if (!arr[37].trim().isEmpty()) {
              firstCharge = arr[37].trim();
            }

            // 解析 次充赠送描述
            if (!arr[38].trim().isEmpty()) {
              followupCharge = arr[38].trim();
            }

            // 解析 是否自动开启
            if (!arr[39].trim().isEmpty()) {
              autoOpenGift = Integer.parseInt(arr[39].trim());
            }

            // 解析 额外展示列
            if (!arr[40].trim().isEmpty()) {
              extraShowItemId = Integer.parseInt(arr[40].trim());
            }

            // 解析 开服区间
            if (!arr[41].trim().isEmpty()) {
              OpenServiceActivity = Integer.parseInt(arr[41].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          CommodityInfo2Config config = new CommodityInfo2Config(id, type, typePara, grade, school, group, sequence, name, des, icon, itemList, limitType, limitPara, levelLowerLimit, levelUpperLimit, moneyType, price, priceShow, PrePriceShow, priceStepValue, timeType, activityID, startTime, endTime, specialEndTime, isShowTime, sloganType, startTimeTips, startTimeWord, endTimeTips, groupId, batch, rechargeShopId, rechargeShopId1, tabshow, herocondition, rechargecondition, firstCharge, followupCharge, autoOpenGift, extraShowItemId, OpenServiceActivity);
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

    public List<CommodityInfo2Config> getConfigList() {
      return configList;
    }

    public Map<Integer, CommodityInfo2Config> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "commodityInfo2.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
