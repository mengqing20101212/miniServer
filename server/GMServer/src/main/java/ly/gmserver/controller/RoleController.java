package ly.gmserver.controller;

import ly.gmserver.dto.ApiResponse;
import ly.gmserver.dto.RoleVO;
import ly.gmserver.service.GmRoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ApiResponse<RoleVO> get(@PathVariable Integer id) {
        RoleVO vo = roleService.getById(id);
        if (vo == null) return ApiResponse.error("角色不存在");
        return ApiResponse.success(vo);
    }

    @PostMapping("/create")
    public ApiResponse<Void> create(@RequestParam String name,
                                     @RequestParam(required = false) String description,
                                     @RequestParam(required = false) List<String> permissions,
                                     @RequestParam(required = false) List<Integer> menuIds) {
        boolean ok = roleService.create(name, description, permissions, menuIds);
        return ok ? ApiResponse.success() : ApiResponse.error("创建失败（名称可能已存在）");
    }

    @PostMapping("/update")
    public ApiResponse<Void> update(@RequestParam Integer id,
                                     @RequestParam(required = false) String name,
                                     @RequestParam(required = false) String description,
                                     @RequestParam(required = false) List<String> permissions,
                                     @RequestParam(required = false) List<Integer> menuIds) {
        boolean ok = roleService.update(id, name, description, permissions, menuIds);
        return ok ? ApiResponse.success() : ApiResponse.error("更新失败");
    }

    @PostMapping("/delete/{id}")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        boolean ok = roleService.delete(id);
        return ok ? ApiResponse.success() : ApiResponse.error("删除失败");
    }
}
