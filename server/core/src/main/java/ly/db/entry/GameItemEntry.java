package ly.db.entry;

import ly.db.AbstractEntry;
import ly.db.DbMeta;

/*
 * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@
 */
@DbMeta.DbTable(name = "game_item")
public class GameItemEntry extends AbstractEntry {

  /**item id */
  @DbMeta.DbMasterKey(name="id", autoIncrement=true)
  @DbMeta.DbField(name="id")
  private Long id;

  /**物品类型*/
  @DbMeta.DbField(name="item_type")
  private Integer itemType;

  /**物品名称*/
  @DbMeta.DbField(name="item_name")
  private String itemName;

  /**物品描述*/
  @DbMeta.DbField(name="item_desc")
  private String itemDesc;

  /**物品数量*/
  @DbMeta.DbField(name="quantity")
  private Integer quantity;

  /**拥有者ID*/
  @DbMeta.DbField(name="owner_id")
  private Long ownerId;

  /**获取时间*/
  @DbMeta.DbField(name="acquire_time")
  private java.time.LocalDateTime acquireTime;

  /**过期时间*/
  @DbMeta.DbField(name="expire_time")
  private java.time.LocalDateTime expireTime;

  /**物品等级*/
  @DbMeta.DbField(name="item_level")
  private Integer itemLevel;

  /**强化次数*/
  @DbMeta.DbField(name="enhance_count")
  private Integer enhanceCount;

  public void save() {
    GameItemEntryHelper.save(this);
  }

  public void update() {
    GameItemEntryHelper.update(this);
  }

  public void delete() {
    GameItemEntryHelper.delete(this);
  }

  public void asyncSave() {
    GameItemEntryHelper.asyncSave(this);
  }

  public void asyncUpdate() {
    GameItemEntryHelper.asyncUpdate(this);
  }

 public void setId(Long Id) {
    this.id = Id;
    autoAddCurVersion();
  }
  public Long getId() {
    return id;
  }
 public void setItemtype(Integer Itemtype) {
    this.itemType = Itemtype;
    autoAddCurVersion();
  }
  public Integer getItemtype() {
    return itemType;
  }
 public void setItemname(String Itemname) {
    this.itemName = Itemname;
    autoAddCurVersion();
  }
  public String getItemname() {
    return itemName;
  }
 public void setItemdesc(String Itemdesc) {
    this.itemDesc = Itemdesc;
    autoAddCurVersion();
  }
  public String getItemdesc() {
    return itemDesc;
  }
 public void setQuantity(Integer Quantity) {
    this.quantity = Quantity;
    autoAddCurVersion();
  }
  public Integer getQuantity() {
    return quantity;
  }
 public void setOwnerid(Long Ownerid) {
    this.ownerId = Ownerid;
    autoAddCurVersion();
  }
  public Long getOwnerid() {
    return ownerId;
  }
 public void setAcquiretime(java.time.LocalDateTime Acquiretime) {
    this.acquireTime = Acquiretime;
    autoAddCurVersion();
  }
  public java.time.LocalDateTime getAcquiretime() {
    return acquireTime;
  }
 public void setExpiretime(java.time.LocalDateTime Expiretime) {
    this.expireTime = Expiretime;
    autoAddCurVersion();
  }
  public java.time.LocalDateTime getExpiretime() {
    return expireTime;
  }
 public void setItemlevel(Integer Itemlevel) {
    this.itemLevel = Itemlevel;
    autoAddCurVersion();
  }
  public Integer getItemlevel() {
    return itemLevel;
  }
 public void setEnhancecount(Integer Enhancecount) {
    this.enhanceCount = Enhancecount;
    autoAddCurVersion();
  }
  public Integer getEnhancecount() {
    return enhanceCount;
  }

  // @@@@@自定义方法开始区@@@@@

  // @@@@@自定义方法结束区@@@@@

  @Override
  public String toString() {
    return "GameItemEntry{"
+
        ", id="+id+
        ", itemType="+itemType+
        ", itemName="+itemName+
        ", itemDesc="+itemDesc+
        ", quantity="+quantity+
        ", ownerId="+ownerId+
        ", acquireTime="+acquireTime+
        ", expireTime="+expireTime+
        ", itemLevel="+itemLevel+
        ", enhanceCount="+enhanceCount
        + '}';
  }
}