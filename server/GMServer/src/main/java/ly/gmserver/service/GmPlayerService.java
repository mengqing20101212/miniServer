package ly.gmserver.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import ly.db.entry.PlayerEntry;
import ly.db.entry.PlayerEntryHelper;
import ly.proto.Cmd;
import ly.proto.GmPlayer;
import ly.redis.RedisKeys;
import ly.redis.RedisUtils;
import ly.rpc.RpcUtils;
import org.springframework.stereotype.Service;

/** GM 玩家详情服务，负责定位在线 GameServer 并转发 GM RPC。 */
@Service
public class GmPlayerService {
  private static final ObjectMapper MAPPER = new ObjectMapper();

  public Map<String, Object> detail(long playerId) {
    TargetGameServer target = locateTargetGameServer(playerId);
    GmPlayer.csGmPlayerDetail request =
        GmPlayer.csGmPlayerDetail.newBuilder().setPlayerId(playerId).build();
    GmPlayer.scGmPlayerDetail response =
        RpcUtils.syncRequest(
            target.gameServerId(), playerId, Cmd.CMD.CS_GmPlayerDetail_VALUE, request);
    if (response == null) {
      throw new IllegalStateException("目标 GameServer 无响应: " + target.gameServerId());
    }
    if (response.getCode() != 0) {
      throw new IllegalStateException(response.getError());
    }

    Map<String, Object> result = new LinkedHashMap<>();
    result.put("playerId", response.getPlayerId());
    result.put("online", response.getOnline());
    result.put("gameServerId", target.gameServerId());
    result.put("base", parseJson(response.getBaseJson()));
    List<Map<String, Object>> modules = new ArrayList<>();
    for (GmPlayer.gmPlayerModule module : response.getModulesList()) {
      Map<String, Object> item = new LinkedHashMap<>();
      item.put("moduleName", module.getModuleName());
      item.put("className", module.getClassName());
      item.put("editable", module.getEditable());
      item.put("json", parseJson(module.getJson()));
      modules.add(item);
    }
    result.put("modules", modules);
    return result;
  }

  @SuppressWarnings("unchecked")
  public Map<String, Object> updateModule(Map<String, Object> body, String operator) {
    long playerId = toLong(body.get("playerId"));
    String moduleName = stringValue(body.get("moduleName"));
    List<Map<String, Object>> changes =
        body.get("changes") instanceof List<?> list ? (List<Map<String, Object>>) list : List.of();
    if (playerId <= 0 || moduleName.isBlank() || changes.isEmpty()) {
      throw new IllegalArgumentException("参数错误");
    }

    TargetGameServer target = locateTargetGameServer(playerId);
    GmPlayer.csGmUpdatePlayerModule.Builder request =
        GmPlayer.csGmUpdatePlayerModule.newBuilder()
            .setPlayerId(playerId)
            .setModuleName(moduleName)
            .setOperator(operator == null ? "" : operator);
    for (Map<String, Object> change : changes) {
      request.addChanges(
          GmPlayer.gmPlayerFieldChange.newBuilder()
              .setPath(stringValue(change.get("path")))
              .setOldValue(toJsonValue(change.get("oldValue")))
              .setNewValue(toJsonValue(change.get("newValue")))
              .build());
    }

    GmPlayer.scGmUpdatePlayerModule response =
        RpcUtils.syncRequest(
            target.gameServerId(),
            playerId,
            Cmd.CMD.CS_GmUpdatePlayerModule_VALUE,
            request.build());
    if (response == null) {
      throw new IllegalStateException("目标 GameServer 无响应: " + target.gameServerId());
    }
    if (response.getCode() != 0) {
      throw new IllegalStateException(response.getError());
    }

    Map<String, Object> result = new LinkedHashMap<>();
    result.put("playerId", response.getPlayerId());
    result.put("moduleName", response.getModuleName());
    result.put("json", parseJson(response.getJson()));
    return result;
  }

  private TargetGameServer locateTargetGameServer(long playerId) {
    PlayerEntry entry = PlayerEntryHelper.getPlayerEntryById(playerId);
    if (entry == null) {
      throw new IllegalStateException("玩家不存在: " + playerId);
    }
    String account = entry.getAccount();
    if (account == null || account.isBlank()) {
      throw new IllegalStateException("玩家账号为空: " + playerId);
    }
    String gameServerId = RedisUtils.get(RedisKeys.ACCOUNT_GAME_SERVER_ID_KEY.getKey(account));
    if (gameServerId == null || gameServerId.isBlank()) {
      throw new IllegalStateException("玩家不在线，不能查看或编辑");
    }
    return new TargetGameServer(account, gameServerId);
  }

  private Map<String, Object> parseJson(String json) {
    try {
      if (json == null || json.isBlank()) {
        return Map.of();
      }
      return MAPPER.readValue(json, new TypeReference<Map<String, Object>>() {});
    } catch (Exception e) {
      Map<String, Object> raw = new LinkedHashMap<>();
      raw.put("raw", json);
      return raw;
    }
  }

  private String toJsonValue(Object value) {
    try {
      return MAPPER.writeValueAsString(value);
    } catch (Exception e) {
      return value == null ? "null" : String.valueOf(value);
    }
  }

  private String stringValue(Object value) {
    return value == null ? "" : String.valueOf(value).trim();
  }

  private long toLong(Object value) {
    if (value == null || value.toString().isBlank()) {
      return 0L;
    }
    return Long.parseLong(value.toString());
  }

  private record TargetGameServer(String account, String gameServerId) {}
}
