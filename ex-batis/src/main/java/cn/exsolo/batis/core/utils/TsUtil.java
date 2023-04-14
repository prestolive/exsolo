package cn.exsolo.batis.core.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * FIXME sdf线程安全问题
 * @author prestolive
 */
public class TsUtil {

    private static SimpleDateFormat tsFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static SimpleDateFormat datetimeFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private static SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");

    private static SimpleDateFormat argDateTimeFmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public static String getTimestamp() {
        return tsFmt.format(Calendar.getInstance().getTime());
    }

    public static String getDateTimeAcc2Min() {
        return datetimeFmt.format(Calendar.getInstance().getTime());
    }

    public static String getExpireTimestamp( int dateType, int offset) {
        Calendar cal = Calendar.getInstance();
        cal.add(dateType, offset);
        return tsFmt.format(cal.getTime());
    }

    public static String getDate() {
        return dateFmt.format(Calendar.getInstance().getTime());
    }

    public static String formatDate(Date date) {
        return dateFmt.format(date);
    }

    public static Date parsetDate(String str) {
        try {
            return dateFmt.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static Date parsetTs(String str) {
        try {
            return tsFmt.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static boolean isTimeOverflow(String targetTs,Date compareDate,int dateType,int offset){
        Date targetDate = parsetTs(targetTs);
        Calendar cal= Calendar.getInstance();
        cal.setTime(targetDate);
        cal.add(dateType,offset);
        if(targetDate.compareTo(compareDate)>0){
            return true;
        }
        return false;
    }

}
