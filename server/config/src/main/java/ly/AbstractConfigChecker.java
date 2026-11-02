package ly;

import java.util.List;
import org.slf4j.Logger;

public abstract class AbstractConfigChecker<T> {
  public abstract String getConfigFileName();
  public abstract List<ConfigColumnMeta> getExpectedColumns();
  public final void checkAfterParse(Logger logger, List<T> configs) throws ConfigLoadException {
    customCheck(logger, configs);
  }
  protected void customCheck(Logger logger, List<T> configs) throws ConfigLoadException {}
}
