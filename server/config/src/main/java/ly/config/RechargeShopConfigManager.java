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
 * File: RechargeShopConfigManager
 */
public class RechargeShopConfigManager implements InterfaceConfigManagerProxy {
  AtomicBoolean switched = new AtomicBoolean(false);
  private static final RechargeShopConfigManager instance = new RechargeShopConfigManager();
  private static final RechargeShopConfigManagerImpl instanceImplA =
      new RechargeShopConfigManagerImpl();
  private static final RechargeShopConfigManagerImpl instanceImplB =
      new RechargeShopConfigManagerImpl();

  public boolean isSwitched() {
    return switched.get();
  }

  public static RechargeShopConfigManagerImpl getInstance() {
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

  public static class RechargeShopConfigManagerImpl extends AbstractConfigManger {

    List<RechargeShopConfig> configList = new ArrayList<RechargeShopConfig>();
    Map<Integer, RechargeShopConfig> configMap = new HashMap<Integer, RechargeShopConfig>();


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
          RechargeShopConfig config = new RechargeShopConfig();
          try {
            //解析 编号
            if (!arr[0].trim().isEmpty()) {
            config.id =  Integer.parseInt(arr[0].trim());
            }

            //解析 商品名称名称
            if (!arr[1].trim().isEmpty()) {
            config.name = arr[1].trim();
            }

            //解析 购买说明
            if (!arr[2].trim().isEmpty()) {
            config.bugFDesc = arr[2].trim();
            }

            //解析 正常购买说明
            if (!arr[3].trim().isEmpty()) {
            config.bugDesc = arr[3].trim();
            }

            //解析 购买物品类型
            if (!arr[4].trim().isEmpty()) {
            config.bugType =  Integer.parseInt(arr[4].trim());
            }

            //解析 显示等级
            if (!arr[5].trim().isEmpty()) {
            config.levelUpperLimit =  Integer.parseInt(arr[5].trim());
            }

            //解析 礼包后端
            if (!arr[6].trim().isEmpty()) {
            config.drop =  Integer.parseInt(arr[6].trim());
            }

            //解析 礼包前端
            if (!arr[7].trim().isEmpty()) {
            config.dropShow =  Integer.parseInt(arr[7].trim());
            }

            //解析 购买获得彩钻
            if (!arr[8].trim().isEmpty()) {
            config.reward =  Integer.parseInt(arr[8].trim());
            }

            //解析 购买赠送
            if (!arr[9].trim().isEmpty()) {
            config.give = arr[9].trim();
            }

            //解析 价格类型
            if (!arr[10].trim().isEmpty()) {
            config.priceType =  Integer.parseInt(arr[10].trim());
            }

            //解析 价格1
            if (!arr[11].trim().isEmpty()) {
            config.price =  Integer.parseInt(arr[11].trim());
            }

            //解析 显示价格
            if (!arr[12].trim().isEmpty()) {
            config.priceShow =  Integer.parseInt(arr[12].trim());
            }

            //解析 原价
            if (!arr[13].trim().isEmpty()) {
            config.PrePriceShow =  Integer.parseInt(arr[13].trim());
            }

            //解析 页签
            if (!arr[14].trim().isEmpty()) {
            config.page = arr[14].trim();
            }

            //解析 子页签
            if (!arr[15].trim().isEmpty()) {
            config.page1 =  Integer.parseInt(arr[15].trim());
            }

            //解析 展示优先级
            if (!arr[16].trim().isEmpty()) {
            config.priority =  Integer.parseInt(arr[16].trim());
            }

            //解析 触发条件
            if (!arr[17].trim().isEmpty()) {
            config.missionID =  Integer.parseInt(arr[17].trim());
            }

            //解析 是否月卡权限
            if (!arr[18].trim().isEmpty()) {
            config.cardPower =  Integer.parseInt(arr[18].trim());
            }

            //解析 限购类型
            if (!arr[19].trim().isEmpty()) {
            config.limtType =  Integer.parseInt(arr[19].trim());
            }

            //解析 限购数量
            if (!arr[20].trim().isEmpty()) {
            config.limt =  Integer.parseInt(arr[20].trim());
            }

            //解析 图标
            if (!arr[21].trim().isEmpty()) {
            config.icon = arr[21].trim();
            }

            //解析 持续时间
            if (!arr[22].trim().isEmpty()) {
            config.duration =  Integer.parseInt(arr[22].trim());
            }

            //解析 下架时间
            if (!arr[23].trim().isEmpty()) {
            config.closeTime = arr[23].trim();
            }

            //解析 限时礼包过期时间
            if (!arr[24].trim().isEmpty()) {
            config.giveCloseTime =  Integer.parseInt(arr[24].trim());
            }

            //解析 邮件ID
            if (!arr[25].trim().isEmpty()) {
            config.mail =  Integer.parseInt(arr[25].trim());
            }

            //解析 活动ID
            if (!arr[26].trim().isEmpty()) {
            config.activityId =  Integer.parseInt(arr[26].trim());
            }

            //解析 是否超市购买
            if (!arr[27].trim().isEmpty()) {
            config.buySupermarket =  Integer.parseInt(arr[27].trim());
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
