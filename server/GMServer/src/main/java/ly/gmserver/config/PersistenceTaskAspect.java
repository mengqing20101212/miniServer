package ly.gmserver.config;

import ly.db.PersistenceScope;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/** Flush controller mutations before Spring serializes a successful response. */
@Aspect
@Component
public class PersistenceTaskAspect {
  @Around("within(@org.springframework.web.bind.annotation.RestController *)")
  public Object persistTask(ProceedingJoinPoint point) throws Throwable {
    try (PersistenceScope scope = PersistenceScope.open()) { return point.proceed(); }
  }
}
