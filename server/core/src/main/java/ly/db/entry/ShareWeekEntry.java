package ly.db.entry;

import ly.db.AbstractEntry;
import ly.db.DbMeta;

/*
 * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@
 */
@DbMeta.DbTable(name = "share_week")
public class ShareWeekEntry extends AbstractEntry {

  @DbMeta.DbMasterKey(name="id", autoIncrement=true)
  @DbMeta.DbField(name="id")
  private Integer id;
  @DbMeta.DbField(name="code")
  private String code;

  /**交易所行情日期*/
  @DbMeta.DbField(name="daily_time")
  private java.sql.Date daily_time;

  /**开盘价*/
  @DbMeta.DbField(name="open")
  private String open;

  /**最高价*/
  @DbMeta.DbField(name="high")
  private String high;

  /**最低价*/
  @DbMeta.DbField(name="low")
  private String low;

  /**收盘价*/
  @DbMeta.DbField(name="close")
  private String close;

  /**前收盘价*/
  @DbMeta.DbField(name="preclose")
  private String preclose;

  /**成交量 累计 单位：股*/
  @DbMeta.DbField(name="volume")
  private Long volume;

  /**成交额 单位：人民币元 */
  @DbMeta.DbField(name="amount")
  private Long amount;

  /**复权状态 1：后复权， 2：前复权，3：不复权 */
  @DbMeta.DbField(name="adjustflag")
  private Integer adjustflag;

  /**换手率	[指定交易日的成交量(股)/指定交易日的股票的流通股总股数(股)]*100%*/
  @DbMeta.DbField(name="turn")
  private String turn;

  /**涨跌幅 百分比	日涨跌幅=[(指定交易日的收盘价-指定交易日前收盘价)/指定交易日前收盘价]*100%*/
  @DbMeta.DbField(name="pctChg")
  private String pctChg;
  public void save() {
    ShareWeekEntryHelper.save(this);
  }

  public void update() {
    ShareWeekEntryHelper.update(this);
  }

  public void delete() {
    ShareWeekEntryHelper.delete(this);
  }

  public void asyncSave() {
    ShareWeekEntryHelper.asyncSave(this);
  }

  public void asyncUpdate() {
    ShareWeekEntryHelper.asyncUpdate(this);
  }

 public void setId(Integer Id) {
    this.id = Id;
    autoAddCurVersion();
  }
  public Integer getId() {
    return id;
  }
 public void setCode(String Code) {
    this.code = Code;
    autoAddCurVersion();
  }
  public String getCode() {
    return code;
  }
 public void setDailyTime(java.sql.Date DailyTime) {
    this.daily_time = DailyTime;
    autoAddCurVersion();
  }
  public java.sql.Date getDailyTime() {
    return daily_time;
  }
 public void setOpen(String Open) {
    this.open = Open;
    autoAddCurVersion();
  }
  public String getOpen() {
    return open;
  }
 public void setHigh(String High) {
    this.high = High;
    autoAddCurVersion();
  }
  public String getHigh() {
    return high;
  }
 public void setLow(String Low) {
    this.low = Low;
    autoAddCurVersion();
  }
  public String getLow() {
    return low;
  }
 public void setClose(String Close) {
    this.close = Close;
    autoAddCurVersion();
  }
  public String getClose() {
    return close;
  }
 public void setPreclose(String Preclose) {
    this.preclose = Preclose;
    autoAddCurVersion();
  }
  public String getPreclose() {
    return preclose;
  }
 public void setVolume(Long Volume) {
    this.volume = Volume;
    autoAddCurVersion();
  }
  public Long getVolume() {
    return volume;
  }
 public void setAmount(Long Amount) {
    this.amount = Amount;
    autoAddCurVersion();
  }
  public Long getAmount() {
    return amount;
  }
 public void setAdjustflag(Integer Adjustflag) {
    this.adjustflag = Adjustflag;
    autoAddCurVersion();
  }
  public Integer getAdjustflag() {
    return adjustflag;
  }
 public void setTurn(String Turn) {
    this.turn = Turn;
    autoAddCurVersion();
  }
  public String getTurn() {
    return turn;
  }
 public void setPctchg(String Pctchg) {
    this.pctChg = Pctchg;
    autoAddCurVersion();
  }
  public String getPctchg() {
    return pctChg;
  }

  // @@@@@自定义方法开始区@@@@@

  // @@@@@自定义方法结束区@@@@@

  @Override
  public String toString() {
    return "ShareWeekEntry{"
+
        ", id="+id+
        ", code="+code+
        ", daily_time="+daily_time+
        ", open="+open+
        ", high="+high+
        ", low="+low+
        ", close="+close+
        ", preclose="+preclose+
        ", volume="+volume+
        ", amount="+amount+
        ", adjustflag="+adjustflag+
        ", turn="+turn+
        ", pctChg="+pctChg
        + '}';
  }
}
