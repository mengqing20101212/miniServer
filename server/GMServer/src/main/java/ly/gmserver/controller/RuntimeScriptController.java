package ly.gmserver.controller;

import java.util.List;
import java.util.Map;
import ly.db.entry.GmRuntimeScriptEntry;
import ly.gmserver.dto.ApiResponse;
import ly.gmserver.service.RuntimeScriptService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/runtime-script")
public class RuntimeScriptController {
  private final RuntimeScriptService service;

  public RuntimeScriptController(RuntimeScriptService service) {
    this.service = service;
  }

  @GetMapping("/servers")
  public ApiResponse<List<Map<String, Object>>> servers() {
    return ApiResponse.success(service.servers());
  }

  @GetMapping("/history")
  public ApiResponse<List<Map<String, Object>>> history() {
    return ApiResponse.success(service.list());
  }

  @PostMapping("/validate")
  public ApiResponse<Map<String, Object>> validate(@RequestBody Map<String, Object> body) {
    try {
      return ApiResponse.success(
          service.validate(text(body.get("source")), text(body.get("entryClass"))));
    } catch (Exception error) {
      return ApiResponse.error(error.getMessage());
    }
  }

  @PostMapping("/execute")
  public ApiResponse<GmRuntimeScriptEntry> execute(@RequestBody Map<String, Object> body) {
    try {
      return ApiResponse.success(
          service.execute(
              text(body.get("targetServerId")),
              text(body.get("source")),
              text(body.get("entryClass")),
              text(body.get("argumentsJson")),
              operator()));
    } catch (Exception error) {
      return ApiResponse.error(error.getMessage());
    }
  }

  private String operator() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      return "unknown";
    }
    Object credentials = authentication.getCredentials();
    return credentials == null ? String.valueOf(authentication.getPrincipal()) : String.valueOf(credentials);
  }

  private String text(Object value) {
    return value == null ? "" : String.valueOf(value);
  }
}
