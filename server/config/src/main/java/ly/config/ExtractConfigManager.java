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
 * File: ExtractConfigManager
 */
public class ExtractConfigManager implements InterfaceConfigManagerProxy {
  AtomicBoolean switched = new AtomicBoolean(false);
  private static final ExtractConfigManager instance = new ExtractConfigManager();
  private static final ExtractConfigManagerImpl instanceImplA =
      new ExtractConfigManagerImpl();
  private static final ExtractConfigManagerImpl instanceImplB =
      new ExtractConfigManagerImpl();

  public boolean isSwitched() {
    return switched.get();
  }

  public static ExtractConfigManagerImpl getInstance() {
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

  public static class ExtractConfigManagerImpl extends AbstractConfigManger {

    List<ExtractConfig> configList = new ArrayList<ExtractConfig>();
    Map<Integer, ExtractConfig> configMap = new HashMap<Integer, ExtractConfig>();


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
          ExtractConfig config = new ExtractConfig();
          try {
            //解析 英雄
            if (!arr[0].trim().isEmpty()) {
            config.id =  Integer.parseInt(arr[0].trim());
            }

            //解析 备注
            if (!arr[1].trim().isEmpty()) {
            config.name = arr[1].trim();
            }

            //解析 等级
            if (!arr[2].trim().isEmpty()) {
            config.level =  Integer.parseInt(arr[2].trim());
            }

            //解析 排行
            if (!arr[3].trim().isEmpty()) {
            config.ranking = arr[3].trim();
            }

            //解析 排名
            if (!arr[4].trim().isEmpty()) {
            config.rankNum =  Integer.parseInt(arr[4].trim());
            }

            //解析 城市
            if (!arr[5].trim().isEmpty()) {
            config.city = arr[5].trim();
            }

            //解析 宣言
            if (!arr[6].trim().isEmpty()) {
            config.lines = arr[6].trim();
            }

            //解析 随机展示
            if (!arr[7].trim().isEmpty()) {
            config.random =  Integer.parseInt(arr[7].trim());
            }

            //解析 角色半身像
            if (!arr[8].trim().isEmpty()) {
            config.bodyPic =  Integer.parseInt(arr[8].trim());
            }

            //解析 简短的描述
            if (!arr[9].trim().isEmpty()) {
            config.features = arr[9].trim();
            }

            //解析 人物招募动作
            if (!arr[10].trim().isEmpty()) {
            config.timeline = arr[10].trim();
            }

            //解析 招募动画背景
            if (!arr[11].trim().isEmpty()) {
            config.bg =  Integer.parseInt(arr[11].trim());
            }

            //解析 英文名
            if (!arr[12].trim().isEmpty()) {
            config.englishName = arr[12].trim();
            }

            //解析 角色小图片资源Id(目前不用)
            if (!arr[13].trim().isEmpty()) {
            config.smallPicResId =  Integer.parseInt(arr[13].trim());
            }

            //解析 招募英雄位置
            if (!arr[14].trim().isEmpty()) {
            config.smallPicPosOffset = arr[14].trim();
            }

            //解析 招募英雄角度
            if (!arr[15].trim().isEmpty()) {
            config.smallPicRotationOffset = arr[15].trim();
            }

            //解析 招募英雄缩放
            if (!arr[16].trim().isEmpty()) {
            config.smallPicScale = arr[16].trim();
            }

            //解析 英雄品质底框
            if (!arr[17].trim().isEmpty()) {
            config.bodyQualityBgResId =  Integer.parseInt(arr[17].trim());
            }

            //解析 名字底框
            if (!arr[18].trim().isEmpty()) {
            config.nameQualityBgResId =  Integer.parseInt(arr[18].trim());
            }

            //解析 重复播放英雄展示动画
            if (!arr[19].trim().isEmpty()) {
            config.isRepeatPlay =  Integer.parseInt(arr[19].trim());
            }

            //解析 英雄模型背景预设
            if (!arr[20].trim().isEmpty()) {
            config.backgroundId =  Integer.parseInt(arr[20].trim());
            }

            //解析 分享使用立绘
            if (!arr[21].trim().isEmpty()) {
            config.shareAnimation =  Integer.parseInt(arr[21].trim());
            }

            //解析 分享立绘缩放
            if (!arr[22].trim().isEmpty()) {
            config.sharePicScale = arr[22].trim();
            }

            //解析 分享立绘位置
            if (!arr[23].trim().isEmpty()) {
            config.sharePicPosOffset = arr[23].trim();
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

    public List<ExtractConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ExtractConfig> getConfigMap() {
      return configMap;
    }
    @Override
    public String getConfigFileName() {
      return "extract.txt";
    }

    // @@@@@自定义方法开始区@@@@@
    @Override
    protected void afterLoad() {

    }

    // @@@@@自定义方法结束区@@@@@
  }
}
