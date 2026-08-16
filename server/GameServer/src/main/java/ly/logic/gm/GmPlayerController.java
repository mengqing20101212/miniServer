package ly.logic.gm;

import com.google.protobuf.AbstractMessage;
import ly.LoggerDef;
import ly.logic.player.AbstractModule;
import ly.logic.player.ModuleEnum;
import ly.logic.player.Player;
import ly.logic.player.PlayerManager;
import ly.logic.player.coroutine.CoroutineUtils;
import ly.net.GameConnectSession;
import ly.net.HandlerContext;
import ly.net.IGameController;
import ly.net.packet.MessagePacket;
import ly.net.packet.MessagePacketFactory;
import ly.proto.Cmd;
import ly.proto.GmPlayer;

/** GM 玩家详情和在线编辑控制器。 */
public class GmPlayerController implements IGameController {
  private static final int OK = 0;
  private static final int PLAYER_OFFLINE = 1;
  private static final int MODULE_NOT_FOUND = 2;
  private static final int PATCH_FAILED = 3;
  private static final int SAVE_UNSUPPORTED = 4;
  private static final int PARAM_INVALID = 5;

  @Override
  public void registerHandlerRouter() {
    clientHandlerRegister(
        Cmd.CMD.CS_GmPlayerDetail, GmPlayer.csGmPlayerDetail.class, this::handleDetail);
    clientHandlerRegister(
        Cmd.CMD.CS_GmUpdatePlayerModule,
        GmPlayer.csGmUpdatePlayerModule.class,
        this::handleUpdateModule);
  }

  private void handleDetail(
      HandlerContext<GameConnectSession, MessagePacket> context,
      GmPlayer.csGmPlayerDetail request) {
    long playerId = request.getPlayerId();
    Player player = PlayerManager.getInstance().getOnlinePlayer(playerId);
    if (player == null) {
      send(
          context.session(),
          playerId,
          Cmd.CMD.SC_GmPlayerDetail_VALUE,
          GmPlayer.scGmPlayerDetail.newBuilder()
              .setCode(PLAYER_OFFLINE)
              .setError("玩家不在线")
              .setPlayerId(playerId)
              .setOnline(false)
              .build());
      return;
    }

    try {
      GmPlayer.scGmPlayerDetail response = CoroutineUtils.call(player, this::buildDetail, 3_000L);
      send(context.session(), playerId, Cmd.CMD.SC_GmPlayerDetail_VALUE, response);
    } catch (Exception e) {
      LoggerDef.SystemLogger.error("GM 查询玩家详情失败, playerId={}", playerId, e);
      send(
          context.session(),
          playerId,
          Cmd.CMD.SC_GmPlayerDetail_VALUE,
          GmPlayer.scGmPlayerDetail.newBuilder()
              .setCode(PATCH_FAILED)
              .setError(e.getMessage())
              .setPlayerId(playerId)
              .setOnline(true)
              .build());
    }
  }

  private GmPlayer.scGmPlayerDetail buildDetail(Player player) {
    GmPlayer.scGmPlayerDetail.Builder builder =
        GmPlayer.scGmPlayerDetail.newBuilder()
            .setCode(OK)
            .setPlayerId(player.getPlayerId())
            .setOnline(true)
            .setBaseJson(GmPlayerReflectionUtils.toJson(player.getPlayerData().getPlayerEntry()));

    for (ModuleEnum moduleEnum : ModuleEnum.values()) {
      AbstractModule module = player.getPlayerData().getModule(moduleEnum);
      if (module == null) {
        continue;
      }
      builder.addModules(
          GmPlayer.gmPlayerModule.newBuilder()
              .setModuleName(moduleEnum.name())
              .setClassName(module.getClass().getName())
              .setJson(GmPlayerReflectionUtils.toJson(module))
              .setEditable(true)
              .build());
    }
    return builder.build();
  }

