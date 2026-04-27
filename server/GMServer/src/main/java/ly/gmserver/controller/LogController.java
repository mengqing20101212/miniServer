package ly.gmserver.controller;

import ly.db.entry.GmOperationLogEntry;
import ly.db.entry.GmOperationLogEntryHelper;
import ly.gmserver.dto.ApiResponse;
import ly.gmserver.dto.PageResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/log")
public class LogController {

    @GetMapping("/list")
    public ApiResponse<PageResult<GmOperationLogEntry>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String action,
            @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo) {

        List<GmOperationLogEntry> all = GmOperationLogEntryHelper.select(null);

        // sort by created_at desc
        all.sort((a, b) -> {
            if (a.getCreatedAt() == null || b.getCreatedAt() == null) return 0;
            return b.getCreatedAt().compareTo(a.getCreatedAt());
        });

        // filters
        if (username != null && !username.isEmpty()) {
            all = all.stream()
                .filter(l -> l.getUsername() != null && l.getUsername().contains(username))
                .collect(Collectors.toList());
        }
        if (action != null && !action.isEmpty()) {
            all = all.stream()
                .filter(l -> l.getAction() != null && l.getAction().contains(action))
                .collect(Collectors.toList());
        }
        if (dateFrom != null && !dateFrom.isEmpty()) {
            LocalDateTime from = LocalDateTime.parse(dateFrom + " 00:00:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            all = all.stream()
                .filter(l -> l.getCreatedAt() != null && !l.getCreatedAt().isBefore(from))
                .collect(Collectors.toList());
        }
        if (dateTo != null && !dateTo.isEmpty()) {
            LocalDateTime to = LocalDateTime.parse(dateTo + " 23:59:59",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            all = all.stream()
                .filter(l -> l.getCreatedAt() != null && !l.getCreatedAt().isAfter(to))
                .collect(Collectors.toList());
        }

        int total = all.size();
        int from = (page - 1) * pageSize;
        int to = Math.min(from + pageSize, total);
        List<GmOperationLogEntry> pageList = from < total ? all.subList(from, to) : new ArrayList<>();

        return ApiResponse.success(new PageResult<>(pageList, total));
    }
}
