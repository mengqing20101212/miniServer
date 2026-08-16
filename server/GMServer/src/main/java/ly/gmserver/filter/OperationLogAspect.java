package ly.gmserver.filter;

import jakarta.servlet.http.HttpServletRequest;
import ly.db.entry.GmOperationLogEntry;
import ly.db.entry.GmOperationLogEntryHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class OperationLogAspect {
    private static final Logger log = LoggerFactory.getLogger(OperationLogAspect.class);

    @Around("execution(* ly.gmserver.controller.*.*(..))")
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
            String signatureName = joinPoint.getSignature().toShortString();
            log.info("AOP [{}] duration={}ms", signatureName, duration);
            try {
                ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (attributes != null) {
                    HttpServletRequest request = attributes.getRequest();
                    String path = request.getRequestURI();

                    // Skip login endpoint and static resources to avoid recursion
                    if (path.contains("/api/admin/login") || path.startsWith("/gm/")) {
                        // skip logging, fall through
                    } else if (path.startsWith("/api/")) {
                    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
                    String className = signature.getDeclaringType().getSimpleName();
                    String methodName = signature.getName();
                    String action = className + "." + methodName;
                    String detail = path.startsWith("/api/runtime-script")
                        ? String.format("runtime script request redacted, duration=%dms", duration)
                        : String.format("args=%s, duration=%dms",
                            Arrays.toString(joinPoint.getArgs()), duration);

                    String ip = getClientIp(request);

                    // Get current admin info from security context
                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                    Long adminId = null;
                    String username = null;
                    if (auth != null && auth.isAuthenticated()
                        && !"anonymousUser".equals(auth.getPrincipal())) {
                        Object principal = auth.getPrincipal();
                        if (principal instanceof Long) {
                            adminId = (Long) principal;
                        } else if (principal instanceof String) {
                            try { adminId = Long.valueOf((String) principal); } catch (Exception ignored) {}
                        }
                        Object creds = auth.getCredentials();
                        if (creds instanceof String) {
                            username = (String) creds;
                        }
                    }

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
                        GmOperationLogEntryHelper.asyncSave(logEntry);
                    }
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
            return parts[2];
        }
        return "";
    }

    private String extractTargetId(String path) {
        String[] parts = path.split("/");
        if (parts.length >= 4 && !parts[3].matches("\\d+")) {
            return "";
        }
        return parts.length >= 4 ? parts[3] : "";
    }
}
