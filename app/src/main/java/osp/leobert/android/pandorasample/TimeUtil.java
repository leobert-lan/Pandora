/*
 * MIT License
 *
 * Copyright (c) 2018 leobert-lan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package osp.leobert.android.pandorasample;

import android.support.annotation.IntRange;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class TimeUtil {
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
}
