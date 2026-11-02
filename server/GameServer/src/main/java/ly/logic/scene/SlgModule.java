package ly.logic.scene;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.baidu.bjf.remoting.protobuf.FieldType;
import java.util.*;
import ly.logic.player.AbstractModule;
import ly.logic.player.event.*;
import ly.logic.asset.*;
import ly.proto.Scene;

@ProtobufClass
public class SlgModule extends AbstractModule {
    @Protobuf(fieldType = FieldType.INT64, order = 1)
    public List<Long> settledMarches = new ArrayList<>();

    public void settle(Scene.SceneMarchSnapshot march) {
        if (march.getStatus() != Scene.SceneMarchStatus.SCENE_MARCH_FINISHED
                || settledMarches.contains(march.getMarchId())) return;
        if (march.getRewardAmount() > 0)
            Award.create(march.getResourceId(), march.getRewardAmount()).award(player, ChangeReason.OTHER);
        settledMarches.add(march.getMarchId());
        markModuleDataDirty();
    }
    @Override public void onLoadData() {}
    @Override public void onOpenFunction() {}
    @Override public void onEvent(PlayerEventParam event) {}
    @Override public List<PlayerEventType> getRegisterEventTypes() { return List.of(); }
}
