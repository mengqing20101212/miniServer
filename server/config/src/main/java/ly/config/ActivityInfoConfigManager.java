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
 * File: ActivityInfoConfigManager
 */
public class ActivityInfoConfigManager implements InterfaceConfigManagerProxy {
  AtomicBoolean switched = new AtomicBoolean(false);
  private static final ActivityInfoConfigManager instance = new ActivityInfoConfigManager();
  private static final ActivityInfoConfigManagerImpl instanceImplA =
      new ActivityInfoConfigManagerImpl();
  private static final ActivityInfoConfigManagerImpl instanceImplB =
      new ActivityInfoConfigManagerImpl();

  public boolean isSwitched() {
    return switched.get();
  }

  public static ActivityInfoConfigManagerImpl getInstance() {
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

  public static class ActivityInfoConfigManagerImpl extends AbstractConfigManger {

    List<ActivityInfoConfig> configList = new ArrayList<ActivityInfoConfig>();
    Map<Integer, ActivityInfoConfig> configMap = new HashMap<Integer, ActivityInfoConfig>();


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
          ActivityInfoConfig config = new ActivityInfoConfig();
          try {
            //解析 编号
            if (!arr[0].trim().isEmpty()) {
            config.id =  Integer.parseInt(arr[0].trim());
            }

            //解析 功能名称
            if (!arr[1].trim().isEmpty()) {
            config.name = arr[1].trim();
            }

            //解析 开启类型
            if (!arr[2].trim().isEmpty()) {
            config.openType =  Integer.parseInt(arr[2].trim());
            }

            //解析 活动排期
            if (!arr[3].trim().isEmpty()) {
            config.scheDuling =  Integer.parseInt(arr[3].trim());
            }

            //解析 开启参数1
            if (!arr[4].trim().isEmpty()) {
            config.openPara1 =  Integer.parseInt(arr[4].trim());
            }

            //解析 开启参数2
            if (!arr[5].trim().isEmpty()) {
            config.openPara2 =  Integer.parseInt(arr[5].trim());
            }

            //解析 开启参数3
            if (!arr[6].trim().isEmpty()) {
            config.openPara3 =  Integer.parseInt(arr[6].trim());
            }

            //解析 时间类型
            if (!arr[7].trim().isEmpty()) {
            config.timeType =  Integer.parseInt(arr[7].trim());
            }

            //解析 开始时间
            if (!arr[8].trim().isEmpty()) {
            config.startTime = arr[8].trim();
            }

            //解析 结束时间
            if (!arr[9].trim().isEmpty()) {
            config.endTime = arr[9].trim();
            }

            //解析 特殊时间
            if (!arr[10].trim().isEmpty()) {
            config.specialEndTime = arr[10].trim();
            }

            //解析 刷新时间
            if (!arr[11].trim().isEmpty()) {
            config.freshTime = arr[11].trim();
            }

            //解析 关闭时间
            if (!arr[12].trim().isEmpty()) {
            config.closeTime =  Integer.parseInt(arr[12].trim());
            }

            //解析 任务领取完是否关闭活动
            if (!arr[13].trim().isEmpty()) {
            config.closeActivity =  Integer.parseInt(arr[13].trim());
            }

            //解析 开服区间
            if (!arr[14].trim().isEmpty()) {
            config.OpenServiceActivity =  Integer.parseInt(arr[14].trim());
            }

            //解析 积分类型
            if (!arr[15].trim().isEmpty()) {
            config.integralType =  Integer.parseInt(arr[15].trim());
            }

            //解析 阶段积分
            if (!arr[16].trim().isEmpty()) {
            config.integralStage = arr[16].trim();
            }

            //解析 积分奖励（掉落表id）
            if (!arr[17].trim().isEmpty()) {
            config.integralReward = arr[17].trim();
            }

            //解析 积分奖励（前端）
            if (!arr[18].trim().isEmpty()) {
            config.integralRewardShow = arr[18].trim();
            }

            //解析 活动标题
            if (!arr[19].trim().isEmpty()) {
            config.title = arr[19].trim();
            }

            //解析 立绘
            if (!arr[20].trim().isEmpty()) {
            config.picture = arr[20].trim();
            }

            //解析 立绘描述
            if (!arr[21].trim().isEmpty()) {
            config.description = arr[21].trim();
            }

            //解析 功能参数1
            if (!arr[22].trim().isEmpty()) {
            config.para1 = arr[22].trim();
            }

            //解析 功能参数2
            if (!arr[23].trim().isEmpty()) {
            config.para2 = arr[23].trim();
            }

            //解析 功能参数3
            if (!arr[24].trim().isEmpty()) {
            config.para3 = arr[24].trim();
            }

            //解析 邮件模板ID
            if (!arr[25].trim().isEmpty()) {
            config.mailTemplateId =  Integer.parseInt(arr[25].trim());
            }

            //解析 活动剩余道具转换资源
            if (!arr[26].trim().isEmpty()) {
            config.exchangeResources = arr[26].trim();
            }

            //解析 活动入口类型
            if (!arr[27].trim().isEmpty()) {
            config.enterType =  Integer.parseInt(arr[27].trim());
            }

            //解析 排序
            if (!arr[28].trim().isEmpty()) {
            config.sort =  Integer.parseInt(arr[28].trim());
            }

            //解析 活动描述
            if (!arr[29].trim().isEmpty()) {
            config.des =  Integer.parseInt(arr[29].trim());
            }

            //解析 活动时间描述
            if (!arr[30].trim().isEmpty()) {
            config.destime = arr[30].trim();
            }

            //解析 是否显示倒计时
            if (!arr[31].trim().isEmpty()) {
            config.timeDown =  Integer.parseInt(arr[31].trim());
            }

            //解析 活动描述
            if (!arr[32].trim().isEmpty()) {
            config.desPic = arr[32].trim();
            }

            //解析 商品ID
            if (!arr[33].trim().isEmpty()) {
            config.RechargeId = arr[33].trim();
            }

            //解析 活动类型
            if (!arr[34].trim().isEmpty()) {
            config.iACTIVITYTYPE =  Integer.parseInt(arr[34].trim());
            }

            //解析 topID
            if (!arr[35].trim().isEmpty()) {
            config.topId =  Integer.parseInt(arr[35].trim());
            }

            //解析 是否不显示在活动栏
            if (!arr[36].trim().isEmpty()) {
            config.NoShow =  Integer.parseInt(arr[36].trim());
            }

            //解析 功能显示解锁类型
            if (!arr[37].trim().isEmpty()) {
            config.DisplayFunctionType =  Integer.parseInt(arr[37].trim());
            }

            //解析 解锁类型参数
            if (!arr[38].trim().isEmpty()) {
            config.DisplayFunctionParam = arr[38].trim();
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
