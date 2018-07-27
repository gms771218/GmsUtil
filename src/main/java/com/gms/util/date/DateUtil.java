package com.gms.util.date;

import android.util.Log;

import com.gms.util.date.model.WeekVO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by gms on 2017/10/20.
 */

public class DateUtil {

    static final String TAG = "DateUtil";

    public enum DateType {
        YYYYMMDDTHHMMSS("yyyy-MM-dd HH:mm:ss"), YYYYMMDD("yyyy-MM-dd");
        String format;

        DateType(String format) {
            this.format = format;
        }

        public String getFormat() {
            return format;
        }
    }

    final static String DEFAULT_SIGN = ":";

    /**
     * @param value
     * @return 沒有小時
     * 00:00
     * 有小時
     * 00:00:00
     */
    public static String millisecondToHMS(long value) {
        // 毫秒轉換成秒數
        value = (int) (value / 1000);
        int second = (int) (value % 60);
        int minute = (int) (value / 60) % 60;
        int hour = (int) (value / 3600) % 24;
        return hour == 0 ? String.format("%02d:%02d", minute, second) : String.format("%02d:%02d:%02d", hour, minute, second);
    }

    public static String millisecondToHMS(long value, String... signs) {

        if (signs.length == 0)
            signs = new String[]{DEFAULT_SIGN, DEFAULT_SIGN , DEFAULT_SIGN};

        // 毫秒轉換成秒數
        value = (int) (value / 1000);
        int second = (int) (value % 60);
        int minute = (int) (value / 60) % 60;
        int hour = (int) (value / 3600) % 24;
        return hour == 0 ? String.format("%02d%s%02d%s", minute, signs[1], second , signs[2]) : String.format("%02d%s%02d%s%02d%s", hour, signs[0], minute, signs[1], second,signs[2]);
    }

    public static String countMillisecondToHMS(long value, String... signs) {

        if (signs.length == 0)
            signs = new String[]{DEFAULT_SIGN, DEFAULT_SIGN , DEFAULT_SIGN};

        // 毫秒轉換成秒數
        value = (int) (value / 1000);
        int second = (int) (value % 60);
        int minute = (int) (value / 60) % 60;
        int hour = (int) (value / 3600) ;
        return hour == 0 ? String.format("%02d%s%02d%s", minute, signs[1], second , signs[2]) : String.format("%02d%s%02d%s%02d%s", hour, signs[0], minute, signs[1], second,signs[2]);
    }


    public static String getDate(DateType dateType, long time) {

        SimpleDateFormat formatter = null;
        String dateStr = "";
        Date date = new Date(time);
        switch (dateType) {
            case YYYYMMDDTHHMMSS:
                formatter = new SimpleDateFormat(dateType.YYYYMMDDTHHMMSS.getFormat());
                dateStr = formatter.format(date);
                break;
            case YYYYMMDD:
                formatter = new SimpleDateFormat(dateType.YYYYMMDD.getFormat());
                dateStr = formatter.format(date);
                break;
        }
        return dateStr;
    }

    public static String getDate(DateType dateType, Calendar calendar) {

        if (calendar == null)
            return "";
        String dateStr = "";
        String yyyy, MM, dd;
        switch (dateType) {
            case YYYYMMDDTHHMMSS:
                yyyy = String.format("%04d", calendar.get(Calendar.YEAR));
                MM = String.format("%02d", calendar.get(Calendar.MONTH) + 1);
                dd = String.format("%02d", calendar.get(Calendar.DATE));
                String hh = String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY));
                String mm = String.format("%02d", calendar.get(Calendar.MINUTE));
                String ss = String.format("%02d", calendar.get(Calendar.SECOND));
                dateStr = String.format("%s/%s/%s %s:%s:%s", yyyy, MM, dd, hh, mm, ss);
                break;
            case YYYYMMDD:
                yyyy = String.format("%04d", calendar.get(Calendar.YEAR));
                MM = String.format("%02d", calendar.get(Calendar.MONTH) + 1);
                dd = String.format("%02d", calendar.get(Calendar.DATE));
                dateStr = String.format("%s/%s/%s", yyyy, MM, dd);
                break;
        }
        return dateStr;
    }


    public static Calendar getCalendar(String dateString) {
        // "2017-03-23T17:00:00+0800"
        String[] dateFormatTypes = new String[]{"yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd", "yyyy/MM/dd"};
        Date date = null;
        Calendar calendar = null;
        DateFormat dateformat ;

        boolean isFormatSuccess = false;
        for (int i = 0; i < dateFormatTypes.length; i++) {
            try {
                dateformat = new SimpleDateFormat(dateFormatTypes[i]);
                date = dateformat.parse(dateString);
                isFormatSuccess = true;
                break;
            } catch (ParseException e) {
                e.printStackTrace();
                isFormatSuccess = false;
            }
        }

        if (!isFormatSuccess)
            return null;

        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * MM/dd
     *
     * @param dateString
     * @return
     */
    public static String getMonth_Date(String dateString) {
        if (dateString == null)
            return "";
        Calendar calendar = getCalendar(dateString);
        String mm = String.format("%02d", calendar.get(Calendar.MONTH) + 1);
        String dd = String.format("%02d", calendar.get(Calendar.DATE));
        return String.format("%s/%s", mm, dd);
    }

    /**
     * 限三倍德使用,因為週的初始值為週一
     *
     * @param calendar
     * @return
     */
    public static WeekVO getWeekVO(Calendar calendar) {
        // 取得當下第幾週時間
        if (checkDayOfWeek(calendar, Calendar.SUNDAY)) {
            calendar.add(Calendar.DATE, -1);
        }
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        int year = calendar.get(Calendar.YEAR);

        // 計算初始值
        int difference = calendar.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
        calendar.add(Calendar.DATE, -difference);

        WeekVO weekVO = new WeekVO(year, weekOfYear);
        WeekVO.Day day;
        int y, m, d;
        for (int i = 0; i < 7; i++) {
            y = calendar.get(Calendar.YEAR);
            m = calendar.get(Calendar.MONTH) + 1;
            d = calendar.get(Calendar.DATE);
            day = new WeekVO.Day(y, m, d);
            weekVO.add(day);
            calendar.add(Calendar.DATE, 1);
        }

        return weekVO;
    }

    public static WeekVO getCurrentWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return getWeekVO(calendar);
    }

    public static WeekVO getPreWeekVO(Calendar calendar) {
        calendar.add(Calendar.DATE, -7);
        return getWeekVO(calendar);
    }

    public static WeekVO getNextWeekVO(Calendar calendar) {
        calendar.add(Calendar.DATE, 7);
        return getWeekVO(calendar);
    }


    private static boolean checkDayOfWeek(Calendar calendar, int day) {
        return calendar.get(Calendar.DAY_OF_WEEK) == day;
    }


} // class close

