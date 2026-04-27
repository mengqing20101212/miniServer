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
 * File: SceneConfigManager
 */
public class SceneConfigManager implements InterfaceConfigManagerProxy {
  AtomicBoolean switched = new AtomicBoolean(false);
  private static final SceneConfigManager instance = new SceneConfigManager();
  private static final SceneConfigManagerImpl instanceImplA =
      new SceneConfigManagerImpl();
  private static final SceneConfigManagerImpl instanceImplB =
      new SceneConfigManagerImpl();

  public boolean isSwitched() {
    return switched.get();
  }

  public static SceneConfigManagerImpl getInstance() {
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

  public static class SceneConfigManagerImpl extends AbstractConfigManger {

    List<SceneConfig> configList = new ArrayList<SceneConfig>();
    Map<Integer, SceneConfig> configMap = new HashMap<Integer, SceneConfig>();


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
          SceneConfig config = new SceneConfig();
          try {
            //解析 编号
            if (!arr[0].trim().isEmpty()) {
            config.id =  Integer.parseInt(arr[0].trim());
            }

            //解析 场景配置
            if (!arr[1].trim().isEmpty()) {
            config.sceneConfig = arr[1].trim();
            }

            //解析 场景配置
            if (!arr[2].trim().isEmpty()) {
            config.sceneConfigEnemy = arr[2].trim();
            }

            //解析 场景名称
            if (!arr[3].trim().isEmpty()) {
            config.resname = arr[3].trim();
            }

            //解析 后期效果
            if (!arr[4].trim().isEmpty()) {
            config.profile =  Integer.parseInt(arr[4].trim());
            }

            //解析 场景类型
            if (!arr[5].trim().isEmpty()) {
            config.tpye =  Integer.parseInt(arr[5].trim());
            }

            //解析 场景雾配置
            if (!arr[6].trim().isEmpty()) {
            config.fogConfig = arr[6].trim();
            }

            //解析 场景光配置
            if (!arr[7].trim().isEmpty()) {
            config.lightConfig = arr[7].trim();
            }

            //解析 功能挂点信息
            if (!arr[8].trim().isEmpty()) {
            config.functionPoint = arr[8].trim();
            }

            //解析 挂点推镜配置
            if (!arr[9].trim().isEmpty()) {
            config.functionCameraConfig = arr[9].trim();
            }

            //解析 场景角色光影设置
            if (!arr[10].trim().isEmpty()) {
            config.roleLight = arr[10].trim();
            }

            //解析 胜利动作位置
            if (!arr[11].trim().isEmpty()) {
            config.WinPos = arr[11].trim();
            }

            //解析 后期特效资源
            if (!arr[12].trim().isEmpty()) {
            config.eftResid =  Integer.parseInt(arr[12].trim());
            }

            //解析 后期特效资源偏移
            if (!arr[13].trim().isEmpty()) {
            config.eftResOffset = arr[13].trim();
            }

            //解析 场景音效(非BGM)
            if (!arr[14].trim().isEmpty()) {
            config.SFX = arr[14].trim();
            }

            //解析 场景镜头转动
            if (!arr[15].trim().isEmpty()) {
            config.sceneConfigRotation =  Integer.parseInt(arr[15].trim());
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

    public List<SceneConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SceneConfig> getConfigMap() {
      return configMap;
    }
    @Override
    public String getConfigFileName() {
      return "scene.txt";
    }

    // @@@@@自定义方法开始区@@@@@
    @Override
    protected void afterLoad() {

    }

    // @@@@@自定义方法结束区@@@@@
  }
}
