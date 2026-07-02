package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class DungeonMainConfig {
  /**编号*/
  public final int id;

  /**备注*/
  public final String beizhu;

  /**活动日期*/
  public final String name;

  /**开放时间显示*/
  public final String timeShow;

  /**标题字母图片*/
  public final int titleWordPic;

  /**标题*/
  public final String title;

  /**奖励预览*/
  public final String rewards;

  /**背景图片*/
  public final int background;

  /**展示bossId*/
  public final int bossModel;

  /**播放动作*/
  public final String bossAction;

  /**缩放*/
  public final int scale;

  /**旋转*/
  public final String rotation;

  /**位置偏移*/
  public final String modelShift;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public DungeonMainConfig(int id, String beizhu, String name, String timeShow, int titleWordPic, String title, String rewards, int background, int bossModel, String bossAction, int scale, String rotation, String modelShift) {
    this.id = id;
    this.beizhu = beizhu;
    this.name = name;
    this.timeShow = timeShow;
    this.titleWordPic = titleWordPic;
    this.title = title;
    this.rewards = rewards;
    this.background = background;
    this.bossModel = bossModel;
    this.bossAction = bossAction;
    this.scale = scale;
    this.rotation = rotation;
    this.modelShift = modelShift;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
