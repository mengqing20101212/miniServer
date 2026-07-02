package ly.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import ly.AbstractConfigManger;
import ly.ConfigLoadException;
import ly.InterfaceConfigManagerProxy;
import ly.utils.KV;
import org.slf4j.Logger;

/*
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 * File: ActivityUpRecruitShowConfigManager
 */
public class ActivityUpRecruitShowConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final ActivityUpRecruitShowConfigManager instance = new ActivityUpRecruitShowConfigManager();
  private static final ActivityUpRecruitShowConfigManagerImpl instanceImplA = new ActivityUpRecruitShowConfigManagerImpl();
  private static final ActivityUpRecruitShowConfigManagerImpl instanceImplB = new ActivityUpRecruitShowConfigManagerImpl();

  public static ActivityUpRecruitShowConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static ActivityUpRecruitShowConfigManagerImpl getStandby() {
    return switched.get() ? instanceImplB : instanceImplA;
  }

  @Override
  public void loadConfig(Logger logger, String configDir) throws ConfigLoadException {
    getInstance().reload(logger, configDir);
  }

  @Override
  public void loadStandbyConfig(Logger logger, String configDir) throws ConfigLoadException {
    getStandby().reload(logger, configDir);
  }

  @Override
  public AbstractConfigManger switchConfig() {
    ActivityUpRecruitShowConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class ActivityUpRecruitShowConfigManagerImpl extends AbstractConfigManger {
    private List<ActivityUpRecruitShowConfig> configList = List.of();
    private Map<Integer, ActivityUpRecruitShowConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      String fileName = configDir + File.separator + getConfigFileName();
      File file = new File(fileName);
      if (!file.exists()) {
        logger.error(fileName + " does not exist");
        throw new ConfigLoadException("Config file does not exist :" + fileName);
      }
      ActivityUpRecruitShowConfigChecker checker = new ActivityUpRecruitShowConfigChecker();
      checker.checkHeader(logger, configDir);
      List<ActivityUpRecruitShowConfig> newList = new ArrayList<>();
      Map<Integer, ActivityUpRecruitShowConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 30) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          int scheDuling = 0;
          int heroShowStyleType = 0;
          String name = null;
          String name2 = null;
          String namePos = null;
          String namePos2 = null;
          int rolePic = 0;
          String rolePicPosOffset = null;
          String rolePicScaleOffset = null;
          int rolePic2 = 0;
          String rolePicPosOffset2 = null;
          String rolePicScaleOffset2 = null;
          int getDesPicResId = 0;
          String getDesText = null;
          int skillPicResId1 = 0;
          String skillPicResId1PosOffset = null;
          int skillPicResId2 = 0;
          String skillPicResId2PosOffset = null;
          int jumpType = 0;
          int sceneId = 0;
          int turnId = 0;
          int drawCardDecPic = 0;
          int bgPic = 0;
          String bgHeroPics = null;
          String bgPicPosOffset = null;
          String bgPicScaleOffset = null;
          int holographic = 0;
          int trueActivityId = 0;
          int SpecialEffects = 0;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 活动排期
            if (!arr[1].trim().isEmpty()) {
              scheDuling = Integer.parseInt(arr[1].trim());
            }

            // 解析 英雄显示样式模板
            if (!arr[2].trim().isEmpty()) {
              heroShowStyleType = Integer.parseInt(arr[2].trim());
            }

            // 解析 名称
            if (!arr[3].trim().isEmpty()) {
              name = arr[3].trim();
            }

            // 解析 名称2
            if (!arr[4].trim().isEmpty()) {
              name2 = arr[4].trim();
            }

            // 解析 名称坐标
            if (!arr[5].trim().isEmpty()) {
              namePos = arr[5].trim();
            }

            // 解析 名称坐标2
            if (!arr[6].trim().isEmpty()) {
              namePos2 = arr[6].trim();
            }

            // 解析 角色立绘
            if (!arr[7].trim().isEmpty()) {
              rolePic = Integer.parseInt(arr[7].trim());
            }

            // 解析 角色立绘坐标偏移
            if (!arr[8].trim().isEmpty()) {
              rolePicPosOffset = arr[8].trim();
            }

            // 解析 角色立绘缩放偏移
            if (!arr[9].trim().isEmpty()) {
              rolePicScaleOffset = arr[9].trim();
            }

            // 解析 角色立绘2
            if (!arr[10].trim().isEmpty()) {
              rolePic2 = Integer.parseInt(arr[10].trim());
            }

            // 解析 角色立绘坐标偏移2
            if (!arr[11].trim().isEmpty()) {
              rolePicPosOffset2 = arr[11].trim();
            }

            // 解析 角色立绘缩放偏移2
            if (!arr[12].trim().isEmpty()) {
              rolePicScaleOffset2 = arr[12].trim();
            }

            // 解析 获取描述文本图片id
            if (!arr[13].trim().isEmpty()) {
              getDesPicResId = Integer.parseInt(arr[13].trim());
            }

            // 解析 获取描述文本
            if (!arr[14].trim().isEmpty()) {
              getDesText = arr[14].trim();
            }

            // 解析 技能描述图片id1
            if (!arr[15].trim().isEmpty()) {
              skillPicResId1 = Integer.parseInt(arr[15].trim());
            }

            // 解析 技能描述图片1坐标
            if (!arr[16].trim().isEmpty()) {
              skillPicResId1PosOffset = arr[16].trim();
            }

            // 解析 技能描述图片id2
            if (!arr[17].trim().isEmpty()) {
              skillPicResId2 = Integer.parseInt(arr[17].trim());
            }

            // 解析 技能描述图片2坐标
            if (!arr[18].trim().isEmpty()) {
              skillPicResId2PosOffset = arr[18].trim();
            }

            // 解析 跳转类型
            if (!arr[19].trim().isEmpty()) {
              jumpType = Integer.parseInt(arr[19].trim());
            }

            // 解析 试玩关卡ID
            if (!arr[20].trim().isEmpty()) {
              sceneId = Integer.parseInt(arr[20].trim());
            }

            // 解析 立即前往ID
            if (!arr[21].trim().isEmpty()) {
              turnId = Integer.parseInt(arr[21].trim());
            }

            // 解析 抽卡说明（SSR概率小字）
            if (!arr[22].trim().isEmpty()) {
              drawCardDecPic = Integer.parseInt(arr[22].trim());
            }

            // 解析 背景图
            if (!arr[23].trim().isEmpty()) {
              bgPic = Integer.parseInt(arr[23].trim());
            }

            // 解析 背景中角色小图(多个)
            if (!arr[24].trim().isEmpty()) {
              bgHeroPics = arr[24].trim();
            }

            // 解析 小图立绘坐标偏移
            if (!arr[25].trim().isEmpty()) {
              bgPicPosOffset = arr[25].trim();
            }

            // 解析 小图立绘缩放偏移
            if (!arr[26].trim().isEmpty()) {
              bgPicScaleOffset = arr[26].trim();
            }

            // 解析 全息标记
            if (!arr[27].trim().isEmpty()) {
              holographic = Integer.parseInt(arr[27].trim());
            }

            // 解析 对应的活动id
            if (!arr[28].trim().isEmpty()) {
              trueActivityId = Integer.parseInt(arr[28].trim());
            }

            // 解析 是否显示特效
            if (!arr[29].trim().isEmpty()) {
              SpecialEffects = Integer.parseInt(arr[29].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          ActivityUpRecruitShowConfig config = new ActivityUpRecruitShowConfig(id, scheDuling, heroShowStyleType, name, name2, namePos, namePos2, rolePic, rolePicPosOffset, rolePicScaleOffset, rolePic2, rolePicPosOffset2, rolePicScaleOffset2, getDesPicResId, getDesText, skillPicResId1, skillPicResId1PosOffset, skillPicResId2, skillPicResId2PosOffset, jumpType, sceneId, turnId, drawCardDecPic, bgPic, bgHeroPics, bgPicPosOffset, bgPicScaleOffset, holographic, trueActivityId, SpecialEffects);
          config.afterLoad();
          newList.add(config);
          newMap.put(config.id, config);
        }
        checker.checkAfterParse(logger, newList);
        configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
        afterLoad();
      } catch (IOException e) {
        throw new ConfigLoadException("Config file could not be read :" + fileName);
      }
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    private List<Integer> parseIntList(String value) {
      if (value == null || value.trim().isEmpty()) { return new ArrayList<>(); }
      String[] parts = value.split(",");
      List<Integer> result = new ArrayList<>();
      for (String part : parts) {
        if (!part.trim().isEmpty()) { result.add(Integer.parseInt(part.trim())); }
      }
      return result;
    }

    private List<KV<Integer, Integer>> parseIntKVList(String value) {
      if (value == null || value.trim().isEmpty()) { return new ArrayList<>(); }
      List<KV<Integer, Integer>> result = new ArrayList<>();
      for (String pair : value.split(",")) {
        int idx = pair.indexOf(":");
        if (idx > 0) {
          result.add(new KV<>(Integer.parseInt(pair.substring(0, idx).trim()), Integer.parseInt(pair.substring(idx + 1).trim())));
        }
      }
      return result;
    }

    private List<KV<String, String>> parseStringKVList(String value) {
      if (value == null || value.trim().isEmpty()) { return new ArrayList<>(); }
      List<KV<String, String>> result = new ArrayList<>();
      for (String pair : value.split(",")) {
        int idx = pair.indexOf(":");
        if (idx > 0) { result.add(new KV<>(pair.substring(0, idx).trim(), pair.substring(idx + 1).trim())); }
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
