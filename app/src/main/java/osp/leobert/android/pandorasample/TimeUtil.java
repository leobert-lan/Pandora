package osp.leobert.android.pandorasample;

import android.annotation.SuppressLint;
import android.support.annotation.IntRange;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by jibl on 2015/10/9.
 */
public class TimeUtil {
    private static DateFormat sFormatStyle1 = new SimpleDateFormat("hh:mm");
    private static DateFormat sFormatStyle2 = new SimpleDateFormat("yyyy-MM-dd");
    private static DateFormat sFormatStyle3 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static DateFormat sFormatStyle4 = new SimpleDateFormat("MM-dd HH:mm");
    private static DateFormat sFormatStyle5 = new SimpleDateFormat("yyyy年MM月dd日");
    private static DateFormat sFormatStyle6 = new SimpleDateFormat("yyyyMMdd");

    public static String formatHour(Date date) {
        return sFormatStyle1.format(date);
    }

    /**
     * yyyy-MM-dd
     */
    public static String formatDateStyle2(Date date) {
        return sFormatStyle2.format(date);
    }

    public static String formatDateStyle5(Date date) {
        return sFormatStyle5.format(date);
    }

    public static String formatDateStyle6(Date date) {
        return sFormatStyle6.format(date);
    }

    /**
     * 将秒数转换成00：00的形式
     *
     * @param seconds
     * @return
     */
    public static String formatTimeBySecond(int seconds) {
        String timeString = "";
        if (seconds > 60) {
            int second = seconds % 60;
            int minute = seconds / 60;
            if (minute > 60) {
                minute = minute % 60;
                int hour = minute / 60;
                timeString = hour > 9 ? "" + hour + ":" : "0" + hour + ":";
            }

            timeString += minute > 9 ? "" + minute + ":" : "0" + minute + ":";
            timeString += second > 9 ? "" + second : "0" + second;
        }
        return timeString;
    }

    public static String formatValidTimeBySecond(long seconds) {
        long min = seconds / 60;
        long sec = seconds % 60;
        if (min > 0) {
            return String.valueOf((min > 9 ? "" + min : "0" + min) + "分"
                    + (sec > 9 ? "" + sec : "0" + sec) + "秒");
        } else {
            if (min == 0 && sec > 0) {
                return String.valueOf((sec > 9 ? "" + sec : "0" + sec) + "秒");
            } else {
                return "";
            }
        }
    }

    public static String formatTimeStyle3(long time) {

        return sFormatStyle3.format(new Date(time));
    }

    /**
     * 将秒数转换成00:00或00:00:00的形式
     *
     * @param seconds
     * @return
     */
    public static String formatTime(long seconds) {
        StringBuilder timeBuilder = new StringBuilder();
        long hour = seconds / 3600;
        long surplusSec = seconds % 3600;
        long minute = surplusSec / 60;
        long second = surplusSec % 60;

        if (hour > 0) {
            if (hour < 10) {
                timeBuilder.append("0");
            }

            timeBuilder.append(hour).append(":");
        }

        if (minute < 10) {
            timeBuilder.append("0");
        }
        timeBuilder.append(minute).append(":");

        if (second < 10) {
            timeBuilder.append("0");
        }
        timeBuilder.append(second);

        return timeBuilder.toString();
    }

    /**
     * 设置每个阶段时间
     */
    private static final int seconds_of_1minute = 60;

    private static final int seconds_of_30minutes = 30 * 60;

    private static final int seconds_of_1hour = 60 * 60;

    private static final int seconds_of_1day = 24 * 60 * 60;

    private static final int seconds_of_3days = seconds_of_1day * 3;

    private static final int seconds_of_7days = seconds_of_1day * 7;

    private static final int seconds_of_15days = seconds_of_1day * 15;

    private static final int seconds_of_30days = seconds_of_1day * 30;

    private static final int seconds_of_6months = seconds_of_30days * 6;

    private static final int seconds_of_1year = seconds_of_30days * 12;

