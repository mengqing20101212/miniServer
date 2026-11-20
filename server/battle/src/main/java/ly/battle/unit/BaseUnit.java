package ly.battle.unit;

/** 战斗的基础单位 */
public abstract class BaseUnit {
    private final long id;
    private final int tid;
    public long getId() {
        return id;
    }

    public BaseUnit(long id, int tid) {
        this.id = id;
        this.tid = tid;
    }

    public int getTid() {
        return tid;
    }
}
