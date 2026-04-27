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
 * File: ActivityUpRecruitShowConfigManager
 */
public class ActivityUpRecruitShowConfigManager implements InterfaceConfigManagerProxy {
  AtomicBoolean switched = new AtomicBoolean(false);
  private static final ActivityUpRecruitShowConfigManager instance = new ActivityUpRecruitShowConfigManager();
  private static final ActivityUpRecruitShowConfigManagerImpl instanceImplA =
      new ActivityUpRecruitShowConfigManagerImpl();
  private static final ActivityUpRecruitShowConfigManagerImpl instanceImplB =
      new ActivityUpRecruitShowConfigManagerImpl();

  public boolean isSwitched() {
    return switched.get();
  }

  public static ActivityUpRecruitShowConfigManagerImpl getInstance() {
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

  public static class ActivityUpRecruitShowConfigManagerImpl extends AbstractConfigManger {

    List<ActivityUpRecruitShowConfig> configList = new ArrayList<ActivityUpRecruitShowConfig>();
    Map<Integer, ActivityUpRecruitShowConfig> configMap = new HashMap<Integer, ActivityUpRecruitShowConfig>();


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
          ActivityUpRecruitShowConfig config = new ActivityUpRecruitShowConfig();
          try {
            //解析 编号
            if (!arr[0].trim().isEmpty()) {
            config.id =  Integer.parseInt(arr[0].trim());
            }

            //解析 活动排期
            if (!arr[1].trim().isEmpty()) {
            config.scheDuling =  Integer.parseInt(arr[1].trim());
            }

            //解析 英雄显示样式模板
            if (!arr[2].trim().isEmpty()) {
            config.heroShowStyleType =  Integer.parseInt(arr[2].trim());
            }

            //解析 名称
            if (!arr[3].trim().isEmpty()) {
            config.name = arr[3].trim();
            }

            //解析 名称2
            if (!arr[4].trim().isEmpty()) {
            config.name2 = arr[4].trim();
            }

            //解析 名称坐标
            if (!arr[5].trim().isEmpty()) {
            config.namePos = arr[5].trim();
            }

            //解析 名称坐标2
            if (!arr[6].trim().isEmpty()) {
            config.namePos2 = arr[6].trim();
            }

            //解析 角色立绘
            if (!arr[7].trim().isEmpty()) {
            config.rolePic =  Integer.parseInt(arr[7].trim());
            }

            //解析 角色立绘坐标偏移
            if (!arr[8].trim().isEmpty()) {
            config.rolePicPosOffset = arr[8].trim();
            }

            //解析 角色立绘缩放偏移
            if (!arr[9].trim().isEmpty()) {
            config.rolePicScaleOffset = arr[9].trim();
            }

            //解析 角色立绘2
            if (!arr[10].trim().isEmpty()) {
            config.rolePic2 =  Integer.parseInt(arr[10].trim());
            }

            //解析 角色立绘坐标偏移2
            if (!arr[11].trim().isEmpty()) {
            config.rolePicPosOffset2 = arr[11].trim();
            }

            //解析 角色立绘缩放偏移2
            if (!arr[12].trim().isEmpty()) {
            config.rolePicScaleOffset2 = arr[12].trim();
            }

            //解析 获取描述文本图片id
            if (!arr[13].trim().isEmpty()) {
            config.getDesPicResId =  Integer.parseInt(arr[13].trim());
            }

            //解析 获取描述文本
            if (!arr[14].trim().isEmpty()) {
            config.getDesText = arr[14].trim();
            }

            //解析 技能描述图片id1
            if (!arr[15].trim().isEmpty()) {
            config.skillPicResId1 =  Integer.parseInt(arr[15].trim());
            }

            //解析 技能描述图片1坐标
            if (!arr[16].trim().isEmpty()) {
            config.skillPicResId1PosOffset = arr[16].trim();
            }

            //解析 技能描述图片id2
            if (!arr[17].trim().isEmpty()) {
            config.skillPicResId2 =  Integer.parseInt(arr[17].trim());
            }

            //解析 技能描述图片2坐标
            if (!arr[18].trim().isEmpty()) {
            config.skillPicResId2PosOffset = arr[18].trim();
            }

            //解析 跳转类型
            if (!arr[19].trim().isEmpty()) {
            config.jumpType =  Integer.parseInt(arr[19].trim());
            }

            //解析 试玩关卡ID
            if (!arr[20].trim().isEmpty()) {
            config.sceneId =  Integer.parseInt(arr[20].trim());
            }

            //解析 立即前往ID
            if (!arr[21].trim().isEmpty()) {
            config.turnId =  Integer.parseInt(arr[21].trim());
            }

            //解析 抽卡说明（SSR概率小字）
            if (!arr[22].trim().isEmpty()) {
            config.drawCardDecPic =  Integer.parseInt(arr[22].trim());
            }

            //解析 背景图
            if (!arr[23].trim().isEmpty()) {
            config.bgPic =  Integer.parseInt(arr[23].trim());
            }

            //解析 背景中角色小图(多个)
            if (!arr[24].trim().isEmpty()) {
            config.bgHeroPics = arr[24].trim();
            }

            //解析 小图立绘坐标偏移
            if (!arr[25].trim().isEmpty()) {
            config.bgPicPosOffset = arr[25].trim();
            }

            //解析 小图立绘缩放偏移
            if (!arr[26].trim().isEmpty()) {
            config.bgPicScaleOffset = arr[26].trim();
            }

            //解析 全息标记
            if (!arr[27].trim().isEmpty()) {
            config.holographic =  Integer.parseInt(arr[27].trim());
            }

            //解析 对应的活动id
            if (!arr[28].trim().isEmpty()) {
            config.trueActivityId =  Integer.parseInt(arr[28].trim());
            }

            //解析 是否显示特效
            if (!arr[29].trim().isEmpty()) {
            config.SpecialEffects =  Integer.parseInt(arr[29].trim());
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

    public List<ActivityUpRecruitShowConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivityUpRecruitShowConfig> getConfigMap() {
      return configMap;
    }
    @Override
    public String getConfigFileName() {
      return "activityUpRecruitShow.txt";
    }

    // @@@@@自定义方法开始区@@@@@
    @Override
    protected void afterLoad() {

    }

    // @@@@@自定义方法结束区@@@@@
  }
}