    /**
     * 格式化时间
     */
    public static String parseTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        /**获取当前时间*/
        Date curDate = new Date(System.currentTimeMillis());
        String dataStrNew = sdf.format(curDate);
        Date startTime = null;
        try {
            /**将时间转化成Date*/
            curDate = sdf.parse(dataStrNew);
            startTime = new Date(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        /**除以1000是为了转换成秒*/
        long between = (curDate.getTime() - startTime.getTime()) / 1000;
        int elapsedTime = (int) (between);
        if (elapsedTime < seconds_of_1minute) {

            return "刚刚";
        } else if (elapsedTime < seconds_of_30minutes) {

            return elapsedTime / seconds_of_1minute + "分钟前";
        } else if (elapsedTime < seconds_of_1hour) {

            return "半小时前";
        } else if (elapsedTime < seconds_of_1day) {

            return elapsedTime / seconds_of_1hour + "小时前";
        } else if (elapsedTime < seconds_of_3days) {

            return elapsedTime / seconds_of_1day + "天前";
        } else {
            return sFormatStyle4.format(startTime);
        }
        /*if (elapsedTime < seconds_of_15days) {

            return elapsedTime / seconds_of_1day + "天前";
        }
        if (elapsedTime < seconds_of_30days) {

            return "半个月前";
        }
        if (elapsedTime < seconds_of_6months) {

            return elapsedTime / seconds_of_30days + "月前";
        }
        if (elapsedTime < seconds_of_1year) {

            return "半年前";
        }
        if (elapsedTime >= seconds_of_1year) {

            return elapsedTime / seconds_of_1year + "年前";
        }*/
    }


    ///////////////////////////////////////////////////////////////////////////
    // new
    ///////////////////////////////////////////////////////////////////////////

    public static final int DAY_MILLIS = 1000 * 60 * 60 * 24;

    public static final int HOUR_MILLIS = 1000 * 60 * 60;

    public static final int MINUTE_MILLIS = 1000 * 60;

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd " +
            "HH:mm:ss", Locale.CHINA);
    public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd",
            Locale.CHINA);

    private TimeUtil() {
        throw new AssertionError();
    }

    /**
     * long time to string0
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @return
     */
    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }

    public static int getYear(long timestamp) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(timestamp);
        return calendar.get(Calendar.YEAR);
    }

    @IntRange(from = 1, to = 12)
    public static int getMonth(long timestamp) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(timestamp);
        /**
         * because JANUARY is 0
         * @see Calendar#JANUARY
         * */
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getDay(long timestamp) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(timestamp);
        return calendar.get(Calendar.DATE);
    }

    public static int getHour(long timestamp) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(timestamp);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute(long timestamp) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(timestamp);
        return calendar.get(Calendar.MINUTE);
    }

    public static int getSecond(long timestamp) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(timestamp);
        return calendar.get(Calendar.SECOND);
    }

    public static DateTransformer newDateTransformer(long millisTs) {
        return new DateTransformer(millisTs);
    }

    public static DateTransformer newDateTransformer() {
        return new DateTransformer(getCurrentTimeInLong());
    }

    public static DateTransformer newDateTransformer(int year, int month, int day) {
        return new DateTransformer(year, month, day);
    }

    public static class DateTransformer {
        private GregorianCalendar calendar;

        DateTransformer(long msTs) {
            calendar = new GregorianCalendar();
            calendar.setTimeInMillis(msTs);
        }

        DateTransformer(int year, @IntRange(from = 0,to = 11) int month,@IntRange(from = 1) int day) {
            calendar = new GregorianCalendar();
            calendar.set(year, month, day, 0, 0, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        }

        public int getYear() {
            return calendar.get(Calendar.YEAR);
        }

        public int getMonth() {
            //as we all know :int JANUARY = 0;
            return calendar.get(Calendar.MONTH) + 1;
        }

        public int getDay() {
            return calendar.get(Calendar.DATE);
        }

        public long getMillisInLong() {
            return calendar.getTimeInMillis();
        }

        public String getDefaultFormat() {
            String format = "%d-%d-%d";
            return String.format(format, getYear(), getMonth(), getDay());
        }

        public String getFormat(String f) {
            return String.format(Locale.CHINESE,f, getYear(), getMonth(), getDay());

        }
    }

    public static long getTodayBeginning() {
        long _c = getCurrentTimeInLong();
        int year = getYear(_c);

        //start from 0
        int month = getMonth(_c) - 1;
        int day = getDay(_c);
        return newDateTransformer(year, month, day).getMillisInLong();
    }

    public static long getDayBeginning(long millisStamp) {
        int year = getYear(millisStamp);

        //start from 0
        int month = getMonth(millisStamp) - 1;
        int day = getDay(millisStamp);
        return newDateTransformer(year, month, day).getMillisInLong();
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatDate(String date, String originFormat, String targetFormat) {
        String res;
        if (TextUtils.isEmpty(originFormat) || TextUtils.isEmpty(targetFormat))
            return date;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(originFormat);
        try {
            Date d = simpleDateFormat.parse(date);
            long ts = d.getTime();
            SimpleDateFormat target = new SimpleDateFormat(targetFormat);
            return getTime(ts, target);
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }


    /**
     * @param date
     * @param originFormat
     * @return time millis stamp
     */
    @SuppressLint("SimpleDateFormat")
    public static long parseData2Stamp(String date, String originFormat) {
        if (TextUtils.isEmpty(originFormat))
            return 0;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(originFormat);
        try {
            Date d = simpleDateFormat.parse(date);
            return d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }


}
