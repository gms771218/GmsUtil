package com.gms.util.date.model;

import android.util.SparseArray;

/**
 * Created by gms on 2018/3/5.
 */

public class WeekVO {

    SparseArray<Day> daySparseArray;

    // 年
    int year;
    // 第幾週
    int weekOhfYear;


    public WeekVO(int year, int weekOhfYear) {
        this.year = year;
        this.weekOhfYear = weekOhfYear;
        daySparseArray = new SparseArray<Day>();
    }

    public void add(Day day) {
        daySparseArray.put(daySparseArray.size(), day);
    }


    public Day[] getDays() {
        Day[] days = new Day[daySparseArray.size()];
        for (int i = 0; i < days.length; i++) {
            days[i] = daySparseArray.get(i);
        }
        return days;
    }

    public int getYear() {
        return year;
    }

    public int getWeekOhfYear() {
        return weekOhfYear;
    }

    public static class Day {
        int year;
        int month;
        int day;

        public Day(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }

        public int getYear() {
            return year;
        }

        public int getMonth() {
            return month;
        }

        public int getDay() {
            return day;
        }

        public String getDayString(){
            return String.format("%04d-%02d-%02d" , year , month , day ) ;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Day)) return false;

            Day day1 = (Day) o;

            if (year != day1.year) return false;
            if (month != day1.month) return false;
            return day == day1.day;
        }

        @Override
        public int hashCode() {
            int result = year;
            result = 31 * result + month;
            result = 31 * result + day;
            return result;
        }

        @Override
        public String toString() {
            return "Day{" +
                    "year=" + year +
                    ", month=" + month +
                    ", day=" + day +
                    '}';
        }
    }
} // class close