  private void handleUpdateModule(
      HandlerContext<GameConnectSession, MessagePacket> context,
      GmPlayer.csGmUpdatePlayerModule request) {
    long playerId = request.getPlayerId();
    Player player = PlayerManager.getInstance().getOnlinePlayer(playerId);
    if (player == null) {
      sendUpdateError(context.session(), playerId, request.getModuleName(), PLAYER_OFFLINE, "玩家不在线");
      return;
    }

    try {
      GmPlayer.scGmUpdatePlayerModule response =
          CoroutineUtils.call(player, p -> updateModule(p, request), 3_000L);
      send(context.session(), playerId, Cmd.CMD.SC_GmUpdatePlayerModule_VALUE, response);
    } catch (Exception e) {
      LoggerDef.SystemLogger.error(
          "GM 修改玩家模块失败, playerId={}, module={}", playerId, request.getModuleName(), e);
      sendUpdateError(
          context.session(), playerId, request.getModuleName(), PATCH_FAILED, e.getMessage());
    }
  }

  private GmPlayer.scGmUpdatePlayerModule updateModule(
      Player player, GmPlayer.csGmUpdatePlayerModule request) {
    ModuleEnum moduleEnum = findModule(request.getModuleName());
    if (moduleEnum == null) {
      return updateError(player.getPlayerId(), request.getModuleName(), MODULE_NOT_FOUND, "模块不存在");
    }
    AbstractModule module = player.getPlayerData().getModule(moduleEnum);
    if (module == null) {
      return updateError(player.getPlayerId(), request.getModuleName(), MODULE_NOT_FOUND, "模块未加载");
    }
    if (request.getChangesCount() <= 0) {
      return updateError(player.getPlayerId(), request.getModuleName(), PARAM_INVALID, "没有提交字段变更");
    }

    try {
      for (GmPlayer.gmPlayerFieldChange change : request.getChangesList()) {
        GmPlayerReflectionUtils.patch(module, change.getPath(), change.getNewValue());
      }
      if (!module.saveData()) {
        return updateError(
            player.getPlayerId(), moduleEnum.name(), SAVE_UNSUPPORTED, "模块暂不支持持久化");
      }
      player.getPlayerData().flushAsync();
      return GmPlayer.scGmUpdatePlayerModule.newBuilder()
          .setCode(OK)
          .setPlayerId(player.getPlayerId())
          .setModuleName(moduleEnum.name())
          .setJson(GmPlayerReflectionUtils.toJson(module))
          .build();
    } catch (Exception e) {
      LoggerDef.SystemLogger.error(
          "GM patch module failed, playerId={}, module={}",
          player.getPlayerId(),
          moduleEnum.name(),
          e);
      return updateError(player.getPlayerId(), moduleEnum.name(), PATCH_FAILED, e.getMessage());
    }
  }

  private ModuleEnum findModule(String moduleName) {
    if (moduleName == null || moduleName.isBlank()) {
      return null;
    }
    for (ModuleEnum moduleEnum : ModuleEnum.values()) {
      if (moduleEnum.name().equals(moduleName) || moduleEnum.getName().equals(moduleName)) {
        return moduleEnum;
      }
    }
    return null;
  }

  private void sendUpdateError(
      GameConnectSession session, long playerId, String moduleName, int code, String error) {
    send(session, playerId, Cmd.CMD.SC_GmUpdatePlayerModule_VALUE, updateError(playerId, moduleName, code, error));
  }

  private GmPlayer.scGmUpdatePlayerModule updateError(
      long playerId, String moduleName, int code, String error) {
    return GmPlayer.scGmUpdatePlayerModule.newBuilder()
        .setCode(code)
        .setError(error == null ? "" : error)
        .setPlayerId(playerId)
        .setModuleName(moduleName == null ? "" : moduleName)
        .build();
  }

  private void send(GameConnectSession session, long playerId, int cmd, AbstractMessage response) {
    session.addSendPacket(MessagePacketFactory.createMessagePacket(playerId, cmd, response, 0, 0));
  }
}
