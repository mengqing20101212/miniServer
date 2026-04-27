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
 * File: MainStoryConfigManager
 */
public class MainStoryConfigManager implements InterfaceConfigManagerProxy {
  AtomicBoolean switched = new AtomicBoolean(false);
  private static final MainStoryConfigManager instance = new MainStoryConfigManager();
  private static final MainStoryConfigManagerImpl instanceImplA =
      new MainStoryConfigManagerImpl();
  private static final MainStoryConfigManagerImpl instanceImplB =
      new MainStoryConfigManagerImpl();

  public boolean isSwitched() {
    return switched.get();
  }

  public static MainStoryConfigManagerImpl getInstance() {
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

  public static class MainStoryConfigManagerImpl extends AbstractConfigManger {

    List<MainStoryConfig> configList = new ArrayList<MainStoryConfig>();
    Map<Integer, MainStoryConfig> configMap = new HashMap<Integer, MainStoryConfig>();


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
          MainStoryConfig config = new MainStoryConfig();
          try {
            //解析 ID（任务唯一id）
            if (!arr[0].trim().isEmpty()) {
            config.id =  Integer.parseInt(arr[0].trim());
            }

            //解析 任务名称
            if (!arr[1].trim().isEmpty()) {
            config.name = arr[1].trim();
            }

            //解析 任务栏处名称
            if (!arr[2].trim().isEmpty()) {
            config.titile = arr[2].trim();
            }

            //解析 任务栏目标
            if (!arr[3].trim().isEmpty()) {
            config.target = arr[3].trim();
            }

            //解析 事件ID（多个用,隔开）
            if (!arr[4].trim().isEmpty()) {
            config.eventIds = arr[4].trim();
            }

            //解析 章节名称图片资源
            if (!arr[5].trim().isEmpty()) {
            config.chapterRes =  Integer.parseInt(arr[5].trim());
            }

            //解析 章节数字
            if (!arr[6].trim().isEmpty()) {
            config.chapterNum =  Integer.parseInt(arr[6].trim());
            }

            //解析 通关全息调查
            if (!arr[7].trim().isEmpty()) {
            config.chapterId =  Integer.parseInt(arr[7].trim());
            }

            //解析 解锁等级
            if (!arr[8].trim().isEmpty()) {
            config.requireLv =  Integer.parseInt(arr[8].trim());
            }

            //解析 前续任务ID
            if (!arr[9].trim().isEmpty()) {
            config.preIds =  Integer.parseInt(arr[9].trim());
            }

            //解析 后续任务ID
            if (!arr[10].trim().isEmpty()) {
            config.followID =  Integer.parseInt(arr[10].trim());
            }

            //解析 掉落
            if (!arr[11].trim().isEmpty()) {
            config.dropGroupId =  Integer.parseInt(arr[11].trim());
            }

            //解析 掉落展示
            if (!arr[12].trim().isEmpty()) {
            config.dropshow = arr[12].trim();
            }

            //解析 未解锁提示
            if (!arr[13].trim().isEmpty()) {
            config.lockWord = arr[13].trim();
            }

            //解析 图片
            if (!arr[14].trim().isEmpty()) {
            config.pic =  Integer.parseInt(arr[14].trim());
            }

            //解析 关联showID
            if (!arr[15].trim().isEmpty()) {
            config.showId =  Integer.parseInt(arr[15].trim());
            }

            //解析 图片2
            if (!arr[16].trim().isEmpty()) {
            config.pic2 =  Integer.parseInt(arr[16].trim());
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

    public List<MainStoryConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, MainStoryConfig> getConfigMap() {
      return configMap;
    }
    @Override
    public String getConfigFileName() {
      return "mainStory.txt";
    }

    // @@@@@自定义方法开始区@@@@@
    @Override
    protected void afterLoad() {

    }

    // @@@@@自定义方法结束区@@@@@
  }
}
