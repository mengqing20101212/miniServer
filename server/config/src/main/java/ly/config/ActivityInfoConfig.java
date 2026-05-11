package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ActivityInfoConfig {
  /**编号*/
  public final int id;

  /**功能名称*/
  public final String name;

  /**开启类型*/
  public final int openType;

  /**活动排期*/
  public final int scheDuling;

  /**开启参数1*/
  public final int openPara1;

  /**开启参数2*/
  public final int openPara2;

  /**开启参数3*/
  public final int openPara3;

  /**时间类型*/
  public final int timeType;

  /**开始时间*/
  public final String startTime;

  /**结束时间*/
  public final String endTime;

  /**特殊时间*/
  public final String specialEndTime;

  /**刷新时间*/
  public final String freshTime;

  /**关闭时间*/
  public final int closeTime;

  /**任务领取完是否关闭活动*/
  public final int closeActivity;

  /**开服区间*/
  public final int OpenServiceActivity;

  /**积分类型*/
  public final int integralType;

  /**阶段积分*/
  public final String integralStage;

  /**积分奖励（掉落表id）*/
  public final String integralReward;

  /**积分奖励（前端）*/
  public final String integralRewardShow;

  /**活动标题*/
  public final String title;

  /**立绘*/
  public final String picture;

  /**立绘描述*/
  public final String description;

  /**功能参数1*/
  public final String para1;

  /**功能参数2*/
  public final String para2;

  /**功能参数3*/
  public final String para3;

  /**邮件模板ID*/
  public final int mailTemplateId;

  /**活动剩余道具转换资源*/
  public final String exchangeResources;

  /**活动入口类型*/
  public final int enterType;

  /**排序*/
  public final int sort;

  /**活动描述*/
  public final int des;

  /**活动时间描述*/
  public final String destime;

  /**是否显示倒计时*/
  public final int timeDown;

  /**活动描述*/
  public final String desPic;

  /**商品ID*/
  public final String RechargeId;

  /**活动类型*/
  public final int iACTIVITYTYPE;

  /**topID*/
  public final int topId;

  /**是否不显示在活动栏*/
  public final int NoShow;

  /**功能显示解锁类型*/
  public final int DisplayFunctionType;

  /**解锁类型参数*/
  public final String DisplayFunctionParam;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ActivityInfoConfig(int id, String name, int openType, int scheDuling, int openPara1, int openPara2, int openPara3, int timeType, String startTime, String endTime, String specialEndTime, String freshTime, int closeTime, int closeActivity, int OpenServiceActivity, int integralType, String integralStage, String integralReward, String integralRewardShow, String title, String picture, String description, String para1, String para2, String para3, int mailTemplateId, String exchangeResources, int enterType, int sort, int des, String destime, int timeDown, String desPic, String RechargeId, int iACTIVITYTYPE, int topId, int NoShow, int DisplayFunctionType, String DisplayFunctionParam) {
    this.id = id;
    this.name = name;
    this.openType = openType;
    this.scheDuling = scheDuling;
    this.openPara1 = openPara1;
    this.openPara2 = openPara2;
    this.openPara3 = openPara3;
    this.timeType = timeType;
    this.startTime = startTime;
    this.endTime = endTime;
    this.specialEndTime = specialEndTime;
    this.freshTime = freshTime;
    this.closeTime = closeTime;
    this.closeActivity = closeActivity;
    this.OpenServiceActivity = OpenServiceActivity;
    this.integralType = integralType;
    this.integralStage = integralStage;
    this.integralReward = integralReward;
    this.integralRewardShow = integralRewardShow;
    this.title = title;
    this.picture = picture;
    this.description = description;
    this.para1 = para1;
    this.para2 = para2;
    this.para3 = para3;
    this.mailTemplateId = mailTemplateId;
    this.exchangeResources = exchangeResources;
    this.enterType = enterType;
    this.sort = sort;
    this.des = des;
    this.destime = destime;
    this.timeDown = timeDown;
    this.desPic = desPic;
    this.RechargeId = RechargeId;
    this.iACTIVITYTYPE = iACTIVITYTYPE;
    this.topId = topId;
    this.NoShow = NoShow;
    this.DisplayFunctionType = DisplayFunctionType;
    this.DisplayFunctionParam = DisplayFunctionParam;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
