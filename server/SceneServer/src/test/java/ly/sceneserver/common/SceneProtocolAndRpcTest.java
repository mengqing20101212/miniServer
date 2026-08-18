package ly.sceneserver.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

import ly.ProtoMessageFactory;
import ly.net.Server2ServerRpcContext;
import ly.net.packet.MessagePacket;
import ly.net.packet.MessagePacketFactory;
import ly.proto.Cmd;
import ly.proto.ErrorMsg;
import ly.proto.Scene;
import ly.proto.Server;

/** 验证场景协议工厂和现有通用 Server2Server RPC 外壳可以组合工作。 */
public class SceneProtocolAndRpcTest {

    @Test
    public void sceneCommandIsRegisteredInProtoFactory() {
        Scene.csSceneMove request = Scene.csSceneMove.newBuilder()
                .setSceneId("world-1")
                .setScope(Scene.SceneScope.SCENE_SCOPE_LOCAL)
                .setPlayerId(10_000_001L)
                .setTargetPoint(Scene.ScenePoint.newBuilder().setX(10).setY(20))
                .setRequestId(99)
                .build();

        Object decoded = ProtoMessageFactory.createProtoMessage(
                Cmd.CMD.CS_SceneMove_VALUE, request.toByteArray());

        assertTrue(decoded instanceof Scene.csSceneMove);
        Scene.csSceneMove decodedMove = (Scene.csSceneMove) decoded;
        assertEquals(99, decodedMove.getRequestId());
        assertEquals(10, decodedMove.getTargetPoint().getX());
        assertEquals(20, decodedMove.getTargetPoint().getY());
    }

    @Test
    public void genericRpcEnvelopeKeepsCallIdAndInnerCommand() throws Exception {
        Scene.scSceneMetrics response = Scene.scSceneMetrics.newBuilder()
                .setResult(ErrorMsg.ErrorCode.Ok)
                .setSceneId("world-1")
                .build();
        AtomicReference<MessagePacket> wrappedPacket = new AtomicReference<>();

        Server2ServerRpcContext.run(12345L, () -> wrappedPacket.set(
                Server2ServerRpcContext.wrapResponseIfNeeded(
                        MessagePacketFactory.createMessagePacket(
                                7L,
                                Cmd.CMD.SC_SceneMetrics_VALUE,
                                response,
                                0,
                                0))));

        MessagePacket packet = wrappedPacket.get();
        Server.scServer2Server envelope = Server.scServer2Server.parseFrom(packet.getData());
        assertEquals(Cmd.CMD.SC_Server2Server_VALUE, packet.getCmd());
        assertEquals(12345L, envelope.getCallId());
        assertEquals(Cmd.CMD.SC_SceneMetrics_VALUE, envelope.getCmd());
        assertEquals(response, Scene.scSceneMetrics.parseFrom(envelope.getData()));
    }

    @Test
    public void viewAndPathCommandsAreRegisteredInProtoFactory() {
        Scene.csSceneView view = Scene.csSceneView.newBuilder()
                .setSceneId("world-1")
                .setPlayerId(1)
                .setCenterPoint(Scene.ScenePoint.newBuilder().setX(10).setY(20))
                .setViewLevel(Scene.SceneViewLevel.SCENE_VIEW_REGION)
                .build();
        Scene.csScenePathFind path = Scene.csScenePathFind.newBuilder()
                .setSceneId("world-1")
                .setPlayerId(1)
                .setStartPoint(Scene.ScenePoint.newBuilder().setX(10).setY(20))
                .setTargetPoint(Scene.ScenePoint.newBuilder().setX(30).setY(40))
                .setFogPolicy(Scene.SceneFogPolicy.SCENE_FOG_DISCOVERED_ONLY)
                .build();

        assertTrue(ProtoMessageFactory.createProtoMessage(
                Cmd.CMD.CS_SceneView_VALUE, view.toByteArray()) instanceof Scene.csSceneView);
        assertTrue(ProtoMessageFactory.createProtoMessage(
                Cmd.CMD.CS_ScenePathFind_VALUE, path.toByteArray()) instanceof Scene.csScenePathFind);
    }

    @Test
    public void asyncHandlerCanCaptureCurrentRpcCallId() {
        AtomicReference<Long> callId = new AtomicReference<>();
        Server2ServerRpcContext.run(9988L, () -> callId.set(Server2ServerRpcContext.currentCallId()));
        assertEquals(Long.valueOf(9988L), callId.get());
        assertEquals(0L, Server2ServerRpcContext.currentCallId());
    }
}
