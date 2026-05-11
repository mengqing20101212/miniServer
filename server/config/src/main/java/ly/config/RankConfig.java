package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class RankConfig {
  /**编号*/
  public final int id;

  /**玩法类型*/
  public final int type;

  /**玩法名称*/
  public final String name;

  /**子类型*/
  public final int subtype;

  /**子类型名称*/
  public final String subtype_name;

  /**无排行提示*/
  public final String no_ranking;

  /**排行数量*/
  public final int rankNum;

  /**服务器枚举*/
  public final int serverType;

  /**服务器枚举分页*/
  public final int serverSubtype;

  /**服务器枚举字段*/
  public final String serverInfoName;

  /**默认显示页*/
  public final String defaultPage;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public RankConfig(int id, int type, String name, int subtype, String subtype_name, String no_ranking, int rankNum, int serverType, int serverSubtype, String serverInfoName, String defaultPage) {
    this.id = id;
    this.type = type;
    this.name = name;
    this.subtype = subtype;
    this.subtype_name = subtype_name;
    this.no_ranking = no_ranking;
    this.rankNum = rankNum;
    this.serverType = serverType;
    this.serverSubtype = serverSubtype;
    this.serverInfoName = serverInfoName;
    this.defaultPage = defaultPage;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
