package ly.gmserver.controller;

import java.util.Map;

import ly.db.entry.SecurityBanEntry;
import ly.db.entry.SecurityEventLogEntry;
import ly.gmserver.dto.ApiResponse;
import ly.gmserver.dto.PageResult;
import ly.gmserver.service.GmSecurityService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/security")
public class SecurityController {
  private final GmSecurityService securityService;

  public SecurityController(GmSecurityService securityService) {
    this.securityService = securityService;
  }

  @GetMapping("/ban/list")
  public ApiResponse<PageResult<SecurityBanEntry>> listBans(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "20") int pageSize,
      @RequestParam(required = false) Integer banType,
      @RequestParam(required = false) String target,
      @RequestParam(required = false) Integer status) {
    return ApiResponse.success(securityService.listBans(page, pageSize, banType, target, status));
  }

  @PostMapping("/ban/create")
  public ApiResponse<Void> createBan(@RequestBody Map<String, Object> body) {
    boolean ok = securityService.createBan(body, currentOperator());
    return ok ? ApiResponse.success() : ApiResponse.error("封禁失败");
  }

  @PostMapping("/ban/release")
  public ApiResponse<Void> releaseBan(@RequestBody Map<String, Object> body) {
    boolean ok = securityService.releaseBan(body, currentOperator());
    return ok ? ApiResponse.success() : ApiResponse.error("解除封禁失败");
  }

  @GetMapping("/event/list")
  public ApiResponse<PageResult<SecurityEventLogEntry>> listEvents(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "20") int pageSize,
      @RequestParam(required = false) Integer eventType,
      @RequestParam(required = false) String account,
      @RequestParam(required = false) Long playerId) {
    return ApiResponse.success(securityService.listEvents(page, pageSize, eventType, account, playerId));
  }

  private String currentOperator() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || auth.getCredentials() == null) {
      return "unknown";
    }
    return String.valueOf(auth.getCredentials());
  }
}
