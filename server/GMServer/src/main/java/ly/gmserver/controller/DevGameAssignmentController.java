package ly.gmserver.controller;

import java.util.List;
import java.util.Map;
import ly.config.ServerTypeEnum;
import ly.db.entry.LoginEntry;
import ly.db.entry.LoginEntryHelper;
import ly.gmserver.dto.ApiResponse;
import ly.nacos.NacosService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Internal assignment API for planner/test accounts and personal GameServer nodes. */
@RestController
@RequestMapping("/api/dev-game-assignment")
public class DevGameAssignmentController {

  @GetMapping("/list")
  public ApiResponse<List<Map<String, Object>>> list(
      @RequestParam(defaultValue = "") String keyword) {
    String normalized = keyword == null ? "" : keyword.trim().toLowerCase();
    List<Map<String, Object>> result =
        LoginEntryHelper.select(new String[0]).stream()
            .filter(entry -> normalized.isEmpty() || entry.getAccount().toLowerCase().contains(normalized))
            .map(this::toView)
            .toList();
    return ApiResponse.success(result);
  }

  @PostMapping("/assign")
  public ApiResponse<Void> assign(@RequestBody Map<String, String> body) {
    String account = trim(body.get("account"));
    String serverId = trim(body.get("serverId"));
    if (account.isEmpty()) {
      return ApiResponse.error("账号不能为空");
    }
    if (!serverId.isEmpty() && !serverId.matches("game-local-[A-Za-z0-9_-]+")) {
      return ApiResponse.error("开发服 ID 必须以 game-local- 开头");
    }
    List<LoginEntry> entries = LoginEntryHelper.select(new String[] {"account"}, account);
    if (entries.isEmpty()) {
      return ApiResponse.error("账号不存在: " + account);
    }
    LoginEntry entry = entries.get(0);
    entry.setAssignedGameServerId(serverId);
    boolean updated = LoginEntryHelper.update(entry, "assigned_game_server_id");
    return updated ? ApiResponse.success() : ApiResponse.error("保存分配失败");
  }

  private Map<String, Object> toView(LoginEntry entry) {
    String serverId = trim(entry.getAssignedGameServerId());
    boolean online =
        !serverId.isEmpty()
            && NacosService.getInstance().getNodeList(ServerTypeEnum.GAME).stream()
                .anyMatch(node -> node.canUse() && serverId.equals(node.getServerId()));
    return Map.of(
        "account", entry.getAccount(),
        "serverId", serverId,
        "online", online);
  }

  private String trim(String value) {
    return value == null ? "" : value.trim();
  }
}
