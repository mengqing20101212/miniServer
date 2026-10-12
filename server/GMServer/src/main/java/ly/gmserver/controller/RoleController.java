package ly.gmserver.controller;

import ly.gmserver.dto.ApiResponse;
import ly.gmserver.dto.RoleVO;
import ly.gmserver.service.GmRoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    private final GmRoleService roleService;

    public RoleController(GmRoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/list")
    public ApiResponse<List<RoleVO>> list() {
        return ApiResponse.success(roleService.listAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<RoleVO> get(@PathVariable Long id) {
        RoleVO vo = roleService.getById(id);
        if (vo == null) return ApiResponse.error("角色不存在");
        return ApiResponse.success(vo);
    }

    @PostMapping("/create")
    public ApiResponse<Void> create(@RequestBody Map<String, Object> body) {
        String name = (String) body.get("name");
        String description = (String) body.get("description");
        @SuppressWarnings("unchecked")
        List<String> permissions = (List<String>) body.get("permissions");
        @SuppressWarnings("unchecked")
        List<Long> menuIds = parseMenuIds(body.get("menuIds"));
        boolean ok = roleService.create(name, description, permissions, menuIds);
        return ok ? ApiResponse.success() : ApiResponse.error("创建失败（名称可能已存在）");
    }

    @PostMapping("/update")
    public ApiResponse<Void> update(@RequestBody Map<String, Object> body) {
        Long id = Long.valueOf(body.get("id").toString());
        String name = (String) body.get("name");
        String description = (String) body.get("description");
        @SuppressWarnings("unchecked")
        List<String> permissions = (List<String>) body.get("permissions");
        @SuppressWarnings("unchecked")
        List<Long> menuIds = parseMenuIds(body.get("menuIds"));
        boolean ok = roleService.update(id, name, description, permissions, menuIds);
        return ok ? ApiResponse.success() : ApiResponse.error("更新失败");
    }

    @PostMapping("/delete/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        boolean ok = roleService.delete(id);
        return ok ? ApiResponse.success() : ApiResponse.error("删除失败");
    }
    private static List<Long> parseMenuIds(Object value) {
        if (value == null) { return null; }
        List<Long> ids = new java.util.ArrayList<>();
        for (Object id : (List<?>) value) { ids.add(Long.valueOf(id.toString())); }
        return ids;
    }
}
