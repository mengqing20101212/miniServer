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
 * File: ActivityControlConfigManager
 */
public class ActivityControlConfigManager implements InterfaceConfigManagerProxy {
  AtomicBoolean switched = new AtomicBoolean(false);
  private static final ActivityControlConfigManager instance = new ActivityControlConfigManager();
  private static final ActivityControlConfigManagerImpl instanceImplA =
      new ActivityControlConfigManagerImpl();
  private static final ActivityControlConfigManagerImpl instanceImplB =
      new ActivityControlConfigManagerImpl();

  public boolean isSwitched() {
    return switched.get();
  }

  public static ActivityControlConfigManagerImpl getInstance() {
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

  public static class ActivityControlConfigManagerImpl extends AbstractConfigManger {

    List<ActivityControlConfig> configList = new ArrayList<ActivityControlConfig>();
    Map<Integer, ActivityControlConfig> configMap = new HashMap<Integer, ActivityControlConfig>();


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
          ActivityControlConfig config = new ActivityControlConfig();
          try {
            //解析 编号
            if (!arr[0].trim().isEmpty()) {
            config.id =  Integer.parseInt(arr[0].trim());
            }

            //解析 活动名称
            if (!arr[1].trim().isEmpty()) {
            config.name = arr[1].trim();
            }

            //解析 解锁类型
            if (!arr[2].trim().isEmpty()) {
            config.unlockType =  Integer.parseInt(arr[2].trim());
            }

            //解析 解锁参数
            if (!arr[3].trim().isEmpty()) {
            config.unlockPara =  Integer.parseInt(arr[3].trim());
            }

            //解析 解锁参数2
            if (!arr[4].trim().isEmpty()) {
            config.unlockPara2 =  Integer.parseInt(arr[4].trim());
            }

            //解析 阵容id
            if (!arr[5].trim().isEmpty()) {
            config.lineupId =  Integer.parseInt(arr[5].trim());
            }

            //解析 阵容限制
            if (!arr[6].trim().isEmpty()) {
            config.lineupLimit =  Integer.parseInt(arr[6].trim());
            }

            //解析 是否是服务器战斗
            if (!arr[7].trim().isEmpty()) {
            config.isOnlineBattle =  Integer.parseInt(arr[7].trim());
            }

            //解析 系统相关的BGM
            if (!arr[8].trim().isEmpty()) {
            config.BgmId =  Integer.parseInt(arr[8].trim());
            }

            //解析 开启类型
            if (!arr[9].trim().isEmpty()) {
            config.openType =  Integer.parseInt(arr[9].trim());
            }

            //解析 开启参数
            if (!arr[10].trim().isEmpty()) {
            config.openPara = arr[10].trim();
            }

            //解析 新手引导id
            if (!arr[11].trim().isEmpty()) {
            config.guideId =  Integer.parseInt(arr[11].trim());
            }

            //解析 图标
            if (!arr[12].trim().isEmpty()) {
            config.icon =  Integer.parseInt(arr[12].trim());
            }

            //解析 描述
            if (!arr[13].trim().isEmpty()) {
            config.des = arr[13].trim();
            }

            //解析 时间描述
            if (!arr[14].trim().isEmpty()) {
            config.timeDes = arr[14].trim();
            }

            //解析 奖励展示
            if (!arr[15].trim().isEmpty()) {
            config.rewardId = arr[15].trim();
            }

            //解析 未解锁提示
            if (!arr[16].trim().isEmpty()) {
            config.openLimitDes = arr[16].trim();
            }

            //解析 跳转id
            if (!arr[17].trim().isEmpty()) {
            config.turnId =  Integer.parseInt(arr[17].trim());
            }

            //解析 组队阵容id
            if (!arr[18].trim().isEmpty()) {
            config.lineupTeamId =  Integer.parseInt(arr[18].trim());
            }

            //解析 是否在个人空间展示战绩
            if (!arr[19].trim().isEmpty()) {
            config.saveBattleLog =  Integer.parseInt(arr[19].trim());
            }

            //解析 每日限制
            if (!arr[20].trim().isEmpty()) {
            config.dayLimit =  Integer.parseInt(arr[20].trim());
            }

            //解析 每周限制
            if (!arr[21].trim().isEmpty()) {
            config.weekLimit =  Integer.parseInt(arr[21].trim());
            }

            //解析 图标
            if (!arr[22].trim().isEmpty()) {
            config.activityIcon = arr[22].trim();
            }

            //解析 名字
            if (!arr[23].trim().isEmpty()) {
            config.activityName = arr[23].trim();
            }

            //解析 立绘+颜色
            if (!arr[24].trim().isEmpty()) {
            config.bgColour = arr[24].trim();
            }

            //解析 奖励类型
            if (!arr[25].trim().isEmpty()) {
            config.activityreward = arr[25].trim();
            }

            //解析 组队推荐等级
            if (!arr[26].trim().isEmpty()) {
            config.teamLv = arr[26].trim();
            }

            //解析 帮助跳转
            if (!arr[27].trim().isEmpty()) {
            config.help =  Integer.parseInt(arr[27].trim());
            }

            //解析 是否屏幕预解锁
            if (!arr[28].trim().isEmpty()) {
            config.noPrelock =  Integer.parseInt(arr[28].trim());
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

    public List<ActivityControlConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivityControlConfig> getConfigMap() {
      return configMap;
    }
    @Override
    public String getConfigFileName() {
      return "activityControl.txt";
    }

    // @@@@@自定义方法开始区@@@@@
    @Override
    protected void afterLoad() {

    }

    // @@@@@自定义方法结束区@@@@@
  }
}
