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
 * File: BattleFieldConfigManager
 */
public class BattleFieldConfigManager implements InterfaceConfigManagerProxy {
  AtomicBoolean switched = new AtomicBoolean(false);
  private static final BattleFieldConfigManager instance = new BattleFieldConfigManager();
  private static final BattleFieldConfigManagerImpl instanceImplA =
      new BattleFieldConfigManagerImpl();
  private static final BattleFieldConfigManagerImpl instanceImplB =
      new BattleFieldConfigManagerImpl();

  public boolean isSwitched() {
    return switched.get();
  }

  public static BattleFieldConfigManagerImpl getInstance() {
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

  public static class BattleFieldConfigManagerImpl extends AbstractConfigManger {

    List<BattleFieldConfig> configList = new ArrayList<BattleFieldConfig>();
    Map<Integer, BattleFieldConfig> configMap = new HashMap<Integer, BattleFieldConfig>();


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
          BattleFieldConfig config = new BattleFieldConfig();
          try {
            //解析 编号
            if (!arr[0].trim().isEmpty()) {
            config.id =  Integer.parseInt(arr[0].trim());
            }

            //解析 我方点位List
            if (!arr[1].trim().isEmpty()) {
            config.posListRed = arr[1].trim();
            }

            //解析 敌方点位List
            if (!arr[2].trim().isEmpty()) {
            config.posListBlue = arr[2].trim();
            }

            //解析 我方全体前
            if (!arr[3].trim().isEmpty()) {
            config.frontAllRed = arr[3].trim();
            }

            //解析 敌方全体前
            if (!arr[4].trim().isEmpty()) {
            config.frontAllBlue = arr[4].trim();
            }

            //解析 我方战场中心
            if (!arr[5].trim().isEmpty()) {
            config.BFCenterRed = arr[5].trim();
            }

            //解析 敌方战场中心
            if (!arr[6].trim().isEmpty()) {
            config.BFCenterBlue = arr[6].trim();
            }

            //解析 战场中心
            if (!arr[7].trim().isEmpty()) {
            config.BFCenter = arr[7].trim();
            }

            //解析 选择目标镜头list我方
            if (!arr[8].trim().isEmpty()) {
            config.cameraListRed = arr[8].trim();
            }

            //解析 选择目标镜头list敌方
            if (!arr[9].trim().isEmpty()) {
            config.cameraListBlue = arr[9].trim();
            }

            //解析 我方点位List
            if (!arr[10].trim().isEmpty()) {
            config.posListRedSummon = arr[10].trim();
            }

            //解析 敌方点位List
            if (!arr[11].trim().isEmpty()) {
            config.posListBlueSummon = arr[11].trim();
            }

            //解析 场景镜头配置
            if (!arr[12].trim().isEmpty()) {
            config.sceneConfig = arr[12].trim();
            }

            //解析 场景配置敌方
            if (!arr[13].trim().isEmpty()) {
            config.sceneConfigEnemy = arr[13].trim();
            }

            //解析 我方站位连线类型
            if (!arr[14].trim().isEmpty()) {
            config.lineupType =  Integer.parseInt(arr[14].trim());
            }

            //解析 敌方站位连线类型
            if (!arr[15].trim().isEmpty()) {
            config.lineupTypeEnemy =  Integer.parseInt(arr[15].trim());
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

    public List<BattleFieldConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, BattleFieldConfig> getConfigMap() {
      return configMap;
    }
    @Override
    public String getConfigFileName() {
      return "battleField.txt";
    }

    // @@@@@自定义方法开始区@@@@@
    @Override
    protected void afterLoad() {

    }

    // @@@@@自定义方法结束区@@@@@
  }
}
