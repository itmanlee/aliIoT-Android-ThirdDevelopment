package com.aliIoT.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.aliIoT.demo.R;

public class TimeUtils {
    public static final int TWO_MINUTES = 120;

    /**
     * 计算time1与time2的时间差值时间格式为 "yyyy-MM-dd HH:mm:ss"
     *
     * @param time1 时间1
     * @param time2 时间2
     * @return 返回差值单位秒
     */
    public static long CalculateTimeDifference(String time1, String time2) throws ParseException {
        long mDifference = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = format.parse(time1);
        Date date2 = format.parse(time2);
        mDifference = (date1.getTime() - date2.getTime()) / 1000;
        return mDifference;
    }

    /**
     * 判断日期与今天的关系
     *
     * @param oldTime
     * @return
     * @throws ParseException
     */
    public static int isYeaterday(String oldTime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = format.parse(oldTime);
        Date date2 = new Date();
        return getDayDiffer(date1, date2);
    }

    public static int isYeaterday2(String oldTime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = format.parse(oldTime);
        Date date2 = new Date();
        return getDayDiffer(date1, date2);
    }

    public static int getDayDiffer(Date startDate, Date endDate) throws ParseException {
        //判断是否跨年
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        String startYear = yearFormat.format(startDate);
        String endYear = yearFormat.format(endDate);
        if (startYear.equals(endYear)) {
            /*  使用Calendar跨年的情况会出现问题    */
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            int startDay = calendar.get(Calendar.DAY_OF_YEAR);
            calendar.setTime(endDate);
            int endDay = calendar.get(Calendar.DAY_OF_YEAR);
            return endDay - startDay;
        } else {
            /*  跨年不会出现问题，需要注意不满24小时情况（2016-03-18 11:59:59 和 2016-03-19 00:00:01的话差值为 0）  */
            //  只格式化日期，消除不满24小时影响
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            long startDateTime = dateFormat.parse(dateFormat.format(startDate)).getTime();
            long endDateTime = dateFormat.parse(dateFormat.format(endDate)).getTime();
            return (int) ((endDateTime - startDateTime) / (1000 * 3600 * 24));
        }
    }

