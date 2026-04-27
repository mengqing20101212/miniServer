package ly.gmserver.controller;

import ly.gmserver.dto.ApiResponse;
import ly.gmserver.dto.MenuVO;
import ly.gmserver.service.GmMenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ApiResponse<Void> create(@RequestParam String name,
                                     @RequestParam(required = false) String permission,
                                     @RequestParam(required = false, defaultValue = "0") Integer parentId,
                                     @RequestParam(required = false) String path,
                                     @RequestParam(required = false) String icon,
                                     @RequestParam(required = false) Integer sortOrder) {
        boolean ok = menuService.create(name, permission, parentId, path, icon, sortOrder);
        return ok ? ApiResponse.success() : ApiResponse.error("创建失败");
    }

    @PostMapping("/update")
    public ApiResponse<Void> update(@RequestParam Integer id,
                                     @RequestParam(required = false) String name,
                                     @RequestParam(required = false) String permission,
                                     @RequestParam(required = false) Integer parentId,
                                     @RequestParam(required = false) String path,
                                     @RequestParam(required = false) String icon,
                                     @RequestParam(required = false) Integer sortOrder) {
        boolean ok = menuService.update(id, name, permission, parentId, path, icon, sortOrder);
        return ok ? ApiResponse.success() : ApiResponse.error("更新失败");
    }

    @PostMapping("/delete/{id}")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        boolean ok = menuService.delete(id);
        return ok ? ApiResponse.success() : ApiResponse.error("删除失败（请先删除子菜单）");
    }
}
