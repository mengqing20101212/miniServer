package ly;

import org.apache.logging.log4j.core.Logger;

public class ServerContext {
    private static final Logger logger = ly.LoggerDef.SystemLogger;



    public static  void startUp(){
        logger.info("SystemLogger Server started");
        ly.LoggerDef.NetLogger.info("NetLogger Server started");
    }
}
