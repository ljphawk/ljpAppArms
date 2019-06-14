package com.ljphawk.arms.utils;



/*
 *@创建者       L_jp
 *@创建时间     2018/7/6 17:25.
 *@描述         ${""}
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述         ${""}
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

public class DateUtils {

    //获取当前的时间戳
    public static String getTimeStamp() {
        long time = System.currentTimeMillis() / 1000;//获取系统时间的10位的时间戳
        String str = String.valueOf(time);
        return str;
    }

    /**
     *
     * @return 返回固定的 xxxx-xx-xx
     */
    public static String getDate() {
        return getSdfTime(getTimeStamp(), YMDHMS2);
    }

    /**
     *
     * @param timeStamp1
     * @param timeStamp2
     * @return true  是否是同一天
     */
    public static boolean isToday(String timeStamp1, String timeStamp2) {

        try {
            int i = DateUtils.differentDays(timeStamp1, timeStamp2);//data2-data1
            if (Math.abs(i) == 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * date2比date1多的天数
     * @param timeStamp1
     * @param timeStamp2
     * @return
     */
    public static int differentDays(String timeStamp1, String timeStamp2) {
        try {
            Calendar cal1 = Calendar.getInstance(Locale.CHINA);
            cal1.setTimeInMillis(Long.parseLong(timeStamp1 + "000"));

            Calendar cal2 = Calendar.getInstance(Locale.CHINA);
            cal2.setTimeInMillis(Long.parseLong(timeStamp2 + "000"));
            int day1 = cal1.get(Calendar.DAY_OF_YEAR);
            int day2 = cal2.get(Calendar.DAY_OF_YEAR);

            int year1 = cal1.get(Calendar.YEAR);
            int year2 = cal2.get(Calendar.YEAR);

            if (year1 != year2) {//同一年

                int min = Math.min(year1, year2);
                int max = Math.max(year1, year2);


                int timeDistance = 0;
                for (int i = min; i < max; i++) {
                    if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {    //闰年
                        if (year2 > year1) {
                            timeDistance += 366;
                        } else {
                            timeDistance -= 366;
                        }
                    } else {    //不是闰年
                        if (year2 > year1) {
                            timeDistance += 365;
                        } else {
                            timeDistance -= 365;
                        }
                    }
                }
                return timeDistance + (day2 - day1);
            } else {  //不同年

                return day2 - day1;
            }
        } catch (Exception e) {
            return 0;
        }
    }


    public static final String YMDHMS1 = "yyyy-MM-dd HH:mm:ss";
    public static final String YMDHMS2 = "yyyy-MM-dd";
    public static final String YMDHMS3 = "HH:mm:ss";
    public static final String YMDHMS4 = "yyyy/MM/dd";
    public static final String YMDHMS5 = "yyyy.MM.dd";
    public static final String YMDHMS6 = "MMdd";
    public static final String YMDHMS7 = "yyyy年MM月dd日";
    public static final String YMDHMS8 = "yyyy年MM月";


    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014年06月14日16时09分00秒"）
     *  sdfForm "yyyy-MM-dd-HH-mm-ss"
     */
    public static String getSdfTime(String timeStamp, String sdfForm) {
        try {
            SimpleDateFormat sdr = new SimpleDateFormat(sdfForm);

            return sdr.format(new Date(Long.valueOf(timeStamp + "000")));
        } catch (Exception e) {
            return "";
        }
    }

    public static String getSdfTime(long timeStamp, String sdfForm) {
        return getSdfTime(timeStamp + "", sdfForm);
    }


    //返回时间戳
    public static String getTimeStamp(String time, String sdfForm) {
        SimpleDateFormat sdf = new SimpleDateFormat(sdfForm);

        String times = null;
        try {
            Date date = sdf.parse(time);
            times = (date.getTime() / 1000) + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }


    /**
     * 把毫秒转换成：1:20:30这里形式
     * @param timeMs
     * @return
     */
    public static String stringForTime(long timeMs) {
        try {
            StringBuilder mFormatBuilder = new StringBuilder();
            Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());

            long totalSeconds = timeMs / 1000;
            long seconds = totalSeconds % 60;


            long minutes = (totalSeconds / 60) % 60;


            long hours = totalSeconds / 3600;


            mFormatBuilder.setLength(0);
            if (hours > 0) {
                return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
            } else {
                return mFormatter.format("%02d:%02d", minutes, seconds).toString();
            }
        } catch (Exception e) {
            return "";
        }
    }

    /**
     *  格式化时间串00:00:00  毫秒
     */
    public static String formatUsToString2(long us) {
        if (us == 0) {
            return "00:00";
        }

        int second = (int) (us / 1000000.0 + 0.5);
        int hh = (int) (second / 3600 + 0.5);
        int mm = (int) (second % 3600 / 60 + 0.5);
        int ss = (int) (second % 60 + 0.5);
        String timeStr;

        if (hh > 0) {
            timeStr = String.format("%02d:%02d:%02d", hh, mm, ss);
        } else {
            timeStr = String.format("%02d:%02d", mm, ss);
        }
        return timeStr;
    }


    public static String changeCalendarWeek(int week) {
        String weeks;
        switch (week) {
            case 1:
                weeks = "周日";
                break;
            case 2:
                weeks = "周一";
                break;
            case 3:
                weeks = "周二";
                break;
            case 4:
                weeks = "周三";
                break;
            case 5:
                weeks = "周四";
                break;
            case 6:
                weeks = "周五";
                break;
            case 7:
                weeks = "周六";
                break;
            default:
                weeks = "";
                break;
        }
        return weeks;
    }

    /**
     *
     * @param date  2018-05-05 格式
     */
    public static String getEnglishMonth(String date) {
        CalendarUtils calendarUtils = new CalendarUtils(date);
        int moon = calendarUtils.getMoon();
        switch (moon) {
            case 0:
                return "Jan.";
            case 1:
                return "Feb.";
            case 2:
                return "Mar.";
            case 3:
                return "Apr.";
            case 4:
                return "May.";
            case 5:
                return "June.";
            case 6:
                return "July.";
            case 7:
                return "Aug.";
            case 8:
                return "Sept.";
            case 9:
                return "Oct.";
            case 10:
                return "Nov.";
            case 11:
                return "Dec.";
        }
        return "";
    }

    public static String getEnglishWeek(String date) {
        CalendarUtils calendarUtils = new CalendarUtils(date);
        int week = calendarUtils.getWeeks();
        String weeks;
        switch (week) {
            case 1:
                weeks = "Sun.";
                break;
            case 2:
                weeks = "Mon.";
                break;
            case 3:
                weeks = "Tues.";
                break;
            case 4:
                weeks = "Wed.";
                break;
            case 5:
                weeks = "Thurs.";
                break;
            case 6:
                weeks = "Fri.";
                break;
            case 7:
                weeks = "Sat.";
                break;
            default:
                weeks = "";
                break;
        }
        return weeks;
    }
}
