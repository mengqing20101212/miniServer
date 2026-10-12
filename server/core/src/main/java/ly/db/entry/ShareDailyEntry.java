package ly.db.entry;

import ly.db.AbstractEntry;
import ly.db.DbMeta;

/*
 * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@
 */
@DbMeta.DbTable(name = "share_daily")
public class ShareDailyEntry extends AbstractEntry {
  private static final String[] DIRTY_FIELDS = {
      "id", "code", "daily_time", "open", "high", "low", "close", "preclose", "volume", "amount", "adjustflag", "turn", "tradestatus", "pctChg", "peTTM", "pbMRQ", "psTTM", "pcfNcfTTM", "isST"
  };

  @DbMeta.DbMasterKey(name="id", autoIncrement=true)
  @DbMeta.DbField(name="id")
  private Long id;
  @DbMeta.DbField(name="code")
  private String code;

  /**交易所行情日期*/
  @DbMeta.DbField(name="daily_time")
  private java.time.LocalDate daily_time;

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

  /**成交量（累计 单位：股）*/
  @DbMeta.DbField(name="volume")
  private Long volume;

  /**成交额（单位：人民币元）*/
  @DbMeta.DbField(name="amount")
  private Long amount;

  /**复权状态(1：后复权， 2：前复权，3：不复权）*/
  @DbMeta.DbField(name="adjustflag")
  private Integer adjustflag;

  /**换手率	[指定交易日的成交量(股)/指定交易日的股票的流通股总股数(股)]*100%*/
  @DbMeta.DbField(name="turn")
  private String turn;

  /**交易状态(1：正常交易 0：停牌）*/
  @DbMeta.DbField(name="tradestatus")
  private Integer tradestatus;

  /**涨跌幅（百分比）	日涨跌幅=[(指定交易日的收盘价-指定交易日前收盘价)/指定交易日前收盘价]*100%*/
  @DbMeta.DbField(name="pctChg")
  private String pctChg;

  /**滚动市盈率	(指定交易日的股票收盘价/指定交易日的每股盈余TTM)=(指定交易日的股票收盘价*截至当日公司总股本)/归属母公司股东净利润TTM*/
  @DbMeta.DbField(name="peTTM")
  private String peTTM;

  /**市净率	(指定交易日的股票收盘价/指定交易日的每股净资产)=总市值/(最近披露的归属母公司股东的权益-其他权益工具)*/
  @DbMeta.DbField(name="pbMRQ")
  private String pbMRQ;

  /**滚动市销率	(指定交易日的股票收盘价/指定交易日的每股销售额)=(指定交易日的股票收盘价*截至当日公司总股本)/营业总收入TTM*/
  @DbMeta.DbField(name="psTTM")
  private String psTTM;

  /**滚动市现率	(指定交易日的股票收盘价/指定交易日的每股现金流TTM)=(指定交易日的股票收盘价*截至当日公司总股本)/现金以及现金等价物净增加额TTM*/
  @DbMeta.DbField(name="pcfNcfTTM")
  private String pcfNcfTTM;

  /**是否ST股，1是，0否*/
  @DbMeta.DbField(name="isST")
  private Integer isST;
  public ShareDailyEntry() {
    initDirtyState(DIRTY_FIELDS.length);
  }

  @Override
  protected String[] allDirtyFieldNames() {
    return DIRTY_FIELDS;
  }
  public void save() {
    ShareDailyEntryHelper.save(this);
  }

  public void update() {
    ShareDailyEntryHelper.update(this);
  }

  public void delete() {
    ShareDailyEntryHelper.delete(this);
  }

  public void asyncSave() {
    ShareDailyEntryHelper.asyncSave(this);
  }

  public void asyncUpdate() {
    ShareDailyEntryHelper.asyncUpdate(this);
  }

