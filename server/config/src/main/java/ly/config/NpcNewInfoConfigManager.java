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
 * File: NpcNewInfoConfigManager
 */
public class NpcNewInfoConfigManager implements InterfaceConfigManagerProxy {
  AtomicBoolean switched = new AtomicBoolean(false);
  private static final NpcNewInfoConfigManager instance = new NpcNewInfoConfigManager();
  private static final NpcNewInfoConfigManagerImpl instanceImplA =
      new NpcNewInfoConfigManagerImpl();
  private static final NpcNewInfoConfigManagerImpl instanceImplB =
      new NpcNewInfoConfigManagerImpl();

  public boolean isSwitched() {
    return switched.get();
  }

  public static NpcNewInfoConfigManagerImpl getInstance() {
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

  public static class NpcNewInfoConfigManagerImpl extends AbstractConfigManger {

    List<NpcNewInfoConfig> configList = new ArrayList<NpcNewInfoConfig>();
    Map<Integer, NpcNewInfoConfig> configMap = new HashMap<Integer, NpcNewInfoConfig>();


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
          NpcNewInfoConfig config = new NpcNewInfoConfig();
          try {
            //解析 编号
            if (!arr[0].trim().isEmpty()) {
            config.id =  Integer.parseInt(arr[0].trim());
            }

            //解析 名字
            if (!arr[1].trim().isEmpty()) {
            config.beizhu = arr[1].trim();
            }

            //解析 源核类型
            if (!arr[2].trim().isEmpty()) {
            config.circuitName = arr[2].trim();
            }

            //解析 名字
            if (!arr[3].trim().isEmpty()) {
            config.name = arr[3].trim();
            }

            //解析 类型
            if (!arr[4].trim().isEmpty()) {
            config.genType =  Integer.parseInt(arr[4].trim());
            }

            //解析 索引id
            if (!arr[5].trim().isEmpty()) {
            config.checkId =  Integer.parseInt(arr[5].trim());
            }

            //解析 等级
            if (!arr[6].trim().isEmpty()) {
            config.level =  Integer.parseInt(arr[6].trim());
            }

            //解析 星级
            if (!arr[7].trim().isEmpty()) {
            config.star =  Integer.parseInt(arr[7].trim());
            }

            //解析 进阶等级
            if (!arr[8].trim().isEmpty()) {
            config.advance =  Integer.parseInt(arr[8].trim());
            }

            //解析 觉醒等级
            if (!arr[9].trim().isEmpty()) {
            config.awakenLv =  Integer.parseInt(arr[9].trim());
            }

            //解析 技能等级
            if (!arr[10].trim().isEmpty()) {
            config.skillLv = arr[10].trim();
            }

            //解析 S技能等级
            if (!arr[11].trim().isEmpty()) {
            config.sSkillLv = arr[11].trim();
            }

            //解析 源核等级
            if (!arr[12].trim().isEmpty()) {
            config.circuitLv =  Integer.parseInt(arr[12].trim());
            }

            //解析 源核品质
            if (!arr[13].trim().isEmpty()) {
            config.circuitQuality =  Integer.parseInt(arr[13].trim());
            }

            //解析 源核模板
            if (!arr[14].trim().isEmpty()) {
            config.circuitInfo =  Integer.parseInt(arr[14].trim());
            }

            //解析 生命上限系数
            if (!arr[15].trim().isEmpty()) {
            config.maxHPCoe =  Integer.parseInt(arr[15].trim());
            }

            //解析 攻击系数
            if (!arr[16].trim().isEmpty()) {
            config.attackCoe =  Integer.parseInt(arr[16].trim());
            }

            //解析 防御系数
            if (!arr[17].trim().isEmpty()) {
            config.defenceCoe =  Integer.parseInt(arr[17].trim());
            }

            //解析 速度系数
            if (!arr[18].trim().isEmpty()) {
            config.speedCoe =  Integer.parseInt(arr[18].trim());
            }

            //解析 暴击系数
            if (!arr[19].trim().isEmpty()) {
            config.critCoe =  Integer.parseInt(arr[19].trim());
            }

            //解析 暴击伤害系数
            if (!arr[20].trim().isEmpty()) {
            config.critRatioCoe =  Integer.parseInt(arr[20].trim());
            }

            //解析 效果命中系数
            if (!arr[21].trim().isEmpty()) {
            config.effectHitCoe =  Integer.parseInt(arr[21].trim());
            }

            //解析 效果抵抗系数
            if (!arr[22].trim().isEmpty()) {
            config.effectDodgeCoe =  Integer.parseInt(arr[22].trim());
            }

            //解析 技能列表
            if (!arr[23].trim().isEmpty()) {
            config.skills = arr[23].trim();
            }

            //解析 S技能列表
            if (!arr[24].trim().isEmpty()) {
            config.sSkills = arr[24].trim();
            }

            //解析 AI模板
            if (!arr[25].trim().isEmpty()) {
            config.aiName = arr[25].trim();
            }

            //解析 是否boss
            if (!arr[26].trim().isEmpty()) {
            config.isBoss =  Integer.parseInt(arr[26].trim());
            }

            //解析 能否逃跑
            if (!arr[27].trim().isEmpty()) {
            config.canRun = null;
            }

            //解析 AI技能CD
            if (!arr[28].trim().isEmpty()) {
            config.extraSkillInfo = arr[28].trim();
            }

            //解析 NPC类别
            if (!arr[29].trim().isEmpty()) {
            config.npcType =  Integer.parseInt(arr[29].trim());
            }

            //解析 NPC标记
            if (!arr[30].trim().isEmpty()) {
            config.entityTags =  Integer.parseInt(arr[30].trim());
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

    public List<NpcNewInfoConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, NpcNewInfoConfig> getConfigMap() {
      return configMap;
    }
    @Override
    public String getConfigFileName() {
      return "npcNewInfo.txt";
    }

    // @@@@@自定义方法开始区@@@@@
    @Override
    protected void afterLoad() {

    }

    // @@@@@自定义方法结束区@@@@@
  }
}
