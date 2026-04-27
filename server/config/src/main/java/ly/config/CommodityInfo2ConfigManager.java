package ly.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import ly.utils.KV;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import ly.AbstractConfigManger;
import ly.ConfigLoadException;
import ly.InterfaceConfigManagerProxy;
import org.slf4j.Logger;

/*
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 * File: CommodityInfo2ConfigManager
 */
public class CommodityInfo2ConfigManager implements InterfaceConfigManagerProxy {
  AtomicBoolean switched = new AtomicBoolean(false);
  private static final CommodityInfo2ConfigManager instance = new CommodityInfo2ConfigManager();
  private static final CommodityInfo2ConfigManagerImpl instanceImplA =
      new CommodityInfo2ConfigManagerImpl();
  private static final CommodityInfo2ConfigManagerImpl instanceImplB =
      new CommodityInfo2ConfigManagerImpl();

  public boolean isSwitched() {
    return switched.get();
  }

  public static CommodityInfo2ConfigManagerImpl getInstance() {
    if (instance.isSwitched()) {
      return instanceImplA;
    } else {
      return instanceImplB;
    }
  }

  @Override
  public void loadConfig(Logger logger, String configDir) throws ConfigLoadException {
    getInstance().reload(logger, configDir);
  }

  public static class CommodityInfo2ConfigManagerImpl extends AbstractConfigManger {

    List<CommodityInfo2Config> configList = new ArrayList<CommodityInfo2Config>();
    Map<Integer, CommodityInfo2Config> configMap = new HashMap<Integer, CommodityInfo2Config>();


    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    protected void reload(Logger logger, String configDir) throws ConfigLoadException {
      String fileName = configDir + File.separator + getConfigFileName();
      File file = new File(fileName);
      clear();
      if (!file.exists()) {
        logger.error(fileName + " does not exist");
        throw new ConfigLoadException("Config file does not exist :" + fileName);
      }
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        br.readLine(); //先读取一行表头 
        while ((line = br.readLine()) != null) { // 按行读取
          String[] arr = line.split("\t");
          CommodityInfo2Config config = new CommodityInfo2Config();
          try {
            //解析 商品ID
            if (!arr[0].trim().isEmpty()) {
            config.id =  Integer.parseInt(arr[0].trim());
            }

            //解析 商品类型
            if (!arr[1].trim().isEmpty()) {
            config.type =  Integer.parseInt(arr[1].trim());
            }

            //解析 商品参数
            if (!arr[2].trim().isEmpty()) {
            config.typePara = arr[2].trim();
            }

            //解析 一级商店id
            if (!arr[3].trim().isEmpty()) {
            config.grade =  Integer.parseInt(arr[3].trim());
            }

            //解析 二级商店id
            if (!arr[4].trim().isEmpty()) {
            config.school =  Integer.parseInt(arr[4].trim());
            }

            //解析 商品组id
            if (!arr[5].trim().isEmpty()) {
            config.group =  Integer.parseInt(arr[5].trim());
            }

            //解析 序列id
            if (!arr[6].trim().isEmpty()) {
            config.sequence =  Integer.parseInt(arr[6].trim());
            }

            //解析 商品名称
            if (!arr[7].trim().isEmpty()) {
            config.name = arr[7].trim();
            }

            //解析 商品描述
            if (!arr[8].trim().isEmpty()) {
            config.des = arr[8].trim();
            }

            //解析 商品图标
            if (!arr[9].trim().isEmpty()) {
            config.icon =  Integer.parseInt(arr[9].trim());
            }

            //解析 道具列表
            if (!arr[10].trim().isEmpty()) {
            config.itemList = arr[10].trim();
            }

            //解析 限购类型
            if (!arr[11].trim().isEmpty()) {
            config.limitType =  Integer.parseInt(arr[11].trim());
            }

            //解析 限购参数
            if (!arr[12].trim().isEmpty()) {
            config.limitPara =  Integer.parseInt(arr[12].trim());
            }

            //解析 等级下限
            if (!arr[13].trim().isEmpty()) {
            config.levelLowerLimit =  Integer.parseInt(arr[13].trim());
            }

            //解析 等级上限
            if (!arr[14].trim().isEmpty()) {
            config.levelUpperLimit =  Integer.parseInt(arr[14].trim());
            }

            //解析 货币种类
            if (!arr[15].trim().isEmpty()) {
            config.moneyType =  Integer.parseInt(arr[15].trim());
            }

            //解析 价格
            if (!arr[16].trim().isEmpty()) {
            config.price =  Integer.parseInt(arr[16].trim());
            }

            //解析 显示价格
            if (!arr[17].trim().isEmpty()) {
            config.priceShow =  Integer.parseInt(arr[17].trim());
            }

            //解析 原价
            if (!arr[18].trim().isEmpty()) {
            config.PrePriceShow =  Integer.parseInt(arr[18].trim());
            }

            //解析 价格累进值
            if (!arr[19].trim().isEmpty()) {
            config.priceStepValue =  Integer.parseInt(arr[19].trim());
            }

            //解析 时间类型
            if (!arr[20].trim().isEmpty()) {
            config.timeType =  Integer.parseInt(arr[20].trim());
            }

            //解析 活动ID
            if (!arr[21].trim().isEmpty()) {
            config.activityID =  Integer.parseInt(arr[21].trim());
            }

            //解析 上架时间
            if (!arr[22].trim().isEmpty()) {
            config.startTime = arr[22].trim();
            }

            //解析 下架时间
            if (!arr[23].trim().isEmpty()) {
            config.endTime = arr[23].trim();
            }

            //解析 特殊时间
            if (!arr[24].trim().isEmpty()) {
            config.specialEndTime = arr[24].trim();
            }

            //解析 是否显示时间
            if (!arr[25].trim().isEmpty()) {
            config.isShowTime =  Integer.parseInt(arr[25].trim());
            }

            //解析 标语类型
            if (!arr[26].trim().isEmpty()) {
            config.sloganType = arr[26].trim();
            }

            //解析 上架提醒
            if (!arr[27].trim().isEmpty()) {
            config.startTimeTips =  Integer.parseInt(arr[27].trim());
            }

            //解析 上架提醒文字
            if (!arr[28].trim().isEmpty()) {
            config.startTimeWord = arr[28].trim();
            }

            //解析 下架提醒
            if (!arr[29].trim().isEmpty()) {
            config.endTimeTips =  Integer.parseInt(arr[29].trim());
            }

            //解析 商品快捷组id
            if (!arr[30].trim().isEmpty()) {
            config.groupId =  Integer.parseInt(arr[30].trim());
            }

            //解析 是否批量购买
            if (!arr[31].trim().isEmpty()) {
            config.batch =  Integer.parseInt(arr[31].trim());
            }

            //解析 商品ID
            if (!arr[32].trim().isEmpty()) {
            config.rechargeShopId =  Integer.parseInt(arr[32].trim());
            }

            //解析 商品ID
            if (!arr[33].trim().isEmpty()) {
            config.rechargeShopId1 =  Integer.parseInt(arr[33].trim());
            }

            //解析 返利标签
            if (!arr[34].trim().isEmpty()) {
            config.tabshow =  Integer.parseInt(arr[34].trim());
            }

            //解析 英雄可见性
            if (!arr[35].trim().isEmpty()) {
            config.herocondition =  Integer.parseInt(arr[35].trim());
            }

            //解析 充值可见性
            if (!arr[36].trim().isEmpty()) {
            config.rechargecondition =  Integer.parseInt(arr[36].trim());
            }

            //解析 首充赠送描述
            if (!arr[37].trim().isEmpty()) {
            config.firstCharge = arr[37].trim();
            }

            //解析 次充赠送描述
            if (!arr[38].trim().isEmpty()) {
            config.followupCharge = arr[38].trim();
            }

            //解析 是否自动开启
            if (!arr[39].trim().isEmpty()) {
            config.autoOpenGift =  Integer.parseInt(arr[39].trim());
            }

            //解析 额外展示列
            if (!arr[40].trim().isEmpty()) {
            config.extraShowItemId =  Integer.parseInt(arr[40].trim());
            }

            //解析 开服区间
            if (!arr[41].trim().isEmpty()) {
            config.OpenServiceActivity =  Integer.parseInt(arr[41].trim());
            }


          } catch (Exception e) {
            logger.error(
                String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, line, e.getMessage()));
            e.printStackTrace();
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          config.afterLoad();
          configList.add(config);
          configMap.put(config.id, config);
        }
        afterLoad();
      } catch (IOException e) {
        e.printStackTrace();
        throw new ConfigLoadException("Config file could not be read :" + fileName);
      }
    }

