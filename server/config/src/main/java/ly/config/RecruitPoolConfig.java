package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class RecruitPoolConfig {
  /**招募编号*/
  public final int id;

  /**招募名称*/
  public final String name;

  /**招募卡池类型*/
  public final int recruitType;

  /**招募显示模板类型*/
  public final int showStyleType;

  /**单抽卡池id*/
  public final int oneDrawId;

  /**十抽卡池id*/
  public final int tenDrawId;

  /**活动排期*/
  public final int scheDuling;

  /**卡池类型(目前没用)*/
  public final int type;

  /**首次招募展示*/
  public final String timesShowType;

  /**播放招募视频(名称)*/
  public final String video;

  /**显示持续时间*/
  public final String lastDateShow;

  /**概率文本*/
  public final String chanceText;

  /**标题条ID*/
  public final int topId;

  /**招募结果ID*/
  public final int recruitEndTopId;

  /**是否显示在招募*/
  public final int recruitmentshow;

  /**对应的活动id*/
  public final int trueActivityId;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public RecruitPoolConfig(int id, String name, int recruitType, int showStyleType, int oneDrawId, int tenDrawId, int scheDuling, int type, String timesShowType, String video, String lastDateShow, String chanceText, int topId, int recruitEndTopId, int recruitmentshow, int trueActivityId) {
    this.id = id;
    this.name = name;
    this.recruitType = recruitType;
    this.showStyleType = showStyleType;
    this.oneDrawId = oneDrawId;
    this.tenDrawId = tenDrawId;
    this.scheDuling = scheDuling;
    this.type = type;
    this.timesShowType = timesShowType;
    this.video = video;
    this.lastDateShow = lastDateShow;
    this.chanceText = chanceText;
    this.topId = topId;
    this.recruitEndTopId = recruitEndTopId;
    this.recruitmentshow = recruitmentshow;
    this.trueActivityId = trueActivityId;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
