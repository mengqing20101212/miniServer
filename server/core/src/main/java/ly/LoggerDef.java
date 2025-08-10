package ly;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerDef {

    public final static Logger SystemLogger = LoggerFactory.getLogger("systemLogger");
    public final static Logger DbLogger = LoggerFactory.getLogger("DbLogger");
    public final static Logger NetLogger = LoggerFactory.getLogger("netLogger");
    public final static Logger ProtoLogger = LoggerFactory.getLogger("protoLogger");

    public static void LogProto(String msg, Object... args) {
        ProtoLogger.info(msg, args);
    }

    public static void LogNet(String msg, Object... args) {
        NetLogger.info(msg, args);
    }

    public static void LogSystem(String msg, Object... args) {
        SystemLogger.info(msg, args);
    }

    public static void LogDb(String msg, Object... args) {
        DbLogger.info(msg, args);
    }


}
