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
 * File: HeroStarConfigManager
 */
public class HeroStarConfigManager implements InterfaceConfigManagerProxy {
  AtomicBoolean switched = new AtomicBoolean(false);
  private static final HeroStarConfigManager instance = new HeroStarConfigManager();
  private static final HeroStarConfigManagerImpl instanceImplA =
      new HeroStarConfigManagerImpl();
  private static final HeroStarConfigManagerImpl instanceImplB =
      new HeroStarConfigManagerImpl();

  public boolean isSwitched() {
    return switched.get();
  }

  public static HeroStarConfigManagerImpl getInstance() {
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

  public static class HeroStarConfigManagerImpl extends AbstractConfigManger {

    List<HeroStarConfig> configList = new ArrayList<HeroStarConfig>();

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
          HeroStarConfig config = new HeroStarConfig();
          try {
            //解析 星级
            if (!arr[0].trim().isEmpty()) {
            config.star =  Integer.parseInt(arr[0].trim());
            }

            //解析 模板id
            if (!arr[1].trim().isEmpty()) {
            config.modelId =  Integer.parseInt(arr[1].trim());
            }

            //解析 等级上限
            if (!arr[2].trim().isEmpty()) {
            config.maxLevel =  Integer.parseInt(arr[2].trim());
            }

            //解析 觉醒上限
            if (!arr[3].trim().isEmpty()) {
            config.awakenLevel =  Integer.parseInt(arr[3].trim());
            }

            //解析 升星道具1
            if (!arr[4].trim().isEmpty()) {
            config.starItem = arr[4].trim();
            }

            //解析 升星道具2
            if (!arr[5].trim().isEmpty()) {
            config.starItem2 = arr[5].trim();
            }

            //解析 分解返还道具
            if (!arr[6].trim().isEmpty()) {
            config.retainItem = arr[6].trim();
            }

            //解析 分解消耗货币类型
            if (!arr[7].trim().isEmpty()) {
            config.currencyType =  Integer.parseInt(arr[7].trim());
            }

            //解析 分解消耗货币数量
            if (!arr[8].trim().isEmpty()) {
            config.currencyNum = arr[8].trim();
            }

            //解析 分解消耗物品
            if (!arr[9].trim().isEmpty()) {
            config.item = arr[9].trim();
            }

            //解析 源核对应星级锁
            if (!arr[10].trim().isEmpty()) {
            config.circuitSlot = arr[10].trim();
            }

            //解析 是否显示后续觉醒
            if (!arr[11].trim().isEmpty()) {
            config.followAwaken =  Integer.parseInt(arr[11].trim());
            }


          } catch (Exception e) {
            logger.error(
                String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, line, e.getMessage()));
            e.printStackTrace();
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          config.afterLoad();
          configList.add(config);
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

    public List<HeroStarConfig> getConfigList() {
      return configList;
    }


    @Override
    public String getConfigFileName() {
      return "heroStar.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {}

    /**
     * 根据模板ID和星级获取配置
     */
    public HeroStarConfig getByModelIdAndStar(int modelId, int star) {
      for (HeroStarConfig config : configList) {
        if (config.modelId == modelId && config.star == star) {
          return config;
        }
      }
      return null;
    }

    // @@@@@自定义方法结束区@@@@@
  }
}
