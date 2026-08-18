package ly.sceneserver.common;

import java.util.Arrays;

/**
 * 场景静态地图数据。
 *
 * <p>100 万格不创建 100 万个 Tile 对象，而使用紧凑的一维数组保存静态字段：
 * terrain、configId、flags 和 spawnRuleId。数组下标由 {@code y * width + x} 计算得到。
 * 该对象可以被同一逻辑场景下的多个 SceneShard 共享，避免每个分片重复保存一份静态地图。
 */
public final class SceneStaticMap {
    /** 地图尺寸固定，所有静态字段都使用相同的一维下标 y * width + x。 */
    private final int width;
    private final int height;
    /** 地形类型，决定道路、平原、森林、山地、水域等基础移动规则。 */
    private final short[] terrain;
    /** 策划表配置 ID，供业务查询格子关联的静态配置。 */
    private final int[] configId;
    /** WALKABLE、BLOCKS_VISION 等可组合静态标记。 */
    private final byte[] flags;
    /** 资源点、怪物等定时刷新规则 ID；运行时实例不直接存进静态数组。 */
    private final int[] spawnRuleId;
    /** 每次加载器修改静态数组都会增加，用于使旧 Region 连通图失效。 */
    private long modificationVersion;
    /** SceneRuntime 启动前置为 true；冻结后只能通过动态对象/阻挡快照表达变化。 */
    private boolean frozen;

    public SceneStaticMap(int width, int height) {
        if (width <= 0 || height <= 0 || width > Integer.MAX_VALUE / height) {
            throw new IllegalArgumentException("invalid static map size");
        }
        this.width = width;
        this.height = height;
        int cellCount = width * height;
        this.terrain = new short[cellCount];
        this.configId = new int[cellCount];
        this.flags = new byte[cellCount];
        this.spawnRuleId = new int[cellCount];
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public int cellCount() {
        return terrain.length;
    }

    public short terrain(int x, int y) {
        return terrain[index(x, y)];
    }

    public int configId(int x, int y) {
        return configId[index(x, y)];
    }

    public byte flags(int x, int y) {
        return flags[index(x, y)];
    }

    public int spawnRuleId(int x, int y) {
        return spawnRuleId[index(x, y)];
    }

    /** A* 热路径按一维下标读取，避免重复计算坐标索引。 */
    short terrainAt(int index) {
        return terrain[index];
    }

    /** A* 热路径按一维下标读取，数组在场景启动后只读。 */
    byte flagsAt(int index) {
        return flags[index];
    }

    /** 由地图加载器调用，运行时不应频繁修改静态数组。 */
    public void set(int x, int y, short terrain, int configId, byte flags, int spawnRuleId) {
        // 只允许地图加载阶段调用。运行期直接修改会使寻路线程读到不一致的 Region 图。
        requireMutable();
        int index = index(x, y);
        this.terrain[index] = terrain;
        this.configId[index] = configId;
        this.flags[index] = flags;
        this.spawnRuleId[index] = spawnRuleId;
        modificationVersion++;
    }

    /** 清空所有静态字段，便于测试或重新加载一张空地图。 */
    public void clear() {
        // clear 同样属于重新加载行为；已启动场景不允许原地清空权威静态地图。
        requireMutable();
        Arrays.fill(terrain, (short) 0);
        Arrays.fill(configId, 0);
        Arrays.fill(flags, (byte) 0);
        Arrays.fill(spawnRuleId, 0);
        modificationVersion++;
    }

    /** Region 连通图用它判断静态地图是否在建图后被重新加载。 */
    long modificationVersion() {
        return modificationVersion;
    }

    /** 静态地图加载结束后冻结，运行期地形变化必须进入独立的动态阻挡快照。 */
    void freeze() {
        // 只做状态切换，不复制四组大数组；多个 SceneShard 和寻路线程继续共享同一份只读数据。
        frozen = true;
    }

    private void requireMutable() {
        if (frozen) {
            throw new IllegalStateException("static scene map is frozen");
        }
    }

    private int index(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IndexOutOfBoundsException("scene point out of bounds: " + x + "," + y);
        }
        return y * width + x;
    }
}
