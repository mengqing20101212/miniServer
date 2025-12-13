package ly.utils;

import ly.LoggerDef;

public class TimeStatisticsUtils {

    public static TimeStatisticsLog makeLogBegin(String callName, long maxCost) {
        return new TimeStatisticsLog(callName, maxCost);
    }

    public static TimeStatisticsLog makeLogBegin(String callName) {
        return new TimeStatisticsLog(callName);
    }

    public static class TimeStatisticsLog {
        final long begin;
        String callName;
        final long maxCost;
        long end;

        public TimeStatisticsLog(String callName) {
            this.callName = callName;
            this.maxCost = 1000;
            begin = TimeUtils.nowMillis();
        }

        public TimeStatisticsLog(String callName, long maxCost) {
            this.callName = callName;
            this.maxCost = maxCost;
            begin = TimeUtils.nowMillis();
        }

        public void LogEnd() {
            end = TimeUtils.nowMillis();
            long cost = end - begin;
            if (cost > maxCost) {
                LoggerDef.SystemLogger.warn("{} process too long cost:{} ms", callName, cost);
            }
        }
    }
}
