package ly.logic.login;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import ly.logic.player.AbstractModule;
import ly.logic.player.event.PlayerEventParam;
import ly.logic.player.event.PlayerEventType;

import java.util.List;

/**
 * 游戏服玩家相关模型，承载玩家连接状态、持久化数据或模块数据。
 */
@ProtobufClass
@EnableZigZap
public class PlayerLogicModule extends AbstractModule {
    @Protobuf(fieldType = FieldType.INT64, order = 1, required = false)
    long lastLoginTimer;

    @Override
    public void onLoadData() {
    }

    @Override
    public void onOpenFunction() {
    }

    @Override
    public void onEvent(PlayerEventParam param) {

    }

    @Override
    public List<PlayerEventType> getRegisterEventTypes() {
        return List.of(PlayerEventType.CROSS_DAY_ZERO);
    }
}
