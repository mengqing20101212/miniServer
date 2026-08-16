package ly.script;

import ly.net.ConnectSession;
import ly.net.HandlerContext;
import ly.net.IController;
import ly.net.packet.MessagePacket;
import ly.net.packet.MessagePacketFactory;
import ly.proto.Cmd;
import ly.proto.GmRuntimeScriptProto;

/** Core RPC route shared by every server that supports one-shot emergency scripts. */
public final class RuntimeScriptController implements IController {
  @Override
  public void registerHandlerRouter() {
    register(
        Cmd.CMD.CS_GmRuntimeScriptExecute,
        ConnectSession.class,
        MessagePacket.class,
        GmRuntimeScriptProto.csGmRuntimeScriptExecute.class,
        this::execute);
  }

  private void execute(
      HandlerContext<ConnectSession, MessagePacket> context,
      GmRuntimeScriptProto.csGmRuntimeScriptExecute command) {
    GmRuntimeScriptProto.scGmRuntimeScriptExecute response =
        RuntimeScriptExecutor.getInstance().execute(command);
    context
        .session()
        .addSendPacket(
            MessagePacketFactory.createMessagePacket(
                context.packet().getGuid(),
                Cmd.CMD.SC_GmRuntimeScriptExecute_VALUE,
                response,
                0,
                0));
  }
}
