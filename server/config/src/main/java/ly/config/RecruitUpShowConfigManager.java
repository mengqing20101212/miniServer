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
 * File: RecruitUpShowConfigManager
 */
public class RecruitUpShowConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final RecruitUpShowConfigManager instance = new RecruitUpShowConfigManager();
  private static final RecruitUpShowConfigManagerImpl instanceImplA = new RecruitUpShowConfigManagerImpl();
  private static final RecruitUpShowConfigManagerImpl instanceImplB = new RecruitUpShowConfigManagerImpl();

  public static RecruitUpShowConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static RecruitUpShowConfigManagerImpl getStandby() {
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
    RecruitUpShowConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class RecruitUpShowConfigManagerImpl extends AbstractConfigManger {
    private List<RecruitUpShowConfig> configList = List.of();
    private Map<Integer, RecruitUpShowConfig> configMap = Map.of();

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
      RecruitUpShowConfigChecker checker = new RecruitUpShowConfigChecker();
      checker.checkHeader(logger, configDir);
      List<RecruitUpShowConfig> newList = new ArrayList<>();
      Map<Integer, RecruitUpShowConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 21) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          int scheDuling = 0;
          int piecePicResId = 0;
          int heroId = 0;
          String heroName = null;
          int contentPicResId = 0;
          int jumpType = 0;
          int sceneId = 0;
          int heroShowStyleType = 0;
          String heroPicResId = null;
          String heroJumpId = null;
          String videoName = null;
          int videoHeroPic = 0;
          int videoHeroInfoPic = 0;
          String videoHeroPicPosOffset = null;
          int isShowHolograpicLogo = 0;
          int isShowReturnLogo = 0;
          int gifBagIcon = 0;
          int gifBagTurn = 0;
          int turnId = 0;
          int trueActivityId = 0;
          try {
            // 解析 招募编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 活动排期
            if (!arr[1].trim().isEmpty()) {
              scheDuling = Integer.parseInt(arr[1].trim());
            }

            // 解析 碎片显示图片
            if (!arr[2].trim().isEmpty()) {
              piecePicResId = Integer.parseInt(arr[2].trim());
            }

            // 解析 招募角色id
            if (!arr[3].trim().isEmpty()) {
              heroId = Integer.parseInt(arr[3].trim());
            }

            // 解析 英雄名称
            if (!arr[4].trim().isEmpty()) {
              heroName = arr[4].trim();
            }

            // 解析 招募说明文本图片
            if (!arr[5].trim().isEmpty()) {
              contentPicResId = Integer.parseInt(arr[5].trim());
            }

            // 解析 跳转类型
            if (!arr[6].trim().isEmpty()) {
              jumpType = Integer.parseInt(arr[6].trim());
            }

            // 解析 试玩关卡ID
            if (!arr[7].trim().isEmpty()) {
              sceneId = Integer.parseInt(arr[7].trim());
            }

            // 解析 英雄显示样式模板
            if (!arr[8].trim().isEmpty()) {
              heroShowStyleType = Integer.parseInt(arr[8].trim());
            }

            // 解析 英雄显示图片(可能有多个)
            if (!arr[9].trim().isEmpty()) {
              heroPicResId = arr[9].trim();
            }

            // 解析 英雄按钮跳转id(根据样式改变)
            if (!arr[10].trim().isEmpty()) {
              heroJumpId = arr[10].trim();
            }

            // 解析 视频名称
            if (!arr[11].trim().isEmpty()) {
              videoName = arr[11].trim();
            }

            // 解析 视频界面中英雄图片
            if (!arr[12].trim().isEmpty()) {
              videoHeroPic = Integer.parseInt(arr[12].trim());
            }

            // 解析 视频界面中英雄信息图片
            if (!arr[13].trim().isEmpty()) {
              videoHeroInfoPic = Integer.parseInt(arr[13].trim());
            }

            // 解析 视频界面中英雄图片的坐标偏移
            if (!arr[14].trim().isEmpty()) {
              videoHeroPicPosOffset = arr[14].trim();
            }

            // 解析 是否显示全息logo
            if (!arr[15].trim().isEmpty()) {
              isShowHolograpicLogo = Integer.parseInt(arr[15].trim());
            }

            // 解析 是否显示返场英雄
            if (!arr[16].trim().isEmpty()) {
              isShowReturnLogo = Integer.parseInt(arr[16].trim());
            }

            // 解析 礼包图标
            if (!arr[17].trim().isEmpty()) {
              gifBagIcon = Integer.parseInt(arr[17].trim());
            }

            // 解析 礼包跳转
            if (!arr[18].trim().isEmpty()) {
              gifBagTurn = Integer.parseInt(arr[18].trim());
            }

            // 解析 招募跳转
            if (!arr[19].trim().isEmpty()) {
              turnId = Integer.parseInt(arr[19].trim());
            }

            // 解析 对应的活动id
            if (!arr[20].trim().isEmpty()) {
              trueActivityId = Integer.parseInt(arr[20].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          RecruitUpShowConfig config = new RecruitUpShowConfig(id, scheDuling, piecePicResId, heroId, heroName, contentPicResId, jumpType, sceneId, heroShowStyleType, heroPicResId, heroJumpId, videoName, videoHeroPic, videoHeroInfoPic, videoHeroPicPosOffset, isShowHolograpicLogo, isShowReturnLogo, gifBagIcon, gifBagTurn, turnId, trueActivityId);
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
