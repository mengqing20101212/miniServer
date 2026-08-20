package ly.sceneserver.common.persistence;

import com.google.protobuf.InvalidProtocolBufferException;
import java.util.ArrayList;
import java.util.List;
import ly.db.entry.SceneMarchEntry;
import ly.db.entry.SceneMarchEntryHelper;
import ly.db.entry.SceneObjectEntry;
import ly.db.entry.SceneObjectEntryHelper;
import ly.db.entry.SceneRallyEntry;
import ly.db.entry.SceneRallyEntryHelper;
import ly.proto.Scene;
import ly.sceneserver.common.SceneObjectType;
import ly.sceneserver.common.ScenePoint;
import ly.sceneserver.common.SceneWorldObjectProtoMapper;
import ly.sceneserver.common.SceneWorldObjectState;
import ly.sceneserver.common.march.SceneMarchProtoMapper;
import ly.sceneserver.common.march.SceneMarchSnapshot;
import ly.sceneserver.common.march.SceneRallySnapshot;

/**
 * MySQL 场景聚合仓储。
 *
 * <p>本类只做领域快照与 Entry 的转换；查询、分页、SQL 生成和 revision UPSERT 全部由实体
 * Helper 与 MysqlService 完成。
 */
public final class MysqlSceneAggregateStore implements SceneAggregateStore {

    @Override
    public List<SceneObjectProjection> loadActiveObjectPage(
            String sceneId, long afterObjectId, int limit) {
        List<SceneObjectEntry> entries = SceneObjectEntryHelper.selectActivePage(
                sceneId, afterObjectId, limit);
        ArrayList<SceneObjectProjection> result = new ArrayList<>(entries.size());
        for (SceneObjectEntry entry : entries) {
            result.add(toProjection(entry));
        }
        return List.copyOf(result);
    }

    @Override
    public List<SceneMarchProjection> loadActiveMarchPage(
            String sceneId, long afterMarchId, int limit) {
        List<SceneMarchEntry> entries = SceneMarchEntryHelper.selectActivePage(
                sceneId, afterMarchId, limit);
        ArrayList<SceneMarchProjection> result = new ArrayList<>(entries.size());
        for (SceneMarchEntry entry : entries) {
            result.add(toProjection(entry));
        }
        return List.copyOf(result);
    }

    @Override
    public List<SceneRallyProjection> loadActiveRallyPage(
            String sceneId, long afterRallyId, int limit) {
        List<SceneRallyEntry> entries = SceneRallyEntryHelper.selectActivePage(
                sceneId, afterRallyId, limit);
        ArrayList<SceneRallyProjection> result = new ArrayList<>(entries.size());
        for (SceneRallyEntry entry : entries) {
            result.add(toProjection(entry));
        }
        return List.copyOf(result);
    }

    @Override
    public void upsert(SceneObjectProjection projection) {
        toEntry(projection).upsertIfNewer();
    }

    @Override
    public void upsert(SceneMarchProjection projection) {
        toEntry(projection).upsertIfNewer();
    }

    @Override
    public void upsert(SceneRallyProjection projection) {
        toEntry(projection).upsertIfNewer();
    }

    private static SceneObjectEntry toEntry(SceneObjectProjection projection) {
        SceneObjectEntry entry = new SceneObjectEntry();
        entry.setSceneId(projection.sceneId());
        entry.setObjectId(projection.objectId());
        entry.setObjectType(projection.objectType().persistenceCode());
        entry.setOwnerId(projection.ownerId());
        entry.setX(projection.point().x());
        entry.setY(projection.point().y());
        entry.setStateVersion(projection.stateVersion());
        entry.setDataTagMask(projection.dataTagMask());
        entry.setStateData(SceneWorldObjectProtoMapper.toProto(projection.state()).toByteArray());
        entry.setDataVersion(projection.state().dataVersion());
        entry.setRevision(projection.revision());
        entry.setDeleted(projection.deleted() ? 1 : 0);
        entry.setUpdateTime(projection.updateTime());
        return entry;
    }

    private static SceneMarchEntry toEntry(SceneMarchProjection projection) {
        Scene.SceneMarchSnapshot proto = SceneMarchProtoMapper.toProto(projection.snapshot());
        SceneMarchEntry entry = new SceneMarchEntry();
        entry.setSceneId(projection.sceneId());
        entry.setMarchId(projection.snapshot().marchId());
        entry.setOwnerPlayerId(projection.snapshot().ownerPlayerId());
        entry.setAllianceId(projection.snapshot().allianceId());
        entry.setCurrentX(projection.currentPoint().x());
        entry.setCurrentY(projection.currentPoint().y());
        entry.setMarchStatus(proto.getStatusValue());
        entry.setArrivalAtMillis(projection.snapshot().arrivalAtMillis());
        entry.setStateVersion(projection.snapshot().stateVersion());
        entry.setSnapshotData(proto.toByteArray());
        entry.setDataVersion(projection.dataVersion());
        entry.setRevision(projection.revision());
        entry.setDeleted(projection.deleted() ? 1 : 0);
        entry.setUpdateTime(projection.updateTime());
        return entry;
    }

