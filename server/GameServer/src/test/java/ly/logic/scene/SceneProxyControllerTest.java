package ly.logic.scene;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ly.proto.Scene;

/** 验证客户端无法通过 Scene 请求体伪造其他玩家身份。 */
public class SceneProxyControllerTest {
    private static final long SPOOFED_PLAYER_ID = 999_999L;
    private static final long LOGGED_IN_PLAYER_ID = 123_456L;

    @Test
    public void allPlayerSceneRequestsUseLoggedInPlayerId() {
        assertEquals(LOGGED_IN_PLAYER_ID, SceneProxyController.bindPlayerId(
                Scene.csSceneEnter.newBuilder().setPlayerId(SPOOFED_PLAYER_ID).build(),
                LOGGED_IN_PLAYER_ID).getPlayerId());
        assertEquals(LOGGED_IN_PLAYER_ID, SceneProxyController.bindPlayerId(
                Scene.csSceneMove.newBuilder().setPlayerId(SPOOFED_PLAYER_ID).build(),
                LOGGED_IN_PLAYER_ID).getPlayerId());
        assertEquals(LOGGED_IN_PLAYER_ID, SceneProxyController.bindPlayerId(
                Scene.csSceneLeave.newBuilder().setPlayerId(SPOOFED_PLAYER_ID).build(),
                LOGGED_IN_PLAYER_ID).getPlayerId());
        assertEquals(LOGGED_IN_PLAYER_ID, SceneProxyController.bindPlayerId(
                Scene.csSceneView.newBuilder().setPlayerId(SPOOFED_PLAYER_ID).build(),
                LOGGED_IN_PLAYER_ID).getPlayerId());
        assertEquals(LOGGED_IN_PLAYER_ID, SceneProxyController.bindPlayerId(
                Scene.csScenePathFind.newBuilder().setPlayerId(SPOOFED_PLAYER_ID).build(),
                LOGGED_IN_PLAYER_ID).getPlayerId());
    }
}
