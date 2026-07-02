package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class BlockBattleConvergeConfig {
  /**编号*/
  public final int id;

  /**选择类型*/
  public final int type;

  /**是否集火*/
  public final int convergeCheck;

  /**防守方视角*/
  public final String convergePara;

  /**进攻方视角*/
  public final String convergeParaAtack;

  /**备注*/
  public final String beizhu;

  /**集火类型描述*/
  public final String dec;

  /**使用场景*/
  public final int useScence;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public BlockBattleConvergeConfig(int id, int type, int convergeCheck, String convergePara, String convergeParaAtack, String beizhu, String dec, int useScence) {
    this.id = id;
    this.type = type;
    this.convergeCheck = convergeCheck;
    this.convergePara = convergePara;
    this.convergeParaAtack = convergeParaAtack;
    this.beizhu = beizhu;
    this.dec = dec;
    this.useScence = useScence;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
