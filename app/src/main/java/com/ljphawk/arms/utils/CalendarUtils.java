package com.ljphawk.arms.utils;


import java.util.Calendar;
import java.util.Locale;

/*
 *@创建者       L_jp
 *@创建时间     2018/10/16 16:48.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
public class CalendarUtils {


    private Calendar mCalendar;

    public CalendarUtils(Calendar calendar) {
        mCalendar = calendar;
    }

    public CalendarUtils(String date) {
        mCalendar = Calendar.getInstance(Locale.CHINA);
        mCalendar.clear();
        String timeStamp = DateUtils.getTimeStamp(date, DateUtils.YMDHMS2);
        mCalendar.setTimeInMillis(Long.parseLong(timeStamp + "000"));
    }

    public Calendar getCalendar() {
        return mCalendar;
    }

    public CalendarUtils(long timeMillis) {
        mCalendar = Calendar.getInstance(Locale.CHINA);
        mCalendar.clear();
        mCalendar.setTimeInMillis(timeMillis);
    }


    public CalendarUtils() {
        mCalendar = Calendar.getInstance(Locale.CHINA);
        mCalendar.clear();
        mCalendar.setTimeInMillis(Long.parseLong(DateUtils.getTimeStamp() + "000"));
    }


    public int getYear() {
        return mCalendar.get(Calendar.YEAR);
    }

    public int getMoon() {
        return mCalendar.get(Calendar.MONTH);
    }

    public int getDate() {
        return mCalendar.get(Calendar.DAY_OF_MONTH);


    }

    public long getTimeMillis() {
        return mCalendar.getTimeInMillis() / 1000;
    }

    public int getWeeks() {
        return mCalendar.get(Calendar.DAY_OF_WEEK);
    }


}
