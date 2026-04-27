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
 * File: SocietyPurchaseConfigManager
 */
public class SocietyPurchaseConfigManager implements InterfaceConfigManagerProxy {
  AtomicBoolean switched = new AtomicBoolean(false);
  private static final SocietyPurchaseConfigManager instance = new SocietyPurchaseConfigManager();
  private static final SocietyPurchaseConfigManagerImpl instanceImplA =
      new SocietyPurchaseConfigManagerImpl();
  private static final SocietyPurchaseConfigManagerImpl instanceImplB =
      new SocietyPurchaseConfigManagerImpl();

  public boolean isSwitched() {
    return switched.get();
  }

  public static SocietyPurchaseConfigManagerImpl getInstance() {
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

  public static class SocietyPurchaseConfigManagerImpl extends AbstractConfigManger {

    List<SocietyPurchaseConfig> configList = new ArrayList<SocietyPurchaseConfig>();
    Map<Integer, SocietyPurchaseConfig> configMap = new HashMap<Integer, SocietyPurchaseConfig>();


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
          SocietyPurchaseConfig config = new SocietyPurchaseConfig();
          try {
            //解析 编号
            if (!arr[0].trim().isEmpty()) {
            config.id =  Integer.parseInt(arr[0].trim());
            }

            //解析 等级段
            if (!arr[1].trim().isEmpty()) {
            config.phase =  Integer.parseInt(arr[1].trim());
            }

            //解析 次序
            if (!arr[2].trim().isEmpty()) {
            config.sequence =  Integer.parseInt(arr[2].trim());
            }

            //解析 最小等级
            if (!arr[3].trim().isEmpty()) {
            config.minLevel =  Integer.parseInt(arr[3].trim());
            }

            //解析 最大等级
            if (!arr[4].trim().isEmpty()) {
            config.maxLevel =  Integer.parseInt(arr[4].trim());
            }

            //解析 稀有道具标记
            if (!arr[5].trim().isEmpty()) {
            config.isRare =  Integer.parseInt(arr[5].trim());
            }

            //解析 需求道具
            if (!arr[6].trim().isEmpty()) {
            config.demandProps =  Integer.parseInt(arr[6].trim());
            }

            //解析 道具名称
            if (!arr[7].trim().isEmpty()) {
            config.beizhu1 = arr[7].trim();
            }

            //解析 需求数量
            if (!arr[8].trim().isEmpty()) {
            config.demandNum =  Integer.parseInt(arr[8].trim());
            }

            //解析 每个道具奖励
            if (!arr[9].trim().isEmpty()) {
            config.eachReward = arr[9].trim();
            }

            //解析 协助额外奖励
            if (!arr[10].trim().isEmpty()) {
            config.additionalReward = arr[10].trim();
            }

            //解析 随机权重
            if (!arr[11].trim().isEmpty()) {
            config.weights =  Integer.parseInt(arr[11].trim());
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

    public List<SocietyPurchaseConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SocietyPurchaseConfig> getConfigMap() {
      return configMap;
    }
    @Override
    public String getConfigFileName() {
      return "societyPurchase.txt";
    }

    // @@@@@自定义方法开始区@@@@@
    @Override
    protected void afterLoad() {

    }

    // @@@@@自定义方法结束区@@@@@
  }
}
