package ly.utils;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;

/**
 * 公共工具类，提供时间、随机、位图、KV 解析或通用数据结构等辅助能力。
 */
public class TimeUtils {
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    public static long nowMillis() {
        return now().toInstant(ZoneId.systemDefault().getRules().getOffset(now())).toEpochMilli();
    }

    static int getCurWeekByMoth() {
        ZonedDateTime now = ZonedDateTime.now();
        // 指定每周的第一天为周一

        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return now.get(weekFields.weekOfMonth());
    }

    public static long getNextWeekBeginTimer() {
        // 当前时间（默认时区）
        ZonedDateTime now = ZonedDateTime.now();

        // 当前 Locale，默认是系统语言，可手动指定为 Locale.JAPAN、Locale.CHINA 等
        Locale locale = Locale.getDefault();

        // 获取当前地区的周起始日
        DayOfWeek firstDayOfWeek = WeekFields.of(locale).getFirstDayOfWeek();

        // 找到“下一个周的起始日”
        ZonedDateTime nextWeekStart = now
                .with(TemporalAdjusters.next(firstDayOfWeek)) // 下一个“周起始日”
                .truncatedTo(ChronoUnit.DAYS);               // 设置为 00:00:00

        return nextWeekStart.toInstant().toEpochMilli(); // 转为 UTC 时间戳（毫秒）
    }

    public static void main(String[] args) {
        int week = getCurWeekByMoth();
        System.out.println(week);
        long time = getNextWeekBeginTimer();
        System.out.println(time);
    }

    public static long getTimer(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
