package ly.sceneserver.common;

import java.util.List;

/** 多个 SceneShard 汇总后的客户端视野快照。 */
public record SceneViewSnapshot(
        long playerId,
        ScenePoint centerBlock,
        SceneViewLevel viewLevel,
        long tickNumber,
        List<SceneObjectSnapshot> objects,
        List<SceneBlockSnapshot> blocks,
        List<Integer> discoveredBlockIndices) {

    public SceneViewSnapshot {
        objects = List.copyOf(objects);
        blocks = List.copyOf(blocks);
        discoveredBlockIndices = List.copyOf(discoveredBlockIndices);
    }
}
