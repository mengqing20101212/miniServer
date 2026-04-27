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
 * File: NpcConfigManager
 */
public class NpcConfigManager implements InterfaceConfigManagerProxy {
  AtomicBoolean switched = new AtomicBoolean(false);
  private static final NpcConfigManager instance = new NpcConfigManager();
  private static final NpcConfigManagerImpl instanceImplA =
      new NpcConfigManagerImpl();
  private static final NpcConfigManagerImpl instanceImplB =
      new NpcConfigManagerImpl();

  public boolean isSwitched() {
    return switched.get();
  }

  public static NpcConfigManagerImpl getInstance() {
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

  public static class NpcConfigManagerImpl extends AbstractConfigManger {

    List<NpcConfig> configList = new ArrayList<NpcConfig>();
    Map<Integer, NpcConfig> configMap = new HashMap<Integer, NpcConfig>();


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
          NpcConfig config = new NpcConfig();
          try {
            //解析 编号
            if (!arr[0].trim().isEmpty()) {
            config.id =  Integer.parseInt(arr[0].trim());
            }

            //解析 名字
            if (!arr[1].trim().isEmpty()) {
            config.beizhu = arr[1].trim();
            }

            //解析 名字
            if (!arr[2].trim().isEmpty()) {
            config.name = arr[2].trim();
            }

            //解析 等级
            if (!arr[3].trim().isEmpty()) {
            config.level =  Integer.parseInt(arr[3].trim());
            }

            //解析 星级
            if (!arr[4].trim().isEmpty()) {
            config.star =  Integer.parseInt(arr[4].trim());
            }

            //解析 生命上限
            if (!arr[5].trim().isEmpty()) {
            config.maxHP =  Integer.parseInt(arr[5].trim());
            }

            //解析 攻击
            if (!arr[6].trim().isEmpty()) {
            config.attack =  Integer.parseInt(arr[6].trim());
            }

            //解析 防御
            if (!arr[7].trim().isEmpty()) {
            config.defence =  Integer.parseInt(arr[7].trim());
            }

            //解析 速度
            if (!arr[8].trim().isEmpty()) {
            config.speed =  Integer.parseInt(arr[8].trim());
            }

            //解析 暴击
            if (!arr[9].trim().isEmpty()) {
            config.crit =  Integer.parseInt(arr[9].trim());
            }

            //解析 暴击伤害
            if (!arr[10].trim().isEmpty()) {
            config.critRatio =  Integer.parseInt(arr[10].trim());
            }

            //解析 效果命中
            if (!arr[11].trim().isEmpty()) {
            config.effectHit =  Integer.parseInt(arr[11].trim());
            }

            //解析 效果抵抗
            if (!arr[12].trim().isEmpty()) {
            config.effectDodge =  Integer.parseInt(arr[12].trim());
            }

            //解析 回能
            if (!arr[13].trim().isEmpty()) {
            config.spCoe =  Integer.parseInt(arr[13].trim());
            }

            //解析 常规技能
            if (!arr[14].trim().isEmpty()) {
            config.skill_1 =  Integer.parseInt(arr[14].trim());
            }

            //解析   null
            if (!arr[15].trim().isEmpty()) {
            config.skill_2 =  Integer.parseInt(arr[15].trim());
            }

            //解析   null
            if (!arr[16].trim().isEmpty()) {
            config.skill_3 =  Integer.parseInt(arr[16].trim());
            }

            //解析 超级技能
            if (!arr[17].trim().isEmpty()) {
            config.skill_s1 =  Integer.parseInt(arr[17].trim());
            }

            //解析 超级技能
            if (!arr[18].trim().isEmpty()) {
            config.skill_s2 =  Integer.parseInt(arr[18].trim());
            }

            //解析 AI模板
            if (!arr[19].trim().isEmpty()) {
            config.aiName = arr[19].trim();
            }

            //解析 模型id
            if (!arr[20].trim().isEmpty()) {
            config.modelId =  Integer.parseInt(arr[20].trim());
            }

            //解析 右侧头像
            if (!arr[21].trim().isEmpty()) {
            config.headResource_3 =  Integer.parseInt(arr[21].trim());
            }

            //解析 立绘头像
            if (!arr[22].trim().isEmpty()) {
            config.headResource_4 =  Integer.parseInt(arr[22].trim());
            }

            //解析 s技能立绘切割坐标
            if (!arr[23].trim().isEmpty()) {
            config.sSkillCutUp = arr[23].trim();
            }

            //解析 是否boss
            if (!arr[24].trim().isEmpty()) {
            config.isBoss =  Integer.parseInt(arr[24].trim());
            }

            //解析 能否逃跑
            if (!arr[25].trim().isEmpty()) {
            config.canRun = null;
            }

            //解析 关联hero
            if (!arr[26].trim().isEmpty()) {
            config.relateId =  Integer.parseInt(arr[26].trim());
            }

            //解析 AI技能CD
            if (!arr[27].trim().isEmpty()) {
            config.extraSkillInfo = arr[27].trim();
            }

            //解析 是否强制使用AI技能CD
            if (!arr[28].trim().isEmpty()) {
            config.forcedAICD =  Integer.parseInt(arr[28].trim());
            }

            //解析 技能列表
            if (!arr[29].trim().isEmpty()) {
            config.skills = arr[29].trim();
            }

            //解析 S技能列表
            if (!arr[30].trim().isEmpty()) {
            config.sSkills = arr[30].trim();
            }

            //解析 NPC类别
            if (!arr[31].trim().isEmpty()) {
            config.npcType =  Integer.parseInt(arr[31].trim());
            }

            //解析 NPC标记
            if (!arr[32].trim().isEmpty()) {
            config.entityTags =  Integer.parseInt(arr[32].trim());
            }

            //解析 s技能跳过立绘切割坐标
            if (!arr[33].trim().isEmpty()) {
            config.sSkipCutUp = arr[33].trim();
            }

            //解析 变色类别
            if (!arr[34].trim().isEmpty()) {
            config.changeColorInfo = arr[34].trim();
            }

            //解析 颜色类型
            if (!arr[35].trim().isEmpty()) {
            config.colorType =  Integer.parseInt(arr[35].trim());
            }

            //解析 菲尼尔颜色
            if (!arr[36].trim().isEmpty()) {
            config.ShaderFresnel = arr[36].trim();
            }

            //解析 英雄类型
            if (!arr[37].trim().isEmpty()) {
            config.heroType =  Integer.parseInt(arr[37].trim());
            }

            //解析 品质
            if (!arr[38].trim().isEmpty()) {
            config.quality =  Integer.parseInt(arr[38].trim());
            }

            //解析 角色类别
            if (!arr[39].trim().isEmpty()) {
            config.characterType =  Integer.parseInt(arr[39].trim());
            }

            //解析 觉醒等级
            if (!arr[40].trim().isEmpty()) {
            config.awakenLv =  Integer.parseInt(arr[40].trim());
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

    public List<NpcConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, NpcConfig> getConfigMap() {
      return configMap;
    }
    @Override
    public String getConfigFileName() {
      return "npc.txt";
    }

    // @@@@@自定义方法开始区@@@@@
    @Override
    protected void afterLoad() {

    }

    // @@@@@自定义方法结束区@@@@@
  }
}
