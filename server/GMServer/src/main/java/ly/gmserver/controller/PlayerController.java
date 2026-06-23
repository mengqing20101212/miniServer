package ly.gmserver.controller;

import java.util.Map;
import ly.gmserver.dto.ApiResponse;
import ly.gmserver.service.GmPlayerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/player")
public class PlayerController {
  private final GmPlayerService playerService;

  public PlayerController(GmPlayerService playerService) {
    this.playerService = playerService;
  }

  @GetMapping("/detail")
  public ApiResponse<Map<String, Object>> detail(@RequestParam long playerId) {
    try {
      return ApiResponse.success(playerService.detail(playerId));
    } catch (Exception e) {
      return ApiResponse.error(e.getMessage());
    }
  }

  @PostMapping("/module/update")
  public ApiResponse<Map<String, Object>> updateModule(@RequestBody Map<String, Object> body) {
    try {
      return ApiResponse.success(playerService.updateModule(body, currentOperator()));
    } catch (Exception e) {
      return ApiResponse.error(e.getMessage());
    }
  }

  private String currentOperator() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || auth.getName() == null || auth.getName().isBlank()) {
      return "unknown";
    }
    return auth.getName();
  }
}