 public void setId(Long Id) {
    checkPrimaryKeyChange(this.id, Id);
    if (java.util.Objects.deepEquals(this.id, Id)) { return; }
    this.id = Id;
    autoAddCurVersion();
    markFieldDirty(0);
  }
  public Long getId() {
    return id;
  }
 public void setCode(String Code) {
    if (java.util.Objects.deepEquals(this.code, Code)) { return; }
    this.code = Code;
    autoAddCurVersion();
    markFieldDirty(1);
  }
  public String getCode() {
    return code;
  }
 public void setDailyTime(java.time.LocalDate DailyTime) {
    if (java.util.Objects.deepEquals(this.daily_time, DailyTime)) { return; }
    this.daily_time = DailyTime;
    autoAddCurVersion();
    markFieldDirty(2);
  }
  public java.time.LocalDate getDailyTime() {
    return daily_time;
  }
 public void setOpen(String Open) {
    if (java.util.Objects.deepEquals(this.open, Open)) { return; }
    this.open = Open;
    autoAddCurVersion();
    markFieldDirty(3);
  }
  public String getOpen() {
    return open;
  }
 public void setHigh(String High) {
    if (java.util.Objects.deepEquals(this.high, High)) { return; }
    this.high = High;
    autoAddCurVersion();
    markFieldDirty(4);
  }
  public String getHigh() {
    return high;
  }
 public void setLow(String Low) {
    if (java.util.Objects.deepEquals(this.low, Low)) { return; }
    this.low = Low;
    autoAddCurVersion();
    markFieldDirty(5);
  }
  public String getLow() {
    return low;
  }
 public void setClose(String Close) {
    if (java.util.Objects.deepEquals(this.close, Close)) { return; }
    this.close = Close;
    autoAddCurVersion();
    markFieldDirty(6);
  }
  public String getClose() {
    return close;
  }
 public void setPreclose(String Preclose) {
    if (java.util.Objects.deepEquals(this.preclose, Preclose)) { return; }
    this.preclose = Preclose;
    autoAddCurVersion();
    markFieldDirty(7);
  }
  public String getPreclose() {
    return preclose;
  }
 public void setVolume(Long Volume) {
    if (java.util.Objects.deepEquals(this.volume, Volume)) { return; }
    this.volume = Volume;
    autoAddCurVersion();
    markFieldDirty(8);
  }
  public Long getVolume() {
    return volume;
  }
 public void setAmount(Long Amount) {
    if (java.util.Objects.deepEquals(this.amount, Amount)) { return; }
    this.amount = Amount;
    autoAddCurVersion();
    markFieldDirty(9);
  }
  public Long getAmount() {
    return amount;
  }
 public void setAdjustflag(Integer Adjustflag) {
    if (java.util.Objects.deepEquals(this.adjustflag, Adjustflag)) { return; }
    this.adjustflag = Adjustflag;
    autoAddCurVersion();
    markFieldDirty(10);
  }
  public Integer getAdjustflag() {
    return adjustflag;
  }
 public void setTurn(String Turn) {
    if (java.util.Objects.deepEquals(this.turn, Turn)) { return; }
    this.turn = Turn;
    autoAddCurVersion();
    markFieldDirty(11);
  }
  public String getTurn() {
    return turn;
  }
 public void setTradestatus(Integer Tradestatus) {
    if (java.util.Objects.deepEquals(this.tradestatus, Tradestatus)) { return; }
    this.tradestatus = Tradestatus;
    autoAddCurVersion();
    markFieldDirty(12);
  }
  public Integer getTradestatus() {
    return tradestatus;
  }
 public void setPctchg(String Pctchg) {
    if (java.util.Objects.deepEquals(this.pctChg, Pctchg)) { return; }
    this.pctChg = Pctchg;
    autoAddCurVersion();
    markFieldDirty(13);
  }
  public String getPctchg() {
    return pctChg;
  }
 public void setPettm(String Pettm) {
    if (java.util.Objects.deepEquals(this.peTTM, Pettm)) { return; }
    this.peTTM = Pettm;
    autoAddCurVersion();
    markFieldDirty(14);
  }
  public String getPettm() {
    return peTTM;
  }
 public void setPbmrq(String Pbmrq) {
    if (java.util.Objects.deepEquals(this.pbMRQ, Pbmrq)) { return; }
    this.pbMRQ = Pbmrq;
    autoAddCurVersion();
    markFieldDirty(15);
  }
  public String getPbmrq() {
    return pbMRQ;
  }
 public void setPsttm(String Psttm) {
    if (java.util.Objects.deepEquals(this.psTTM, Psttm)) { return; }
    this.psTTM = Psttm;
    autoAddCurVersion();
    markFieldDirty(16);
  }
  public String getPsttm() {
    return psTTM;
  }
 public void setPcfncfttm(String Pcfncfttm) {
    if (java.util.Objects.deepEquals(this.pcfNcfTTM, Pcfncfttm)) { return; }
    this.pcfNcfTTM = Pcfncfttm;
    autoAddCurVersion();
    markFieldDirty(17);
  }
  public String getPcfncfttm() {
    return pcfNcfTTM;
  }
 public void setIsst(Integer Isst) {
    if (java.util.Objects.deepEquals(this.isST, Isst)) { return; }
    this.isST = Isst;
    autoAddCurVersion();
    markFieldDirty(18);
  }
  public Integer getIsst() {
    return isST;
  }

  // @@@@@自定义方法开始区@@@@@

  // @@@@@自定义方法结束区@@@@@

  @Override
  public String toString() {
    return "ShareDailyEntry{"
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
        ", tradestatus="+tradestatus+
        ", pctChg="+pctChg+
        ", peTTM="+peTTM+
        ", pbMRQ="+pbMRQ+
        ", psTTM="+psTTM+
        ", pcfNcfTTM="+pcfNcfTTM+
        ", isST="+isST
        + '}';
  }
}
