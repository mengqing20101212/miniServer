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
import org.slf4j.Logger;

/*
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 * File: HeroInfoConfigManager
 */
public class HeroInfoConfigManager implements InterfaceConfigManagerProxy {
  AtomicBoolean switched = new AtomicBoolean(false);
  private static final HeroInfoConfigManager instance = new HeroInfoConfigManager();
  private static final HeroInfoConfigManagerImpl instanceImplA = new HeroInfoConfigManagerImpl();
  private static final HeroInfoConfigManagerImpl instanceImplB = new HeroInfoConfigManagerImpl();

  public boolean isSwitched() {
    return switched.getAndSet(!switched.get());
  }

  public static HeroInfoConfigManagerImpl getInstance() {
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

  public static class HeroInfoConfigManagerImpl extends AbstractConfigManger {

    List<HeroInfoConfig> configList = new ArrayList<HeroInfoConfig>();
    Map<Integer, HeroInfoConfig> configMap = new HashMap<Integer, HeroInfoConfig>();

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
        br.readLine(); // 先读取一行表头
        while ((line = br.readLine()) != null) { // 按行读取
          String[] arr = line.split("\t");
          HeroInfoConfig config = new HeroInfoConfig();
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              config.id = Integer.parseInt(arr[0].trim());
            }

            // 解析 名字
            if (!arr[1].trim().isEmpty()) {
              config.name = arr[1].trim();
            }

            // 解析 英雄类型
            if (!arr[2].trim().isEmpty()) {
              config.heroType = Integer.parseInt(arr[2].trim());
            }

            // 解析 英雄性别
            if (!arr[3].trim().isEmpty()) {
              config.heroSex = Integer.parseInt(arr[3].trim());
            }

            // 解析 战斗模型
            if (!arr[4].trim().isEmpty()) {
              config.modelResource = Integer.parseInt(arr[4].trim());
            }

            // 解析 战斗模型缩放
            if (!arr[5].trim().isEmpty()) {
              config.modelResourceScale = arr[5].trim();
            }

            // 解析 布阵模型
            if (!arr[6].trim().isEmpty()) {
              config.battleResource = Integer.parseInt(arr[6].trim());
            }

            // 解析 展示模型
            if (!arr[7].trim().isEmpty()) {
              config.showResource = Integer.parseInt(arr[7].trim());
            }

            // 解析 展示模型缩放
            if (!arr[8].trim().isEmpty()) {
              config.showResourceScale = arr[8].trim();
            }

            // 解析 行动序列头像
            if (!arr[9].trim().isEmpty()) {
              config.headResource = Integer.parseInt(arr[9].trim());
            }

            // 解析 右侧头像
            if (!arr[10].trim().isEmpty()) {
              config.headResource_2 = Integer.parseInt(arr[10].trim());
            }

            // 解析 立绘头像
            if (!arr[11].trim().isEmpty()) {
              config.headResource_3 = Integer.parseInt(arr[11].trim());
            }

            // 解析 全身立绘
            if (!arr[12].trim().isEmpty()) {
              config.headResource_4 = Integer.parseInt(arr[12].trim());
            }

            // 解析 英雄辅助列
            if (!arr[13].trim().isEmpty()) {
              config.heropinyin = arr[13].trim();
            }

            // 解析 英雄表演
            if (!arr[14].trim().isEmpty()) {
              config.heroPerformance = arr[14].trim();
            }

            // 解析 英雄列表未获得立绘切割坐标
            if (!arr[15].trim().isEmpty()) {
              config.heroCutUp = arr[15].trim();
            }

            // 解析 s技能立绘切割坐标
            if (!arr[16].trim().isEmpty()) {
              config.sSkillCutUp = arr[16].trim();
            }

            // 解析 s技能跳过立绘切割坐标
            if (!arr[17].trim().isEmpty()) {
              config.sSkipCutUp = arr[17].trim();
            }

            // 解析 SP英雄
            if (!arr[18].trim().isEmpty()) {
              config.isSP = Integer.parseInt(arr[18].trim());
            }

            // 解析 品质
            if (!arr[19].trim().isEmpty()) {
              config.quality = Integer.parseInt(arr[19].trim());
            }

            // 解析 常规技能
            if (!arr[20].trim().isEmpty()) {
              config.skill_1 = Integer.parseInt(arr[20].trim());
            }

            // 解析   null
            if (!arr[21].trim().isEmpty()) {
              config.skill_2 = Integer.parseInt(arr[21].trim());
            }

            // 解析   null
            if (!arr[22].trim().isEmpty()) {
              config.skill_3 = Integer.parseInt(arr[22].trim());
            }

            // 解析 超级技能
            if (!arr[23].trim().isEmpty()) {
              config.skill_s1 = Integer.parseInt(arr[23].trim());
            }

            // 解析 超级技能
            if (!arr[24].trim().isEmpty()) {
              config.skill_s2 = Integer.parseInt(arr[24].trim());
            }

            // 解析 多段觉醒技能
            if (!arr[25].trim().isEmpty()) {
              config.moreAwakenSkill1 = Integer.parseInt(arr[25].trim());
            }

            // 解析 多段觉醒技能
            if (!arr[26].trim().isEmpty()) {
              config.moreAwakenSkill2 = Integer.parseInt(arr[26].trim());
            }

            // 解析 多段觉醒技能
            if (!arr[27].trim().isEmpty()) {
              config.moreAwakenSkill3 = Integer.parseInt(arr[27].trim());
            }

            // 解析 结束镜头
            if (!arr[28].trim().isEmpty()) {
              config.endPerformance = arr[28].trim();
            }

            // 解析 英雄情报
            if (!arr[29].trim().isEmpty()) {
              config.heroDebris = Integer.parseInt(arr[29].trim());
            }

            // 解析 英雄抽卡台词
            if (!arr[30].trim().isEmpty()) {
              config.herogachadoc = Integer.parseInt(arr[30].trim());
            }

            // 解析 英雄升级升星模板
            if (!arr[31].trim().isEmpty()) {
              config.heroStateData = Integer.parseInt(arr[31].trim());
            }

            // 解析 英雄觉醒模板
            if (!arr[32].trim().isEmpty()) {
              config.heroAwakenData = Integer.parseInt(arr[32].trim());
            }

            // 解析 升级经验模板id
            if (!arr[33].trim().isEmpty()) {
              config.expModelId = Integer.parseInt(arr[33].trim());
            }

            // 解析 升星经验模板id
            if (!arr[34].trim().isEmpty()) {
              config.starModelId = Integer.parseInt(arr[34].trim());
            }

            // 解析 回路id
            if (!arr[35].trim().isEmpty()) {
              config.circuitId = Integer.parseInt(arr[35].trim());
            }

            // 解析 预加载ID
            if (!arr[36].trim().isEmpty()) {
              config.perloadId = Integer.parseInt(arr[36].trim());
            }

            // 解析 英雄列表预加载ID
            if (!arr[37].trim().isEmpty()) {
              config.heroListPreloadId = Integer.parseInt(arr[37].trim());
            }

            // 解析 pvp摄像机参数
            if (!arr[38].trim().isEmpty()) {
              config.pvpCamera = arr[38].trim();
            }

            // 解析 组队摄像机参数
            if (!arr[39].trim().isEmpty()) {
              config.teamCamera = arr[39].trim();
            }

            // 解析 AI类型
            if (!arr[40].trim().isEmpty()) {
              config.aiName = arr[40].trim();
            }

            // 解析 属性评级id
            if (!arr[41].trim().isEmpty()) {
              config.attrRankId = Integer.parseInt(arr[41].trim());
            }

            // 解析 获取后自动上阵位置
            if (!arr[42].trim().isEmpty()) {
              config.getPpos = Integer.parseInt(arr[42].trim());
            }

            // 解析 角色特殊背景板
            if (!arr[43].trim().isEmpty()) {
              config.speicalBg = Integer.parseInt(arr[43].trim());
            }

            // 解析 未获得时背景板
            if (!arr[44].trim().isEmpty()) {
              config.unlockBg = Integer.parseInt(arr[44].trim());
            }

            // 解析 品质图标
            if (!arr[45].trim().isEmpty()) {
              config.qualityIcon = Integer.parseInt(arr[45].trim());
            }

            // 解析 角色的功能NPC_ID
            if (!arr[46].trim().isEmpty()) {
              config.activityNpcId = Integer.parseInt(arr[46].trim());
            }

            // 解析 英雄模型背景图
            if (!arr[47].trim().isEmpty()) {
              config.background = Integer.parseInt(arr[47].trim());
            }

            // 解析 512尺寸半身像
            if (!arr[48].trim().isEmpty()) {
              config.cardBust = Integer.parseInt(arr[48].trim());
            }

            // 解析 表彰加成(supportTeamHeroAttr表的attrClass)
            if (!arr[49].trim().isEmpty()) {
              config.supportTeamAttr = arr[49].trim();
            }

            // 解析 表彰使用货币
            if (!arr[50].trim().isEmpty()) {
              config.supportItem = Integer.parseInt(arr[50].trim());
            }

            // 解析 情报兑换
            if (!arr[51].trim().isEmpty()) {
              config.exchangeItem = Integer.parseInt(arr[51].trim());
            }

            // 解析 英雄模型背景预设
            if (!arr[52].trim().isEmpty()) {
              config.backgroundId = Integer.parseInt(arr[52].trim());
            }

            // 解析 时间类型
            if (!arr[53].trim().isEmpty()) {
              config.timeType = Integer.parseInt(arr[53].trim());
            }

            // 解析 开始时间
            if (!arr[54].trim().isEmpty()) {
              config.startTime = arr[54].trim();
            }

            // 解析 特殊时间
            if (!arr[55].trim().isEmpty()) {
              config.specialEndTime = arr[55].trim();
            }

            // 解析 是否分享
            if (!arr[56].trim().isEmpty()) {
              config.heroshare = Integer.parseInt(arr[56].trim());
            }

            // 解析 相同英雄组
            if (!arr[57].trim().isEmpty()) {
              config.herogroup = Integer.parseInt(arr[57].trim());
            }

            // 解析 开服区间
            if (!arr[58].trim().isEmpty()) {
              config.OpenServiceActivity = Integer.parseInt(arr[58].trim());
            }

            // 解析 角色类别
            if (!arr[59].trim().isEmpty()) {
              config.characterType = Integer.parseInt(arr[59].trim());
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

    public List<HeroInfoConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, HeroInfoConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "heroInfo.txt";
    }

    // @@@@@自定义方法开始区@@@@@
    @Override
    protected void afterLoad() {}

    // @@@@@自定义方法结束区@@@@@
  }
}
