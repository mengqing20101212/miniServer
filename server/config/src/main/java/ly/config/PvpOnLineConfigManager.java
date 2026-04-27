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
 * File: PvpOnLineConfigManager
 */
public class PvpOnLineConfigManager implements InterfaceConfigManagerProxy {
  AtomicBoolean switched = new AtomicBoolean(false);
  private static final PvpOnLineConfigManager instance = new PvpOnLineConfigManager();
  private static final PvpOnLineConfigManagerImpl instanceImplA =
      new PvpOnLineConfigManagerImpl();
  private static final PvpOnLineConfigManagerImpl instanceImplB =
      new PvpOnLineConfigManagerImpl();

  public boolean isSwitched() {
    return switched.get();
  }

  public static PvpOnLineConfigManagerImpl getInstance() {
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

  public static class PvpOnLineConfigManagerImpl extends AbstractConfigManger {

    List<PvpOnLineConfig> configList = new ArrayList<PvpOnLineConfig>();
    Map<Integer, PvpOnLineConfig> configMap = new HashMap<Integer, PvpOnLineConfig>();


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
          PvpOnLineConfig config = new PvpOnLineConfig();
          try {
            //解析 编号
            if (!arr[0].trim().isEmpty()) {
            config.id =  Integer.parseInt(arr[0].trim());
            }

            //解析 段位
            if (!arr[1].trim().isEmpty()) {
            config.duan = arr[1].trim();
            }

            //解析 大段名
            if (!arr[2].trim().isEmpty()) {
            config.duanBigName = arr[2].trim();
            }

            //解析 大段位
            if (!arr[3].trim().isEmpty()) {
            config.duanBig =  Integer.parseInt(arr[3].trim());
            }

            //解析 小段位
            if (!arr[4].trim().isEmpty()) {
            config.duanSmall =  Integer.parseInt(arr[4].trim());
            }

            //解析 段位图标
            if (!arr[5].trim().isEmpty()) {
            config.duanIconBig =  Integer.parseInt(arr[5].trim());
            }

            //解析 段位图标
            if (!arr[6].trim().isEmpty()) {
            config.duanIconSmall =  Integer.parseInt(arr[6].trim());
            }

            //解析 星级数量
            if (!arr[7].trim().isEmpty()) {
            config.starNum =  Integer.parseInt(arr[7].trim());
            }

            //解析 累积星数
            if (!arr[8].trim().isEmpty()) {
            config.starCollect =  Integer.parseInt(arr[8].trim());
            }

            //解析 ELO分K值
            if (!arr[9].trim().isEmpty()) {
            config.eloKValue =  Float.parseFloat(arr[9].trim());
            }

            //解析 勇者积分上限
            if (!arr[10].trim().isEmpty()) {
            config.scoreMax =  Integer.parseInt(arr[10].trim());
            }

            //解析 保星积分
            if (!arr[11].trim().isEmpty()) {
            config.scoreProtection =  Integer.parseInt(arr[11].trim());
            }

            //解析 是否开启积分保星
            if (!arr[12].trim().isEmpty()) {
            config.isScoreProtection =  Integer.parseInt(arr[12].trim());
            }

            //解析 连胜加星
            if (!arr[13].trim().isEmpty()) {
            config.winningStreak =  Integer.parseInt(arr[13].trim());
            }

            //解析 掉段保护
            if (!arr[14].trim().isEmpty()) {
            config.dropProtection =  Integer.parseInt(arr[14].trim());
            }

            //解析 保大段
            if (!arr[15].trim().isEmpty()) {
            config.duanProtection =  Integer.parseInt(arr[15].trim());
            }

            //解析 精确匹配分数
            if (!arr[16].trim().isEmpty()) {
            config.bestMatchScore =  Integer.parseInt(arr[16].trim());
            }

            //解析 精确匹配时间
            if (!arr[17].trim().isEmpty()) {
            config.bestMatchTime =  Integer.parseInt(arr[17].trim());
            }

            //解析 精确匹配星数
            if (!arr[18].trim().isEmpty()) {
            config.bestMatchStar =  Integer.parseInt(arr[18].trim());
            }

            //解析 模糊匹配分数
            if (!arr[19].trim().isEmpty()) {
            config.fuzzyMatchScore =  Integer.parseInt(arr[19].trim());
            }

            //解析 模糊匹配时间
            if (!arr[20].trim().isEmpty()) {
            config.fuzzyMatchTime =  Integer.parseInt(arr[20].trim());
            }

            //解析 模糊匹配星数
            if (!arr[21].trim().isEmpty()) {
            config.fuzzyMatchStar =  Integer.parseInt(arr[21].trim());
            }

            //解析 保底匹配分数
            if (!arr[22].trim().isEmpty()) {
            config.leastMatchScore =  Integer.parseInt(arr[22].trim());
            }

            //解析 保底匹配时间
            if (!arr[23].trim().isEmpty()) {
            config.leastMatchTime =  Integer.parseInt(arr[23].trim());
            }

            //解析 保底匹配星数
            if (!arr[24].trim().isEmpty()) {
            config.leastMatchStar =  Integer.parseInt(arr[24].trim());
            }

            //解析 是否超时保底匹配机器人
            if (!arr[25].trim().isEmpty()) {
            config.isTimeOutRebotMatch =  Integer.parseInt(arr[25].trim());
            }

            //解析 是否战败保底匹配机器人
            if (!arr[26].trim().isEmpty()) {
            config.isLoseRobotMatch =  Integer.parseInt(arr[26].trim());
            }

            //解析 是否轮选
            if (!arr[27].trim().isEmpty()) {
            config.isPick =  Integer.parseInt(arr[27].trim());
            }

            //解析 战斗胜利荣誉
            if (!arr[28].trim().isEmpty()) {
            config.winReward =  Integer.parseInt(arr[28].trim());
            }

            //解析 战斗失败荣誉
            if (!arr[29].trim().isEmpty()) {
            config.loseReward =  Integer.parseInt(arr[29].trim());
            }

            //解析 每周荣誉上限
            if (!arr[30].trim().isEmpty()) {
            config.honorLimit =  Integer.parseInt(arr[30].trim());
            }

            //解析 每周结算奖励
            if (!arr[31].trim().isEmpty()) {
            config.awardWeek = arr[31].trim();
            }

            //解析 每周结算奖励预览
            if (!arr[32].trim().isEmpty()) {
            config.awardWeekPre = arr[32].trim();
            }

            //解析 匹配服务器
            if (!arr[33].trim().isEmpty()) {
            config.bossTimeSetting = arr[33].trim();
            }

            //解析 对应机器人范围
            if (!arr[34].trim().isEmpty()) {
            config.robotPool =  Integer.parseInt(arr[34].trim());
            }

            //解析 对应场次
            if (!arr[35].trim().isEmpty()) {
            config.sceneMatch =  Integer.parseInt(arr[35].trim());
            }

            //解析 精准匹配等级差
            if (!arr[36].trim().isEmpty()) {
            config.bestMatchLevelDiff =  Integer.parseInt(arr[36].trim());
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

    public List<PvpOnLineConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, PvpOnLineConfig> getConfigMap() {
      return configMap;
    }
    @Override
    public String getConfigFileName() {
      return "pvpOnLine.txt";
    }

    // @@@@@自定义方法开始区@@@@@
    @Override
    protected void afterLoad() {

    }

    // @@@@@自定义方法结束区@@@@@
  }
}
