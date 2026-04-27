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
 * File: HeroAttrConfigManager
 */
public class HeroAttrConfigManager implements InterfaceConfigManagerProxy {
  AtomicBoolean switched = new AtomicBoolean(false);
  private static final HeroAttrConfigManager instance = new HeroAttrConfigManager();
  private static final HeroAttrConfigManagerImpl instanceImplA =
      new HeroAttrConfigManagerImpl();
  private static final HeroAttrConfigManagerImpl instanceImplB =
      new HeroAttrConfigManagerImpl();

  public boolean isSwitched() {
    return switched.get();
  }

  public static HeroAttrConfigManagerImpl getInstance() {
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

  public static class HeroAttrConfigManagerImpl extends AbstractConfigManger {

    List<HeroAttrConfig> configList = new ArrayList<HeroAttrConfig>();

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
          HeroAttrConfig config = new HeroAttrConfig();
          try {
            //解析 模板名
            if (!arr[0].trim().isEmpty()) {
            config.modelName =  Integer.parseInt(arr[0].trim());
            }

            //解析 等级
            if (!arr[1].trim().isEmpty()) {
            config.level =  Integer.parseInt(arr[1].trim());
            }

            //解析 生命
            if (!arr[2].trim().isEmpty()) {
            config.maxHP = arr[2].trim();
            }

            //解析 攻击
            if (!arr[3].trim().isEmpty()) {
            config.attack = arr[3].trim();
            }

            //解析 防御
            if (!arr[4].trim().isEmpty()) {
            config.defence = arr[4].trim();
            }

            //解析 速度
            if (!arr[5].trim().isEmpty()) {
            config.speed = arr[5].trim();
            }

            //解析 暴击
            if (!arr[6].trim().isEmpty()) {
            config.crit = arr[6].trim();
            }

            //解析 暴伤
            if (!arr[7].trim().isEmpty()) {
            config.critRatio = arr[7].trim();
            }

            //解析 命中
            if (!arr[8].trim().isEmpty()) {
            config.effectHit = arr[8].trim();
            }

            //解析 抵抗
            if (!arr[9].trim().isEmpty()) {
            config.effectDodge = arr[9].trim();
            }

            //解析 生命2
            if (!arr[10].trim().isEmpty()) {
            config.maxHP2 =  Integer.parseInt(arr[10].trim());
            }

            //解析 攻击2
            if (!arr[11].trim().isEmpty()) {
            config.attack2 =  Integer.parseInt(arr[11].trim());
            }

            //解析 防御2
            if (!arr[12].trim().isEmpty()) {
            config.defence2 =  Integer.parseInt(arr[12].trim());
            }

            //解析 速度2
            if (!arr[13].trim().isEmpty()) {
            config.speed2 =  Integer.parseInt(arr[13].trim());
            }

            //解析 暴击2
            if (!arr[14].trim().isEmpty()) {
            config.crit2 =  Integer.parseInt(arr[14].trim());
            }

            //解析 暴伤2
            if (!arr[15].trim().isEmpty()) {
            config.critRatio2 =  Integer.parseInt(arr[15].trim());
            }

            //解析 命中2
            if (!arr[16].trim().isEmpty()) {
            config.effectHit2 =  Integer.parseInt(arr[16].trim());
            }

            //解析 抵抗2
            if (!arr[17].trim().isEmpty()) {
            config.effectDodge2 =  Integer.parseInt(arr[17].trim());
            }

            //解析 生命3
            if (!arr[18].trim().isEmpty()) {
            config.maxHP3 =  Integer.parseInt(arr[18].trim());
            }

            //解析 攻击3
            if (!arr[19].trim().isEmpty()) {
            config.attack3 =  Integer.parseInt(arr[19].trim());
            }

            //解析 防御3
            if (!arr[20].trim().isEmpty()) {
            config.defence3 =  Integer.parseInt(arr[20].trim());
            }

            //解析 速度3
            if (!arr[21].trim().isEmpty()) {
            config.speed3 =  Integer.parseInt(arr[21].trim());
            }

            //解析 暴击3
            if (!arr[22].trim().isEmpty()) {
            config.crit3 =  Integer.parseInt(arr[22].trim());
            }

            //解析 暴伤3
            if (!arr[23].trim().isEmpty()) {
            config.critRatio3 =  Integer.parseInt(arr[23].trim());
            }

            //解析 命中3
            if (!arr[24].trim().isEmpty()) {
            config.effectHit3 =  Integer.parseInt(arr[24].trim());
            }

            //解析 抵抗3
            if (!arr[25].trim().isEmpty()) {
            config.effectDodge3 =  Integer.parseInt(arr[25].trim());
            }

            //解析 生命4
            if (!arr[26].trim().isEmpty()) {
            config.maxHP4 =  Integer.parseInt(arr[26].trim());
            }

            //解析 攻击4
            if (!arr[27].trim().isEmpty()) {
            config.attack4 =  Integer.parseInt(arr[27].trim());
            }

            //解析 防御4
            if (!arr[28].trim().isEmpty()) {
            config.defence4 =  Integer.parseInt(arr[28].trim());
            }

            //解析 速度4
            if (!arr[29].trim().isEmpty()) {
            config.speed4 =  Integer.parseInt(arr[29].trim());
            }

            //解析 暴击4
            if (!arr[30].trim().isEmpty()) {
            config.crit4 =  Integer.parseInt(arr[30].trim());
            }

            //解析 暴伤4
            if (!arr[31].trim().isEmpty()) {
            config.critRatio4 =  Integer.parseInt(arr[31].trim());
            }

            //解析 命中4
            if (!arr[32].trim().isEmpty()) {
            config.effectHit4 =  Integer.parseInt(arr[32].trim());
            }

            //解析 抵抗4
            if (!arr[33].trim().isEmpty()) {
            config.effectDodge4 =  Integer.parseInt(arr[33].trim());
            }

            //解析 生命5
            if (!arr[34].trim().isEmpty()) {
            config.maxHP5 =  Integer.parseInt(arr[34].trim());
            }

            //解析 攻击5
            if (!arr[35].trim().isEmpty()) {
            config.attack5 =  Integer.parseInt(arr[35].trim());
            }

            //解析 防御5
            if (!arr[36].trim().isEmpty()) {
            config.defence5 =  Integer.parseInt(arr[36].trim());
            }

            //解析 速度5
            if (!arr[37].trim().isEmpty()) {
            config.speed5 =  Integer.parseInt(arr[37].trim());
            }

            //解析 暴击5
            if (!arr[38].trim().isEmpty()) {
            config.crit5 =  Integer.parseInt(arr[38].trim());
            }

            //解析 暴伤5
            if (!arr[39].trim().isEmpty()) {
            config.critRatio5 =  Integer.parseInt(arr[39].trim());
            }

            //解析 命中5
            if (!arr[40].trim().isEmpty()) {
            config.effectHit5 =  Integer.parseInt(arr[40].trim());
            }

            //解析 抵抗5
            if (!arr[41].trim().isEmpty()) {
            config.effectDodge5 =  Integer.parseInt(arr[41].trim());
            }

            //解析 生命6
            if (!arr[42].trim().isEmpty()) {
            config.maxHP6 =  Integer.parseInt(arr[42].trim());
            }

            //解析 攻击6
            if (!arr[43].trim().isEmpty()) {
            config.attack6 =  Integer.parseInt(arr[43].trim());
            }

            //解析 防御6
            if (!arr[44].trim().isEmpty()) {
            config.defence6 =  Integer.parseInt(arr[44].trim());
            }

            //解析 速度6
            if (!arr[45].trim().isEmpty()) {
            config.speed6 =  Integer.parseInt(arr[45].trim());
            }

            //解析 暴击6
            if (!arr[46].trim().isEmpty()) {
            config.crit6 =  Integer.parseInt(arr[46].trim());
            }

            //解析 暴伤6
            if (!arr[47].trim().isEmpty()) {
            config.critRatio6 =  Integer.parseInt(arr[47].trim());
            }

            //解析 命中6
            if (!arr[48].trim().isEmpty()) {
            config.effectHit6 =  Integer.parseInt(arr[48].trim());
            }

            //解析 抵抗6
            if (!arr[49].trim().isEmpty()) {
            config.effectDodge6 =  Integer.parseInt(arr[49].trim());
            }

            //解析 生命7
            if (!arr[50].trim().isEmpty()) {
            config.maxHP7 =  Integer.parseInt(arr[50].trim());
            }

            //解析 攻击7
            if (!arr[51].trim().isEmpty()) {
            config.attack7 =  Integer.parseInt(arr[51].trim());
            }

            //解析 防御7
            if (!arr[52].trim().isEmpty()) {
            config.defence7 =  Integer.parseInt(arr[52].trim());
            }

            //解析 速度7
            if (!arr[53].trim().isEmpty()) {
            config.speed7 =  Integer.parseInt(arr[53].trim());
            }

            //解析 暴击7
            if (!arr[54].trim().isEmpty()) {
            config.crit7 =  Integer.parseInt(arr[54].trim());
            }

            //解析 暴伤7
            if (!arr[55].trim().isEmpty()) {
            config.critRatio7 =  Integer.parseInt(arr[55].trim());
            }

            //解析 命中7
            if (!arr[56].trim().isEmpty()) {
            config.effectHit7 =  Integer.parseInt(arr[56].trim());
            }

            //解析 抵抗7
            if (!arr[57].trim().isEmpty()) {
            config.effectDodge7 =  Integer.parseInt(arr[57].trim());
            }

            //解析 生命8
            if (!arr[58].trim().isEmpty()) {
            config.maxHP8 =  Integer.parseInt(arr[58].trim());
            }

            //解析 攻击8
            if (!arr[59].trim().isEmpty()) {
            config.attack8 =  Integer.parseInt(arr[59].trim());
            }

            //解析 防御8
            if (!arr[60].trim().isEmpty()) {
            config.defence8 =  Integer.parseInt(arr[60].trim());
            }

            //解析 速度8
            if (!arr[61].trim().isEmpty()) {
            config.speed8 =  Integer.parseInt(arr[61].trim());
            }

            //解析 暴击8
            if (!arr[62].trim().isEmpty()) {
            config.crit8 =  Integer.parseInt(arr[62].trim());
            }

            //解析 暴伤8
            if (!arr[63].trim().isEmpty()) {
            config.critRatio8 =  Integer.parseInt(arr[63].trim());
            }

            //解析 命中8
            if (!arr[64].trim().isEmpty()) {
            config.effectHit8 =  Integer.parseInt(arr[64].trim());
            }

            //解析 抵抗8
            if (!arr[65].trim().isEmpty()) {
            config.effectDodge8 =  Integer.parseInt(arr[65].trim());
            }

            //解析 生命9
            if (!arr[66].trim().isEmpty()) {
            config.maxHP9 =  Integer.parseInt(arr[66].trim());
            }

            //解析 攻击9
            if (!arr[67].trim().isEmpty()) {
            config.attack9 =  Integer.parseInt(arr[67].trim());
            }

            //解析 防御9
            if (!arr[68].trim().isEmpty()) {
            config.defence9 =  Integer.parseInt(arr[68].trim());
            }

            //解析 速度9
            if (!arr[69].trim().isEmpty()) {
            config.speed9 =  Integer.parseInt(arr[69].trim());
            }

            //解析 暴击9
            if (!arr[70].trim().isEmpty()) {
            config.crit9 =  Integer.parseInt(arr[70].trim());
            }

            //解析 暴伤9
            if (!arr[71].trim().isEmpty()) {
            config.critRatio9 =  Integer.parseInt(arr[71].trim());
            }

            //解析 命中9
            if (!arr[72].trim().isEmpty()) {
            config.effectHit9 =  Integer.parseInt(arr[72].trim());
            }

            //解析 抵抗9
            if (!arr[73].trim().isEmpty()) {
            config.effectDodge9 =  Integer.parseInt(arr[73].trim());
            }

            //解析 生命10
            if (!arr[74].trim().isEmpty()) {
            config.maxHP10 =  Integer.parseInt(arr[74].trim());
            }

            //解析 攻击10
            if (!arr[75].trim().isEmpty()) {
            config.attack10 =  Integer.parseInt(arr[75].trim());
            }

            //解析 防御10
            if (!arr[76].trim().isEmpty()) {
            config.defence10 =  Integer.parseInt(arr[76].trim());
            }

            //解析 速度10
            if (!arr[77].trim().isEmpty()) {
            config.speed10 =  Integer.parseInt(arr[77].trim());
            }

            //解析 暴击10
            if (!arr[78].trim().isEmpty()) {
            config.crit10 =  Integer.parseInt(arr[78].trim());
            }

            //解析 暴伤10
            if (!arr[79].trim().isEmpty()) {
            config.critRatio10 =  Integer.parseInt(arr[79].trim());
            }

            //解析 命中10
            if (!arr[80].trim().isEmpty()) {
            config.effectHit10 =  Integer.parseInt(arr[80].trim());
            }

            //解析 抵抗10
            if (!arr[81].trim().isEmpty()) {
            config.effectDodge10 =  Integer.parseInt(arr[81].trim());
            }

            //解析 回能
            if (!arr[82].trim().isEmpty()) {
            config.spCoe = arr[82].trim();
            }

            //解析 回能2
            if (!arr[83].trim().isEmpty()) {
            config.spCoe2 =  Integer.parseInt(arr[83].trim());
            }

            //解析 回能3
            if (!arr[84].trim().isEmpty()) {
            config.spCoe3 =  Integer.parseInt(arr[84].trim());
            }

            //解析 回能4
            if (!arr[85].trim().isEmpty()) {
            config.spCoe4 =  Integer.parseInt(arr[85].trim());
            }

            //解析 回能5
            if (!arr[86].trim().isEmpty()) {
            config.spCoe5 =  Integer.parseInt(arr[86].trim());
            }

            //解析 回能6
            if (!arr[87].trim().isEmpty()) {
            config.spCoe6 =  Integer.parseInt(arr[87].trim());
            }

            //解析 回能7
            if (!arr[88].trim().isEmpty()) {
            config.spCoe7 =  Integer.parseInt(arr[88].trim());
            }

            //解析 回能8
            if (!arr[89].trim().isEmpty()) {
            config.spCoe8 =  Integer.parseInt(arr[89].trim());
            }

            //解析 回能9
            if (!arr[90].trim().isEmpty()) {
            config.spCoe9 =  Integer.parseInt(arr[90].trim());
            }

            //解析 回能10
            if (!arr[91].trim().isEmpty()) {
            config.spCoe10 =  Integer.parseInt(arr[91].trim());
            }


          } catch (Exception e) {
            logger.error(
                String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, line, e.getMessage()));
            e.printStackTrace();
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          config.afterLoad();
          configList.add(config);
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

    public List<HeroAttrConfig> getConfigList() {
      return configList;
    }


    @Override
    public String getConfigFileName() {
      return "heroAttr.txt";
    }

    // @@@@@自定义方法开始区@@@@@
    @Override
    protected void afterLoad() {

    }

    // @@@@@自定义方法结束区@@@@@
  }
}