    public static String olnyNoShowss(String string) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = format.parse(string);
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:");
        return format.format(date1);
    }

    public static String olnyShowHhmmss(String string) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = format.parse(string);
        format = new SimpleDateFormat("HH:mm");
        return format.format(date1);
    }

    public static String getNowTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    public static String getNowTimeDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }

    public static String msecToString(long l) {
        Date date = new Date();
        date.setTime(l);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static String msecToYearMonthDayWeek(long l) {
        Date date = new Date();
        date.setTime(l);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String s = format.format(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        s = s + "  " + intToWeek(week);
        return s;
    }

    public static String intToWeek(int i) {
        String s = "";
        switch (i) {
            case 0: {
                s = MyApplication.getInstance().getResources().getString(R.string.sun_chinese);
                break;
            }
            case 1: {
                s = MyApplication.getInstance().getResources().getString(R.string.mon_chinese);
                break;
            }
            case 2: {
                s = MyApplication.getInstance().getResources().getString(R.string.tue_chinese);
                break;
            }
            case 3: {
                s = MyApplication.getInstance().getResources().getString(R.string.wed_chinese);
                break;
            }
            case 4: {
                s = MyApplication.getInstance().getResources().getString(R.string.thu_chinese);
                break;
            }
            case 5: {
                s = MyApplication.getInstance().getResources().getString(R.string.fri_chinese);
                break;
            }
            case 6: {
                s = MyApplication.getInstance().getResources().getString(R.string.sat_chinese);
                break;
            }
        }
        return s;
    }

    public static String intToWeek2(int i) {
        String s = "";
        switch (i) {
            case 6: {
                s = MyApplication.getInstance().getResources().getString(R.string.sun_chinese);
                break;
            }
            case 0: {
                s = MyApplication.getInstance().getResources().getString(R.string.mon_chinese);
                break;
            }
            case 1: {
                s = MyApplication.getInstance().getResources().getString(R.string.tue_chinese);
                break;
            }
            case 2: {
                s = MyApplication.getInstance().getResources().getString(R.string.wed_chinese);
                break;
            }
            case 3: {
                s = MyApplication.getInstance().getResources().getString(R.string.thu_chinese);
                break;
            }
            case 4: {
                s = MyApplication.getInstance().getResources().getString(R.string.fri_chinese);
                break;
            }
            case 5: {
                s = MyApplication.getInstance().getResources().getString(R.string.sat_chinese);
                break;
            }
        }
        return s;
    }

    public static int stringToWeek2(String s) {
        int i = 0;
        if (MyApplication.getInstance().getResources().getString(R.string.sun_chinese).equals(s)) {
            i = 6;
        } else if (MyApplication.getInstance().getResources().getString(R.string.mon_chinese).equals(s)) {
            i = 0;
        } else if (MyApplication.getInstance().getResources().getString(R.string.tue_chinese).equals(s)) {
            i = 1;
        } else if (MyApplication.getInstance().getResources().getString(R.string.wed_chinese).equals(s)) {
            i = 2;
        } else if (MyApplication.getInstance().getResources().getString(R.string.thu_chinese).equals(s)) {
            i = 3;
        } else if (MyApplication.getInstance().getResources().getString(R.string.fri_chinese).equals(s)) {
            i = 4;
        } else if (MyApplication.getInstance().getResources().getString(R.string.sat_chinese).equals(s)) {
            i = 5;
        }

        return i;
    }

    public static boolean isLeapYear() {
        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        System.out.println("今年是 " + y + "年");
        // 判断是否为闰年
        if (y % 4 == 0 && y % 100 != 0) {
            return true;
        } else {
            return false;
        }
    }

    public static List<String> creatDayList(int month) {
        ArrayList<String> list = new ArrayList<>();
        int i = 30;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                i = 31;
                break;
            case 2: {
                if (isLeapYear()) {
                    i = 29;
                } else {
                    i = 28;
                }
                break;
            }
        }
        for (int j = 1; j <= i; j++) {
            list.add(j + "");
        }
        return list;
    }

    public static List<String> creatHourList() {
        ArrayList<String> list = new ArrayList<>();
        for (int j = 0; j < 24; j++) {
            list.add(j + "");
        }
        return list;
    }

    public static List<String> creatWeekList() {
        String[] stringArray = MyApplication.getInstance().getResources().getStringArray(R.array.week_array2);
        List<String> list = Arrays.asList(stringArray);
        return list;
    }

    public static List<String> creaMintueList() {
        ArrayList<String> list = new ArrayList<>();
        for (int j = 0; j < 60; j++) {
            list.add(j + "");
        }
        return list;
    }

    public static List<String> creaWeekList() {
        ArrayList<String> list = new ArrayList<>();
        for (int j = 1; j < 6; j++) {
            list.add(j + "");
        }
        return list;
    }

    /**
     * @param m
     * @return yyyy-mm-dd
     */
    public static String millisecondToDate(long m) {
        Date date2 = new Date();
        date2.setTime(m);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//24小时制
        String format = simpleDateFormat.format(date2);
        return format;
    }

    public static String secondToDate(long m) {
        Date date2 = new Date();
        date2.setTime(m * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//24小时制
        String format = simpleDateFormat.format(date2);
        return format;
    }


    public static String millisecondToTime(long m) {
        Date date2 = new Date();
        date2.setTime(m);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
        String format = simpleDateFormat.format(date2);
        return format;
    }

    public static long dateToMillisecond(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//24小时制
        try {
            long time2 = simpleDateFormat.parse(date).getTime();
            return time2;
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static long dayToMillisecond(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
        try {
            long time2 = simpleDateFormat.parse(date).getTime();
            return time2;
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
