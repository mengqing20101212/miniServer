package ly;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ly.proto.Cmd;

/** Scene 客户端协议必须保持在 Gate 的客户端业务号段。 */
public class SceneProtocolCommandRangeTest {
    @Test
    public void sceneClientCommandsStayBelowServerInternalRange() {
        int[] requestCommands = {
                Cmd.CMD.CS_SceneQuery_VALUE,
                Cmd.CMD.CS_SceneEnter_VALUE,
                Cmd.CMD.CS_SceneMove_VALUE,
                Cmd.CMD.CS_SceneLeave_VALUE,
                Cmd.CMD.CS_SceneView_VALUE,
                Cmd.CMD.CS_ScenePathFind_VALUE
        };
        for (int requestCommand : requestCommands) {
            assertTrue(requestCommand < Cmd.CMD.CS_Server2Server_VALUE);
            assertTrue(requestCommand >= 2_000 && requestCommand < 2_100);
        }

        assertEquals(Cmd.CMD.CS_SceneQuery_VALUE + 1, Cmd.CMD.SC_SceneQuery_VALUE);
        assertEquals(Cmd.CMD.CS_SceneEnter_VALUE + 1, Cmd.CMD.SC_SceneEnter_VALUE);
        assertEquals(Cmd.CMD.CS_SceneMove_VALUE + 1, Cmd.CMD.SC_SceneMove_VALUE);
        assertEquals(Cmd.CMD.CS_SceneLeave_VALUE + 1, Cmd.CMD.SC_SceneLeave_VALUE);
        assertEquals(Cmd.CMD.CS_SceneView_VALUE + 1, Cmd.CMD.SC_SceneView_VALUE);
        assertEquals(Cmd.CMD.CS_ScenePathFind_VALUE + 1, Cmd.CMD.SC_ScenePathFind_VALUE);
    }
}
