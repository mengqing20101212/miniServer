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
 * File: AlertContractConfigManager
 */
public class AlertContractConfigManager implements InterfaceConfigManagerProxy {
  AtomicBoolean switched = new AtomicBoolean(false);
  private static final AlertContractConfigManager instance = new AlertContractConfigManager();
  private static final AlertContractConfigManagerImpl instanceImplA =
      new AlertContractConfigManagerImpl();
  private static final AlertContractConfigManagerImpl instanceImplB =
      new AlertContractConfigManagerImpl();

  public boolean isSwitched() {
    return switched.get();
  }

  public static AlertContractConfigManagerImpl getInstance() {
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

  public static class AlertContractConfigManagerImpl extends AbstractConfigManger {

    List<AlertContractConfig> configList = new ArrayList<AlertContractConfig>();
    Map<Integer, AlertContractConfig> configMap = new HashMap<Integer, AlertContractConfig>();


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
          AlertContractConfig config = new AlertContractConfig();
          try {
            //解析 关卡组id
            if (!arr[0].trim().isEmpty()) {
            config.id =  Integer.parseInt(arr[0].trim());
            }

            //解析 层级
            if (!arr[1].trim().isEmpty()) {
            config.level =  Integer.parseInt(arr[1].trim());
            }

            //解析 敌人数值信息
            if (!arr[2].trim().isEmpty()) {
            config.enemyInfo = arr[2].trim();
            }

            //解析 敌人源核信息
            if (!arr[3].trim().isEmpty()) {
            config.enemyCircuitInfo = arr[3].trim();
            }

            //解析 敌人系数
            if (!arr[4].trim().isEmpty()) {
            config.enemyPara = arr[4].trim();
            }

            //解析 实际关卡id
            if (!arr[5].trim().isEmpty()) {
            config.sceneId = arr[5].trim();
            }

            //解析 奖励id
            if (!arr[6].trim().isEmpty()) {
            config.dropId =  Integer.parseInt(arr[6].trim());
            }

            //解析 首通奖励
            if (!arr[7].trim().isEmpty()) {
            config.firstDrop =  Integer.parseInt(arr[7].trim());
            }

            //解析 奖励展示
            if (!arr[8].trim().isEmpty()) {
            config.dropShow = arr[8].trim();
            }

            //解析 关卡提示
            if (!arr[9].trim().isEmpty()) {
            config.hint = arr[9].trim();
            }

            //解析 扫荡图片背景图片
            if (!arr[10].trim().isEmpty()) {
            config.tipPic =  Integer.parseInt(arr[10].trim());
            }

            //解析 背景图片（主界面）
            if (!arr[11].trim().isEmpty()) {
            config.background =  Integer.parseInt(arr[11].trim());
            }

            //解析 背景图片（关卡内布阵和敌人）
            if (!arr[12].trim().isEmpty()) {
            config.backgroundIn =  Integer.parseInt(arr[12].trim());
            }

            //解析 英雄立绘资源id
            if (!arr[13].trim().isEmpty()) {
            config.heroPicId =  Integer.parseInt(arr[13].trim());
            }

            //解析 推荐英雄Id组(,)
            if (!arr[14].trim().isEmpty()) {
            config.recommendHeroIds = arr[14].trim();
            }

            //解析 推荐类型显示组(1辅 2 群  3单  4控)
            if (!arr[15].trim().isEmpty()) {
            config.recommendTypes = arr[15].trim();
            }

            //解析 推荐阵容平均等级
            if (!arr[16].trim().isEmpty()) {
            config.avgLineupLevel =  Integer.parseInt(arr[16].trim());
            }

            //解析 每日奖励
            if (!arr[17].trim().isEmpty()) {
            config.dropDay =  Integer.parseInt(arr[17].trim());
            }

            //解析 每日奖励展示
            if (!arr[18].trim().isEmpty()) {
            config.dropDayShow =  Integer.parseInt(arr[18].trim());
            }

            //解析 阶段层数
            if (!arr[19].trim().isEmpty()) {
            config.targetLevel =  Integer.parseInt(arr[19].trim());
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

    public List<AlertContractConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, AlertContractConfig> getConfigMap() {
      return configMap;
    }
    @Override
    public String getConfigFileName() {
      return "AlertContract.txt";
    }

    // @@@@@自定义方法开始区@@@@@
    @Override
    protected void afterLoad() {

    }

    // @@@@@自定义方法结束区@@@@@
  }
}
