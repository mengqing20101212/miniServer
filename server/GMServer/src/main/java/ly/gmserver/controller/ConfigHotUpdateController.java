package ly.gmserver.controller;

import ly.db.entry.GmConfigFileEntry;
import ly.db.entry.GmConfigServerStatusEntry;
import ly.db.entry.GmConfigVersionEntry;
import ly.gmserver.dto.ApiResponse;
import ly.gmserver.service.ConfigHotUpdateService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/config-hot-update")
public class ConfigHotUpdateController {
    private final ConfigHotUpdateService service;

    public ConfigHotUpdateController(ConfigHotUpdateService service) {
        this.service = service;
    }

    @GetMapping("/versions")
    public ApiResponse<List<GmConfigVersionEntry>> versions() {
        return ApiResponse.success(service.listVersions());
    }

    @GetMapping("/files/{version}")
    public ApiResponse<List<GmConfigFileEntry>> files(@PathVariable String version) {
        return ApiResponse.success(service.listFiles(version));
    }

    @GetMapping("/status/{version}")
    public ApiResponse<List<GmConfigServerStatusEntry>> status(@PathVariable String version) {
        return ApiResponse.success(service.listServerStatus(version));
    }

    @GetMapping("/download/{version}")
    public ApiResponse<List<Map<String, String>>> download(@PathVariable String version)
            throws Exception {
        return ApiResponse.success(service.download(version));
    }

    @PostMapping("/upload")
    public ApiResponse<List<Map<String, Object>>> upload(
            @RequestParam String version,
            @RequestParam(defaultValue = "gm") String operator,
            @RequestParam("files") MultipartFile[] files) {
        try {
            return ApiResponse.success(service.upload(version, files, operator));
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @PostMapping("/prepare")
    public ApiResponse<Void> prepare(
            @RequestParam String version,
            @RequestParam long switchAtMillis,
            @RequestParam java.util.Set<String> targetServerIds,
            @RequestParam(defaultValue = "gm") String operator) {
        try {
            service.prepare(version, switchAtMillis, operator, targetServerIds);
            return ApiResponse.success();
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @PostMapping("/commit")
    public ApiResponse<Void> commit(
            @RequestParam String version, @RequestParam(defaultValue = "gm") String operator) {
        try {
            service.commit(version, operator);
            return ApiResponse.success();
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @PostMapping("/cancel")
    public ApiResponse<Void> cancel(@RequestParam String version) throws Exception {
        service.cancel(version);
        return ApiResponse.success();
    }

    @PostMapping("/report")
    public ApiResponse<Void> report(
            @RequestParam String publishId,
            @RequestParam String version,
            @RequestParam String serverId,
            @RequestParam String serverType,
            @RequestParam String status,
            @RequestParam(defaultValue = "") String errorMsg,
            @RequestParam String bootId,
            @RequestParam String digest,
            @RequestParam long nodeTimeMillis)
            throws Exception {
        service.reportVerified(
                publishId,
                version,
                serverId,
                serverType,
                status,
                errorMsg,
                bootId,
                digest,
                nodeTimeMillis);
        return ApiResponse.success();
    }
}
