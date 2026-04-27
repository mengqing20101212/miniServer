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
 * File: RecruitUpShowConfigManager
 */
public class RecruitUpShowConfigManager implements InterfaceConfigManagerProxy {
  AtomicBoolean switched = new AtomicBoolean(false);
  private static final RecruitUpShowConfigManager instance = new RecruitUpShowConfigManager();
  private static final RecruitUpShowConfigManagerImpl instanceImplA =
      new RecruitUpShowConfigManagerImpl();
  private static final RecruitUpShowConfigManagerImpl instanceImplB =
      new RecruitUpShowConfigManagerImpl();

  public boolean isSwitched() {
    return switched.get();
  }

  public static RecruitUpShowConfigManagerImpl getInstance() {
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

  public static class RecruitUpShowConfigManagerImpl extends AbstractConfigManger {

    List<RecruitUpShowConfig> configList = new ArrayList<RecruitUpShowConfig>();
    Map<Integer, RecruitUpShowConfig> configMap = new HashMap<Integer, RecruitUpShowConfig>();


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
          RecruitUpShowConfig config = new RecruitUpShowConfig();
          try {
            //解析 招募编号
            if (!arr[0].trim().isEmpty()) {
            config.id =  Integer.parseInt(arr[0].trim());
            }

            //解析 活动排期
            if (!arr[1].trim().isEmpty()) {
            config.scheDuling =  Integer.parseInt(arr[1].trim());
            }

            //解析 碎片显示图片
            if (!arr[2].trim().isEmpty()) {
            config.piecePicResId =  Integer.parseInt(arr[2].trim());
            }

            //解析 招募角色id
            if (!arr[3].trim().isEmpty()) {
            config.heroId =  Integer.parseInt(arr[3].trim());
            }

            //解析 英雄名称
            if (!arr[4].trim().isEmpty()) {
            config.heroName = arr[4].trim();
            }

            //解析 招募说明文本图片
            if (!arr[5].trim().isEmpty()) {
            config.contentPicResId =  Integer.parseInt(arr[5].trim());
            }

            //解析 跳转类型
            if (!arr[6].trim().isEmpty()) {
            config.jumpType =  Integer.parseInt(arr[6].trim());
            }

            //解析 试玩关卡ID
            if (!arr[7].trim().isEmpty()) {
            config.sceneId =  Integer.parseInt(arr[7].trim());
            }

            //解析 英雄显示样式模板
            if (!arr[8].trim().isEmpty()) {
            config.heroShowStyleType =  Integer.parseInt(arr[8].trim());
            }

            //解析 英雄显示图片(可能有多个)
            if (!arr[9].trim().isEmpty()) {
            config.heroPicResId = arr[9].trim();
            }

            //解析 英雄按钮跳转id(根据样式改变)
            if (!arr[10].trim().isEmpty()) {
            config.heroJumpId = arr[10].trim();
            }

            //解析 视频名称
            if (!arr[11].trim().isEmpty()) {
            config.videoName = arr[11].trim();
            }

            //解析 视频界面中英雄图片
            if (!arr[12].trim().isEmpty()) {
            config.videoHeroPic =  Integer.parseInt(arr[12].trim());
            }

            //解析 视频界面中英雄信息图片
            if (!arr[13].trim().isEmpty()) {
            config.videoHeroInfoPic =  Integer.parseInt(arr[13].trim());
            }

            //解析 视频界面中英雄图片的坐标偏移
            if (!arr[14].trim().isEmpty()) {
            config.videoHeroPicPosOffset = arr[14].trim();
            }

            //解析 是否显示全息logo
            if (!arr[15].trim().isEmpty()) {
            config.isShowHolograpicLogo =  Integer.parseInt(arr[15].trim());
            }

            //解析 是否显示返场英雄
            if (!arr[16].trim().isEmpty()) {
            config.isShowReturnLogo =  Integer.parseInt(arr[16].trim());
            }

            //解析 礼包图标
            if (!arr[17].trim().isEmpty()) {
            config.gifBagIcon =  Integer.parseInt(arr[17].trim());
            }

            //解析 礼包跳转
            if (!arr[18].trim().isEmpty()) {
            config.gifBagTurn =  Integer.parseInt(arr[18].trim());
            }

            //解析 招募跳转
            if (!arr[19].trim().isEmpty()) {
            config.turnId =  Integer.parseInt(arr[19].trim());
            }

            //解析 对应的活动id
            if (!arr[20].trim().isEmpty()) {
            config.trueActivityId =  Integer.parseInt(arr[20].trim());
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

    public List<RecruitUpShowConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, RecruitUpShowConfig> getConfigMap() {
      return configMap;
    }
    @Override
    public String getConfigFileName() {
      return "recruitUpShow.txt";
    }

    // @@@@@自定义方法开始区@@@@@
    @Override
    protected void afterLoad() {

    }

    // @@@@@自定义方法结束区@@@@@
  }
}
