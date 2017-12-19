package com.gms.util.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gms on 2017/10/20.
 */

public class DateUtil {

    public enum DateType {
        YYYYMMDDTHHMMSS("yyyy-MM-dd HH:mm:ss")
        , YYYYMMDD("yyyy-MM-dd");
        String format;

        DateType(String format) {
            this.format = format;
        }

        public String getFormat() {
            return format;
        }
    }

    /**
     *
     * @param value
     * @return
     *
     *  沒有小時
     *  00:00
     *  有小時
     *  00:00:00
     */
    public static String millisecondToHMS(long value) {
        // 毫秒轉換成秒數
        value = (int)(value / 1000) ;
        int second = (int) (value % 60);
        int minute = (int)(value / 60) % 60;
        int hour = (int)(value / 3600) % 24;
        return hour == 0 ? String.format("%02d:%02d", minute, second) : String.format("%02d:%02d:%02d", hour, minute, second);
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


}

