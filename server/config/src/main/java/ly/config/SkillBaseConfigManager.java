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
 * File: SkillBaseConfigManager
 */
public class SkillBaseConfigManager implements InterfaceConfigManagerProxy {
  AtomicBoolean switched = new AtomicBoolean(false);
  private static final SkillBaseConfigManager instance = new SkillBaseConfigManager();
  private static final SkillBaseConfigManagerImpl instanceImplA =
      new SkillBaseConfigManagerImpl();
  private static final SkillBaseConfigManagerImpl instanceImplB =
      new SkillBaseConfigManagerImpl();

  public boolean isSwitched() {
    return switched.get();
  }

  public static SkillBaseConfigManagerImpl getInstance() {
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

  public static class SkillBaseConfigManagerImpl extends AbstractConfigManger {

    List<SkillBaseConfig> configList = new ArrayList<SkillBaseConfig>();
    Map<Integer, SkillBaseConfig> configMap = new HashMap<Integer, SkillBaseConfig>();


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
          SkillBaseConfig config = new SkillBaseConfig();
          try {
            //解析 技能ID
            if (!arr[0].trim().isEmpty()) {
            config.id =  Integer.parseInt(arr[0].trim());
            }

            //解析 技能名称
            if (!arr[1].trim().isEmpty()) {
            config.name = arr[1].trim();
            }

            //解析 技能描述
            if (!arr[2].trim().isEmpty()) {
            config.description = arr[2].trim();
            }

            //解析 升级描述
            if (!arr[3].trim().isEmpty()) {
            config.upgradeDes = arr[3].trim();
            }

            //解析 详情
            if (!arr[4].trim().isEmpty()) {
            config.detail = arr[4].trim();
            }

            //解析 是否bonus触发图标
            if (!arr[5].trim().isEmpty()) {
            config.bonusTrigger =  Integer.parseInt(arr[5].trim());
            }

            //解析 技能泡泡字本地化id
            if (!arr[6].trim().isEmpty()) {
            config.popLocId = arr[6].trim();
            }

            //解析 是否展示泡泡字
            if (!arr[7].trim().isEmpty()) {
            config.isPopShow =  Integer.parseInt(arr[7].trim());
            }

            //解析 泡泡字显示类型
            if (!arr[8].trim().isEmpty()) {
            config.popType =  Integer.parseInt(arr[8].trim());
            }

            //解析 技能图标
            if (!arr[9].trim().isEmpty()) {
            config.icon =  Integer.parseInt(arr[9].trim());
            }

            //解析 反查技能组id
            if (!arr[10].trim().isEmpty()) {
            config.showSGId =  Integer.parseInt(arr[10].trim());
            }

            //解析 技能模板id(服务器)
            if (!arr[11].trim().isEmpty()) {
            config.skillBase =  Integer.parseInt(arr[11].trim());
            }

            //解析 技能模板id
            if (!arr[12].trim().isEmpty()) {
            config.skillGroupId =  Integer.parseInt(arr[12].trim());
            }

            //解析 互斥类型
            if (!arr[13].trim().isEmpty()) {
            config.mutexType =  Integer.parseInt(arr[13].trim());
            }

            //解析 互斥优先级
            if (!arr[14].trim().isEmpty()) {
            config.priority =  Integer.parseInt(arr[14].trim());
            }

            //解析 组
            if (!arr[15].trim().isEmpty()) {
            config.group =  Integer.parseInt(arr[15].trim());
            }

            //解析 状态中是否触发
            if (!arr[16].trim().isEmpty()) {
            config.isTriggerInState =  Integer.parseInt(arr[16].trim());
            }

            //解析 转化条件
            if (!arr[17].trim().isEmpty()) {
            config.transType =  Integer.parseInt(arr[17].trim());
            }

            //解析 转化条件
            if (!arr[18].trim().isEmpty()) {
            config.transCondition = arr[18].trim();
            }

            //解析 转化技能
            if (!arr[19].trim().isEmpty()) {
            config.transSkills = arr[19].trim();
            }

            //解析 攻击类型
            if (!arr[20].trim().isEmpty()) {
            config.skillTargetType =  Integer.parseInt(arr[20].trim());
            }

            //解析 能否施法者死亡后释放
            if (!arr[21].trim().isEmpty()) {
            config.canZombie =  Integer.parseInt(arr[21].trim());
            }

            //解析 是否延迟死亡
            if (!arr[22].trim().isEmpty()) {
            config.isDelayDeath =  Integer.parseInt(arr[22].trim());
            }

            //解析 是否技能中死亡
            if (!arr[23].trim().isEmpty()) {
            config.isCasterDieInSkill =  Integer.parseInt(arr[23].trim());
            }

            //解析 是否为普攻
            if (!arr[24].trim().isEmpty()) {
            config.isBasic =  Integer.parseInt(arr[24].trim());
            }

            //解析 是否为攻击
            if (!arr[25].trim().isEmpty()) {
            config.isAttack =  Integer.parseInt(arr[25].trim());
            }

            //解析 是否为被动技能
            if (!arr[26].trim().isEmpty()) {
            config.isPassive =  Integer.parseInt(arr[26].trim());
            }

            //解析 是否为主动技能
            if (!arr[27].trim().isEmpty()) {
            config.isEnergy =  Integer.parseInt(arr[27].trim());
            }

            //解析 是否为触发技能
            if (!arr[28].trim().isEmpty()) {
            config.isTrigger =  Integer.parseInt(arr[28].trim());
            }

            //解析 是否为S技能
            if (!arr[29].trim().isEmpty()) {
            config.isSuper =  Integer.parseInt(arr[29].trim());
            }

            //解析 是否为召唤技能
            if (!arr[30].trim().isEmpty()) {
            config.isSummon =  Integer.parseInt(arr[30].trim());
            }

            //解析 能量消耗
            if (!arr[31].trim().isEmpty()) {
            config.consumeEnergy =  Integer.parseInt(arr[31].trim());
            }

            //解析 是否战斗开始生效
            if (!arr[32].trim().isEmpty()) {
            config.isUseAtStart =  Integer.parseInt(arr[32].trim());
            }

            //解析 冷却时间
            if (!arr[33].trim().isEmpty()) {
            config.cd =  Integer.parseInt(arr[33].trim());
            }

            //解析 行为树名称
            if (!arr[34].trim().isEmpty()) {
            config.behaviorTreeName = arr[34].trim();
            }

            //解析 技能等级
            if (!arr[35].trim().isEmpty()) {
            config.skillLv =  Integer.parseInt(arr[35].trim());
            }

            //解析 升级物品
            if (!arr[36].trim().isEmpty()) {
            config.upgradeItems = arr[36].trim();
            }

            //解析 升级替换物
            if (!arr[37].trim().isEmpty()) {
            config.replaceItems =  Integer.parseInt(arr[37].trim());
            }

            //解析 标签
            if (!arr[38].trim().isEmpty()) {
            config.flags = arr[38].trim();
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

    public List<SkillBaseConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SkillBaseConfig> getConfigMap() {
      return configMap;
    }
    @Override
    public String getConfigFileName() {
      return "skillBase.txt";
    }

    // @@@@@自定义方法开始区@@@@@
    @Override
    protected void afterLoad() {

    }

    // @@@@@自定义方法结束区@@@@@
  }
}
