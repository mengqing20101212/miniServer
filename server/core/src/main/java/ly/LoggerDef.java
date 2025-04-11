package ly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerDef {

  public static Logger SystemLogger = LoggerFactory.getLogger("systemLogger");
  public static Logger DbLogger = LoggerFactory.getLogger("DbLogger");
  public static Logger NetLogger = LoggerFactory.getLogger("netLogger");
}
