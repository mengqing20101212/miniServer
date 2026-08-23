package ly.bot.action.impl;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.protobuf.AbstractMessage;

import ly.ProtoMessageFactory;
import ly.bot.action.RobotAction;
import ly.bot.action.RobotActionContext;
import ly.bot.action.RobotActionResult;
import ly.net.packet.MessagePacket;
import ly.proto.Cmd;

/**
 * 一次真实客户端场景请求及其异步响应。
 *
 * <p>本 Action 不创建任何 TCP 连接，只调用 RobotSession 的统一发包入口，因此 seq、sid、
 * guid 和 PendingRequest 都来自当前 Gate 会话。响应经过 Scene → Game → Gate 回到 Bot 后，
 * ResponseHandler 才会完成 {@link #responseFuture()}。</p>
 */
public final class SceneRobotAction<T extends AbstractMessage> implements RobotAction {
    private final Cmd.CMD requestCmd;
    private final Cmd.CMD responseCmd;
    private final AbstractMessage request;
    private final Class<T> responseType;
    private final String name;
    private final AtomicBoolean executed = new AtomicBoolean();
    private final CompletableFuture<T> responseFuture = new CompletableFuture<>();

    public SceneRobotAction(
            Cmd.CMD requestCmd,
            Cmd.CMD responseCmd,
            AbstractMessage request,
            Class<T> responseType,
            String name) {
        this.requestCmd = requestCmd;
        this.responseCmd = responseCmd;
        this.request = request;
        this.responseType = responseType;
        this.name = name;
    }

    @Override
    public RobotActionResult execute(RobotActionContext context) {
        if (!executed.compareAndSet(false, true)) {
            return RobotActionResult.fail(name + " 不能重复执行");
        }
        if (!context.getSession().sendActionPacket(this, request)) {
            IllegalStateException failure = new IllegalStateException(name + " 发包失败");
            responseFuture.completeExceptionally(failure);
            return RobotActionResult.fail(failure.getMessage());
        }
        return RobotActionResult.success();
    }

    @Override
    public void onResponse(MessagePacket response, RobotActionContext context) {
        AbstractMessage decoded = ProtoMessageFactory.createProtoMessage(response.getCmd(), response.getData());
        if (!responseType.isInstance(decoded)) {
            responseFuture.completeExceptionally(new IllegalStateException(
                    name + " 回包类型错误: expected=" + responseType.getSimpleName()
                            + ", actual=" + (decoded == null ? "null" : decoded.getClass().getName())));
            return;
        }
        responseFuture.complete(responseType.cast(decoded));
    }

    public CompletableFuture<T> responseFuture() {
        return responseFuture;
    }

    @Override
    public int requestCmd() {
        return requestCmd.getNumber();
    }

    @Override
    public int responseCmd() {
        return responseCmd.getNumber();
    }

    @Override
    public String getName() {
        return name;
    }
}