    private static SceneRallyEntry toEntry(SceneRallyProjection projection) {
        Scene.SceneRallySnapshot proto = SceneMarchProtoMapper.toProto(projection.snapshot());
        SceneRallyEntry entry = new SceneRallyEntry();
        entry.setSceneId(projection.sceneId());
        entry.setRallyId(projection.snapshot().rallyId());
        entry.setLeaderPlayerId(projection.snapshot().leaderPlayerId());
        entry.setAllianceId(projection.snapshot().allianceId());
        entry.setCurrentX(projection.currentPoint().x());
        entry.setCurrentY(projection.currentPoint().y());
        entry.setRallyStatus(proto.getStatusValue());
        entry.setLaunchAtMillis(projection.snapshot().launchAtMillis());
        entry.setAppliedBattleResultId(projection.snapshot().appliedBattleResultId());
        entry.setStateVersion(projection.snapshot().stateVersion());
        entry.setSnapshotData(proto.toByteArray());
        entry.setDataVersion(projection.dataVersion());
        entry.setRevision(projection.revision());
        entry.setDeleted(projection.deleted() ? 1 : 0);
        entry.setUpdateTime(projection.updateTime());
        return entry;
    }

    private static SceneObjectProjection toProjection(SceneObjectEntry entry) {
        try {
            SceneWorldObjectState state = SceneWorldObjectProtoMapper.fromProto(
                    Scene.ScenePersistentObjectState.parseFrom(entry.getStateData()));
            if (state.dataVersion() != entry.getDataVersion()) {
                throw new IllegalStateException("scene object data version mismatch: " + entry.getObjectId());
            }
            return new SceneObjectProjection(
                    entry.getSceneId(),
                    entry.getObjectId(),
                    SceneObjectType.fromPersistenceCode(entry.getObjectType()),
                    entry.getOwnerId(),
                    new ScenePoint(entry.getX(), entry.getY()),
                    entry.getStateVersion(),
                    entry.getDataTagMask(),
                    state,
                    entry.getRevision(),
                    entry.getDeleted() != 0,
                    entry.getUpdateTime());
        } catch (InvalidProtocolBufferException error) {
            throw corrupt("scene_object", entry.getObjectId(), error);
        }
    }

    private static SceneMarchProjection toProjection(SceneMarchEntry entry) {
        try {
            SceneMarchSnapshot snapshot = SceneMarchProtoMapper.fromProto(
                    Scene.SceneMarchSnapshot.parseFrom(entry.getSnapshotData()));
            SceneMarchProjection projection = new SceneMarchProjection(
                    entry.getSceneId(),
                    new ScenePoint(entry.getCurrentX(), entry.getCurrentY()),
                    snapshot,
                    entry.getDataVersion(),
                    entry.getRevision(),
                    entry.getDeleted() != 0,
                    entry.getUpdateTime());
            if (snapshot.marchId() != entry.getMarchId()
                    || snapshot.ownerPlayerId() != entry.getOwnerPlayerId()
                    || snapshot.stateVersion() != entry.getStateVersion()
                    || snapshot.arrivalAtMillis() != entry.getArrivalAtMillis()
                    || SceneMarchProtoMapper.toProto(snapshot).getStatusValue() != entry.getMarchStatus()) {
                throw new IllegalStateException("scene_march metadata mismatch: " + entry.getMarchId());
            }
            return projection;
        } catch (InvalidProtocolBufferException error) {
            throw corrupt("scene_march", entry.getMarchId(), error);
        }
    }

    private static SceneRallyProjection toProjection(SceneRallyEntry entry) {
        try {
            SceneRallySnapshot snapshot = SceneMarchProtoMapper.fromProto(
                    Scene.SceneRallySnapshot.parseFrom(entry.getSnapshotData()));
            SceneRallyProjection projection = new SceneRallyProjection(
                    entry.getSceneId(),
                    new ScenePoint(entry.getCurrentX(), entry.getCurrentY()),
                    snapshot,
                    entry.getDataVersion(),
                    entry.getRevision(),
                    entry.getDeleted() != 0,
                    entry.getUpdateTime());
            if (snapshot.rallyId() != entry.getRallyId()
                    || snapshot.leaderPlayerId() != entry.getLeaderPlayerId()
                    || snapshot.stateVersion() != entry.getStateVersion()
                    || snapshot.launchAtMillis() != entry.getLaunchAtMillis()
                    || snapshot.appliedBattleResultId() != entry.getAppliedBattleResultId()
                    || SceneMarchProtoMapper.toProto(snapshot).getStatusValue() != entry.getRallyStatus()) {
                throw new IllegalStateException("scene_rally metadata mismatch: " + entry.getRallyId());
            }
            return projection;
        } catch (InvalidProtocolBufferException error) {
            throw corrupt("scene_rally", entry.getRallyId(), error);
        }
    }

    private static IllegalStateException corrupt(String table, long aggregateId, Exception cause) {
        return new IllegalStateException(
                "cannot decode " + table + " protobuf, aggregateId=" + aggregateId, cause);
    }
}
