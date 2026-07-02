package ly.gmserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gm")
public class PageController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping({"", "/", "/index"})
    public String index() {
        return "index";
    }

    @GetMapping("/admin/list")
    public String adminList() {
        return "admin/list";
    }

    @GetMapping("/admin/add")
    public String adminAdd() {
        return "admin/form";
    }

    @GetMapping("/admin/edit/{id}")
    public String adminEdit() {
        return "admin/form";
    }

    @GetMapping("/role/list")
    public String roleList() {
        return "role/list";
    }

    @GetMapping("/role/add")
    public String roleAdd() {
        return "role/form";
    }

    @GetMapping("/role/edit/{id}")
    public String roleEdit() {
        return "role/form";
    }

    @GetMapping("/menu/list")
    public String menuList() {
        return "menu/list";
    }

    @GetMapping("/menu/add")
    public String menuAdd() {
        return "menu/form";
    }

    @GetMapping("/menu/edit/{id}")
    public String menuEdit() {
        return "menu/form";
    }

    @GetMapping("/log/list")
    public String logList() {
        return "log/list";
    }

    @GetMapping("/security/ban")
    public String securityBan() {
        return "security/ban";
    }

    @GetMapping("/security/event")
    public String securityEvent() {
        return "security/event";
    }

    @GetMapping("/config/hot-update")
    public String configHotUpdate() {
        return "config/hot-update";
    }

    @GetMapping("/player/detail")
    public String playerDetail() {
        return "player/detail";
    }

    @GetMapping("/admin/dashboard")
    public String dashboard() {
        return "admin/list";
    }

    @GetMapping("/admin/profile")
    public String profile() {
        return "admin/list";
    }
}