    @Override
    protected void clear() {

      configList.clear();
      configMap.clear();

      // @@@@@自定义clear方法开始区@@@@@


      // @@@@@自定义clear方法结束区@@@@@
    }

    private List<Integer> parseIntList(String value) {
      if (value == null || value.trim().isEmpty()) {
        return new ArrayList<>();
      }
      String[] parts = value.split(",");
      List<Integer> result = new ArrayList<>();
      for (String part : parts) {
        try {
          result.add(Integer.parseInt(part.trim()));
        } catch (NumberFormatException e) {
          // 如果不是数字，则跳过
        }
      }
      return result;
    }

    private List<KV<Integer, Integer>> parseIntKVList(String value) {
      if (value == null || value.trim().isEmpty()) {
        return new ArrayList<>();
      }
      List<KV<Integer, Integer>> result = new ArrayList<>();
      String[] pairs = value.split(",");
      for (String pair : pairs) {
        pair = pair.trim();
        if (!pair.isEmpty()) {
          int idx = pair.indexOf(":");
          if (idx > 0) {
            String keyStr = pair.substring(0, idx).trim();
            String valueStr = pair.substring(idx + 1).trim();
            try {
              Integer key = Integer.parseInt(keyStr);
              Integer val = Integer.parseInt(valueStr);
              result.add(new KV<>(key, val));
            } catch (NumberFormatException e) {
              // 如果不是数字，则跳过
            }
          }
        }
      }
      return result;
    }

    private List<KV<String, String>> parseStringKVList(String value) {
      if (value == null || value.trim().isEmpty()) {
        return new ArrayList<>();
      }
      List<KV<String, String>> result = new ArrayList<>();
      String[] pairs = value.split(",");
      for (String pair : pairs) {
        pair = pair.trim();
        if (!pair.isEmpty()) {
          int idx = pair.indexOf(":");
          if (idx > 0) {
            String keyStr = pair.substring(0, idx).trim();
            String valueStr = pair.substring(idx + 1).trim();
            result.add(new KV<>(keyStr, valueStr));
          }
        }
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
