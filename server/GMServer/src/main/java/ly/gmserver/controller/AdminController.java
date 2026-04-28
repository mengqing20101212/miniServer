package ly.gmserver.controller;

import ly.gmserver.dto.*;
import ly.gmserver.service.GmAdminService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final GmAdminService adminService;

    public AdminController(GmAdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse resp = adminService.login(request);
        if (resp == null) {
            return ApiResponse.error("用户名或密码错误");
        }
        return ApiResponse.success(resp);
    }

    @GetMapping("/me")
    public ApiResponse<AdminVO> me() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ApiResponse.error(401, "未登录");
        }
        Long adminId = (Long) auth.getPrincipal();
        AdminVO vo = adminService.getById(adminId);
        return ApiResponse.success(vo);
    }

    @GetMapping("/list")
    public ApiResponse<PageResult<AdminVO>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(required = false) String keyword) {
        return ApiResponse.success(adminService.list(page, pageSize, keyword));
    }

    @GetMapping("/{id}")
    public ApiResponse<AdminVO> get(@PathVariable Long id) {
        AdminVO vo = adminService.getById(id);
        if (vo == null) return ApiResponse.error("管理员不存在");
        return ApiResponse.success(vo);
    }

    @PostMapping("/create")
    public ApiResponse<Void> create(@RequestBody Map<String, Object> body) {
        String username = (String) body.get("username");
        String password = (String) body.get("password");
        Integer roleId = body.get("roleId") != null ? Integer.valueOf(body.get("roleId").toString()) : null;
        boolean ok = adminService.create(username, password, roleId);
        return ok ? ApiResponse.success() : ApiResponse.error("创建失败（用户名可能已存在）");
    }

    @PostMapping("/update")
    public ApiResponse<Void> update(@RequestBody Map<String, Object> body) {
        Long id = Long.valueOf(body.get("id").toString());
        String username = (String) body.get("username");
        Integer roleId = body.get("roleId") != null ? Integer.valueOf(body.get("roleId").toString()) : null;
        Byte status = body.get("status") != null ? Byte.valueOf(body.get("status").toString()) : null;
        boolean ok = adminService.update(id, username, roleId, status);
        return ok ? ApiResponse.success() : ApiResponse.error("更新失败");
    }

    @PostMapping("/reset-password")
    public ApiResponse<Void> resetPassword(@RequestBody Map<String, Object> body) {
        Long id = Long.valueOf(body.get("id").toString());
        String newPassword = (String) body.get("password");
        boolean ok = adminService.resetPassword(id, newPassword);
        return ok ? ApiResponse.success() : ApiResponse.error("重置密码失败");
    }

    @PostMapping("/delete/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        boolean ok = adminService.delete(id);
        return ok ? ApiResponse.success() : ApiResponse.error("删除失败");
    }

    @GetMapping("/info")
    public ApiResponse<AdminVO> info() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ApiResponse.error(401, "未登录");
        }
        Long adminId = (Long) auth.getPrincipal();
        AdminVO vo = adminService.getById(adminId);
        return ApiResponse.success(vo);
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout() {
        SecurityContextHolder.clearContext();
        return ApiResponse.success();
    }
}
