package ly.gmserver.entity;

import ly.db.AbstractEntry;
import ly.db.DbMeta;

@DbMeta.DbTable(name = "gm_role")
public class GmRoleEntry extends AbstractEntry {
    private static final String[] DIRTY_FIELDS = {
        "id", "name", "description"
    };

    @DbMeta.DbMasterKey(name = "id", autoIncrement = true)
    @DbMeta.DbField(name = "id")
    private Integer id;

    @DbMeta.DbField(name = "name")
    private String name;

    @DbMeta.DbField(name = "description")
    private String description;

    public GmRoleEntry() {
        initDirtyState(DIRTY_FIELDS.length);
    }

    @Override
    protected String[] allDirtyFieldNames() {
        return DIRTY_FIELDS;
    }

    public void save() {
        GmRoleHelper.save(this);
    }

    public void update() {
        GmRoleHelper.update(this);
    }

    public void delete() {
        GmRoleHelper.delete(this);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
        autoAddCurVersion();
        markFieldDirty(0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        autoAddCurVersion();
        markFieldDirty(1);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        autoAddCurVersion();
        markFieldDirty(2);
    }

    @Override
    public String toString() {
        return "GmRoleEntry{" +
                "id=" + id +
                ", name=" + name +
                ", description=" + description +
                '}';
    }
}
