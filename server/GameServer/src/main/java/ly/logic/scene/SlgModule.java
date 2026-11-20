package ly.logic.scene;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.baidu.bjf.remoting.protobuf.FieldType;
import java.util.*;
import ly.logic.player.AbstractModule;
import ly.logic.player.event.*;
import ly.logic.asset.*;

@ProtobufClass
public class SlgModule extends AbstractModule {
    @Protobuf(fieldType = FieldType.INT64, order = 1)
    public List<Long> settledMarches = new ArrayList<>();

    public void settle(long marchId, int resourceId, long amount) {
        if (settledMarches.contains(marchId)) return;
        Award.create(resourceId, amount).award(player, ChangeReason.OTHER);
        settledMarches.add(marchId);
        markModuleDataDirty();
    }
    @Override public void onLoadData() {}
    @Override public void onOpenFunction() {}
    @Override public void onEvent(PlayerEventParam event) {}
    @Override public List<PlayerEventType> getRegisterEventTypes() { return List.of(); }
}
