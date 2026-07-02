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
 * File: HeroAttrConfigManager
 */
public class HeroAttrConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final HeroAttrConfigManager instance = new HeroAttrConfigManager();
  private static final HeroAttrConfigManagerImpl instanceImplA = new HeroAttrConfigManagerImpl();
  private static final HeroAttrConfigManagerImpl instanceImplB = new HeroAttrConfigManagerImpl();

  public static HeroAttrConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static HeroAttrConfigManagerImpl getStandby() {
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
    HeroAttrConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class HeroAttrConfigManagerImpl extends AbstractConfigManger {
    private List<HeroAttrConfig> configList = List.of();
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
      HeroAttrConfigChecker checker = new HeroAttrConfigChecker();
      checker.checkHeader(logger, configDir);
      List<HeroAttrConfig> newList = new ArrayList<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 92) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int modelName = 0;
          int level = 0;
          String maxHP = null;
          String attack = null;
          String defence = null;
          String speed = null;
          String crit = null;
          String critRatio = null;
          String effectHit = null;
          String effectDodge = null;
          int maxHP2 = 0;
          int attack2 = 0;
          int defence2 = 0;
          int speed2 = 0;
          int crit2 = 0;
          int critRatio2 = 0;
          int effectHit2 = 0;
          int effectDodge2 = 0;
          int maxHP3 = 0;
          int attack3 = 0;
          int defence3 = 0;
          int speed3 = 0;
          int crit3 = 0;
          int critRatio3 = 0;
          int effectHit3 = 0;
          int effectDodge3 = 0;
          int maxHP4 = 0;
          int attack4 = 0;
          int defence4 = 0;
          int speed4 = 0;
          int crit4 = 0;
          int critRatio4 = 0;
          int effectHit4 = 0;
          int effectDodge4 = 0;
          int maxHP5 = 0;
          int attack5 = 0;
          int defence5 = 0;
          int speed5 = 0;
          int crit5 = 0;
          int critRatio5 = 0;
          int effectHit5 = 0;
          int effectDodge5 = 0;
          int maxHP6 = 0;
          int attack6 = 0;
          int defence6 = 0;
          int speed6 = 0;
          int crit6 = 0;
          int critRatio6 = 0;
          int effectHit6 = 0;
          int effectDodge6 = 0;
          int maxHP7 = 0;
          int attack7 = 0;
          int defence7 = 0;
          int speed7 = 0;
          int crit7 = 0;
          int critRatio7 = 0;
          int effectHit7 = 0;
          int effectDodge7 = 0;
          int maxHP8 = 0;
          int attack8 = 0;
          int defence8 = 0;
          int speed8 = 0;
          int crit8 = 0;
          int critRatio8 = 0;
          int effectHit8 = 0;
          int effectDodge8 = 0;
          int maxHP9 = 0;
          int attack9 = 0;
          int defence9 = 0;
          int speed9 = 0;
          int crit9 = 0;
          int critRatio9 = 0;
          int effectHit9 = 0;
          int effectDodge9 = 0;
          int maxHP10 = 0;
          int attack10 = 0;
          int defence10 = 0;
          int speed10 = 0;
          int crit10 = 0;
          int critRatio10 = 0;
          int effectHit10 = 0;
          int effectDodge10 = 0;
          String spCoe = null;
          int spCoe2 = 0;
          int spCoe3 = 0;
          int spCoe4 = 0;
          int spCoe5 = 0;
          int spCoe6 = 0;
          int spCoe7 = 0;
          int spCoe8 = 0;
          int spCoe9 = 0;
          int spCoe10 = 0;
          try {
            // 解析 模板名
            if (!arr[0].trim().isEmpty()) {
              modelName = Integer.parseInt(arr[0].trim());
            }

            // 解析 等级
            if (!arr[1].trim().isEmpty()) {
              level = Integer.parseInt(arr[1].trim());
            }

            // 解析 生命
            if (!arr[2].trim().isEmpty()) {
              maxHP = arr[2].trim();
            }

            // 解析 攻击
            if (!arr[3].trim().isEmpty()) {
              attack = arr[3].trim();
            }

            // 解析 防御
            if (!arr[4].trim().isEmpty()) {
              defence = arr[4].trim();
            }

            // 解析 速度
            if (!arr[5].trim().isEmpty()) {
              speed = arr[5].trim();
            }

            // 解析 暴击
            if (!arr[6].trim().isEmpty()) {
              crit = arr[6].trim();
            }

            // 解析 暴伤
            if (!arr[7].trim().isEmpty()) {
              critRatio = arr[7].trim();
            }

            // 解析 命中
            if (!arr[8].trim().isEmpty()) {
              effectHit = arr[8].trim();
            }

            // 解析 抵抗
            if (!arr[9].trim().isEmpty()) {
              effectDodge = arr[9].trim();
            }

            // 解析 生命2
            if (!arr[10].trim().isEmpty()) {
              maxHP2 = Integer.parseInt(arr[10].trim());
            }

            // 解析 攻击2
            if (!arr[11].trim().isEmpty()) {
              attack2 = Integer.parseInt(arr[11].trim());
            }

            // 解析 防御2
            if (!arr[12].trim().isEmpty()) {
              defence2 = Integer.parseInt(arr[12].trim());
            }

            // 解析 速度2
            if (!arr[13].trim().isEmpty()) {
              speed2 = Integer.parseInt(arr[13].trim());
            }

            // 解析 暴击2
            if (!arr[14].trim().isEmpty()) {
              crit2 = Integer.parseInt(arr[14].trim());
            }

            // 解析 暴伤2
            if (!arr[15].trim().isEmpty()) {
              critRatio2 = Integer.parseInt(arr[15].trim());
            }

            // 解析 命中2
            if (!arr[16].trim().isEmpty()) {
              effectHit2 = Integer.parseInt(arr[16].trim());
            }

            // 解析 抵抗2
            if (!arr[17].trim().isEmpty()) {
              effectDodge2 = Integer.parseInt(arr[17].trim());
            }

            // 解析 生命3
            if (!arr[18].trim().isEmpty()) {
              maxHP3 = Integer.parseInt(arr[18].trim());
            }

            // 解析 攻击3
            if (!arr[19].trim().isEmpty()) {
              attack3 = Integer.parseInt(arr[19].trim());
            }

            // 解析 防御3
            if (!arr[20].trim().isEmpty()) {
              defence3 = Integer.parseInt(arr[20].trim());
            }

            // 解析 速度3
            if (!arr[21].trim().isEmpty()) {
              speed3 = Integer.parseInt(arr[21].trim());
            }

            // 解析 暴击3
            if (!arr[22].trim().isEmpty()) {
              crit3 = Integer.parseInt(arr[22].trim());
            }

            // 解析 暴伤3
            if (!arr[23].trim().isEmpty()) {
              critRatio3 = Integer.parseInt(arr[23].trim());
            }

            // 解析 命中3
            if (!arr[24].trim().isEmpty()) {
              effectHit3 = Integer.parseInt(arr[24].trim());
            }

            // 解析 抵抗3
            if (!arr[25].trim().isEmpty()) {
              effectDodge3 = Integer.parseInt(arr[25].trim());
            }

            // 解析 生命4
            if (!arr[26].trim().isEmpty()) {
              maxHP4 = Integer.parseInt(arr[26].trim());
            }

            // 解析 攻击4
            if (!arr[27].trim().isEmpty()) {
              attack4 = Integer.parseInt(arr[27].trim());
            }

            // 解析 防御4
            if (!arr[28].trim().isEmpty()) {
              defence4 = Integer.parseInt(arr[28].trim());
            }

            // 解析 速度4
            if (!arr[29].trim().isEmpty()) {
              speed4 = Integer.parseInt(arr[29].trim());
            }

            // 解析 暴击4
            if (!arr[30].trim().isEmpty()) {
              crit4 = Integer.parseInt(arr[30].trim());
            }

            // 解析 暴伤4
            if (!arr[31].trim().isEmpty()) {
              critRatio4 = Integer.parseInt(arr[31].trim());
            }

            // 解析 命中4
            if (!arr[32].trim().isEmpty()) {
              effectHit4 = Integer.parseInt(arr[32].trim());
            }

            // 解析 抵抗4
            if (!arr[33].trim().isEmpty()) {
              effectDodge4 = Integer.parseInt(arr[33].trim());
            }

            // 解析 生命5
            if (!arr[34].trim().isEmpty()) {
              maxHP5 = Integer.parseInt(arr[34].trim());
            }

            // 解析 攻击5
            if (!arr[35].trim().isEmpty()) {
              attack5 = Integer.parseInt(arr[35].trim());
            }

            // 解析 防御5
            if (!arr[36].trim().isEmpty()) {
              defence5 = Integer.parseInt(arr[36].trim());
            }

            // 解析 速度5
            if (!arr[37].trim().isEmpty()) {
              speed5 = Integer.parseInt(arr[37].trim());
            }

            // 解析 暴击5
            if (!arr[38].trim().isEmpty()) {
              crit5 = Integer.parseInt(arr[38].trim());
            }

            // 解析 暴伤5
            if (!arr[39].trim().isEmpty()) {
              critRatio5 = Integer.parseInt(arr[39].trim());
            }

            // 解析 命中5
            if (!arr[40].trim().isEmpty()) {
              effectHit5 = Integer.parseInt(arr[40].trim());
            }

            // 解析 抵抗5
            if (!arr[41].trim().isEmpty()) {
              effectDodge5 = Integer.parseInt(arr[41].trim());
            }

            // 解析 生命6
            if (!arr[42].trim().isEmpty()) {
              maxHP6 = Integer.parseInt(arr[42].trim());
            }

            // 解析 攻击6
            if (!arr[43].trim().isEmpty()) {
              attack6 = Integer.parseInt(arr[43].trim());
            }

            // 解析 防御6
            if (!arr[44].trim().isEmpty()) {
              defence6 = Integer.parseInt(arr[44].trim());
            }

            // 解析 速度6
            if (!arr[45].trim().isEmpty()) {
              speed6 = Integer.parseInt(arr[45].trim());
            }

            // 解析 暴击6
            if (!arr[46].trim().isEmpty()) {
              crit6 = Integer.parseInt(arr[46].trim());
            }

            // 解析 暴伤6
            if (!arr[47].trim().isEmpty()) {
              critRatio6 = Integer.parseInt(arr[47].trim());
            }

            // 解析 命中6
            if (!arr[48].trim().isEmpty()) {
              effectHit6 = Integer.parseInt(arr[48].trim());
            }

            // 解析 抵抗6
            if (!arr[49].trim().isEmpty()) {
              effectDodge6 = Integer.parseInt(arr[49].trim());
            }

            // 解析 生命7
            if (!arr[50].trim().isEmpty()) {
              maxHP7 = Integer.parseInt(arr[50].trim());
            }

            // 解析 攻击7
            if (!arr[51].trim().isEmpty()) {
              attack7 = Integer.parseInt(arr[51].trim());
            }

            // 解析 防御7
            if (!arr[52].trim().isEmpty()) {
              defence7 = Integer.parseInt(arr[52].trim());
            }

            // 解析 速度7
            if (!arr[53].trim().isEmpty()) {
              speed7 = Integer.parseInt(arr[53].trim());
            }

            // 解析 暴击7
            if (!arr[54].trim().isEmpty()) {
              crit7 = Integer.parseInt(arr[54].trim());
            }

            // 解析 暴伤7
            if (!arr[55].trim().isEmpty()) {
              critRatio7 = Integer.parseInt(arr[55].trim());
            }

            // 解析 命中7
            if (!arr[56].trim().isEmpty()) {
              effectHit7 = Integer.parseInt(arr[56].trim());
            }

            // 解析 抵抗7
            if (!arr[57].trim().isEmpty()) {
              effectDodge7 = Integer.parseInt(arr[57].trim());
            }

            // 解析 生命8
            if (!arr[58].trim().isEmpty()) {
              maxHP8 = Integer.parseInt(arr[58].trim());
            }

            // 解析 攻击8
            if (!arr[59].trim().isEmpty()) {
              attack8 = Integer.parseInt(arr[59].trim());
            }

            // 解析 防御8
            if (!arr[60].trim().isEmpty()) {
              defence8 = Integer.parseInt(arr[60].trim());
            }

            // 解析 速度8
            if (!arr[61].trim().isEmpty()) {
              speed8 = Integer.parseInt(arr[61].trim());
            }

            // 解析 暴击8
            if (!arr[62].trim().isEmpty()) {
              crit8 = Integer.parseInt(arr[62].trim());
            }

            // 解析 暴伤8
            if (!arr[63].trim().isEmpty()) {
              critRatio8 = Integer.parseInt(arr[63].trim());
            }

            // 解析 命中8
            if (!arr[64].trim().isEmpty()) {
              effectHit8 = Integer.parseInt(arr[64].trim());
            }

            // 解析 抵抗8
            if (!arr[65].trim().isEmpty()) {
              effectDodge8 = Integer.parseInt(arr[65].trim());
            }

            // 解析 生命9
            if (!arr[66].trim().isEmpty()) {
              maxHP9 = Integer.parseInt(arr[66].trim());
            }

            // 解析 攻击9
            if (!arr[67].trim().isEmpty()) {
              attack9 = Integer.parseInt(arr[67].trim());
            }

            // 解析 防御9
            if (!arr[68].trim().isEmpty()) {
              defence9 = Integer.parseInt(arr[68].trim());
            }

            // 解析 速度9
            if (!arr[69].trim().isEmpty()) {
              speed9 = Integer.parseInt(arr[69].trim());
            }

            // 解析 暴击9
            if (!arr[70].trim().isEmpty()) {
              crit9 = Integer.parseInt(arr[70].trim());
            }

            // 解析 暴伤9
            if (!arr[71].trim().isEmpty()) {
              critRatio9 = Integer.parseInt(arr[71].trim());
            }

            // 解析 命中9
            if (!arr[72].trim().isEmpty()) {
              effectHit9 = Integer.parseInt(arr[72].trim());
            }

            // 解析 抵抗9
            if (!arr[73].trim().isEmpty()) {
              effectDodge9 = Integer.parseInt(arr[73].trim());
            }

            // 解析 生命10
            if (!arr[74].trim().isEmpty()) {
              maxHP10 = Integer.parseInt(arr[74].trim());
            }

            // 解析 攻击10
            if (!arr[75].trim().isEmpty()) {
              attack10 = Integer.parseInt(arr[75].trim());
            }

            // 解析 防御10
            if (!arr[76].trim().isEmpty()) {
              defence10 = Integer.parseInt(arr[76].trim());
            }

            // 解析 速度10
            if (!arr[77].trim().isEmpty()) {
              speed10 = Integer.parseInt(arr[77].trim());
            }

            // 解析 暴击10
            if (!arr[78].trim().isEmpty()) {
              crit10 = Integer.parseInt(arr[78].trim());
            }

            // 解析 暴伤10
            if (!arr[79].trim().isEmpty()) {
              critRatio10 = Integer.parseInt(arr[79].trim());
            }

            // 解析 命中10
            if (!arr[80].trim().isEmpty()) {
              effectHit10 = Integer.parseInt(arr[80].trim());
            }

            // 解析 抵抗10
            if (!arr[81].trim().isEmpty()) {
              effectDodge10 = Integer.parseInt(arr[81].trim());
            }

            // 解析 回能
            if (!arr[82].trim().isEmpty()) {
              spCoe = arr[82].trim();
            }

            // 解析 回能2
            if (!arr[83].trim().isEmpty()) {
              spCoe2 = Integer.parseInt(arr[83].trim());
            }

            // 解析 回能3
            if (!arr[84].trim().isEmpty()) {
              spCoe3 = Integer.parseInt(arr[84].trim());
            }

            // 解析 回能4
            if (!arr[85].trim().isEmpty()) {
              spCoe4 = Integer.parseInt(arr[85].trim());
            }

            // 解析 回能5
            if (!arr[86].trim().isEmpty()) {
              spCoe5 = Integer.parseInt(arr[86].trim());
            }

            // 解析 回能6
            if (!arr[87].trim().isEmpty()) {
              spCoe6 = Integer.parseInt(arr[87].trim());
            }

            // 解析 回能7
            if (!arr[88].trim().isEmpty()) {
              spCoe7 = Integer.parseInt(arr[88].trim());
            }

            // 解析 回能8
            if (!arr[89].trim().isEmpty()) {
              spCoe8 = Integer.parseInt(arr[89].trim());
            }

            // 解析 回能9
            if (!arr[90].trim().isEmpty()) {
              spCoe9 = Integer.parseInt(arr[90].trim());
            }

            // 解析 回能10
            if (!arr[91].trim().isEmpty()) {
              spCoe10 = Integer.parseInt(arr[91].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          HeroAttrConfig config = new HeroAttrConfig(modelName, level, maxHP, attack, defence, speed, crit, critRatio, effectHit, effectDodge, maxHP2, attack2, defence2, speed2, crit2, critRatio2, effectHit2, effectDodge2, maxHP3, attack3, defence3, speed3, crit3, critRatio3, effectHit3, effectDodge3, maxHP4, attack4, defence4, speed4, crit4, critRatio4, effectHit4, effectDodge4, maxHP5, attack5, defence5, speed5, crit5, critRatio5, effectHit5, effectDodge5, maxHP6, attack6, defence6, speed6, crit6, critRatio6, effectHit6, effectDodge6, maxHP7, attack7, defence7, speed7, crit7, critRatio7, effectHit7, effectDodge7, maxHP8, attack8, defence8, speed8, crit8, critRatio8, effectHit8, effectDodge8, maxHP9, attack9, defence9, speed9, crit9, critRatio9, effectHit9, effectDodge9, maxHP10, attack10, defence10, speed10, crit10, critRatio10, effectHit10, effectDodge10, spCoe, spCoe2, spCoe3, spCoe4, spCoe5, spCoe6, spCoe7, spCoe8, spCoe9, spCoe10);
          config.afterLoad();
          newList.add(config);
        }
        checker.checkAfterParse(logger, newList);
        configList = List.copyOf(newList);
        afterLoad();
      } catch (IOException e) {
        throw new ConfigLoadException("Config file could not be read :" + fileName);
      }
    }

    @Override
    public void clear() {
      configList = List.of();
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
