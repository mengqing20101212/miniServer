package ly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerDef {

    public final static Logger SystemLogger = LoggerFactory.getLogger("systemLogger");
    public final static Logger DbLogger = LoggerFactory.getLogger("DbLogger");
    public final static Logger NetLogger = LoggerFactory.getLogger("netLogger");
}
