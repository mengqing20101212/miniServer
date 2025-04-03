package ly;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class LoggerDef {

  public static Logger SystemLogger = (Logger) LogManager.getLogger("systemLogger");
  public static Logger DbLogger = (Logger) LogManager.getLogger("DbLogger");
  public static Logger NetLogger = (Logger) LogManager.getLogger("netLogger");
}
