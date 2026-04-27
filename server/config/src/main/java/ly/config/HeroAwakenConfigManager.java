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
 * File: HeroAwakenConfigManager
 */
public class HeroAwakenConfigManager implements InterfaceConfigManagerProxy {
  AtomicBoolean switched = new AtomicBoolean(false);
  private static final HeroAwakenConfigManager instance = new HeroAwakenConfigManager();
  private static final HeroAwakenConfigManagerImpl instanceImplA =
      new HeroAwakenConfigManagerImpl();
  private static final HeroAwakenConfigManagerImpl instanceImplB =
      new HeroAwakenConfigManagerImpl();

  public boolean isSwitched() {
    return switched.get();
  }

  public static HeroAwakenConfigManagerImpl getInstance() {
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

  public static class HeroAwakenConfigManagerImpl extends AbstractConfigManger {

    List<HeroAwakenConfig> configList = new ArrayList<HeroAwakenConfig>();
    Map<Integer, HeroAwakenConfig> configMap = new HashMap<Integer, HeroAwakenConfig>();


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
          HeroAwakenConfig config = new HeroAwakenConfig();
          try {
            //解析 编号
            if (!arr[0].trim().isEmpty()) {
            config.id =  Integer.parseInt(arr[0].trim());
            }

            //解析 英雄模板名
            if (!arr[1].trim().isEmpty()) {
            config.modelName =  Integer.parseInt(arr[1].trim());
            }

            //解析 觉醒次序
            if (!arr[2].trim().isEmpty()) {
            config.sequence =  Integer.parseInt(arr[2].trim());
            }

            //解析 觉醒类型
            if (!arr[3].trim().isEmpty()) {
            config.awakenType =  Integer.parseInt(arr[3].trim());
            }

            //解析 觉醒后常规技能
            if (!arr[4].trim().isEmpty()) {
            config.awakenskill_1 =  Integer.parseInt(arr[4].trim());
            }

            //解析   null
            if (!arr[5].trim().isEmpty()) {
            config.awakenskill_2 =  Integer.parseInt(arr[5].trim());
            }

            //解析   null
            if (!arr[6].trim().isEmpty()) {
            config.awakenskill_3 =  Integer.parseInt(arr[6].trim());
            }

            //解析 觉醒超级技能
            if (!arr[7].trim().isEmpty()) {
            config.awakenskill_s1 =  Integer.parseInt(arr[7].trim());
            }

            //解析 觉醒超级技能
            if (!arr[8].trim().isEmpty()) {
            config.awakenskill_s2 =  Integer.parseInt(arr[8].trim());
            }

            //解析 多段觉醒技能
            if (!arr[9].trim().isEmpty()) {
            config.moreAwakenSkill1 =  Integer.parseInt(arr[9].trim());
            }

            //解析 多段觉醒技能
            if (!arr[10].trim().isEmpty()) {
            config.moreAwakenSkill2 =  Integer.parseInt(arr[10].trim());
            }

            //解析 多段觉醒技能
            if (!arr[11].trim().isEmpty()) {
            config.moreAwakenSkill3 =  Integer.parseInt(arr[11].trim());
            }

            //解析 属性类型
            if (!arr[12].trim().isEmpty()) {
            config.attrType =  Integer.parseInt(arr[12].trim());
            }

            //解析 属性数值
            if (!arr[13].trim().isEmpty()) {
            config.attrNum =  Integer.parseInt(arr[13].trim());
            }

            //解析 觉醒阶段
            if (!arr[14].trim().isEmpty()) {
            config.awakenPhase = arr[14].trim();
            }

            //解析 觉醒图标
            if (!arr[15].trim().isEmpty()) {
            config.awakenIcon =  Integer.parseInt(arr[15].trim());
            }

            //解析 觉醒标题
            if (!arr[16].trim().isEmpty()) {
            config.awakenTitle = arr[16].trim();
            }

            //解析 觉醒属性描述
            if (!arr[17].trim().isEmpty()) {
            config.awakenAttrDes = arr[17].trim();
            }

            //解析 觉醒材料需求
            if (!arr[18].trim().isEmpty()) {
            config.awakenItem = arr[18].trim();
            }

            //解析 觉醒货币类型
            if (!arr[19].trim().isEmpty()) {
            config.awakenCurrencyType =  Integer.parseInt(arr[19].trim());
            }

            //解析 觉醒货币数量
            if (!arr[20].trim().isEmpty()) {
            config.awakenCurrencyNum =  Integer.parseInt(arr[20].trim());
            }

            //解析 是否还原
            if (!arr[21].trim().isEmpty()) {
            config.isReset =  Integer.parseInt(arr[21].trim());
            }

            //解析 还原返还
            if (!arr[22].trim().isEmpty()) {
            config.retainItem = arr[22].trim();
            }

            //解析 分解消耗货币类型
            if (!arr[23].trim().isEmpty()) {
            config.currencyType =  Integer.parseInt(arr[23].trim());
            }

            //解析 分解消耗货币数量
            if (!arr[24].trim().isEmpty()) {
            config.currencyNum =  Integer.parseInt(arr[24].trim());
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

    public List<HeroAwakenConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, HeroAwakenConfig> getConfigMap() {
      return configMap;
    }
    @Override
    public String getConfigFileName() {
      return "heroAwaken.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {}

    /**
     * 根据英雄觉醒模板ID和觉醒等级获取配置
     */
    public HeroAwakenConfig getByHeroAwakenDataAndLevel(int heroAwakenData, int awakenLevel) {
      for (HeroAwakenConfig config : configList) {
        if (config.modelName == heroAwakenData && config.sequence == awakenLevel) {
          return config;
        }
      }
      return null;
    }

    // @@@@@自定义方法结束区@@@@@
  }
}
