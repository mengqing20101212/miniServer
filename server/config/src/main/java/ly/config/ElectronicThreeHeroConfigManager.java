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
 * File: ElectronicThreeHeroConfigManager
 */
public class ElectronicThreeHeroConfigManager implements InterfaceConfigManagerProxy {
  AtomicBoolean switched = new AtomicBoolean(false);
  private static final ElectronicThreeHeroConfigManager instance = new ElectronicThreeHeroConfigManager();
  private static final ElectronicThreeHeroConfigManagerImpl instanceImplA =
      new ElectronicThreeHeroConfigManagerImpl();
  private static final ElectronicThreeHeroConfigManagerImpl instanceImplB =
      new ElectronicThreeHeroConfigManagerImpl();

  public boolean isSwitched() {
    return switched.get();
  }

  public static ElectronicThreeHeroConfigManagerImpl getInstance() {
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

  public static class ElectronicThreeHeroConfigManagerImpl extends AbstractConfigManger {

    List<ElectronicThreeHeroConfig> configList = new ArrayList<ElectronicThreeHeroConfig>();
    Map<Integer, ElectronicThreeHeroConfig> configMap = new HashMap<Integer, ElectronicThreeHeroConfig>();


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
          ElectronicThreeHeroConfig config = new ElectronicThreeHeroConfig();
          try {
            //解析 编号
            if (!arr[0].trim().isEmpty()) {
            config.id =  Integer.parseInt(arr[0].trim());
            }

            //解析 英雄id
            if (!arr[1].trim().isEmpty()) {
            config.heroId =  Integer.parseInt(arr[1].trim());
            }

            //解析 英雄特性描述
            if (!arr[2].trim().isEmpty()) {
            config.heroDesc = arr[2].trim();
            }

            //解析 权重
            if (!arr[3].trim().isEmpty()) {
            config.pro =  Integer.parseInt(arr[3].trim());
            }

            //解析 组
            if (!arr[4].trim().isEmpty()) {
            config.group =  Integer.parseInt(arr[4].trim());
            }

            //解析 1号位置源核属性
            if (!arr[5].trim().isEmpty()) {
            config.circuitAttr1 = arr[5].trim();
            }

            //解析 2号位置源核
            if (!arr[6].trim().isEmpty()) {
            config.circuit2 =  Integer.parseInt(arr[6].trim());
            }

            //解析 2号位置源核属性
            if (!arr[7].trim().isEmpty()) {
            config.circuitAttr2 = arr[7].trim();
            }

            //解析 3号位置源核
            if (!arr[8].trim().isEmpty()) {
            config.circuit3 =  Integer.parseInt(arr[8].trim());
            }

            //解析 3号位置源核属性
            if (!arr[9].trim().isEmpty()) {
            config.circuitAttr3 = arr[9].trim();
            }

            //解析 4号位置源核
            if (!arr[10].trim().isEmpty()) {
            config.circuit4 =  Integer.parseInt(arr[10].trim());
            }

            //解析 4号位置源核属性
            if (!arr[11].trim().isEmpty()) {
            config.circuitAttr4 = arr[11].trim();
            }

            //解析 5号位置源核
            if (!arr[12].trim().isEmpty()) {
            config.circuit5 =  Integer.parseInt(arr[12].trim());
            }

            //解析 5号位置源核属性
            if (!arr[13].trim().isEmpty()) {
            config.circuitAttr5 = arr[13].trim();
            }

            //解析 6号位置源核
            if (!arr[14].trim().isEmpty()) {
            config.circuit6 =  Integer.parseInt(arr[14].trim());
            }

            //解析 6号位置源核属性
            if (!arr[15].trim().isEmpty()) {
            config.circuitAttr6 = arr[15].trim();
            }

            //解析 7号位置源核
            if (!arr[16].trim().isEmpty()) {
            config.circuit7 =  Integer.parseInt(arr[16].trim());
            }

            //解析 7号位置源核属性
            if (!arr[17].trim().isEmpty()) {
            config.circuitAttr7 = arr[17].trim();
            }

            //解析 生命
            if (!arr[18].trim().isEmpty()) {
            config.maxHP =  Integer.parseInt(arr[18].trim());
            }

            //解析 攻击
            if (!arr[19].trim().isEmpty()) {
            config.attack =  Integer.parseInt(arr[19].trim());
            }

            //解析 防御
            if (!arr[20].trim().isEmpty()) {
            config.defence =  Integer.parseInt(arr[20].trim());
            }

            //解析 速度
            if (!arr[21].trim().isEmpty()) {
            config.speed =  Integer.parseInt(arr[21].trim());
            }

            //解析 暴击
            if (!arr[22].trim().isEmpty()) {
            config.crit =  Integer.parseInt(arr[22].trim());
            }

            //解析 暴伤
            if (!arr[23].trim().isEmpty()) {
            config.critRatio =  Integer.parseInt(arr[23].trim());
            }

            //解析 命中
            if (!arr[24].trim().isEmpty()) {
            config.effectHit =  Integer.parseInt(arr[24].trim());
            }

            //解析 抵抗
            if (!arr[25].trim().isEmpty()) {
            config.effectDodge =  Integer.parseInt(arr[25].trim());
            }

            //解析 回能
            if (!arr[26].trim().isEmpty()) {
            config.spCoe =  Integer.parseInt(arr[26].trim());
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

    public List<ElectronicThreeHeroConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ElectronicThreeHeroConfig> getConfigMap() {
      return configMap;
    }
    @Override
    public String getConfigFileName() {
      return "electronicThreeHero.txt";
    }

    // @@@@@自定义方法开始区@@@@@
    @Override
    protected void afterLoad() {

    }

    // @@@@@自定义方法结束区@@@@@
  }
}
