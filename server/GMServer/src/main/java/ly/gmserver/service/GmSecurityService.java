package ly.gmserver.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import ly.db.entry.SecurityBanEntry;
import ly.db.entry.SecurityBanEntryHelper;
import ly.db.entry.SecurityEventLogEntry;
import ly.db.entry.SecurityEventLogEntryHelper;
import ly.gmserver.dto.PageResult;
import ly.security.SecurityBanService;
import ly.security.SecurityBanType;
import org.springframework.stereotype.Service;

/**
 * GM 安全管理服务，负责封禁记录和安全事件的查询、创建与解除。
 */
@Service
public class GmSecurityService {

  public PageResult<SecurityBanEntry> listBans(
      int page, int pageSize, Integer banType, String target, Integer status) {
    List<SecurityBanEntry> all = SecurityBanEntryHelper.selectAll();
    List<SecurityBanEntry> filtered = new ArrayList<>();
    String targetKeyword = target == null ? "" : target.trim();
    for (SecurityBanEntry entry : all) {
      if (entry == null) {
        continue;
      }
      if (banType != null && !banType.equals(entry.getBanType())) {
        continue;
      }
      if (status != null && !status.equals(entry.getStatus())) {
        continue;
      }
      if (!targetKeyword.isEmpty()
          && (entry.getTarget() == null || !entry.getTarget().contains(targetKeyword))) {
        continue;
      }
      filtered.add(entry);
    }
    filtered.sort(
        Comparator.comparing(SecurityBanEntry::getId, Comparator.nullsLast(Long::compareTo))
            .reversed());
    return page(filtered, page, pageSize);
  }

  public boolean createBan(Map<String, Object> body, String operator) {
    SecurityBanType type = SecurityBanType.byCode(toInt(body.get("banType")));
    String target = toString(body.get("target"));
    String reason = toString(body.get("reason"));
    LocalDateTime endTime = parseEndTime(toString(body.get("endTime")));
    return SecurityBanService.getInstance()
        .createBan(type, target, reason, SecurityBanService.SOURCE_GM, operator, endTime);
  }

  public boolean releaseBan(Map<String, Object> body, String operator) {
    SecurityBanType type = SecurityBanType.byCode(toInt(body.get("banType")));
    String target = toString(body.get("target"));
    return SecurityBanService.getInstance().releaseBan(type, target, operator);
  }

  public PageResult<SecurityEventLogEntry> listEvents(
      int page, int pageSize, Integer eventType, String account, Long playerId) {
    List<SecurityEventLogEntry> all = SecurityEventLogEntryHelper.selectAll();
    List<SecurityEventLogEntry> filtered = new ArrayList<>();
    String accountKeyword = account == null ? "" : account.trim();
    for (SecurityEventLogEntry entry : all) {
      if (entry == null) {
        continue;
      }
      if (eventType != null && !eventType.equals(entry.getEventType())) {
        continue;
      }
      if (playerId != null && !playerId.equals(entry.getPlayerId())) {
        continue;
      }
      if (!accountKeyword.isEmpty()
          && (entry.getAccount() == null || !entry.getAccount().contains(accountKeyword))) {
        continue;
      }
      filtered.add(entry);
    }
    filtered.sort(
        Comparator.comparing(SecurityEventLogEntry::getId, Comparator.nullsLast(Long::compareTo))
            .reversed());
    return page(filtered, page, pageSize);
  }

  private <T> PageResult<T> page(List<T> items, int page, int pageSize) {
    int safePage = Math.max(page, 1);
    int safePageSize = Math.max(pageSize, 1);
    int from = Math.min((safePage - 1) * safePageSize, items.size());
    int to = Math.min(from + safePageSize, items.size());
    return new PageResult<>(items.subList(from, to), items.size());
  }

  private Integer toInt(Object value) {
    if (value == null || value.toString().isBlank()) {
      return null;
    }
    return Integer.valueOf(value.toString());
  }

  private String toString(Object value) {
    return value == null ? "" : value.toString().trim();
  }

  private LocalDateTime parseEndTime(String value) {
    if (value == null || value.isBlank()) {
      return null;
    }
    return LocalDateTime.parse(value);
  }
}
