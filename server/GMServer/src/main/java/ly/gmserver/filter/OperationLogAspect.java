package ly.gmserver.filter;

import jakarta.servlet.http.HttpServletRequest;
import ly.gmserver.entity.GmOperationLogEntry;
import ly.gmserver.entity.GmOperationLogHelper;
import ly.gmserver.service.GmAdminService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class OperationLogAspect {
    private static final Logger log = LoggerFactory.getLogger(OperationLogAspect.class);

    private final GmAdminService adminService;

    public OperationLogAspect(GmAdminService adminService) {
        this.adminService = adminService;
    }

    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping) || "
          + "@annotation(org.springframework.web.bind.annotation.PostMapping) || "
          + "@annotation(org.springframework.web.bind.annotation.GetMapping) || "
          + "@annotation(org.springframework.web.bind.annotation.PutMapping) || "
          + "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public Object logOperation(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String result = "SUCCESS";
        Object response;
        try {
            response = joinPoint.proceed();
        } catch (Exception e) {
            result = "FAIL";
            throw e;
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            try {
                ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (attributes != null) {
                    HttpServletRequest request = attributes.getRequest();
                    String path = request.getRequestURI();

                    // Skip login endpoint to avoid recursion
                    if (path.contains("/api/admin/login") || path.contains("/gm/")) {
                        return;
                    }

                    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
                    String className = signature.getDeclaringType().getSimpleName();
                    String methodName = signature.getName();
                    String action = className + "." + methodName;
                    String detail = String.format("args=%s, duration=%dms",
                        Arrays.toString(joinPoint.getArgs()), duration);

                    String ip = getClientIp(request);

                    // Get current admin info from security context
                    Long adminId = adminService.getCurrentAdminId();
                    String username = adminService.getCurrentUsername();

                    if (adminId != null) {
                        GmOperationLogEntry logEntry = new GmOperationLogEntry();
                        logEntry.setAdminId(adminId);
                        logEntry.setUsername(username != null ? username : "unknown");
                        logEntry.setAction(action);
                        logEntry.setTargetType(extractTargetType(path));
                        logEntry.setTargetId(extractTargetId(path));
                        logEntry.setDetail(detail);
                        logEntry.setIp(ip);
                        logEntry.setResult(result);
                        logEntry.setCreatedAt(LocalDateTime.now());
                        GmOperationLogHelper.asyncSave(logEntry);
                    }
                }
            } catch (Exception e) {
                log.error("Failed to save operation log", e);
            }
        }
        return response;
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }

    private String extractTargetType(String path) {
        String[] parts = path.split("/");
        if (parts.length >= 3) {
            return parts[2]; // e.g., /api/player/query -> player
        }
        return "";
    }

    private String extractTargetId(String path) {
        String[] parts = path.split("/");
        if (parts.length >= 4 && !parts[3].matches("\\d+")) {
            // Try to extract from query or params
            return "";
        }
        return parts.length >= 4 ? parts[3] : "";
    }
}
