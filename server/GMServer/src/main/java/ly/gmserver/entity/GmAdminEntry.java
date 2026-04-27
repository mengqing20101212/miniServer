package ly.gmserver.entity;

import ly.db.AbstractEntry;
import ly.db.DbMeta;

@DbMeta.DbTable(name = "gm_admin")
public class GmAdminEntry extends AbstractEntry {
    private static final String[] DIRTY_FIELDS = {
        "id", "username", "password", "role", "status", "createTime", "updateTime"
    };

    @DbMeta.DbMasterKey(name = "id", autoIncrement = true)
    @DbMeta.DbField(name = "id")
    private Long id;

    @DbMeta.DbField(name = "username")
    private String username;

    @DbMeta.DbField(name = "password")
    private String password;

    @DbMeta.DbField(name = "role")
    private Integer role;

    @DbMeta.DbField(name = "status")
    private Integer status;

    @DbMeta.DbField(name = "createTime")
    private java.time.LocalDateTime createTime;

    @DbMeta.DbField(name = "updateTime")
    private java.time.LocalDateTime updateTime;

    public GmAdminEntry() {
        initDirtyState(DIRTY_FIELDS.length);
    }

    @Override
    protected String[] allDirtyFieldNames() {
        return DIRTY_FIELDS;
    }

    public void save() {
        GmAdminHelper.save(this);
    }

    public void update() {
        GmAdminHelper.update(this);
    }

    public void delete() {
        GmAdminHelper.delete(this);
    }

    public void asyncSave() {
        GmAdminHelper.asyncSave(this);
    }

    public void asyncUpdate() {
        GmAdminHelper.asyncUpdate(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        autoAddCurVersion();
        markFieldDirty(0);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        autoAddCurVersion();
        markFieldDirty(1);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        autoAddCurVersion();
        markFieldDirty(2);
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
        autoAddCurVersion();
        markFieldDirty(3);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
        autoAddCurVersion();
        markFieldDirty(4);
    }

    public java.time.LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.time.LocalDateTime createTime) {
        this.createTime = createTime;
        autoAddCurVersion();
        markFieldDirty(5);
    }

    public java.time.LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(java.time.LocalDateTime updateTime) {
        this.updateTime = updateTime;
        autoAddCurVersion();
        markFieldDirty(6);
    }

    @Override
    public String toString() {
        return "GmAdminEntry{" +
                "id=" + id +
                ", username=" + username +
                ", role=" + role +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
