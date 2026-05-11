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
 * File: ActivityInfoConfigManager
 */
public class ActivityInfoConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final ActivityInfoConfigManager instance = new ActivityInfoConfigManager();
  private static final ActivityInfoConfigManagerImpl instanceImplA = new ActivityInfoConfigManagerImpl();
  private static final ActivityInfoConfigManagerImpl instanceImplB = new ActivityInfoConfigManagerImpl();

  public static ActivityInfoConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static ActivityInfoConfigManagerImpl getStandby() {
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
    ActivityInfoConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class ActivityInfoConfigManagerImpl extends AbstractConfigManger {
    private List<ActivityInfoConfig> configList = List.of();
    private Map<Integer, ActivityInfoConfig> configMap = Map.of();

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
      ActivityInfoConfigChecker checker = new ActivityInfoConfigChecker();
      checker.checkHeader(logger, configDir);
      List<ActivityInfoConfig> newList = new ArrayList<>();
      Map<Integer, ActivityInfoConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 39) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          String name = null;
          int openType = 0;
          int scheDuling = 0;
          int openPara1 = 0;
          int openPara2 = 0;
          int openPara3 = 0;
          int timeType = 0;
          String startTime = null;
          String endTime = null;
          String specialEndTime = null;
          String freshTime = null;
          int closeTime = 0;
          int closeActivity = 0;
          int OpenServiceActivity = 0;
          int integralType = 0;
          String integralStage = null;
          String integralReward = null;
          String integralRewardShow = null;
          String title = null;
          String picture = null;
          String description = null;
          String para1 = null;
          String para2 = null;
          String para3 = null;
          int mailTemplateId = 0;
          String exchangeResources = null;
          int enterType = 0;
          int sort = 0;
          int des = 0;
          String destime = null;
          int timeDown = 0;
          String desPic = null;
          String RechargeId = null;
          int iACTIVITYTYPE = 0;
          int topId = 0;
          int NoShow = 0;
          int DisplayFunctionType = 0;
          String DisplayFunctionParam = null;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 功能名称
            if (!arr[1].trim().isEmpty()) {
              name = arr[1].trim();
            }

            // 解析 开启类型
            if (!arr[2].trim().isEmpty()) {
              openType = Integer.parseInt(arr[2].trim());
            }

            // 解析 活动排期
            if (!arr[3].trim().isEmpty()) {
              scheDuling = Integer.parseInt(arr[3].trim());
            }

            // 解析 开启参数1
            if (!arr[4].trim().isEmpty()) {
              openPara1 = Integer.parseInt(arr[4].trim());
            }

            // 解析 开启参数2
            if (!arr[5].trim().isEmpty()) {
              openPara2 = Integer.parseInt(arr[5].trim());
            }

            // 解析 开启参数3
            if (!arr[6].trim().isEmpty()) {
              openPara3 = Integer.parseInt(arr[6].trim());
            }

            // 解析 时间类型
            if (!arr[7].trim().isEmpty()) {
              timeType = Integer.parseInt(arr[7].trim());
            }

            // 解析 开始时间
            if (!arr[8].trim().isEmpty()) {
              startTime = arr[8].trim();
            }

            // 解析 结束时间
            if (!arr[9].trim().isEmpty()) {
              endTime = arr[9].trim();
            }

            // 解析 特殊时间
            if (!arr[10].trim().isEmpty()) {
              specialEndTime = arr[10].trim();
            }

            // 解析 刷新时间
            if (!arr[11].trim().isEmpty()) {
              freshTime = arr[11].trim();
            }

            // 解析 关闭时间
            if (!arr[12].trim().isEmpty()) {
              closeTime = Integer.parseInt(arr[12].trim());
            }

            // 解析 任务领取完是否关闭活动
            if (!arr[13].trim().isEmpty()) {
              closeActivity = Integer.parseInt(arr[13].trim());
            }

            // 解析 开服区间
            if (!arr[14].trim().isEmpty()) {
              OpenServiceActivity = Integer.parseInt(arr[14].trim());
            }

            // 解析 积分类型
            if (!arr[15].trim().isEmpty()) {
              integralType = Integer.parseInt(arr[15].trim());
            }

            // 解析 阶段积分
            if (!arr[16].trim().isEmpty()) {
              integralStage = arr[16].trim();
            }

            // 解析 积分奖励（掉落表id）
            if (!arr[17].trim().isEmpty()) {
              integralReward = arr[17].trim();
            }

            // 解析 积分奖励（前端）
            if (!arr[18].trim().isEmpty()) {
              integralRewardShow = arr[18].trim();
            }

            // 解析 活动标题
            if (!arr[19].trim().isEmpty()) {
              title = arr[19].trim();
            }

            // 解析 立绘
            if (!arr[20].trim().isEmpty()) {
              picture = arr[20].trim();
            }

            // 解析 立绘描述
            if (!arr[21].trim().isEmpty()) {
              description = arr[21].trim();
            }

            // 解析 功能参数1
            if (!arr[22].trim().isEmpty()) {
              para1 = arr[22].trim();
            }

            // 解析 功能参数2
            if (!arr[23].trim().isEmpty()) {
              para2 = arr[23].trim();
            }

            // 解析 功能参数3
            if (!arr[24].trim().isEmpty()) {
              para3 = arr[24].trim();
            }

            // 解析 邮件模板ID
            if (!arr[25].trim().isEmpty()) {
              mailTemplateId = Integer.parseInt(arr[25].trim());
            }

            // 解析 活动剩余道具转换资源
            if (!arr[26].trim().isEmpty()) {
              exchangeResources = arr[26].trim();
            }

            // 解析 活动入口类型
            if (!arr[27].trim().isEmpty()) {
              enterType = Integer.parseInt(arr[27].trim());
            }

            // 解析 排序
            if (!arr[28].trim().isEmpty()) {
              sort = Integer.parseInt(arr[28].trim());
            }

            // 解析 活动描述
            if (!arr[29].trim().isEmpty()) {
              des = Integer.parseInt(arr[29].trim());
            }

            // 解析 活动时间描述
            if (!arr[30].trim().isEmpty()) {
              destime = arr[30].trim();
            }

            // 解析 是否显示倒计时
            if (!arr[31].trim().isEmpty()) {
              timeDown = Integer.parseInt(arr[31].trim());
            }

            // 解析 活动描述
            if (!arr[32].trim().isEmpty()) {
              desPic = arr[32].trim();
            }

            // 解析 商品ID
            if (!arr[33].trim().isEmpty()) {
              RechargeId = arr[33].trim();
            }

            // 解析 活动类型
            if (!arr[34].trim().isEmpty()) {
              iACTIVITYTYPE = Integer.parseInt(arr[34].trim());
            }

            // 解析 topID
            if (!arr[35].trim().isEmpty()) {
              topId = Integer.parseInt(arr[35].trim());
            }

            // 解析 是否不显示在活动栏
            if (!arr[36].trim().isEmpty()) {
              NoShow = Integer.parseInt(arr[36].trim());
            }

            // 解析 功能显示解锁类型
            if (!arr[37].trim().isEmpty()) {
              DisplayFunctionType = Integer.parseInt(arr[37].trim());
            }

            // 解析 解锁类型参数
            if (!arr[38].trim().isEmpty()) {
              DisplayFunctionParam = arr[38].trim();
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          ActivityInfoConfig config = new ActivityInfoConfig(id, name, openType, scheDuling, openPara1, openPara2, openPara3, timeType, startTime, endTime, specialEndTime, freshTime, closeTime, closeActivity, OpenServiceActivity, integralType, integralStage, integralReward, integralRewardShow, title, picture, description, para1, para2, para3, mailTemplateId, exchangeResources, enterType, sort, des, destime, timeDown, desPic, RechargeId, iACTIVITYTYPE, topId, NoShow, DisplayFunctionType, DisplayFunctionParam);
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

    public List<ActivityInfoConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivityInfoConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "activityInfo.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
