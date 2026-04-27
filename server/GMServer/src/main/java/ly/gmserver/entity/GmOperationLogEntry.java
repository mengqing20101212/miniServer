package ly.gmserver.entity;

import ly.db.AbstractEntry;
import ly.db.DbMeta;

@DbMeta.DbTable(name = "gm_operation_log")
public class GmOperationLogEntry extends AbstractEntry {
    private static final String[] DIRTY_FIELDS = {
        "id", "adminId", "username", "action", "targetType", "targetId", "detail", "ip", "result", "createdAt"
    };

    @DbMeta.DbMasterKey(name = "id", autoIncrement = true)
    @DbMeta.DbField(name = "id")
    private Long id;

    @DbMeta.DbField(name = "adminId")
    private Long adminId;

    @DbMeta.DbField(name = "username")
    private String username;

    @DbMeta.DbField(name = "action")
    private String action;

    @DbMeta.DbField(name = "targetType")
    private String targetType;

    @DbMeta.DbField(name = "targetId")
    private String targetId;

    @DbMeta.DbField(name = "detail")
    private String detail;

    @DbMeta.DbField(name = "ip")
    private String ip;

    @DbMeta.DbField(name = "result")
    private String result;

    @DbMeta.DbField(name = "createdAt")
    private java.time.LocalDateTime createdAt;

    public GmOperationLogEntry() {
        initDirtyState(DIRTY_FIELDS.length);
    }

    @Override
    protected String[] allDirtyFieldNames() {
        return DIRTY_FIELDS;
    }

    public void save() {
        GmOperationLogHelper.save(this);
    }

    public void update() {
        GmOperationLogHelper.update(this);
    }

    public void delete() {
        GmOperationLogHelper.delete(this);
    }

    public void asyncSave() {
        GmOperationLogHelper.asyncSave(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        autoAddCurVersion();
        markFieldDirty(0);
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
        autoAddCurVersion();
        markFieldDirty(1);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        autoAddCurVersion();
        markFieldDirty(2);
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
        autoAddCurVersion();
        markFieldDirty(3);
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
        autoAddCurVersion();
        markFieldDirty(4);
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
        autoAddCurVersion();
        markFieldDirty(5);
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
        autoAddCurVersion();
        markFieldDirty(6);
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
        autoAddCurVersion();
        markFieldDirty(7);
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
        autoAddCurVersion();
        markFieldDirty(8);
    }

    public java.time.LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.time.LocalDateTime createdAt) {
        this.createdAt = createdAt;
        autoAddCurVersion();
        markFieldDirty(9);
    }

    @Override
    public String toString() {
        return "GmOperationLogEntry{" +
                "id=" + id +
                ", adminId=" + adminId +
                ", username=" + username +
                ", action=" + action +
                ", targetType=" + targetType +
                ", targetId=" + targetId +
                ", result=" + result +
                ", createdAt=" + createdAt +
                '}';
    }
}
