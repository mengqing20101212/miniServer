package ly.sceneserver.common;

import java.util.BitSet;

/**
 * 寻路线程使用的战争迷雾只读快照。
 *
 * <p>快照以 AOI 块为粒度，不把 SceneShard 内部可变 BitSet 暴露给异步线程。
 */
public final class SceneVisibilitySnapshot {
    /** AOI/迷雾块边长，必须与当前 SceneConfig.regionSize 一致。 */
    private final int regionSize;
    /** 一行包含多少个 Region，用于把 (x,y) 转换成 BitSet 下标。 */
    private final int blockColumns;
    /** 当前相机实时可见块；玩家移动视野后会变化，不需要永久保存。 */
    private final BitSet visibleBlocks;
    /** 玩家历史探索块；每个玩家、每个场景独立持久化。 */
    private final BitSet discoveredBlocks;

    public SceneVisibilitySnapshot(
            int regionSize,
            int blockColumns,
            BitSet visibleBlocks,
            BitSet discoveredBlocks) {
        this.regionSize = regionSize;
        this.blockColumns = blockColumns;
        // 异步寻路线程只读取快照；克隆后 SceneShard 继续修改原 BitSet 也不会产生数据竞争。
        this.visibleBlocks = (BitSet) visibleBlocks.clone();
        this.discoveredBlocks = (BitSet) discoveredBlocks.clone();
    }

    public boolean allows(int x, int y, SceneFogPolicy policy) {
        if (policy == SceneFogPolicy.IGNORE) {
            return true;
        }
        // 迷雾按 Region 判定，不为 100 万个格子给每个玩家保存一个布尔值。
        int blockIndex = (y / regionSize) * blockColumns + (x / regionSize);
        return allowsRegion(blockIndex, policy);
    }

    /** Region 粗路径使用与格子寻路完全相同的玩家个人迷雾约束。 */
    public boolean allowsRegion(int blockIndex, SceneFogPolicy policy) {
        if (policy == SceneFogPolicy.IGNORE) {
            return true;
        }
        return policy == SceneFogPolicy.VISIBLE_ONLY
                ? visibleBlocks.get(blockIndex)
                : discoveredBlocks.get(blockIndex);
    }

    public int regionSize() {
        return regionSize;
    }

    public int blockColumns() {
        return blockColumns;
    }

    public BitSet visibleBlocks() {
        // 返回副本，禁止寻路或业务代码反向修改 SceneShard 的视野状态。
        return (BitSet) visibleBlocks.clone();
    }

    public BitSet discoveredBlocks() {
        // 返回副本，调用方可安全 OR 聚合多个 SceneShard 的个人探索记录。
        return (BitSet) discoveredBlocks.clone();
    }
}
