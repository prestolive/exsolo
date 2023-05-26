package cn.exsolo.comm.utils;


import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * FIXME sdf线程安全问题
 *
 * @author prestolive
 */
public class TsUtil {

    private static SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");

//    private static SimpleDateFormat argDateTimeFmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static DateTimeFormatter dateTimeMinFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public static String getTimestamp() {
        LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault());
        return time.format(dateTimeFormatter);
    }

    public static String getDateTimeAcc2Min() {
        LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault());
        return time.format(dateTimeMinFormatter);
    }

    public static String getExpireTimestamp(int dateType, int offset) {
        Calendar cal = Calendar.getInstance();
        cal.add(dateType, offset);
        LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(cal.getTimeInMillis()), ZoneId.systemDefault());
        return time.format(dateTimeFormatter);
    }

    public static String getDate() {
        LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault());
        return time.format(dateFormatter);
    }

    public static Date parseTs(String str) {
        LocalDateTime time = LocalDateTime.parse(str, dateTimeFormatter);
        Date date = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
        return date;
    }

    public static boolean isTimeOverflow(String targetTs, Date compareDate, int dateType, int offset) {
        Date targetDate = parseTs(targetTs);
        Calendar cal = Calendar.getInstance();
        cal.setTime(targetDate);
        cal.add(dateType, offset);
        if (targetDate.compareTo(compareDate) > 0) {
            return true;
        }
        return false;
    }

}
