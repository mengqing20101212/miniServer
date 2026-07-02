package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class GuildAuthorityConfig {
  /**ID*/
  public final int id;

  /**成员类型*/
  public final String Name;

  /**级别*/
  public final int level;

  /**升级公会权限*/
  public final int upgrade;

  /**降级公会权限*/
  public final int downgrade;

  /**解散公会权限*/
  public final int dissolve;

  /**审批权限*/
  public final int examine;

  /**踢人权限*/
  public final int kick;

  /**发布招募权限*/
  public final int publish;

  /**公告权限*/
  public final int notice;

  /**福利商店购买权限*/
  public final int welfareShop;

  /**开启首领权限*/
  public final int openBoss;

  /**公会邮件权限*/
  public final int mail;

  /**退出权限*/
  public final int exit;

  /**修改标签权限*/
  public final int label;

  /**修改名称*/
  public final int guildName;

  /**修改标志*/
  public final int sign;

  /**刷新宇宙商店*/
  public final int refreshShop;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public GuildAuthorityConfig(int id, String Name, int level, int upgrade, int downgrade, int dissolve, int examine, int kick, int publish, int notice, int welfareShop, int openBoss, int mail, int exit, int label, int guildName, int sign, int refreshShop) {
    this.id = id;
    this.Name = Name;
    this.level = level;
    this.upgrade = upgrade;
    this.downgrade = downgrade;
    this.dissolve = dissolve;
    this.examine = examine;
    this.kick = kick;
    this.publish = publish;
    this.notice = notice;
    this.welfareShop = welfareShop;
    this.openBoss = openBoss;
    this.mail = mail;
    this.exit = exit;
    this.label = label;
    this.guildName = guildName;
    this.sign = sign;
    this.refreshShop = refreshShop;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
