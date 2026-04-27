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
 * File: ActivityNpcConfigManager
 */
public class ActivityNpcConfigManager implements InterfaceConfigManagerProxy {
  AtomicBoolean switched = new AtomicBoolean(false);
  private static final ActivityNpcConfigManager instance = new ActivityNpcConfigManager();
  private static final ActivityNpcConfigManagerImpl instanceImplA =
      new ActivityNpcConfigManagerImpl();
  private static final ActivityNpcConfigManagerImpl instanceImplB =
      new ActivityNpcConfigManagerImpl();

  public boolean isSwitched() {
    return switched.get();
  }

  public static ActivityNpcConfigManagerImpl getInstance() {
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

  public static class ActivityNpcConfigManagerImpl extends AbstractConfigManger {

    List<ActivityNpcConfig> configList = new ArrayList<ActivityNpcConfig>();
    Map<Integer, ActivityNpcConfig> configMap = new HashMap<Integer, ActivityNpcConfig>();


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
          ActivityNpcConfig config = new ActivityNpcConfig();
          try {
            //解析 编号
            if (!arr[0].trim().isEmpty()) {
            config.id =  Integer.parseInt(arr[0].trim());
            }

            //解析 配置名字辅助列
            if (!arr[1].trim().isEmpty()) {
            config.config_name = arr[1].trim();
            }

            //解析 活动名称
            if (!arr[2].trim().isEmpty()) {
            config.characterModelId = arr[2].trim();
            }

            //解析 默认动作
            if (!arr[3].trim().isEmpty()) {
            config.defaultAni = arr[3].trim();
            }

            //解析 欢迎动作
            if (!arr[4].trim().isEmpty()) {
            config.bornShowAni = arr[4].trim();
            }

            //解析 点击反馈类型(0-不可点，1-通用点击逻辑，2-特殊
            if (!arr[5].trim().isEmpty()) {
            config.clickType = arr[5].trim();
            }

            //解析 点击反馈动作组
            if (!arr[6].trim().isEmpty()) {
            config.clickAniList = arr[6].trim();
            }

            //解析 点击反馈镜头组
            if (!arr[7].trim().isEmpty()) {
            config.clickCameraList = arr[7].trim();
            }

            //解析 推镜的镜头左右偏移（正是左负是右,左右是角度，上下是距离）
            if (!arr[8].trim().isEmpty()) {
            config.moveDistance = arr[8].trim();
            }

            //解析 推镜的距离配置
            if (!arr[9].trim().isEmpty()) {
            config.cameraDistance = arr[9].trim();
            }

            //解析 点击反馈文本
            if (!arr[10].trim().isEmpty()) {
            config.clickText = arr[10].trim();
            }

            //解析 组合动作的文本
            if (!arr[11].trim().isEmpty()) {
            config.groupText = arr[11].trim();
            }

            //解析 特殊功能类型
            if (!arr[12].trim().isEmpty()) {
            config.ActivityType = arr[12].trim();
            }

            //解析 特殊功能参数1
            if (!arr[13].trim().isEmpty()) {
            config.param_1 = arr[13].trim();
            }

            //解析 特殊功能参数2
            if (!arr[14].trim().isEmpty()) {
            config.param_2 = arr[14].trim();
            }

            //解析 角色组合id
            if (!arr[15].trim().isEmpty()) {
            config.npcGrounpId = arr[15].trim();
            }

            //解析 主城显示优先级
            if (!arr[16].trim().isEmpty()) {
            config.showPriority = arr[16].trim();
            }

            //解析 装饰物资源id
            if (!arr[17].trim().isEmpty()) {
            config.decorationId = arr[17].trim();
            }

            //解析 资源物挂点
            if (!arr[18].trim().isEmpty()) {
            config.decorationPoint = arr[18].trim();
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

    public List<ActivityNpcConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivityNpcConfig> getConfigMap() {
      return configMap;
    }
    @Override
    public String getConfigFileName() {
      return "activityNpc.txt";
    }

    // @@@@@自定义方法开始区@@@@@
    @Override
    protected void afterLoad() {

    }

    // @@@@@自定义方法结束区@@@@@
  }
}
