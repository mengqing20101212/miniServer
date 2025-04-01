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
import org.apache.logging.log4j.core.Logger;

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
    return switched.getAndSet(!switched.get());
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
List<ActivityInfoConfig> configList1 = new ArrayList<ActivityInfoConfig>();
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
            config.id =  Integer.parseInt(arr[0]);

            //解析 功能名称
            config.name = arr[1];

            //解析 开启类型
            config.openType =  Integer.parseInt(arr[2]);

            //解析 活动排期
            config.scheDuling =  Integer.parseInt(arr[3]);

            //解析 开启参数1
            config.openPara1 =  Integer.parseInt(arr[4]);

            //解析 开启参数2
            config.openPara2 =  Integer.parseInt(arr[5]);

            //解析 开启参数3
            config.openPara3 =  Integer.parseInt(arr[6]);

            //解析 时间类型
            config.timeType =  Integer.parseInt(arr[7]);

            //解析 开始时间
            config.startTime = arr[8];

            //解析 结束时间
            config.endTime = arr[9];

            //解析 特殊时间
            config.specialEndTime = arr[10];

            //解析 刷新时间
            config.freshTime = arr[11];

            //解析 关闭时间
            config.closeTime =  Integer.parseInt(arr[12]);

            //解析 任务领取完是否关闭活动
            config.closeActivity =  Integer.parseInt(arr[13]);

            //解析 开服区间
            config.OpenServiceActivity =  Integer.parseInt(arr[14]);

            //解析 积分类型
            config.integralType =  Integer.parseInt(arr[15]);

            //解析 阶段积分
            config.integralStage = arr[16];

            //解析 积分奖励（掉落表id）
            config.integralReward = arr[17];

            //解析 积分奖励（前端）
            config.integralRewardShow = arr[18];

            //解析 活动标题
            config.title = arr[19];

            //解析 立绘
            config.picture = arr[20];

            //解析 立绘描述
            config.description = arr[21];

            //解析 功能参数1
            config.para1 = arr[22];

            //解析 功能参数2
            config.para2 = arr[23];

            //解析 功能参数3
            config.para3 = arr[24];

            //解析 邮件模板ID
            config.mailTemplateId =  Integer.parseInt(arr[25]);

            //解析 活动剩余道具转换资源
            config.exchangeResources = arr[26];

            //解析 活动入口类型
            config.enterType =  Integer.parseInt(arr[27]);

            //解析 排序
            config.sort =  Integer.parseInt(arr[28]);

            //解析 活动描述
            config.des =  Integer.parseInt(arr[29]);

            //解析 活动时间描述
            config.destime = arr[30];

            //解析 是否显示倒计时
            config.timeDown =  Integer.parseInt(arr[31]);

            //解析 活动描述
            config.desPic = arr[32];

            //解析 商品ID
            config.RechargeId = arr[33];

            //解析 活动类型
            config.iACTIVITYTYPE =  Integer.parseInt(arr[34]);

            //解析 topID
            config.topId =  Integer.parseInt(arr[35]);

            //解析 是否不显示在活动栏
            config.NoShow =  Integer.parseInt(arr[36]);

            //解析 功能显示解锁类型
            config.DisplayFunctionType =  Integer.parseInt(arr[37]);

            //解析 解锁类型参数
            config.DisplayFunctionParam = arr[38];


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
configList1.clear();

      // @@@@@自定义clear方法结束区@@@@@
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
public List<ActivityInfoConfig> getConfigList1() {
      return configList1;
    }

    @Override
    protected void afterLoad() {}

    // @@@@@自定义方法结束区@@@@@
  }
}
