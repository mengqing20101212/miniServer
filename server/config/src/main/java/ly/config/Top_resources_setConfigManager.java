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
 * File: Top_resources_setConfigManager
 */
public class Top_resources_setConfigManager implements InterfaceConfigManagerProxy {
  AtomicBoolean switched = new AtomicBoolean(false);
  private static final Top_resources_setConfigManager instance = new Top_resources_setConfigManager();
  private static final Top_resources_setConfigManagerImpl instanceImplA =
      new Top_resources_setConfigManagerImpl();
  private static final Top_resources_setConfigManagerImpl instanceImplB =
      new Top_resources_setConfigManagerImpl();

  public boolean isSwitched() {
    return switched.get();
  }

  public static Top_resources_setConfigManagerImpl getInstance() {
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

  public static class Top_resources_setConfigManagerImpl extends AbstractConfigManger {

    List<Top_resources_setConfig> configList = new ArrayList<Top_resources_setConfig>();
    Map<Integer, Top_resources_setConfig> configMap = new HashMap<Integer, Top_resources_setConfig>();


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
          Top_resources_setConfig config = new Top_resources_setConfig();
          try {
            //解析 功能id
            if (!arr[0].trim().isEmpty()) {
            config.id =  Integer.parseInt(arr[0].trim());
            }

            //解析 desc
            if (!arr[1].trim().isEmpty()) {
            config.desc = arr[1].trim();
            }

            //解析 标题
            if (!arr[2].trim().isEmpty()) {
            config.title = arr[2].trim();
            }

            //解析 标题类型
            if (!arr[3].trim().isEmpty()) {
            config.titleTypeJudgment =  Integer.parseInt(arr[3].trim());
            }

            //解析 是否显示时间
            if (!arr[4].trim().isEmpty()) {
            config.showtime =  Integer.parseInt(arr[4].trim());
            }

            //解析 字数
            if (!arr[5].trim().isEmpty()) {
            config.titleType =  Integer.parseInt(arr[5].trim());
            }

            //解析 图标id
            if (!arr[6].trim().isEmpty()) {
            config.icon = arr[6].trim();
            }

            //解析 标题文字
            if (!arr[7].trim().isEmpty()) {
            config.titleWord = arr[7].trim();
            }

            //解析 显示道具ID
            if (!arr[8].trim().isEmpty()) {
            config.show_type = arr[8].trim();
            }

            //解析 加号跳转ID
            if (!arr[9].trim().isEmpty()) {
            config.fastTurnID = arr[9].trim();
            }

            //解析 界面显示类型
            if (!arr[10].trim().isEmpty()) {
            config.activeType = arr[10].trim();
            }

            //解析 帮助跳转id
            if (!arr[11].trim().isEmpty()) {
            config.helpTurnId =  Integer.parseInt(arr[11].trim());
            }

            //解析 帮助按钮内容
            if (!arr[12].trim().isEmpty()) {
            config.helpdesc =  Integer.parseInt(arr[12].trim());
            }

            //解析 关联的活动 Id
            if (!arr[13].trim().isEmpty()) {
            config.activityId =  Integer.parseInt(arr[13].trim());
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

    public List<Top_resources_setConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, Top_resources_setConfig> getConfigMap() {
      return configMap;
    }
    @Override
    public String getConfigFileName() {
      return "top_resources_set.txt";
    }

    // @@@@@自定义方法开始区@@@@@
    @Override
    protected void afterLoad() {

    }

    // @@@@@自定义方法结束区@@@@@
  }
}
