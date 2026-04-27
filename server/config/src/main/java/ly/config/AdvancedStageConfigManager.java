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
 * File: AdvancedStageConfigManager
 */
public class AdvancedStageConfigManager implements InterfaceConfigManagerProxy {
  AtomicBoolean switched = new AtomicBoolean(false);
  private static final AdvancedStageConfigManager instance = new AdvancedStageConfigManager();
  private static final AdvancedStageConfigManagerImpl instanceImplA =
      new AdvancedStageConfigManagerImpl();
  private static final AdvancedStageConfigManagerImpl instanceImplB =
      new AdvancedStageConfigManagerImpl();

  public boolean isSwitched() {
    return switched.get();
  }

  public static AdvancedStageConfigManagerImpl getInstance() {
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

  public static class AdvancedStageConfigManagerImpl extends AbstractConfigManger {

    List<AdvancedStageConfig> configList = new ArrayList<AdvancedStageConfig>();
    Map<Integer, AdvancedStageConfig> configMap = new HashMap<Integer, AdvancedStageConfig>();


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
          AdvancedStageConfig config = new AdvancedStageConfig();
          try {
            //解析 编号
            if (!arr[0].trim().isEmpty()) {
            config.id =  Integer.parseInt(arr[0].trim());
            }

            //解析 关卡类型
            if (!arr[1].trim().isEmpty()) {
            config.stageType =  Integer.parseInt(arr[1].trim());
            }

            //解析 层数
            if (!arr[2].trim().isEmpty()) {
            config.floor =  Integer.parseInt(arr[2].trim());
            }

            //解析 体力消耗
            if (!arr[3].trim().isEmpty()) {
            config.cost =  Integer.parseInt(arr[3].trim());
            }

            //解析 预支体力消耗
            if (!arr[4].trim().isEmpty()) {
            config.advance =  Integer.parseInt(arr[4].trim());
            }

            //解析 关卡id
            if (!arr[5].trim().isEmpty()) {
            config.sceneId =  Integer.parseInt(arr[5].trim());
            }

            //解析 倾向选择
            if (!arr[6].trim().isEmpty()) {
            config.dropSelection =  Integer.parseInt(arr[6].trim());
            }

            //解析 掉落预览
            if (!arr[7].trim().isEmpty()) {
            config.dropList = arr[7].trim();
            }

            //解析 是否有up图标
            if (!arr[8].trim().isEmpty()) {
            config.upIcon = arr[8].trim();
            }

            //解析 关卡掉落
            if (!arr[9].trim().isEmpty()) {
            config.dropGroup = arr[9].trim();
            }

            //解析 名称
            if (!arr[10].trim().isEmpty()) {
            config.name = arr[10].trim();
            }

            //解析 解锁提示
            if (!arr[11].trim().isEmpty()) {
            config.lockTips = arr[11].trim();
            }

            //解析 前置章节
            if (!arr[12].trim().isEmpty()) {
            config.preStage =  Integer.parseInt(arr[12].trim());
            }

            //解析 后置章节
            if (!arr[13].trim().isEmpty()) {
            config.nextStage =  Integer.parseInt(arr[13].trim());
            }

            //解析 解锁等级
            if (!arr[14].trim().isEmpty()) {
            config.needLv =  Integer.parseInt(arr[14].trim());
            }

            //解析 boss头像
            if (!arr[15].trim().isEmpty()) {
            config.bossHead =  Integer.parseInt(arr[15].trim());
            }

            //解析 形象人物模型
            if (!arr[16].trim().isEmpty()) {
            config.model = arr[16].trim();
            }

            //解析 形象人物动作
            if (!arr[17].trim().isEmpty()) {
            config.action = arr[17].trim();
            }

            //解析 形象人物对话
            if (!arr[18].trim().isEmpty()) {
            config.word = arr[18].trim();
            }

            //解析 中心装饰参数1
            if (!arr[19].trim().isEmpty()) {
            config.decorate1 = arr[19].trim();
            }

            //解析 中心装饰参数2
            if (!arr[20].trim().isEmpty()) {
            config.decorate2 = arr[20].trim();
            }

            //解析 中心装饰参数3
            if (!arr[21].trim().isEmpty()) {
            config.decorate3 = arr[21].trim();
            }

            //解析 bossID
            if (!arr[22].trim().isEmpty()) {
            config.bossId = arr[22].trim();
            }

            //解析 缩放比例
            if (!arr[23].trim().isEmpty()) {
            config.scaling = arr[23].trim();
            }

            //解析 Y轴偏移位置
            if (!arr[24].trim().isEmpty()) {
            config.offset = arr[24].trim();
            }

            //解析 X轴偏移位置
            if (!arr[25].trim().isEmpty()) {
            config.offsetX = arr[25].trim();
            }

            //解析 偏移角度
            if (!arr[26].trim().isEmpty()) {
            config.offsetAngle = arr[26].trim();
            }

            //解析 是否特殊展示动作
            if (!arr[27].trim().isEmpty()) {
            config.showType = arr[27].trim();
            }

            //解析 掉落倾向描述
            if (!arr[28].trim().isEmpty()) {
            config.selectionDis = arr[28].trim();
            }

            //解析 突发事件触发概率
            if (!arr[29].trim().isEmpty()) {
            config.trigger = arr[29].trim();
            }

            //解析 spine显示模型预设资源Id
            if (!arr[30].trim().isEmpty()) {
            config.spineModelResId =  Integer.parseInt(arr[30].trim());
            }

            //解析 缩放比例
            if (!arr[31].trim().isEmpty()) {
            config.spineScale =  Integer.parseInt(arr[31].trim());
            }

            //解析 位置偏移(x,y)
            if (!arr[32].trim().isEmpty()) {
            config.spinePosOffset = arr[32].trim();
            }

            //解析 spine动画
            if (!arr[33].trim().isEmpty()) {
            config.spineAnimation = arr[33].trim();
            }

            //解析 期望掉落数量
            if (!arr[34].trim().isEmpty()) {
            config.dropExpect = arr[34].trim();
            }

            //解析 推荐等级
            if (!arr[35].trim().isEmpty()) {
            config.recommendLv =  Integer.parseInt(arr[35].trim());
            }

            //解析 推荐类型
            if (!arr[36].trim().isEmpty()) {
            config.recommendtype =  Integer.parseInt(arr[36].trim());
            }

            //解析 推荐英雄
            if (!arr[37].trim().isEmpty()) {
            config.recommendhero = arr[37].trim();
            }

            //解析 战斗提示
            if (!arr[38].trim().isEmpty()) {
            config.battleTipText = arr[38].trim();
            }

            //解析 倾向类型
            if (!arr[39].trim().isEmpty()) {
            config.selectionType =  Integer.parseInt(arr[39].trim());
            }

            //解析 排行榜枚举
            if (!arr[40].trim().isEmpty()) {
            config.rankType =  Integer.parseInt(arr[40].trim());
            }

            //解析 对应功能ID
            if (!arr[41].trim().isEmpty()) {
            config.activityControlId =  Integer.parseInt(arr[41].trim());
            }

            //解析 GM平台描述
            if (!arr[42].trim().isEmpty()) {
            config.webDes = arr[42].trim();
            }

            //解析 期望掉落效率提升值
            if (!arr[43].trim().isEmpty()) {
            config.dropExpectAdd = arr[43].trim();
            }

            //解析 推荐英雄1
            if (!arr[44].trim().isEmpty()) {
            config.recommendHero1 =  Integer.parseInt(arr[44].trim());
            }

            //解析 推荐英雄2
            if (!arr[45].trim().isEmpty()) {
            config.recommendHero2 =  Integer.parseInt(arr[45].trim());
            }

            //解析 图标类型
            if (!arr[46].trim().isEmpty()) {
            config.iconType =  Integer.parseInt(arr[46].trim());
            }

            //解析 获取提示图标
            if (!arr[47].trim().isEmpty()) {
            config.noticeIcon =  Integer.parseInt(arr[47].trim());
            }

            //解析 首通期望掉落数量
            if (!arr[48].trim().isEmpty()) {
            config.firstDropExpect =  Integer.parseInt(arr[48].trim());
            }

            //解析 英雄试炼角色背景
            if (!arr[49].trim().isEmpty()) {
            config.decorate2Background =  Integer.parseInt(arr[49].trim());
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

    public List<AdvancedStageConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, AdvancedStageConfig> getConfigMap() {
      return configMap;
    }
    @Override
    public String getConfigFileName() {
      return "advancedStage.txt";
    }

    // @@@@@自定义方法开始区@@@@@
    @Override
    protected void afterLoad() {

    }

    // @@@@@自定义方法结束区@@@@@
  }
}
