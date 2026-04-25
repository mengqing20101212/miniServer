package ly;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 项目统一日志入口。
 * <p>
 * 按系统、数据库、网络和协议拆分 logger 名称，便于 logback/log4j 按模块落盘和过滤。
 */
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
