package ly.gmserver.controller;

import ly.gmserver.dto.ApiResponse;
import ly.gmserver.dto.MenuVO;
import ly.gmserver.service.GmMenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final GmMenuService menuService;

    public MenuController(GmMenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/tree")
    public ApiResponse<List<MenuVO>> tree() {
        return ApiResponse.success(menuService.listAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<MenuVO> get(@PathVariable Integer id) {
        MenuVO vo = menuService.getById(id);
        if (vo == null) return ApiResponse.error("菜单不存在");
        return ApiResponse.success(vo);
    }

    @PostMapping("/create")
    public ApiResponse<Void> create(@RequestBody Map<String, Object> body) {
        String name = (String) body.get("name");
        String permission = (String) body.get("permission");
        Integer parentId = body.get("parentId") != null ? Integer.valueOf(body.get("parentId").toString()) : 0;
        String path = (String) body.get("path");
        String icon = (String) body.get("icon");
        Integer sortOrder = body.get("sortOrder") != null ? Integer.valueOf(body.get("sortOrder").toString()) : 0;
        boolean ok = menuService.create(name, permission, parentId, path, icon, sortOrder);
        return ok ? ApiResponse.success() : ApiResponse.error("创建失败");
    }

    @PostMapping("/update")
    public ApiResponse<Void> update(@RequestBody Map<String, Object> body) {
        Integer id = Integer.valueOf(body.get("id").toString());
        String name = (String) body.get("name");
        String permission = (String) body.get("permission");
        Integer parentId = body.get("parentId") != null ? Integer.valueOf(body.get("parentId").toString()) : null;
        String path = (String) body.get("path");
        String icon = (String) body.get("icon");
        Integer sortOrder = body.get("sortOrder") != null ? Integer.valueOf(body.get("sortOrder").toString()) : null;
        boolean ok = menuService.update(id, name, permission, parentId, path, icon, sortOrder);
        return ok ? ApiResponse.success() : ApiResponse.error("更新失败");
    }

    @PostMapping("/delete/{id}")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        boolean ok = menuService.delete(id);
        return ok ? ApiResponse.success() : ApiResponse.error("删除失败（请先删除子菜单）");
    }
}
